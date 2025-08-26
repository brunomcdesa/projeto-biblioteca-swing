package biblioteca.telas.autor;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * Tela principal de gerencia de Autor.
 * <p>
 * Esta classe é responsável por renderizar a tela principal da gerencia de Autores,
 * onde vai ser feito o redirecionamento para as telas referente a listagem/adição de autores.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaGerenciaAutor extends JFrame {

    private final JFrame telaAnterior;
    private final JButton botaoListarAutores = new JButton("Listar Autores");
    private final JButton botaoCadastrarAutor = new JButton("Cadastrar Novo Autor");
    private final JButton botaoVoltar = new JButton("Voltar");

    public TelaGerenciaAutor(JFrame telaAnterior) {
        super("Gerenciar Autores");
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
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoDoBotaoListagemAutores();
        this.configurarAcaoDoBotaoCadastroAutor();
        this.configurarAcaoBotaoVoltar();
    }

    /**
     * Adiciona um listener para interceptar o evento de listagem de Autores.
     */
    private void configurarAcaoDoBotaoListagemAutores() {
        botaoListarAutores.addActionListener(listener -> {
            TelaListagemAutor telaListagem = new TelaListagemAutor(this);
            telaListagem.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de cadastro de Autor.
     */
    private void configurarAcaoDoBotaoCadastroAutor() {
        botaoCadastrarAutor.addActionListener(listener -> {
            TelaFormularioAutor telaCadastroAutor = new TelaFormularioAutor(this);
            telaCadastroAutor.setVisible(true);
            this.setVisible(false);
        });
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
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        botaoListarAutores.setAlignmentX(CENTER_ALIGNMENT);
        botaoCadastrarAutor.setAlignmentX(CENTER_ALIGNMENT);
        botaoVoltar.setAlignmentX(CENTER_ALIGNMENT);

        painelBotoes.add(botaoListarAutores);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelBotoes.add(botaoCadastrarAutor);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 300)));
        painelBotoes.add(botaoVoltar);

        painelPrincipal.add(painelBotoes);
    }
}
