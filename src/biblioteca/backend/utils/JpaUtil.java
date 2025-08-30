package biblioteca.backend.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static javax.persistence.Persistence.createEntityManagerFactory;

/**
 * Classe utilitária para gerenciar o EntityManagerFactory do JPA.
 * <p>
 * Esta classe garante que o EntityManagerFactory seja criado apenas uma vez
 * durante o ciclo de vida da aplicação.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
@UtilityClass
public class JpaUtil {

    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            log.info("Inicializando entity manager...");
            entityManagerFactory = createEntityManagerFactory("biblioteca-unit");
            log.info("Entity manager inicializado com sucesso.");
        } catch (Exception ex) {
            log.severe("Falha ao inicializar entity manager. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Fornece um EntityManager para as operações do DAO.
     * @return uma instância de EntityManager.
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Fecha o EntityManagerFactory para liberar todos os recursos de conexão.
     * Deve ser chamado ao encerrar a aplicação.
     */
    public static void fecharConexao() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            log.info("Fechando conexão do entity manager...");
            entityManagerFactory.close();
            log.info("Entity manager fechado com sucesso..");
        }
    }

    /**
     * Inicia uma transação do entity manager recebido por parâmetro.
     */
    public static void iniciarTransacao(EntityManager entityManager) {
        entityManager.getTransaction().begin();
    }

    /**
     * Efetua o commit da transação no banco de dados, do entity manager recebido por parâmetro.
     */
    public static void commitarTransacao(EntityManager entityManager) {
        entityManager.getTransaction().commit();
    }

    /**
     * Desfaz as alterações realizadas no banco de dados, do entity manager recebido por parâmetro.
     */
    public static void desfazerAlteracoesTransacao(EntityManager entityManager) {
        entityManager.getTransaction().rollback();
    }

    /**
     * Fecha a transação do entity manager recebido por parâmetro.
     */
    public static void fecharTransacao(EntityManager entityManager) {
        entityManager.close();
    }
}
