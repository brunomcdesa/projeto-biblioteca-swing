package biblioteca.backend.facade;

import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.service.AutorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AutorFacade {

    private final AutorService autorService;

    public void salvarNovoAutor(AutorRequest autorRequest) {
        autorService.salvar(autorRequest);
    }

    public List<AutorResponse> listarTodosAutores() {
        return autorService.listarTodos();
    }

    public void editarAutor(Integer id, AutorRequest autorRequest) {
        autorService.editar(id, autorRequest);
    }

    public void deletarAutor(Integer id) {
        autorService.deletar(id);
    }
}
