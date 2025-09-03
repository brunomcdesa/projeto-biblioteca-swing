package biblioteca.backend.dao.contract;

import biblioteca.backend.dto.PredicateResult;
import biblioteca.backend.model.Livro;

import java.util.List;
import java.util.Optional;

/**
 * Interface responsável por definir os métodos que farão operações no banco de dados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public interface ILivroDAO {

    void salvar(Livro livro);

    List<Livro> listarTodos();

    List<Livro> listarTodosPorPredicate(PredicateResult predicate);

    Optional<Livro> findById(Integer id);

    Optional<Livro> findByIsbn(String isbn);

    void deletar(Integer id);

    List<Livro> findByIdIn(List<Integer> ids);

    boolean existsByIsbn(String isbn);
}
