package biblioteca.backend.service;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.dao.impl.AutorDAOImpl;
import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.model.Autor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Classe de serviço para Autor.
 * <p>
 * Esta classe é responsável por guardar a logica de validação e conversão dos dados,
 * e também é a classe que vai chamar as operações do banco de dados através do DAO.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class AutorService {

    private final IAutorDAO autorDAO;

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(AutorRequest autorRequest) {
        Autor autor = Autor.converterDeRequest(autorRequest);
        autorDAO.salvar(autor);
    }

    /**
     * Método responsável por listar todos os Autores do sistema.
     *
     * @return uma lista de Autores.
     */
    public List<AutorResponse> listarTodos() {
        return autorDAO.listarTodos().stream()
                .map(AutorResponse::converterDeAutor)
                .collect(toList());
    }

    /**
     * Método responsável por editar um autor específico, de acordo com os novos dados da request.
     */
    public void editar(Integer id, AutorRequest request) {
        Autor autor = this.findById(id);
        autor.atualizarDados(request);

        autorDAO.salvar(autor);
    }

    /**
     * Método responsável por deletar um autor específico do banco de dados.
     */
    public void deletar(Integer id) {
        autorDAO.deletar(id);
    }

    /**
     * Método responsável por buscar um Autor pelo ID dele.
     * <p>
     * Caso não encontre nenhum Autor com o mesmo ID, será lançado uma excepion.
     *
     * @return um Autor.
     */
    private Autor findById(Integer id) {
        return autorDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Autor não encontrado."));
    }

    public Set<Autor> findByIdIn(List<Integer> autoresIds) {
        return new HashSet<>(autorDAO.findByIdIn(autoresIds));
    }
}
