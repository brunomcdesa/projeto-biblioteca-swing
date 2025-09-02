package biblioteca.backend.facade;

import biblioteca.backend.client.OpenLibraryClient;
import biblioteca.backend.dto.*;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.exceptions.ValidacaoException;
import biblioteca.backend.model.Autor;
import biblioteca.backend.model.Editora;
import biblioteca.backend.service.AutorService;
import biblioteca.backend.service.EditoraService;
import biblioteca.backend.service.LivroService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import static biblioteca.backend.dto.AutorRequest.converterDeOpenLibraryAutorResponses;
import static biblioteca.backend.dto.LivroRequest.converterDeOpenLibraryResponse;

/**
 * Classe definida como Facade dos fluxos de Livro.
 * <p>
 * Esta classe é exposta para as telas do swing e é responsável por fazer a ponte entre o "frontend" e o "backend".
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class LivroFacade {

    private final LivroService livroService;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final OpenLibraryClient openLibraryClient;

    /**
     * Método responsável por salvar um novo livro de acordo com a request recebida.
     * <p>
     * Realiza uma busca dos dados de Editora e Autores em suas respectivas services responsáveis, e com estes dados,
     * junto com a request recebida, chama a service de livro para criar um novo livro.
     */
    public void salvarLivro(LivroRequest livroRequest) {
        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores =this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        livroService.salvar(livroRequest, editora, autores);
    }

    /**
     * Método responsável por buscar todos os livros do sistema.
     * <p>
     * Realiza uma busca de todos os livros na service de livro.
     *
     * @return Uma lista de dados dos livros.
     */
    public List<LivroResponse> listarTodosLivros() {
        return livroService.listarTodos();
    }

    /**
     * Método responsável por editar um livro específico de acordo com o ID e com a request recebida.
     * <p>
     * Realiza uma busca dos dados de Editora e Autores em suas respectivas services responsáveis, e com estes dados,
     * junto com o ID e a request recebida, chama a service de livro para editar o livro escolhido pelo usuário.
     */
    public void editarLivro(Integer id, LivroRequest livroRequest) {
        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores = this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        livroService.editar(id, livroRequest, editora, autores);
    }

    /**
     * Método responsável por deletar um livro específico de acordo com o ID recebido.
     * <p>
     * Chama a service de livro para efetuar o ato de deletar o livro em questão.
     */
    public void deletarLivro(Integer id) {
        livroService.deletar(id);
    }

    /**
     * Método responsável por buscar os dados do Enum de Genero, para ser utilizado em um campo Select.
     *
     * @return Uma lista de SelectResponse.
     */
    public List<SelectResponse> getSelectGenero() {
       return EGenero.getValuesParaSelect();
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
     * Método responsável por buscar todos os livros do sistema de acordo com os filtros.
     *
     * @return Uma lista de dados dos livros de acordo com os filtros.
     */
    public List<LivroResponse> listarPorFiltros(LivroFiltros filtros) {
        return livroService.listarTodosPorFiltros(filtros);
    }

    /**
     * Método responsável por salvar um novo livro de acordo com o ISBN recebido.
     * <p>
     * Realiza uma busca dos dados do livro, da editora e dos autores, de acordo com os dados retornados da busca dos dados do livro por ISBN.
     *
     * @throws ValidacaoException caso já existir um livro cadastrado no sistema com o mesmo ISBN.
     */
    public void cadastrarLivroPorIsbn(String isbn) {
        if (livroService.existsByIsbn(isbn)) {
            throw new ValidacaoException("Já existe um livro com este ISBN no sistema.");
        }

        openLibraryClient.buscarLivroPorIsbn(isbn)
                .ifPresent(livro -> {
                    List<AutorRequest> autoresRequests = converterDeOpenLibraryAutorResponses(openLibraryClient.buscarAutoresPorKeys(livro.getKeysDosAutores()));
                    Set<Autor> autores = autorService.buscarAutoresOuCriarAutores(autoresRequests);
                    Editora editora = editoraService.buscarEditoraOuCriarEditora(livro.getEditoras().get(0));
                    LivroRequest livroRequest = converterDeOpenLibraryResponse(livro);

                    livroService.salvar(livroRequest, editora, autores);
                });
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
}
