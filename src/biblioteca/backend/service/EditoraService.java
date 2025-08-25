package biblioteca.backend.service;

import biblioteca.backend.dao.contract.IEditoraDAO;
import biblioteca.backend.dao.impl.EditoraDAOImpl;
import biblioteca.backend.dto.EditoraRequest;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.model.Editora;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EditoraService {

    private final IEditoraDAO editoraDAO;

    public EditoraService() {
        this.editoraDAO = new EditoraDAOImpl();
    }

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(EditoraRequest editoraRequest) {
        Editora autor = Editora.converterDeRequet(editoraRequest);
        editoraDAO.salvar(autor);
    }

    /**
     * Método responsável por listar todas as Editoras do sistema.
     *
     * @return uma lista de Autores.
     */
    public List<EditoraResponse> listarTodos() {
        return editoraDAO.listarTodos().stream()
                .map(EditoraResponse::converterDeEditora)
                .collect(toList());
    }

    /**
     * Método responsável por editar uma editora específica, de acordo com os novos dados da request.
     */
    public void editar(Integer id, EditoraRequest request) {
        Editora editora = this.findById(id);
        editora.atualizarDados(request);

        editoraDAO.salvar(editora);
    }

    /**
     * Método responsável por deletar uma editora específica do banco de dados.
     */
    public void deletar(Integer id) {
        editoraDAO.deletar(id);
    }

    /**
     * Método responsável por buscar um Autor pelo ID dele.
     * <p>
     * Caso não encontre nenhum Autor com o mesmo ID, será lançado uma excepion.
     *
     * @return uma Editora.
     */
    private Editora findById(Integer id) {
        return editoraDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Editora não encontrada."));
    }
}
