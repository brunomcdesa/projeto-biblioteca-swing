package biblioteca.telas.editora;

import biblioteca.backend.dto.EditoraRequest;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.service.EditoraService;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.isBlank;
import static biblioteca.utils.StringUtils.isCnpjValido;
import static java.awt.BorderLayout.*;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.*;

/**
 * Tela de cadastro de Editora.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a o cadastro de Editora,
 * onde vai ser mostrado os campos para que uma nova editora seja cadastrada no sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaFormularioEditora extends JFrame {

    private final JFrame telaAnterior;
    private final EditoraService editoraService;
    private final JButton botaoSalvar = new JButton("Salvar");
    private final JButton botaoVoltar = new JButton("Voltar");

    private JTextField campoNome;
    private JTextField campoCnpj;

    public TelaFormularioEditora(JFrame telaAnterior) {
        this(telaAnterior, null);
    }

    public TelaFormularioEditora(JFrame telaAnterior, EditoraResponse editora) {
        super("Cadastrar Editora");
        this.telaAnterior = telaAnterior;
        this.editoraService = new EditoraService();

        this.inicializarComponentes(editora);
        this.configurarAcoesDosBotoes(editora);
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes(EditoraResponse editora) {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(createEmptyBorder(50, 50, 50, 50));
        painelPrincipal.add(new JLabel("Preencha os dados do autor:", SwingConstants.CENTER), NORTH);
        this.aplicarConfiguracoesFormulario(painelPrincipal, editora);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);


        add(painelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal, EditoraResponse editora) {
        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setHorizontalAlignment(SwingConstants.CENTER);
        campoNome = new JTextField(mapNullComBackup(editora, EditoraResponse::getNome, ""));
        campoNome.setPreferredSize(new Dimension(250, 30));

        JLabel labelCnpj = new JLabel("Cnpj:");
        labelCnpj.setHorizontalAlignment(SwingConstants.CENTER);
        campoCnpj = new JTextField(mapNullComBackup(editora, EditoraResponse::getCnpj, ""));
        campoCnpj.setPreferredSize(new Dimension(250, 30));

        painelFormulario.add(labelNome);
        painelFormulario.add(campoNome);
        painelFormulario.add(labelCnpj);
        painelFormulario.add(campoCnpj);

        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = new JPanel(new FlowLayout(RIGHT));
        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoSalvar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes(EditoraResponse editora) {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoSalvar(editora);
    }

    /**
     * Configura a ação do botão de voltar para tela anterior.
     */
    private void configurarAcaoBotaoVoltar() {
        botaoVoltar.addActionListener(listener -> {
            telaAnterior.setVisible(true);
            dispose();
        });
    }

    /**
     * Configura a ação de salvar uma nova editora ou de editar uma editora.
     * Envia os dados para a service de Editora, para salvar no banco de dados.
     */
    private void configurarAcaoBotaoSalvar(EditoraResponse editora) {
        botaoSalvar.addActionListener(listener -> {
            String nome = campoNome.getText();
            String cnpj = campoCnpj.getText();

            if (isBlank(nome) || isBlank(cnpj)) {
                showMessageDialog(this, "Todos os campos são obrigatórios!",
                        "Erro de Validação", ERROR_MESSAGE);
                return;
            }

            if (!isCnpjValido(cnpj)) {
                showMessageDialog(this, "CNPJ inválido! Insira o CNPJ com o formato XX.XXX.XXX/XXXX-XX",
                        "Erro de Formato", ERROR_MESSAGE);
                return;
            }

            EditoraRequest request = new EditoraRequest(nome, cnpj);

            if (editora == null) {
                editoraService.salvar(request);
            } else {
                editoraService.editar(editora.getId(), request);
            }
            showMessageDialog(this, "Editora salva com sucesso!", "Sucesso", INFORMATION_MESSAGE);

            campoNome.setText("");
            campoCnpj.setText("");
        });
    }
}
