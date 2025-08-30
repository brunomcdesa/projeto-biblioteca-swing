package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.dto.PredicateResult;
import biblioteca.backend.model.Autor;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static biblioteca.backend.utils.JpaUtil.getEntityManager;
import static java.util.Collections.emptyList;

/**
 * Classe responsável por implementar a lógica das transações realizadas no banco de dados, na tabela Autor.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class AutorDAOImpl implements IAutorDAO {

    /**
     * Método responsável por salvar/atualizar um Autor no banco de dados.
     */
    @Override
    public void salvar(Autor autor) {
        EntityManager entityManager = getEntityManager();
        try {
            this.iniciarTransacao(entityManager);
            if (autor.getId() == null) {
                entityManager.persist(autor);
            } else {
                entityManager.merge(autor);
            }
            this.commitarTransacao(entityManager);
        } catch (Exception ex) {
            this.desfazerAlteracoesTransacao(entityManager);
            log.severe(ex.getMessage());
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todos os Autores salvos no banco de dados.
     *
     * @return Todos os autores salvos no banco de dados.
     */
    @Override
    public List<Autor> listarTodos() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Autor a "
                                    + "ORDER BY a.id",
                            Autor.class)
                    .getResultList();
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todos os Autores salvos no banco de dados, de acordo com os filtros dentro do predicate.
     *
     * @return Todos os autores salvos no banco de dados de acordo com os filtros passados.
     */
    @Override
    public List<Autor> listarTodosPorPredicate(PredicateResult predicate) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Autor> query = entityManager.createQuery(
                    "SELECT a FROM Autor a "
                            + "LEFT JOIN FETCH a.livros l "
                            + predicate.getWhereClause()
                            + "ORDER BY a.id",
                    Autor.class);
            predicate.getParams().
                    forEach(query::setParameter);

            return query.getResultList();
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por buscar um Autor de acordo com o ID dele no banco de dados.
     *
     * @return Um valor Opcional de Autor.
     */
    @Override
    public Optional<Autor> findById(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT a FROM Autor a "
                                    + "LEFT JOIN FETCH a.livros "
                                    + "WHERE a.id = :id",
                            Autor.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por deletar um Autor de acordo com o ID dele no banco de dados.
     */
    @Override
    public void deletar(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            this.iniciarTransacao(entityManager);

            entityManager.createQuery(
                            "DELETE FROM Autor a "
                                    + "WHERE a.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            this.commitarTransacao(entityManager);
        } catch (Exception ex) {
            this.desfazerAlteracoesTransacao(entityManager);
            log.severe(ex.getMessage());
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por buscar os Autores de acordo com os IDs deles no banco de dados.
     *
     * @return Um valor Opcional de Autor.
     */
    @Override
    public List<Autor> findByIdIn(List<Integer> ids) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Autor a "
                                    + "WHERE a.id IN(:ids)",
                            Autor.class)
                    .setParameter("ids", ids)
                    .getResultList();
        } catch (Exception ex) {
            return emptyList();
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    private void iniciarTransacao(EntityManager entityManager) {
        entityManager.getTransaction().begin();
    }

    private void commitarTransacao(EntityManager entityManager) {
        entityManager.getTransaction().commit();
    }

    private void desfazerAlteracoesTransacao(EntityManager entityManager) {
        entityManager.getTransaction().rollback();
    }

    private void fecharTransacao(EntityManager entityManager) {
        entityManager.close();
    }
}
