package biblioteca.backend.service;

import biblioteca.backend.dao.contract.IEditoraDAO;
import biblioteca.backend.dto.EditoraFiltros;
import biblioteca.backend.dto.EditoraRequest;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.exceptions.ValidacaoException;
import biblioteca.backend.model.Editora;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static biblioteca.backend.dto.SelectResponse.montarSelectResponse;
import static java.util.stream.Collectors.toList;

/**
 * Classe de serviço para Editora.
 * <p>
 * Esta classe é responsável por guardar a logica de validação e conversão dos dados,
 * e também é a classe que vai chamar as operações do banco de dados através do DAO.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class EditoraService {

    private final IEditoraDAO editoraDAO;

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(EditoraRequest editoraRequest) {
        Editora autor = Editora.converterDeRequest(editoraRequest);
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
     * Método responsável por listar todas as Editoras do sistema por filtros.
     *
     * @return uma lista de Autores de acordo com os filtros passados por parametro.
     */
    public List<EditoraResponse> listarTodosPorFiltros(EditoraFiltros filtros) {
        return editoraDAO.listarTodosPorPredicate(filtros.toPredicate()).stream()
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
        Editora editora = this.findById(id);
        validarEditoraComLivrosVinculados(editora);

        editoraDAO.deletar(id);
    }

    /**
     * Método responsável por buscar uma Editora pelo ID dele.
     * <p>
     * Caso não encontre nenhuma Editora com o mesmo ID, será lançado uma excepion.
     *
     * @return uma Editora.
     */
    public Editora findById(Integer id) {
        return editoraDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Editora não encontrada."));
    }

    /**
     * Método responsável por buscar um array das Editoras e converter para o Objeto SelectResponse.
     *
     * @return Um array de SelectResponse.
     */
    public SelectResponse[] buscarEditorasSelect() {
        return editoraDAO.listarTodos().stream()
                .map(editora -> montarSelectResponse(editora.getId(), editora.getNome()))
                .toArray(SelectResponse[]::new);
    }


    /**
     * Método responsável por validar se a editora tem livros vinculados.
     *
     * @throws ValidacaoException caso a editora possua livros vinculados a ela.
     */
    private void validarEditoraComLivrosVinculados(Editora editora) {
        if (editora.possuiLivrosVinculados()) {
            throw new ValidacaoException("Editora possui livros vinculados. Por favor remova o vínculo com os livros antes de excluir a editora.");
        }
    }
}
