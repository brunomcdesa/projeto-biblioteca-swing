package biblioteca.backend.service;

import biblioteca.backend.client.OpenLibraryClient;
import biblioteca.backend.dao.contract.ILivroDAO;
import biblioteca.backend.dto.*;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.exceptions.ValidacaoException;
import biblioteca.backend.model.Autor;
import biblioteca.backend.model.Editora;
import biblioteca.backend.model.Livro;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static biblioteca.backend.dto.AutorRequest.converterDeOpenLibraryAutorResponses;
import static biblioteca.backend.dto.LivroRequest.converterDeOpenLibraryResponse;
import static biblioteca.utils.StringUtils.isBlank;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * Classe de serviço para Livro.
 * <p>
 * Esta classe é responsável por guardar a logica de validação e conversão dos dados,
 * e também é a classe que vai chamar as operações do banco de dados através do DAO.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
@RequiredArgsConstructor
public class LivroService {

    private static final String CABECALHO_ARQUIVO_IMPORTACAO = "TITULO;DATA_PUBLICACAO;ISBN;GENERO;NOME_EDITORA;CNPJ_EDITORA;NOME_AUTOR;DATA_NASCIMENTO_AUTOR;DATA_MORTE_AUTOR;BIOGRAFIA_AUTOR";

    private final ILivroDAO livroDAO;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final OpenLibraryClient openLibraryClient;

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(LivroRequest livroRequest) {
        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores = this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByIdIn(livroRequest.getLivrosSelecionadosIds()));
        Livro novoLivro = Livro.montarLivro(livroRequest, editora, autores, livrosParecidos);

        livroDAO.salvar(novoLivro);
    }

    /**
     * Método responsável por converter os dados recebidos por parametro em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(LivroRequest livroRequest, Editora editora, Set<Autor> autores) {
        Livro novoLivro = Livro.montarLivro(livroRequest, editora, autores);
        livroDAO.salvar(novoLivro);
    }

    /**
     * Método responsável por converter os dados recebidos por parametro em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvarPorImportacao(LivroImportacaoDto livroImportacaoDto, Set<Autor> autores, Editora editora) {
        Livro novoLivro = Livro.montarLivroPorImportacao(livroImportacaoDto, editora, autores);
        livroDAO.salvar(novoLivro);
    }

    /**
     * Método responsável por listar todos os Livros do sistema.
     *
     * @return uma lista de Livros.
     */
    public List<LivroResponse> listarTodos() {
        return livroDAO.listarTodos().stream()
                .map(LivroResponse::converterDeLivro)
                .collect(toList());
    }

    /**
     * Método responsável por listar todos os Livros do sistema por filtros.
     *
     * @return uma lista de Livros de acordo com os filtros passados por parametro.
     */
    public List<LivroResponse> listarTodosPorFiltros(LivroFiltros filtros) {
        return livroDAO.listarTodosPorPredicate(filtros.toPredicate()).stream()
                .map(LivroResponse::converterDeLivro)
                .collect(toList());
    }

    /**
     * Método responsável por buscar os nomes das editoras do sistema, para ser utilizado em um campo Select.
     *
     * @return Uma lista de SelectResponse.
     */
    public List<SelectResponse> getSelectEditora() {
        return editoraService.buscarEditorasSelect();
    }

    /**
     * Método responsável por buscar os nomes dos Autores do sistema, para ser utilizado em um campo Multi Select.
     *
     * @return Uma lista de SelectResponse.
     */
    public List<SelectResponse> getSelectAutores() {
        return autorService.buscarAutoresSelect();
    }

    /**
     * Método responsável por editar um livro específico, de acordo com os novos dados da request.
     */
    public void editar(Integer id, LivroRequest livroRequest) {
        Livro livro = this.findById(id);

        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores = this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByIdIn(livroRequest.getLivrosSelecionadosIds()));

        livro.atualizarDados(livroRequest, editora, autores, livrosParecidos);

        livroDAO.salvar(livro);
    }

    /**
     * Método responsável por editar um livro específico, de acordo com os novos dados da request da importação.
     */
    public void editarPorImportacao(LivroImportacaoDto livroImportacaoDto, Set<Autor> autores, Editora editora) {
        Livro livro = this.findByIsbn(livroImportacaoDto.getIsbn());

        livro.atualizarDadosPorImportacao(livroImportacaoDto, editora, autores);

        livroDAO.salvar(livro);
    }

    /**
     * Método responsável por deletar um livro específico do banco de dados.
     */
    public void deletar(Integer id) {
        livroDAO.deletar(id);
    }

    /**
     * Método responsável por salvar um novo livro de acordo com o ISBN recebido.
     * <p>
     * Realiza uma busca dos dados do livro, da editora e dos autores, de acordo com os dados retornados da busca dos dados do livro por ISBN.
     *
     * @throws ValidacaoException caso já existir um livro cadastrado no sistema com o mesmo ISBN.
     */
    public void cadastrarLivroPorIsbn(String isbn) {
        if (livroDAO.existsByIsbn(isbn)) {
            throw new ValidacaoException("Já existe um livro com este ISBN no sistema.");
        }

        openLibraryClient.buscarLivroPorIsbn(isbn)
                .ifPresent(livro -> {
                    Set<Autor> autores = this.buscarAutoresPorKeys(livro.getKeysDosAutores());
                    Editora editora = this.buscarEditoraPorNome(livro.getEditoras());
                    LivroRequest livroRequest = converterDeOpenLibraryResponse(livro);

                    this.salvar(livroRequest, editora, autores);
                });
    }

    /**
     * Método responsável por buscar os livros para ser utilizado em campos Select.
     *
     * @return Uma lista de SelectResponse com dados dos livros.
     */
    public List<SelectResponse> getLivrosSelect(Integer id) {
        return livroDAO.listarTodos().stream()
                .filter(livro -> !Objects.equals(livro.getId(), id))
                .map(livro -> SelectResponse.montarSelectResponse(livro.getId(), livro.getTitulo()))
                .collect(toList());
    }

    /**
     * Método responsável por ler o arquivo de importação e realizar a importação dos dados de cada linha do arquivo em dados de um Livro.
     *
     * @throws ValidacaoException caso alguma linha do arquivo não possua os campos necessários para realizar a importação.
     */
    public void cadastrarLivroPorArquivo(File arquivo) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linhaCabecalho = bufferedReader.readLine();
            validarCabecalho(linhaCabecalho);

            bufferedReader.lines()
                    .filter(linha -> !linha.replace(";", "").trim().isEmpty())
                    .map(linha -> {
                        String[] camposLinha = linha.split(";", -1);
                        if (camposLinha.length != 10) {
                            throw new ValidacaoException(format("A linha %s não possui os campos necessários", linha));
                        }
                        return LivroImportacaoDto.converterDeArrayString(camposLinha);
                    })
                    .forEach(this::cadastrarLivroImportacao);
        } catch (IOException ex) {
            throw new ValidacaoException(format("Erro ao ler o arquivo: %s", ex.getMessage()));
        } catch (ValidacaoException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ValidacaoException(format("Ocorreu um erro inesperado ao processar o arquivo: %s", ex.getMessage()));
        }
    }

    /**
     * Método responsável por buscar um Livro pelo ID dele.
     * <p>
     * Caso não encontre nenhum Livro com o mesmo ID, será lançado uma excepion.
     *
     * @return um Livro.
     */
    private Livro findById(Integer id) {
        return livroDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Livro não encontrado."));
    }

    /**
     * Método responsável por buscar um Livro pelo ISBN dele.
     * <p>
     * Caso não encontre nenhum Livro com o mesmo ISBN, será lançado uma excepion.
     *
     * @return um Livro.
     */
    private Livro findByIsbn(String isbn) {
        return livroDAO.findByIsbn(isbn)
                .orElseThrow(() -> new NaoEncontradoException("Livro não encontrado."));
    }

    /**
     * Método responsável por buscar uma editora específica por ID.
     *
     * @return Uma Editora.
     */
    private Editora buscarEditoraPorId(Integer editoraId) {
        return editoraService.findById(editoraId);
    }

    /**
     * Método responsável por buscar autores específicos de acordo com os IDs.
     *
     * @return Um Set de Autores.
     */
    private Set<Autor> buscarAutoresPorIds(List<Integer> autoresIds) {
        return autorService.findByIdIn(autoresIds);
    }

    /**
     * Método responsável por buscar ou criar os autores específicos de acordo as keys deles.
     *
     * @return Um Set de Autores.
     */
    private Set<Autor> buscarAutoresPorKeys(List<String> keysDosAutores) {
        if (!keysDosAutores.isEmpty()) {
            List<AutorRequest> autoresRequests = converterDeOpenLibraryAutorResponses(openLibraryClient.buscarAutoresPorKeys(keysDosAutores));
            return autorService.buscarAutoresOuCriarAutores(autoresRequests);
        }
        return Collections.emptySet();
    }

    /**
     * Método responsável por buscar uma editora pelo nome.
     *
     * @return Uma Editora.
     */
    private Editora buscarEditoraPorNome(List<String> nomesEditoras) {
        return !nomesEditoras.isEmpty()
                ? editoraService.buscarEditoraOuCriarEditora(nomesEditoras.get(0))
                : null;
    }

    /**
     * Método responsável por validar o cabeçalho do arquivo de importação dos livros.
     *
     * @throws ValidacaoException caso a linha do cabeçalho seja uma linha vazia, ou se o cabeçalho informado não for o mesmo do padrão de importação.
     */
    private void validarCabecalho(String linha) {
        if (isBlank(linha)) {
            throw new ValidacaoException("O arquivo está vazio.");
        }
        if (!CABECALHO_ARQUIVO_IMPORTACAO.equalsIgnoreCase(linha)) {
            throw new ValidacaoException(format("O cabeçalho do arquivo é inválido ou está ausente. O formato esperado é: \n%s",
                    CABECALHO_ARQUIVO_IMPORTACAO));
        }
    }

    /**
     * Método responsável por efetuar o cadastro do livro ou a edição do livro no fluxo de importação.
     */
    private void cadastrarLivroImportacao(LivroImportacaoDto livroImportacaoDto) {
        AutorRequest autorRequest = AutorRequest.converterDeLivroImportacaoDto(livroImportacaoDto);
        Set<Autor> autores = new HashSet<>();
        autores.add(autorService.buscarAutorEEditarOuCriarAutor(autorRequest));
        EditoraRequest editoraRequest = new EditoraRequest(livroImportacaoDto.getNomeEditora(), livroImportacaoDto.getCnpjEditora());
        Editora editora = editoraService.buscarEEditarEditoraOuCriarEditora(editoraRequest);

        if (livroDAO.existsByIsbn(livroImportacaoDto.getIsbn())) {
            this.editarPorImportacao(livroImportacaoDto, autores, editora);
        } else {
            this.salvarPorImportacao(livroImportacaoDto, autores, editora);
        }
    }
}
