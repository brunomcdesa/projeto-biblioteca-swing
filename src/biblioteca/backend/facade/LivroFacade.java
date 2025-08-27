package biblioteca.backend.facade;

import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.model.Autor;
import biblioteca.backend.model.Editora;
import biblioteca.backend.service.AutorService;
import biblioteca.backend.service.EditoraService;
import biblioteca.backend.service.LivroService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class LivroFacade {

    private final LivroService livroService;
    private final AutorService autorService;
    private final EditoraService editoraService;

    public void salvarLivro(LivroRequest livroRequest) {
        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores =this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        livroService.salvar(livroRequest, editora, autores);
    }

    public List<LivroResponse> listarTodosLivros() {
        return livroService.listarTodos();
    }

    public void editarLivro(Integer id, LivroRequest livroRequest) {
        Editora editora = this.buscarEditoraPorId(livroRequest.getEditoraId());
        Set<Autor> autores = this.buscarAutoresPorIds(livroRequest.getAutoresIds());
        livroService.editar(id, livroRequest, editora, autores);
    }

    public void deletarLivro(Integer id) {
        livroService.deletar(id);
    }

    private Editora buscarEditoraPorId(Integer editoraId) {
        return editoraService.findById(editoraId);
    }

    private Set<Autor> buscarAutoresPorIds(List<Integer> autoresIds) {
        return autorService.findByIdIn(autoresIds);
    }
}
