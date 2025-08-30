package biblioteca.telas.livro;

import biblioteca.backend.dto.LivroFiltros;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.enums.EGenero;
import biblioteca.backend.facade.LivroFacade;
import biblioteca.telas.livro.table.LivroTable;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

import static biblioteca.utils.MapUtils.mapNull;
import static biblioteca.utils.StringUtils.*;
import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

/**
 * Tela de Listagem de Livros de acordo com os filtros informados.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a listagem dos livros
 * de acordo com os filtros passados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaPesquisaLivro extends JFrame {

    private final JFrame telaAnterior;
    private final LivroFacade livroFacade;
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoLimparFiltros = criarBotao("Limpar Filtros");
    private final JButton botaoBuscar = criarBotao("Buscar");
    private final LivroTable livroTable = new LivroTable();
    private final JTable tabela = new JTable(livroTable);

    private JTextField filtroId;
    private JTextField filtroTitulo;
    private JTextField filtroDataPublicacao;
    private JTextField filtroIsbn;
    private JComboBox<SelectResponse> filtroGenero;
    private JComboBox<SelectResponse> filtroEditora;
    private JComboBox<SelectResponse> filtroAutor;
    private JTextField filtroIdLivroParecido;
    private JTextField filtroTituloLivroParecido;

    public TelaPesquisaLivro(JFrame telaAnterior, LivroFacade livroFacade) {
        super("Pesquisar Livros");
        this.telaAnterior = telaAnterior;
        this.livroFacade = livroFacade;

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipalListagem(tabela);

        this.aplicarConfiguracoesFiltros(painelPrincipal);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        adicionarConfiguracoesPadroesTela(this, painelPrincipal);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = criarPainelBotoesListagem(botaoVoltar, botaoLimparFiltros, botaoBuscar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    /**
     * Adiciona configurações de cmapos de filtragem.
     */
    private void aplicarConfiguracoesFiltros(JPanel painelPrincipal) {
        this.filtroId = criarTextField("");
        this.filtroTitulo = criarTextField("");
        this.filtroDataPublicacao = criarTextField("");
        this.filtroIsbn = criarTextField("");
        this.filtroGenero = criarSelect(carregarSelectGenero());
        this.filtroEditora = criarSelect(carregarSelectEditora());
        this.filtroAutor = criarSelect(carregarSelectAutores());
        this.filtroIdLivroParecido = criarTextField("");
        this.filtroTituloLivroParecido = criarTextField("");

        JPanel painelFiltros = criarPainelFiltros(
                criarLinhaFiltro("ID: ", filtroId),
                criarLinhaFiltro("Título: ", filtroTitulo),
                criarLinhaFiltro("Data de Publicação: ", filtroDataPublicacao),
                criarLinhaFiltro("ISBN: ", filtroIsbn),
                criarLinhaFiltro("Gênero: ", filtroGenero),
                criarLinhaFiltro("Editora: ", filtroEditora),
                criarLinhaFiltro("Autor: ", filtroAutor),
                criarLinhaFiltro("ID do Livro Parecido: ", filtroIdLivroParecido),
                criarLinhaFiltro("Título do Livro Parecido: ", filtroTituloLivroParecido)
        );

        painelPrincipal.add(painelFiltros, NORTH);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoLimparFiltros();
        this.configurarAcaoBotaoBuscar();
    }

    /**
     * Efetua a configuração da ação do botao de voltar para a tela anterior.
     */
    private void configurarAcaoBotaoVoltar() {
        botaoVoltar.addActionListener(listener -> {
            telaAnterior.setVisible(true);
            dispose();
        });
    }

    /**
     * Efetua a configuração da ação do botao de limpar os filtros.
     */
    private void configurarAcaoBotaoLimparFiltros() {
        botaoLimparFiltros.addActionListener(listener -> {
            this.filtroId.setText("");
            this.filtroTitulo.setText("");
            this.filtroDataPublicacao.setText("");
            this.filtroIsbn.setText("");
            this.filtroGenero.setSelectedIndex(0);
            this.filtroEditora.setSelectedIndex(0);
            this.filtroAutor.setSelectedIndex(0);
            this.filtroIdLivroParecido.setText("");
            this.filtroTituloLivroParecido.setText("");
        });
    }

    /**
     * Efetua a configuração da ação do botao de buscar dados.
     */
    private void configurarAcaoBotaoBuscar() {
        botaoBuscar.addActionListener(listener -> {
            String idText = filtroId.getText();
            String titulo = filtroTitulo.getText();
            String dataPublicacaoText = filtroDataPublicacao.getText();
            String isbn = filtroIsbn.getText();
            SelectResponse generoSelecionado = (SelectResponse) filtroGenero.getSelectedItem();
            SelectResponse editoraSelecionada = (SelectResponse) filtroEditora.getSelectedItem();
            SelectResponse autorSelecionado = (SelectResponse) filtroAutor.getSelectedItem();
            String idLivroParecidoText = filtroIdLivroParecido.getText();
            String tituloLivroParecido = filtroTituloLivroParecido.getText();

            Integer id = converterStringEmInteger(idText, "ID", this);
            LocalDate dataPublicacao = isNotBlank(dataPublicacaoText)
                    ? converterStringParaLocalDate(dataPublicacaoText, "Data de Publicação", this)
                    : null;
            Integer idLivroParecido = converterStringEmInteger(idLivroParecidoText, "ID do Livro", this);

            LivroFiltros filtros = new LivroFiltros(id, titulo, dataPublicacao, isbn,
                    mapNull(generoSelecionado, generoSelect -> (EGenero) generoSelect.getValue()),
                    mapNull(editoraSelecionada, editoraSelect -> (Integer) editoraSelect.getValue()),
                    mapNull(autorSelecionado, autorSelect -> (Integer) autorSelect.getValue()),
                    idLivroParecido, tituloLivroParecido);
            List<LivroResponse> livros = livroFacade.listarPorFiltros(filtros);
            livroTable.setLivros(livros);
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
}
