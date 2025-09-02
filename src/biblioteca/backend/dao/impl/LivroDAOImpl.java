package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.ILivroDAO;
import biblioteca.backend.dto.PredicateResult;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.model.Livro;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static biblioteca.backend.utils.JpaUtil.*;
import static java.util.Collections.emptyList;

/**
 * Classe responsável por implementar a lógica das transações realizadas no banco de dados, na tabela Livro.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class LivroDAOImpl implements ILivroDAO {

    /**
     * Método responsável por salvar/atualizar um Livro no banco de dados.
     */
    @Override
    public void salvar(Livro livro) {
        EntityManager entityManager = getEntityManager();
        try {
            iniciarTransacao(entityManager);
            if (livro.getId() == null) {
                entityManager.persist(livro);
            } else {
                entityManager.merge(livro);
            }
            commitarTransacao(entityManager);
        } catch (Exception ex) {
            desfazerAlteracoesTransacao(entityManager);
            log.severe(ex.getMessage());
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todos os Livros salvos no banco de dados.
     *
     * @return Todos os livros salvos no banco de dados.
     */
    @Override
    public List<Livro> listarTodos() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT DISTINCT l FROM Livro l "
                                    + "LEFT JOIN FETCH l.editora "
                                    + "LEFT JOIN FETCH l.autores "
                                    + "LEFT JOIN FETCH l.livrosParecidos "
                                    + "ORDER BY l.id",
                            Livro.class)
                    .getResultList();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todos os Livros salvos no banco de dados, de acordo com os filtros dentro do predicate.
     *
     * @return Todos os Livros salvos no banco de dados de acordo com os filtros passados.
     */
    @Override
    public List<Livro> listarTodosPorPredicate(PredicateResult predicate) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Livro> query = entityManager.createQuery(
                    "SELECT DISTINCT l FROM Livro l "
                            + "LEFT JOIN FETCH l.editora e "
                            + "LEFT JOIN FETCH l.autores a "
                            + "LEFT JOIN FETCH l.livrosParecidos lp "
                            + predicate.getWhereClause()
                            + "ORDER BY l.id",
                    Livro.class);
            predicate.getParams().
                    forEach(query::setParameter);

            return query.getResultList();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por buscar um Livro de acordo com o ID dele no banco de dados.
     *
     * @return Um valor Opcional de Livro.
     */
    @Override
    public Optional<Livro> findById(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT l FROM Livro l "
                                    + "LEFT JOIN FETCH l.livrosParecidos "
                                    + "WHERE l.id = :id",
                            Livro.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por deletar um Livro de acordo com o ID dele no banco de dados.
     */
    @Override
    public void deletar(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            iniciarTransacao(entityManager);

            entityManager.createQuery(
                            "DELETE FROM Livro l "
                                    + "WHERE l.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            commitarTransacao(entityManager);
        } catch (Exception ex) {
            desfazerAlteracoesTransacao(entityManager);
            log.severe(ex.getMessage());
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todos os Livros salvos no banco de dados com o mesmo gênero que foi
     * passado por parâmetro.
     *
     * @return Todos os livros salvos no banco de dados de um gênero específico.
     */
    @Override
    public List<Livro> findByGenero(EGenero genero) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT l FROM Livro l "
                                    + "WHERE l.genero = :genero",
                            Livro.class)
                    .setParameter("genero", genero)
                    .getResultList();
        } catch (Exception ex) {
            return emptyList();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todos os Livros salvos no banco de dados com o mesmo gênero que foi
     * passado por parâmetro, com exceção do livro que possuir o id igual ao passado por parametro.
     *
     * @return Todos os livros salvos no banco de dados de um gênero específico.
     */
    @Override
    public List<Livro> findByGeneroAndIdNot(EGenero genero, Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT l FROM Livro l "
                                    + "LEFT JOIN FETCH l.livrosParecidos "
                                    + "WHERE l.genero = :genero "
                                    + "AND l.id != :id",
                            Livro.class)
                    .setParameter("genero", genero)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception ex) {
            return emptyList();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por verificar diretamente no banco de dados, se existe algum livro com o mesmo ISBN informado.
     *
     * @return true se existir algum livro já cadastrado com o ISBN. false se não existir nenhum livro cadastrado com o mesmo ISBN.
     */
    @Override
    public boolean existsByIsbn(String isbn) {
        EntityManager entityManager = getEntityManager();
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT l FROM Livro l "
                                    + "WHERE l.isbn = :isbn",
                            Livro.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult())
                    .isPresent();
        } catch (Exception ex) {
            return false;
        } finally {
            fecharTransacao(entityManager);
        }
    }
}
