package biblioteca.telas;

import biblioteca.backend.utils.JpaUtil;
import biblioteca.telas.autor.TelaGerenciaAutor;

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

    private JButton botaoGerenciaAutor;

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

        JPanel painelBotoes = new JPanel();
        this.aplicarConfiguracoesVisuaisBotoes(painelBotoes);
        painelPrincipal.add(botaoGerenciaAutor);

        add(painelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Configura os listeners dos botoes
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoDoBotaoGerenciarAutores();
    }

    /**
     * Adiciona um listener para interceptar o evento de listagem de Autores.
     */
    private void configurarAcaoDoBotaoGerenciarAutores() {
        botaoGerenciaAutor.addActionListener(listener -> {
            TelaGerenciaAutor telaGerenciaAutor = new TelaGerenciaAutor(this);
            telaGerenciaAutor.setVisible(true);
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

    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelBotoes) {
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        botaoGerenciaAutor = new JButton("Gerenciar Autores");

        painelBotoes.add(botaoGerenciaAutor);
    }
}
