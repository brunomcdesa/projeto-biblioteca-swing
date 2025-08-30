package biblioteca.telas;

import biblioteca.backend.facade.AutorFacade;
import biblioteca.backend.facade.EditoraFacade;
import biblioteca.backend.facade.LivroFacade;
import biblioteca.telas.autor.TelaListagemAutor;
import biblioteca.telas.editora.TelaListagemEditora;
import biblioteca.telas.livro.TelaListagemLivro;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.TelasUtils.*;
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

    private final JButton botaoGerenciaLivro = criarBotao("Gerenciar Livros");
    private final JButton botaoGerenciaAutor = criarBotao("Gerenciar Autores");
    private final JButton botaoGerenciaEditora = criarBotao("Gerenciar Editoras");

    public TelaPrincipal(AutorFacade autorFacade, EditoraFacade editoraFacade, LivroFacade livroFacade) {
        super("Biblioteca");

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes(autorFacade, editoraFacade, livroFacade);
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

        adicionarConfiguracoesPadroesTela(this, painelPrincipal);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = criarPainelPadrao();

        botaoGerenciaLivro.setAlignmentX(CENTER_ALIGNMENT);
        botaoGerenciaAutor.setAlignmentX(CENTER_ALIGNMENT);
        botaoGerenciaEditora.setAlignmentX(CENTER_ALIGNMENT);

        painelBotoes.add(botaoGerenciaLivro);
        painelBotoes.add(criarLinhaSeparacao());
        painelBotoes.add(botaoGerenciaAutor);
        painelBotoes.add(criarLinhaSeparacao());
        painelBotoes.add(botaoGerenciaEditora);

        painelPrincipal.add(painelBotoes);
    }

    /**
     * Configura os listeners dos botoes
     */
    private void configurarAcoesDosBotoes(AutorFacade autorFacade, EditoraFacade editoraFacade, LivroFacade livroFacade) {
        this.configurarAcaoDoBotaoGerenciarAutores(autorFacade);
        this.configurarAcaoDoBotaoGerenciarEditoras(editoraFacade);
        this.configurarAcaoDoBotaoGerenciarLivros(livroFacade);
    }

    /**
     * Adiciona um listener para interceptar o evento de gerenciar Autores.
     */
    private void configurarAcaoDoBotaoGerenciarAutores(AutorFacade autorFacade) {
        botaoGerenciaAutor.addActionListener(listener -> {
            TelaListagemAutor telaListageAutor = new TelaListagemAutor(this, autorFacade);
            telaListageAutor.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de gerenciar Editoras.
     */
    private void configurarAcaoDoBotaoGerenciarEditoras(EditoraFacade editoraFacade) {
        botaoGerenciaEditora.addActionListener(listener -> {
            TelaListagemEditora telaListagemEditora = new TelaListagemEditora(this, editoraFacade);
            telaListagemEditora.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de gerenciar Livros.
     */
    private void configurarAcaoDoBotaoGerenciarLivros(LivroFacade livroFacade) {
        botaoGerenciaLivro.addActionListener(listener -> {
            TelaListagemLivro telaListagemLivro = new TelaListagemLivro(this, livroFacade);
            telaListagemLivro.setVisible(true);
            this.setVisible(false);
        });
    }
}
