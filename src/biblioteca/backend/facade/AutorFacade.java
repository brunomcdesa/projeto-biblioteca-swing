package biblioteca.backend.facade;

import biblioteca.backend.dto.AutorFiltros;
import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.service.AutorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Classe definida como Facade dos fluxos de Autor.
 * <p>
 * Esta classe é exposta para as telas do swing e é responsável por fazer a ponte entre o "frontend" e o "backend".
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class AutorFacade {

    private final AutorService autorService;

    /**
     * Método responsável por salvar um novo Autor de acordo com a request recebida.
     */
    public void salvarNovoAutor(AutorRequest autorRequest) {
        autorService.salvar(autorRequest);
    }

    /**
     * Método responsável por buscar todos os autores do sistema.
     *
     * @return Uma lista de dados dos Autores.
     */
    public List<AutorResponse> listarTodosAutores() {
        return autorService.listarTodos();
    }

    /**
     * Método responsável por editar um autore específico de acordo com o ID e com a request recebida.
     */
    public void editarAutor(Integer id, AutorRequest autorRequest) {
        autorService.editar(id, autorRequest);
    }

    /**
     * Método responsável por deletar um autor específico de acordo com o ID recebido.
     */
    public void deletarAutor(Integer id) {
        autorService.deletar(id);
    }

    /**
     * Método responsável por buscar todos os autores do sistema de acordo com os filtros.
     *
     * @return Uma lista de dados dos autores de acordo com os filtros.
     */
    public List<AutorResponse> listarPorFiltros(AutorFiltros filtros) {
        return autorService.listarTodosPorFiltros(filtros);
    }
}
