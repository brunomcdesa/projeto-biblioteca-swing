package biblioteca.telas.livro;

import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.facade.LivroFacade;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.TelasUtils.*;
import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.formatarData;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;

/**
 * Tela de cadastro de Livro.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a o cadastro de livro,
 * onde vai ser mostrado os campos para que um novo livro seja cadastrado no sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaFormularioLivro extends JFrame {

    private final JFrame telaAnterior;
    private final LivroFacade livroFacade;
    private final JButton botaoSalvar = criarBotao("Salvar");
    private final JButton botaoVoltar = criarBotao("Voltar");

    private JTextField campoTitulo;
    private JTextField campoDataPublicacao;
    private JTextField campoIsbm;
    private JComboBox<String> campoGenero;
    private JComboBox<String> campoEditora;
    private JList<String> campoAutores;

    public TelaFormularioLivro(JFrame telaAnterior, LivroFacade livroFacade) {
        this(telaAnterior, livroFacade, null);
    }

    public TelaFormularioLivro(JFrame telaAnterior, LivroFacade livroFacade, LivroResponse livro) {
        super(mapNullComBackup(livro,"Editar Livro", "Cadastar Livro"));
        this.telaAnterior = telaAnterior;
        this.livroFacade = livroFacade;

        this.inicializarComponentes(livro);
        this.configurarAcoesDosBotoes(livro);
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes(LivroResponse livro) {
        JPanel painelPrincipal = criarPainelPrincipalFormulario("Preencha os dados do livro:");
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
        JPanel painelFormulario = criarPainelFormulario();
        this.configurarCamposFormulario(livro, painelFormulario);

        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    /**
     * Cria e define os campos do formulário.
     */
    private void configurarCamposFormulario(LivroResponse livro, JPanel painelFormulario) {
        this.campoTitulo = criarTextField(mapNullComBackup(livro, LivroResponse::getTitulo, ""));
        this.campoDataPublicacao = criarTextField(mapNullComBackup(livro, livroResponse -> formatarData(livroResponse.getDataPublicacao()), ""));
        this.campoIsbm = criarTextField(mapNullComBackup(livro, LivroResponse::getIsbn, ""));
        this.campoGenero = criarSelect(this.carregarSelectGenero());
        this.campoEditora = criarSelect(this.carregarSelectEditora());
        this.campoAutores = criarMultiSelect(this.carregarMultiSelectAutores());
        JScrollPane scrollAutores = new JScrollPane(campoAutores);
        scrollAutores.setPreferredSize(new Dimension(100, 50));

        painelFormulario.add(criarLinhaFormulario("Título:", campoTitulo));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("Data de publicação (dd/MM/yyyy):", campoDataPublicacao));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("ISBM:", campoIsbm));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("Gênero:", campoGenero));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("Editora:", campoEditora));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("Autores:", scrollAutores));
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

    /**
     * Carrega os dados que serão utilizados no campo select de Gênero.
     */
    private String[] carregarSelectGenero() {
        return livroFacade.getSelectGenero();
    }

    /**
     * Carrega os dados que serão utilizados no campo select de Editora.
     */
    private String[] carregarSelectEditora() {
        return livroFacade.getSelectEditora();
    }

    /**
     * Carrega os dados que serão utilizados no campo multi select de Autores.
     */
    private String[] carregarMultiSelectAutores() {
        return livroFacade.getMultiSelectAutores();
    }
}
