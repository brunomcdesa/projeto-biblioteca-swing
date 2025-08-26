package biblioteca.telas;

import biblioteca.backend.utils.JpaUtil;
import biblioteca.telas.autor.TelaListagemAutor;
import biblioteca.telas.editora.TelaListagemEditora;
import biblioteca.telas.livro.TelaListagemLivro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.BorderLayout.NORTH;
import static java.awt.Font.BOLD;
import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * Tela principal do projeto.
 * <p>
 * Esta classe é responsável por renderizar a tela principal do sistema
 * onde vai ser feita a inicialização da conexão com o banco de dados
 * e o redirecionamento para outras telas.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaPrincipal extends JFrame {

    private final JButton botaoGerenciaLivro = new JButton("Gerenciar Livros");
    private final JButton botaoGerenciaAutor = new JButton("Gerenciar Autores");
    private final JButton botaoGerenciaEditora = new JButton("Gerenciar Editoras");

    public TelaPrincipal() {
        super("Biblioteca");

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
        this.configurarListenerDeJanela();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Bem-vindo ao Sistema de Biblioteca!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", BOLD, 18));
        painelPrincipal.add(label, NORTH);

        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        add(painelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        botaoGerenciaLivro.setAlignmentX(CENTER_ALIGNMENT);
        botaoGerenciaAutor.setAlignmentX(CENTER_ALIGNMENT);
        botaoGerenciaEditora.setAlignmentX(CENTER_ALIGNMENT);

        painelBotoes.add(botaoGerenciaLivro);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelBotoes.add(botaoGerenciaAutor);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelBotoes.add(botaoGerenciaEditora);

        painelPrincipal.add(painelBotoes);
    }

    /**
     * Configura os listeners dos botoes
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoDoBotaoGerenciarAutores();
        this.configurarAcaoDoBotaoGerenciarEditoras();
        this.configurarAcaoDoBotaoGerenciarLivros();
    }

    /**
     * Adiciona um listener para interceptar o evento de gerenciar Autores.
     */
    private void configurarAcaoDoBotaoGerenciarAutores() {
        botaoGerenciaAutor.addActionListener(listener -> {
            TelaListagemAutor telaListageAutor = new TelaListagemAutor(this);
            telaListageAutor.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de gerenciar Editoras.
     */
    private void configurarAcaoDoBotaoGerenciarEditoras() {
        botaoGerenciaEditora.addActionListener(listener -> {
            TelaListagemEditora telaListagemEditora = new TelaListagemEditora(this);
            telaListagemEditora.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de gerenciar Livros.
     */
    private void configurarAcaoDoBotaoGerenciarLivros() {
        botaoGerenciaLivro.addActionListener(listener -> {
            TelaListagemLivro telaListagemLivro = new TelaListagemLivro(this);
            telaListagemLivro.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de fechamento da janela.
     */
    private void configurarListenerDeJanela() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                JpaUtil.fecharConexao();
            }
        });
    }
}
