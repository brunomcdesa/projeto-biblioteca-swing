package biblioteca.backend.facade;

import biblioteca.backend.dto.EditoraRequest;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.service.EditoraService;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Classe definida como Facade dos fluxos de Editora.
 * <p>
 * Esta classe é exposta para as telas do swing e é responsável por fazer a ponte entre o "frontend" e o "backend".
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class EditoraFacade {

    private final EditoraService editoraService;

    /**
     * Método responsável por salvar uma nova Editora de acordo com a request recebida.
     */
    public void salvarEditora(EditoraRequest editoraRequest) {
        editoraService.salvar(editoraRequest);
    }

    /**
     * Método responsável por buscar todas as editoras do sistema.
     *
     * @return Uma lista de dados das editoras.
     */
    public List<EditoraResponse> listarTodasEditoras() {
        return editoraService.listarTodos();
    }

    /**
     * Método responsável por editar uma editora específica de acordo com o ID e com a request recebida.
     */
    public void editarEditora(Integer id, EditoraRequest editoraRequest) {
        editoraService.editar(id, editoraRequest);
    }

    /**
     * Método responsável por deletar uma editora específica de acordo com o ID recebido.
     */
    public void deletarEditora(Integer id) {
        editoraService.deletar(id);
    }
}
