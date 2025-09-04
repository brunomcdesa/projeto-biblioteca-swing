package biblioteca.utils;

import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.utils.JpaUtil;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.awt.BorderLayout.*;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

/**
 * Classe utilitária para as telas do sistema.
 * <p>
 * Esta classe é responsável por realizar validações e operações comuns entre as telas do sistema.
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
     *
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
     * Método responsável por criar um campo de área de texto para ser utilizado nos formulários.
     *
     * @return um JTextArea para ser utilizado como um campo de área de texto de um formulário.
     */
    public static JTextArea criarTextArea(String text) {
        JTextArea textField = new JTextArea(text);
        textField.setPreferredSize(new Dimension(250, 100));
        return textField;
    }

    /**
     * Método responsável por criar um campo de select para ser utilizado nos formulários.
     *
     * @return um JComboBox para ser utilizado como um campo de select de um formulário.
     */
    public static JComboBox<SelectResponse> criarSelect(List<SelectResponse> opcoes) {
        SelectResponse selectDefault = new SelectResponse(null, "Selecionar");
        opcoes.add(0, selectDefault);
        return new JComboBox<>(opcoes.toArray(new SelectResponse[0]));
    }

    /**
     * Método responsável por criar um campo de multi select para ser utilizado nos formulários.
     * <p>
     * O campo multi select com o modo MULTIPLE_INTERVAL_SELECTION deve ser utilizado a tecla SHIFT e as setas,
     * ou a tecla CTRL + click para selecionar vários.
     *
     * @return um JList para ser utilizado como um campo de multi select de um formulário.
     */
    public static JList<SelectResponse> criarMultiSelect(List<SelectResponse> opcoes) {
        JList<SelectResponse> multiSelect = new JList<>(opcoes.toArray(new SelectResponse[0]));
        multiSelect.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);

        return multiSelect;
    }

    /**
     * Método responsável por criar um JScrollPane para os campos multi selects.
     *
     * @return um JScrollPane para ser utilizado pelos campos multi select.
     */
    public static JScrollPane montarScrollDeCampoMultiSelect(JList<SelectResponse> campoMultiSelect) {
        JScrollPane scrollCampoMultiSelect = new JScrollPane(campoMultiSelect);
        scrollCampoMultiSelect.setPreferredSize(new Dimension(180, 100));

        return scrollCampoMultiSelect;
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
    public static JPanel criarPainelPadrao() {
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

        painelLinha.add(label, NORTH);
        painelLinha.add(componente, CENTER);

        JPanel painelAuxiliar = new JPanel();
        painelAuxiliar.setSize(100, 100);
        painelAuxiliar.add(painelLinha, CENTER);

        return painelAuxiliar;
    }

    /**
     * Método responsável por criar uma linha de separação.
     *
     * @return um Component para para ser utilizado como uma linha de separação.
     */
    public static Component criarLinhaSeparacao() {
        return Box.createRigidArea(new Dimension(0, 15));
    }

    /**
     * Método responsável por criar uma linha do filtro.
     *
     * @return um JPanel para para ser utilizado como uma linha do painel de filtro.
     */
    public static JPanel criarLinhaFiltro(String textoLabel, Component componente) {
        JPanel painelLinha = new JPanel(new BorderLayout(5, 0));

        JLabel label = new JLabel(textoLabel);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(100, label.getPreferredSize().height));

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
    public static JPanel criarPainelBotoesListagem(Component... botoes) {
        JPanel painelBotoes = new JPanel(new FlowLayout(RIGHT));
        Arrays.stream(botoes)
                .forEach(painelBotoes::add);

        return painelBotoes;
    }

    /**
     * Método responsável por criar o painel de filtros.
     * <p>
     * Recebe as linhas que serão adicionados no painel por parametro e adiciona eles dinamicamente no painel,
     * de acordo com a quantidade de linhas recebidas.
     *
     * @return um JPanel para para ser utilizado como painel de botoes das telas de listagem.
     */
    public static JPanel criarPainelFiltros(Component... linhas) {
        JPanel painelFiltros = new JPanel(new GridLayout(0, 3, 10, 5));
        painelFiltros.setBorder(createEmptyBorder(10, 10, 10, 10));

        Arrays.stream(linhas)
                .forEach(painelFiltros::add);

        return painelFiltros;
    }

    /**
     * Método responsável por atribuir o item selecionado no campo Select.
     * <p>
     * Recebe o campo select e o valor selecionado, e faz uma comparação do valor selecionado com os elementos do campo select,
     * e o index que possuir o value equivalente ao valor selecionado é setado como selectedItem.
     */
    public static void atribuirItemSelecionado(JComboBox<SelectResponse> campoSelect, Object valorSelecionado) {
        ComboBoxModel<SelectResponse> model = campoSelect.getModel();
        IntStream.range(0, model.getSize())
                .mapToObj(model::getElementAt)
                .filter(item -> item.getValue() == valorSelecionado)
                .forEach(campoSelect::setSelectedItem);
    }

    /**
     * Método responsável por atribuir os itens selecionados no campo Multi Select.
     * <p>
     * Recebe o campo multi select e os valorres selecionados, e faz uma comparação dos valores selecionados com os elementos do campo multi select,
     * e os indicies que possuirem o value equivalente a algum dos valores selecionados são setados como como selectedItens.
     */
    public static void atribuirItensSelecionados(JList<SelectResponse> campoSelect, List<Object> valoresSelecionados) {
        ListModel<SelectResponse> model = campoSelect.getModel();
        campoSelect.setSelectedIndices(IntStream.range(0, model.getSize())
                .filter(index -> valoresSelecionados.contains(model.getElementAt(index).getValue()))
                .toArray());
    }

    /**
     * Método responsável por atribuir as configurações padrões das telas do sistema.
     */
    public static void adicionarConfiguracoesPadroesTela(JFrame tela, JPanel painelPrincipal) {
        tela.add(painelPrincipal);
        tela.setSize(1100, 700);
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configurarListenerDeJanela(tela);
    }

    /**
     * Adiciona um listener para interceptar o evento de fechamento da janela.
     */
    private static void configurarListenerDeJanela(JFrame tela) {
        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                JpaUtil.fecharConexao();
            }
        });
    }
}
