package biblioteca.utils;


import biblioteca.backend.dto.EditoraResponse;
import lombok.experimental.UtilityClass;

import javax.swing.*;

import java.awt.*;

import static biblioteca.utils.MapUtils.mapNullComBackup;
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

    public static JButton criarBotao(String texto) {
        return new JButton(texto);
    }

    public static JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    public static JTextField formatarTextField(JTextField textField, String text) {
        textField = new JTextField(text);
        textField.setPreferredSize(new Dimension(250, 30));

        return textField;
    }
}
