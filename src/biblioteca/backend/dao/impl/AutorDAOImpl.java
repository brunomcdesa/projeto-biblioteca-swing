package biblioteca.backend.dao.impl;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.model.Autor;
import biblioteca.backend.utils.JpaUtil;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;

@Log
public class AutorDAOImpl implements IAutorDAO {

    private final EntityManager entityManager = JpaUtil.getEntityManager();

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
