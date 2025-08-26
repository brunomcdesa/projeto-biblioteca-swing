package biblioteca.telas.editora;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * Tela principal de gerencia de Editora.
 * <p>
 * Esta classe é responsável por renderizar a tela principal da gerencia das Editoras,
 * onde vai ser feito o redirecionamento para as telas referente a listagem/adição de editoras.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaGerenciaEditora extends JFrame {

    private final JFrame telaAnterior;

    private final JButton botaoListarEditoras = new JButton("Listar Editoras");
    private final JButton botaoCadastrarEditora = new JButton("Cadastrar Editora");
    private final JButton botaoVoltar = new JButton("Voltar");

    public TelaGerenciaEditora(JFrame telaAnterior) {
        super("Gerenciar Editoras");
        this.telaAnterior = telaAnterior;

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(createEmptyBorder(20, 20, 20, 20));

        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        add(painelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        botaoListarEditoras.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCadastrarEditora.setAlignmentX(CENTER_ALIGNMENT);
        botaoVoltar.setAlignmentX(CENTER_ALIGNMENT);

        painelBotoes.add(botaoListarEditoras);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelBotoes.add(botaoCadastrarEditora);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 300)));
        painelBotoes.add(botaoVoltar);

        painelPrincipal.add(painelBotoes);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoListarEditoras();
        this.configurarAcaoCadastrarEditora();
    }

    /**
     * Adiciona um listener para interceptar o evento de voltar a tela.
     */
    private void configurarAcaoBotaoVoltar() {
        botaoVoltar.addActionListener(listener -> {
            telaAnterior.setVisible(true);
            dispose();
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de listagem de editoras.
     */
    private void configurarAcaoListarEditoras() {
        botaoListarEditoras.addActionListener(listener -> {
            TelaListagemEditora telaListagemEditora = new TelaListagemEditora(this);
            telaListagemEditora.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de cadastrar editora.
     */
    private void configurarAcaoCadastrarEditora() {
        botaoCadastrarEditora.addActionListener(listener -> {
            TelaFormularioEditora formularioEditora = new TelaFormularioEditora(this);
            formularioEditora.setVisible(true);
            this.setVisible(false);
        });
    }
}
