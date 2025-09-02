package biblioteca.backend.dao.contract;

import biblioteca.backend.dto.PredicateResult;
import biblioteca.backend.model.Autor;

import java.util.List;
import java.util.Optional;

/**
 * Interface responsável por definir os métodos que farão operações no banco de dados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public interface IAutorDAO {

    Autor salvar(Autor autor);

    List<Autor> listarTodos();

    List<Autor> listarTodosPorPredicate(PredicateResult predicate);

    Optional<Autor> findById(Integer id);

    void deletar(Integer id);

    List<Autor> findByIdIn(List<Integer> id);

    List<Autor> findByNomes(List<String> nomes);
}
