package biblioteca;

import biblioteca.backend.utils.JpaUtil;
import biblioteca.telas.TelaPrincipal;

import javax.swing.*;

/**
 * Classe Main do projeto
 * <p>
 * Esta classe é responsável inicializar o projeto.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        JpaUtil.getEntityManager().close();

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
