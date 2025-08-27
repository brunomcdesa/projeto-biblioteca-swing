package biblioteca.utils;


import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static java.awt.BorderLayout.*;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

/**
 * Classe utilitária para as telas de formulários.
 * <p>
 * Esta classe é responsável por realizar validações e operações comuns entre as telas de formulário
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@UtilityClass
public class TelasUtils {

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

    /**
     * Método responsável por criar um botão para ser utilizado em algum painel.
     *
     * @return um JButton para ser utilizado como um botão em algum painel.
     */
    public static JButton criarBotao(String texto) {
        return new JButton(texto);
    }

    /**
     * Método responsável por criar um campo de texto para ser utilizado nos formulários.
     *
     * @return um JTextField para ser utilizado como um campo de texto de um formulário.
     */
    public static JTextField criarTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(new Dimension(250, 30));
        return textField;
    }

    /**
     * Método responsável por criar um campo de select para ser utilizado nos formulários.
     *
     * @return um JComboBox para ser utilizado como um campo de select de um formulário.
     */
    public static JComboBox<String> criarSelect(String[] opcoes) {
        return new JComboBox<>(opcoes);
    }

    /**
     * Método responsável por criar um campo de multi select para ser utilizado nos formulários.
     * <p>
     * O campo multi select com o modo MULTIPLE_INTERVAL_SELECTION deve ser utilizado a tecla SHIFT e as setas,
     * ou a tecla CTRL + click para selecionar vários.
     *
     * @return um JList para ser utilizado como um campo de multi select de um formulário.
     */
    public static JList<String> criarMultiSelect(String[] opcoes) {
        JList<String> multiSelect = new JList<>(opcoes);
        multiSelect.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);

        return multiSelect;
    }

    /**
     * Método responsável por criar painel principal para os formulários.
     *
     * @return um JPanel para ser utilizado como painel principal dos formulários.
     */
    public static JPanel criarPainelPrincipalFormulario(String text) {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(createEmptyBorder(50, 50, 50, 50));
        painelPrincipal.add(new JLabel(text, SwingConstants.CENTER), NORTH);

        return painelPrincipal;
    }

    /**
     * Método responsável por criar painel principal para as listagens.
     *
     * @return um JPanel para ser utilizado como painel principal das listagens.
     */
    public static JPanel criarPainelPrincipalListagem(JTable tabela) {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        return painelPrincipal;
    }

    /**
     * Método responsável por criar painel para os campos do formulário.
     * <p>
     * Este painel irãenglobar todos os campos do formulário, tanto labels, quanto campos de input.
     *
     * @return um JPanel para ser utilizado como painel dos campos do formulário.
     */
    public static JPanel criarPainelFormulario() {
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));

        return painelFormulario;
    }

    /**
     * Método responsável por criar uma linha do formulário.
     *
     * @return um JPanel para para ser utilizado como uma linha do painel de formulário.
     */
    public static JPanel criarLinhaFormulario(String textoLabel, Component componente) {
        JPanel painelLinha = new JPanel(new BorderLayout(10, 0));

        JLabel label = new JLabel(textoLabel);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(220, label.getPreferredSize().height));

        painelLinha.add(label, WEST);
        painelLinha.add(componente, CENTER);

        return painelLinha;
    }

    /**
     * Método responsável por criar o painel de botoes das telas de listagem.
     * <p>
     * Recebe os botoes que vao ser adicionados no painel por parametro e adiciona eles dinamicamente no painel,
     * de acordo com a quantidade de botoes recebidos.
     *
     * @return um JPanel para para ser utilizado como painel de botoes das telas de listagem.
     */
    public static JPanel criarPainelBotoesListagem(Component... components) {
        JPanel painelBotoes = new JPanel(new FlowLayout(RIGHT));
        Arrays.stream(components)
                .forEach(painelBotoes::add);

        return painelBotoes;
    }
}
