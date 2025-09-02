package biblioteca.backend.facade;

import biblioteca.backend.dto.LivroFiltros;
import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.service.LivroService;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    /**
     * Método responsável por salvar um novo livro de acordo com a request recebida.
     * <p>
     * Realiza uma busca dos dados de Editora e Autores em suas respectivas services responsáveis, e com estes dados,
     * junto com a request recebida, chama a service de livro para criar um novo livro.
     */
    public void salvarLivro(LivroRequest livroRequest) {
        livroService.salvar(livroRequest);
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
        livroService.editar(id, livroRequest);
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
        return livroService.getSelectEditora();
    }

    /**
     * Método responsável por buscar os nomes dos Autores do sistema, para ser utilizado em um campo Multi Select.
     *
     * @return Uma lista de SelectResponse.
     */
    public List<SelectResponse> getSelectAutores() {
        return livroService.getSelectAutores();
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
     */
    public void cadastrarLivroPorIsbn(String isbn) {
        livroService.cadastrarLivroPorIsbn(isbn);
    }
}
