package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.IEditoraDAO;
import biblioteca.backend.dto.PredicateResult;
import biblioteca.backend.model.Editora;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static biblioteca.backend.utils.JpaUtil.*;

/**
 * Classe responsável por implementar a lógica das transações realizadas no banco de dados, na tabela Editora.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class EditoraDAOImpl implements IEditoraDAO {

    /**
     * Método responsável por salvar/atualizar uma Editora no banco de dados.
     */
    @Override
    public Editora salvar(Editora editora) {
        EntityManager entityManager = getEntityManager();
        try {
            iniciarTransacao(entityManager);
            Editora editoraSalva;
            if (editora.getId() == null) {
                entityManager.persist(editora);
                editoraSalva = editora;
            } else {
                editoraSalva = entityManager.merge(editora);
            }
            commitarTransacao(entityManager);
            return editoraSalva;
        } catch (Exception ex) {
            desfazerAlteracoesTransacao(entityManager);
            log.severe(ex.getMessage());
            return null;
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todas as Editoras salvas no banco de dados.
     *
     * @return Todas as editoras salvas no banco de dados.
     */
    @Override
    public List<Editora> listarTodos() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT e FROM Editora e "
                                    + "ORDER BY e.id",
                            Editora.class)
                    .getResultList();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por listar todas as Editoras salvas no banco de dados, de acordo com os filtros dentro do predicate.
     *
     * @return Todas as editoras salvas no banco de dados de acordo com os filtros passados.
     */
    @Override
    public List<Editora> listarTodosPorPredicate(PredicateResult predicate) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Editora> query = entityManager.createQuery(
                    "SELECT DISTINCT e FROM Editora e "
                            + "LEFT JOIN FETCH e.livros l "
                            + predicate.getWhereClause()
                            + "ORDER BY e.id",
                    Editora.class);
            predicate.getParams().
                    forEach(query::setParameter);

            return query.getResultList();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por buscar uma Editora de acordo com o ID dela no banco de dados.
     *
     * @return Um valor Opcional de Editora.
     */
    @Override
    public Optional<Editora> findById(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT DISTINCT e FROM Editora e "
                                    + "LEFT JOIN FETCH e.livros "
                                    + "WHERE e.id = :id",
                            Editora.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        } finally {
            fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por deletar uma Editora de acordo com o ID dela no banco de dados.
     */
    @Override
    public void deletar(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            iniciarTransacao(entityManager);

            entityManager.createQuery(
                            "DELETE FROM Editora e "
                                    + "WHERE e.id = :id")
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
     * Método responsável por buscar a Editora de acordo com o nome dela no banco de dados.
     *
     * @return Um valor Opcional de Editora.
     */
    @Override
    public Optional<Editora> findByNome(String nome) {
        EntityManager entityManager = getEntityManager();
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT DISTINCT e FROM Editora e "
                                    + "LEFT JOIN FETCH e.livros "
                                    + "WHERE e.nome = :nome",
                            Editora.class)
                    .setParameter("nome", nome)
                    .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        } finally {
            fecharTransacao(entityManager);
        }
    }
}
