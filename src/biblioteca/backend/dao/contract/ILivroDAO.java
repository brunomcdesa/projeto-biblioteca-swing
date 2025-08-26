package biblioteca.backend.dao.contract;

import biblioteca.backend.enums.EGenero;
import biblioteca.backend.model.Livro;

import java.util.List;
import java.util.Optional;

public interface ILivroDAO {

    void salvar(Livro livro);

    List<Livro> listarTodos();

    Optional<Livro> findById(Integer id);

    void deletar(Integer id);

    List<Livro> findByGenero(EGenero genero);
}
