package biblioteca.backend.facade;

import biblioteca.backend.dto.EditoraRequest;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.service.EditoraService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EditoraFacade {

    private final EditoraService editoraService;

    public void salvarEditora(EditoraRequest editoraRequest) {
        editoraService.salvar(editoraRequest);
    }

    public List<EditoraResponse> listarTodasEditoras() {
        return editoraService.listarTodos();
    }

    public void editarEditora(Integer id, EditoraRequest editoraRequest) {
        editoraService.editar(id, editoraRequest);
    }

    public void deletarEditora(Integer id) {
        editoraService.deletar(id);
    }
}
