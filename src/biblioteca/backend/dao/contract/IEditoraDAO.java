package biblioteca.backend.dao.contract;

import biblioteca.backend.dto.PredicateResult;
import biblioteca.backend.model.Editora;

import java.util.List;
import java.util.Optional;

/**
 * Interface responsável por definir os métodos que farão operações no banco de dados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public interface IEditoraDAO {

    Editora salvar(Editora editora);

    List<Editora> listarTodos();

    List<Editora> listarTodosPorPredicate(PredicateResult predicate);

    Optional<Editora> findById(Integer id);

    void deletar(Integer id);

    Optional<Editora> findByNome(String nome);
}
