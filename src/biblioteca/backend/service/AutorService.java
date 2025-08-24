package biblioteca.backend.service;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.dao.impl.AutorDAOImpl;
import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.model.Autor;

import java.util.List;

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
public class AutorService {

    private final IAutorDAO autorDAO;

    public AutorService() {
        this.autorDAO = new AutorDAOImpl();
    }

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(AutorRequest autorRequest) {
        Autor autor = Autor.converterDeRequet(autorRequest);
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
}
