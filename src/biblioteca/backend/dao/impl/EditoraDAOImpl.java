package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.IEditoraDAO;
import biblioteca.backend.model.Editora;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static biblioteca.backend.utils.JpaUtil.getEntityManager;

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
    public void salvar(Editora editora) {
        EntityManager entityManager = getEntityManager();
        try {
            this.iniciarTransacao(entityManager);
            if (editora.getId() == null) {
                entityManager.persist(editora);
            } else {
                entityManager.merge(editora);
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
     * Método responsável por listar todas as Editoras salvas no banco de dados.
     *
     * @return Todas as editoras salvas no banco de dados.
     */
    @Override
    public List<Editora> listarTodos() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT e FROM Editora e ORDER BY e.id",
                            Editora.class)
                    .getResultList();
        } finally {
            this.fecharTransacao(entityManager);
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
                            "SELECT e FROM Editora e where e.id = :id",
                            Editora.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por deletar uma Editora de acordo com o ID dela no banco de dados.
     */
    @Override
    public void deletar(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            this.iniciarTransacao(entityManager);

            entityManager.createQuery(
                            "DELETE FROM Editora e WHERE e.id = :id")
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
