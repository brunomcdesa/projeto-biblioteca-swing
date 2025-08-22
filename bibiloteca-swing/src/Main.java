import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe Main do projeto
 * <p>
 * Esta classe é responsável inicializar o projeto
 * rodando todo o fluxo das telas e também iniciando a conexão com o banco de dados
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO HIBERNATE PARA CRIAR O SCHEMA DO BANCO ---");
        EntityManagerFactory entityManager = null;

        try {
            entityManager = Persistence.createEntityManagerFactory("biblioteca-unit");

            System.out.println("Schema do banco de dados verificado/criado com sucesso!");
            System.out.println("As tabelas foram criadas/atualizadas com sucesso!.");
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar o Hibernate e criar o schema.");
            System.out.println(ex.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
                System.out.println("EntityManagerFactory fechado.");
            }
        }

        System.out.println("--- PROCESSO FINALIZADO ---");
    }
}
