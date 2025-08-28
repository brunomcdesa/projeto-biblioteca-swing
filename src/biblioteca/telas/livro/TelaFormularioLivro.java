package biblioteca.telas.livro;

import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.facade.LivroFacade;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.*;
import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.JOptionPane.*;

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
    private JTextField campoIsbn;
    private JComboBox<SelectResponse> campoGenero;
    private JComboBox<SelectResponse> campoEditora;
    private JList<SelectResponse> campoAutores;

    public TelaFormularioLivro(JFrame telaAnterior, LivroFacade livroFacade) {
        this(telaAnterior, livroFacade, null);
    }

    public TelaFormularioLivro(JFrame telaAnterior, LivroFacade livroFacade, LivroResponse livro) {
        super(mapNullComBackup(livro, "Editar Livro", "Cadastar Livro"));
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
        this.campoIsbn = criarTextField(mapNullComBackup(livro, LivroResponse::getIsbn, ""));
        this.campoGenero = criarSelect(this.carregarSelectGenero());
        this.campoEditora = criarSelect(this.carregarSelectEditora());
        this.campoAutores = criarMultiSelect(this.carregarSelectAutores());
        JScrollPane scrollAutores = new JScrollPane(campoAutores);
        scrollAutores.setPreferredSize(new Dimension(100, 50));

        atribuirValoresSelecionadosCamposSelect(livro);

        painelFormulario.add(criarLinhaFormulario("Título:", campoTitulo));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("Data de publicação (dd/MM/yyyy):", campoDataPublicacao));
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFormulario.add(criarLinhaFormulario("ISBN:", campoIsbn));
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
            this.dispose();
        });
    }

    /**
     * Configura a ação de salvar um novo livro ou de editar um livro.
     * Envia os dados para a service de Editora, para salvar no banco de dados.
     */
    private void configurarAcaoBotaoSalvar(LivroResponse livro) {
        botaoSalvar.addActionListener(listener -> {
            String titulo = campoTitulo.getText();
            String dataPublicacaoText = campoDataPublicacao.getText();
            String isbn = campoIsbn.getText();
            SelectResponse generoSelecionado = (SelectResponse) campoGenero.getSelectedItem();
            SelectResponse editoraSelecionada = (SelectResponse) campoEditora.getSelectedItem();
            List<SelectResponse> autoresSelecionados = campoAutores.getSelectedValuesList();

            if (isBlank(titulo) || isBlank(dataPublicacaoText) || isBlank(isbn) || generoSelecionado == null
                    || editoraSelecionada == null || autoresSelecionados.isEmpty()) {
                showMessageDialog(this, "Todos os campos são obrigatórios!",
                        "Erro de Validação", ERROR_MESSAGE);
                return;
            }

            if (!isDataValida(dataPublicacaoText)) {
                showMessageDialog(this, "Data de Publicação inválida! Insira a data de publicação no formato dd/MM/yyyy.",
                        "Erro de Formato", ERROR_MESSAGE);
                return;
            }

            List<Integer> autoresSelecionadosIds = autoresSelecionados.stream()
                    .map(autor -> (Integer) autor.getValue())
                    .collect(Collectors.toList());

            LivroRequest request = new LivroRequest(titulo, isbn, (EGenero) generoSelecionado.getValue(),
                    converterStringParaLocalDate(dataPublicacaoText), (Integer) editoraSelecionada.getValue(),
                    autoresSelecionadosIds);

            if (livro == null) {
                livroFacade.salvarLivro(request);
            } else {
                livroFacade.editarLivro(livro.getId(), request);
            }
            showMessageDialog(this, "Livro salvo com sucesso!", "Sucesso", INFORMATION_MESSAGE);

            telaAnterior.setVisible(true);
            this.dispose();
        });
    }

    /**
     * Carrega os dados que serão utilizados no campo select de Gênero.
     */
    private SelectResponse[] carregarSelectGenero() {
        return livroFacade.getSelectGenero();
    }

    /**
     * Carrega os dados que serão utilizados no campo select de Editora.
     */
    private SelectResponse[] carregarSelectEditora() {
        return livroFacade.getSelectEditora();
    }

    /**
     * Carrega os dados que serão utilizados no campo multi select de Autores.
     */
    private SelectResponse[] carregarSelectAutores() {
        return livroFacade.getSelectAutores();
    }

    /**
     * Método responsável por atribuir os valores já selecionados nos campos select, para o caso de ser uma edição.
     */
    private void atribuirValoresSelecionadosCamposSelect(LivroResponse livro) {
        if (livro != null) {
            atribuirItemSelecionado(campoGenero, livro.getGenero());
            atribuirItemSelecionado(campoEditora, livro.getEditora().getId());
            atribuirItensSelecionados(campoAutores, livro.getAutoresIdsObjects());
        }
    }
}
