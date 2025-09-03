package biblioteca.telas.livro;

import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.facade.LivroFacade;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static biblioteca.utils.MapUtils.mapNull;
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
 * Esta classe é responsável por renderizar a tela referente ao cadastro de livro,
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
    private JList<SelectResponse> campoLivrosParecidos;

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
        JPanel painelPrincipal = criarPainelPrincipalFormulario("Preencha os dados do Livro (Campos com * são obrigatórios):");
        this.aplicarConfiguracoesFormulario(painelPrincipal, livro);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        adicionarConfiguracoesPadroesTela(this, painelPrincipal);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal, LivroResponse livro) {
        JPanel painelFormulario = criarPainelPadrao();
        painelFormulario.setLayout(new GridLayout(0, 3, 10, 10));
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
        this.campoLivrosParecidos = criarMultiSelect(this.carregarSelectLivros(mapNull(livro, LivroResponse::getId)));

        JScrollPane scrollAutores = montarScrollDeCampoMultiSelect(campoAutores);
        JScrollPane scrollLivrosParecidos = montarScrollDeCampoMultiSelect(campoLivrosParecidos);

        atribuirValoresSelecionadosCamposSelect(livro);

        painelFormulario.add(criarLinhaFormulario("Título *:", campoTitulo));
        painelFormulario.add(criarLinhaFormulario("Data de publicação *:", campoDataPublicacao));
        painelFormulario.add(criarLinhaFormulario("ISBN *:", campoIsbn));
        painelFormulario.add(criarLinhaFormulario("Gênero *:", campoGenero));
        painelFormulario.add(criarLinhaFormulario("Editora *:", campoEditora));
        painelFormulario.add(criarLinhaFormulario("Autores *:", scrollAutores));
        painelFormulario.add(criarLinhaFormulario("Livros Parecidos:", scrollLivrosParecidos));
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
            List<SelectResponse> livrosParecidosSelecionados = campoLivrosParecidos.getSelectedValuesList();

            validarCamposStringObrigatorios(this, titulo, dataPublicacaoText, isbn);
            if (generoSelecionado == null || editoraSelecionada == null || autoresSelecionados.isEmpty()) {
                showMessageDialog(this, "Campos obrigatórios inválidos!", "Erro de Validação", ERROR_MESSAGE);
                return;
            }

            LocalDate dataPublicacao = converterCampoStringParaLocalDate(dataPublicacaoText, "Data de Publicação", this);

            List<Integer> autoresSelecionadosIds = mapearValuesDeSelectResponsesParaIntegers(autoresSelecionados);
            List<Integer> livrosParecidosSelecionadosIds = mapearValuesDeSelectResponsesParaIntegers(livrosParecidosSelecionados);

            LivroRequest request = new LivroRequest(titulo, isbn, (EGenero) generoSelecionado.getValue(), dataPublicacao,
                    (Integer) editoraSelecionada.getValue(), autoresSelecionadosIds, livrosParecidosSelecionadosIds);

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
    private List<SelectResponse> carregarSelectGenero() {
        return livroFacade.getSelectGenero();
    }

    /**
     * Carrega os dados que serão utilizados no campo select de Editora.
     */
    private List<SelectResponse> carregarSelectEditora() {
        return livroFacade.getSelectEditora();
    }

    /**
     * Carrega os dados que serão utilizados no campo multi select de Autores.
     */
    private List<SelectResponse> carregarSelectAutores() {
        return livroFacade.getSelectAutores();
    }

    /**
     * Carrega os dados que serão utilizados no campo multi select de Livros Parecidos.
     */
    private List<SelectResponse> carregarSelectLivros(Integer id) {
        return livroFacade.getSelectLivros(id);
    }

    /**
     * Método responsável por atribuir os valores já selecionados nos campos select, para o caso de ser uma edição.
     */
    private void atribuirValoresSelecionadosCamposSelect(LivroResponse livro) {
        if (livro != null) {
            atribuirItemSelecionado(campoGenero, livro.getGenero());
            atribuirItemSelecionado(campoEditora, livro.getEditora().getId());
            atribuirItensSelecionados(campoAutores, livro.getAutoresIdsObjects());
            atribuirItensSelecionados(campoLivrosParecidos, livro.getLivrosParecidosIdsObjects());
        }
    }

    /**
     * Método responsável por mapear os atributos value dos valores selecionados no SelectResponse para uma lista de Ids.
     */
    private List<Integer> mapearValuesDeSelectResponsesParaIntegers(List<SelectResponse> selectResponses) {
        return selectResponses.stream()
                .map(select -> (Integer) select.getValue())
                .collect(Collectors.toList());
    }
}
