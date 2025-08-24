package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.model.Autor;
import biblioteca.backend.utils.JpaUtil;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Classe responsável por implementar a lógica das transações realizadas no banco de dados, na tabela Autor.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class AutorDAOImpl implements IAutorDAO {

    private final EntityManager entityManager = JpaUtil.getEntityManager();

    /**
     * Método responsável por salvar/atualizar um Autor no banco de dados.
     */
    @Override
    public void salvar(Autor autor) {
        try {
            this.iniciarTransacao();
            if (autor.getId() == null) {
                entityManager.persist(autor);
            } else {
                entityManager.merge(autor);
            }
            this.commitarTransacao();
        } catch (Exception e) {
            this.desfazerAlteracoesTransacao();
            log.severe(e.getMessage());
        } finally {
            this.fecharTransacao();
        }
    }

    /**
     * Método responsável por listar todos os Autores salvos no banco de dados.
     */
    @Override
    public List<Autor> listarTodos() {
        try {
            return entityManager.createQuery("SELECT a FROM Autor a ORDER BY a.id", Autor.class)
                    .getResultList();
        } finally {
            this.fecharTransacao();
        }
    }


    private void iniciarTransacao() {
        entityManager.getTransaction().begin();
    }

    private void commitarTransacao() {
        entityManager.getTransaction().commit();
    }

    private void desfazerAlteracoesTransacao() {
        entityManager.getTransaction().rollback();
    }

    private void fecharTransacao() {
        entityManager.close();
    }
}
