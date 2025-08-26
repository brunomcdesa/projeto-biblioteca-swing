package biblioteca.utils;


import lombok.experimental.UtilityClass;

import javax.swing.*;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Classe utilitária para as telas de formulários.
 * <p>
 * Esta classe é responsável por realizar validações e operações comuns entre as telas de formulário
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@UtilityClass
public class FormUtils {

    /**
     * Método responsável por validar a linha selecionada da tabela
     * <p>
     * Realiza a comparação do index da linha selecionada, com o index de uma linha não selecionada (-1).
     * @return true: caso o resultado da comparação seja falso. false: caso o resultado da comparação seja true.
     */
    public static boolean validarLinhaSelecionada(int indexLinhaSelecionada, JFrame componente, String message, String title) {
        if (indexLinhaSelecionada == -1) {
            showMessageDialog(componente, message, title, WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
