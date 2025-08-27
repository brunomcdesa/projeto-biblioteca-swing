package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.ILivroDAO;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.model.Livro;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static biblioteca.backend.utils.JpaUtil.getEntityManager;
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
            this.iniciarTransacao(entityManager);
            if (livro.getId() == null) {
                entityManager.persist(livro);
            } else {
                entityManager.merge(livro);
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
            this.fecharTransacao(entityManager);
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
                            "SELECT l FROM Livro l where l.id = :id",
                            Livro.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        } finally {
            this.fecharTransacao(entityManager);
        }
    }

    /**
     * Método responsável por deletar um Livro de acordo com o ID dele no banco de dados.
     */
    @Override
    public void deletar(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            this.iniciarTransacao(entityManager);

            entityManager.createQuery(
                            "DELETE FROM Livro l WHERE l.id = :id")
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
                            "SELECT l FROM Livro l where l.genero = :genero",
                            Livro.class)
                    .setParameter("genero", genero)
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
