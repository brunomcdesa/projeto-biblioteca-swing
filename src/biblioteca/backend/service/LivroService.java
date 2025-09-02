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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static biblioteca.backend.dto.AutorRequest.converterDeOpenLibraryAutorResponses;
import static biblioteca.backend.dto.LivroRequest.converterDeOpenLibraryResponse;
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
@RequiredArgsConstructor
public class LivroService {

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
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByGenero(livroRequest.getGenero()));
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
        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores = this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        Livro livro = this.findById(id);
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByGeneroAndIdNot(livroRequest.getGenero(), livro.getId()));
        livro.atualizarDados(livroRequest, editora, autores, livrosParecidos);

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

    private Set<Autor> buscarAutoresPorKeys(List<String> keysDosAutores) {
        if (!keysDosAutores.isEmpty()) {
            List<AutorRequest> autoresRequests = converterDeOpenLibraryAutorResponses(openLibraryClient.buscarAutoresPorKeys(keysDosAutores));
            return autorService.buscarAutoresOuCriarAutores(autoresRequests);
        }
        return Collections.emptySet();
    }

    private Editora buscarEditoraPorNome(List<String> nomesEditoras) {
        return !nomesEditoras.isEmpty()
                ? editoraService.buscarEditoraOuCriarEditora(nomesEditoras.get(0))
                : null;
    }
}
