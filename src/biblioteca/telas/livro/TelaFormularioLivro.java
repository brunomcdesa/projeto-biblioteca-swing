package biblioteca.telas.livro;

import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.service.LivroService;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.FormUtils.*;
import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.formatarData;
import static java.awt.BorderLayout.*;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;

public class TelaFormularioLivro extends JFrame {

    private final JFrame telaAnterior;
    private final LivroService livroService;
    private final JButton botaoSalvar = criarBotao("Salvar");
    private final JButton botaoVoltar = criarBotao("Voltar");

    private JTextField campoTitulo;
    private JTextField campoDataPublicacao;
    private JTextField campoIsbm;
    private JTextField campoGenero; // trocar para select
    private JTextField campoEditora; // trocar para select
    private JTextField campoAutores; // trocar para multiselect

    public TelaFormularioLivro(JFrame telaAnterior) {
        this(telaAnterior, null);
    }

    public TelaFormularioLivro(JFrame telaAnterior, LivroResponse livro) {
        super("Cadastar Livro");
        this.telaAnterior = telaAnterior;
        this.livroService = new LivroService();

        this.inicializarComponentes(livro);
        this.configurarAcoesDosBotoes(livro);
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes(LivroResponse livro) {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(createEmptyBorder(50, 50, 50, 50));
        painelPrincipal.add(new JLabel("Preencha os dados do livro:", SwingConstants.CENTER), NORTH);
        this.aplicarConfiguracoesFormulario(painelPrincipal, livro);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);


        add(painelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal, LivroResponse livro) {
        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));

       this.configurarCamposFormulario(livro, painelFormulario);

        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    private void configurarCamposFormulario(LivroResponse livro, JPanel painelFormulario) {
        JLabel labelTitulo = criarLabel("Título:");
        JTextField campoTitulo = formatarTextField(this.campoTitulo, mapNullComBackup(livro, LivroResponse::getTitulo, ""));
        JLabel labelDataPublicacao = criarLabel("Data de publicação (dd/MM/yyyy):");
        JTextField campoDataPublicacao = formatarTextField(this.campoDataPublicacao, mapNullComBackup(livro, livroResponse -> formatarData(livroResponse.getDataPublicacao()), ""));
        JLabel labelIsbm = criarLabel("ISBM:");
        JTextField campoIsbm = formatarTextField(this.campoIsbm, mapNullComBackup(livro, LivroResponse::getIsbn, ""));
        JLabel labelGenero = criarLabel("Gênero:");
        JTextField campoGenero = formatarTextField(this.campoGenero, mapNullComBackup(livro, LivroResponse::getGenero, ""));

        painelFormulario.add(labelTitulo);
        painelFormulario.add(campoTitulo);
        painelFormulario.add(labelDataPublicacao);
        painelFormulario.add(campoDataPublicacao);
        painelFormulario.add(labelIsbm);
        painelFormulario.add(campoIsbm);
        painelFormulario.add(labelGenero);
        painelFormulario.add(campoGenero);
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
    private void configurarAcoesDosBotoes(LivroResponse livro) {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoSalvar(livro);
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
     * Configura a ação de salvar um novo livro ou de editar um livro.
     * Envia os dados para a service de Editora, para salvar no banco de dados.
     */
    private void configurarAcaoBotaoSalvar(LivroResponse livroResponse) {
        botaoSalvar.addActionListener(listener -> {

        });
    }
}
