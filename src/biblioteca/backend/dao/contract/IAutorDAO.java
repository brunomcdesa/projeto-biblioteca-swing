package biblioteca.backend.dao.contract;

import biblioteca.backend.model.Autor;

import java.util.List;

/**
 * Interface responsável por definir os métodos que farão operações no banco de dados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public interface IAutorDAO {

    void salvar(Autor autor);

    List<Autor> listarTodos();
}
