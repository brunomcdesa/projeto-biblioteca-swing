package biblioteca.telas.editora;

import biblioteca.backend.dto.EditoraFiltros;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.facade.EditoraFacade;
import biblioteca.telas.editora.table.EditoraTable;

import javax.swing.*;
import java.util.List;

import static biblioteca.utils.StringUtils.converterStringEmInteger;
import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

/**
 * Tela de Listagem de Editoras de acordo com os filtros informados.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a listagem das editoras
 * de acordo com os filtros passados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaPesquisaEditora extends JFrame {

    private final JFrame telaAnterior;
    private final EditoraFacade editoraFacade;
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoLimparFiltros = criarBotao("Limpar Filtros");
    private final JButton botaoBuscar = criarBotao("Buscar");
    private final EditoraTable editoraTable = new EditoraTable();
    private final JTable tabela = new JTable(editoraTable);

    private JTextField filtroId;
    private JTextField filtroNome;
    private JTextField filtroCnpj;
    private JTextField filtroIdLivro;
    private JTextField filtroTituloLivro;

    public TelaPesquisaEditora(JFrame telaAnterior, EditoraFacade editoraFacade) {
        super("Pesquisar Editoras");
        this.telaAnterior = telaAnterior;
        this.editoraFacade = editoraFacade;

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
        this.filtroNome = criarTextField("");
        this.filtroCnpj = criarTextField("");
        this.filtroIdLivro = criarTextField("");
        this.filtroTituloLivro = criarTextField("");
        JPanel painelFiltros = criarPainelFiltros(
                criarLinhaFiltro("ID: ", filtroId),
                criarLinhaFiltro("Nome: ", filtroNome),
                criarLinhaFiltro("Cnpj: ", filtroCnpj),
                criarLinhaFiltro("ID do Livro: ", filtroIdLivro),
                criarLinhaFiltro("Título do Livro: ", filtroTituloLivro)
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
            this.filtroNome.setText("");
            this.filtroCnpj.setText("");
            this.filtroIdLivro.setText("");
            this.filtroTituloLivro.setText("");
        });
    }

    /**
     * Efetua a configuração da ação do botao de buscar dados.
     */
    private void configurarAcaoBotaoBuscar() {
        botaoBuscar.addActionListener(listener -> {
            String idText = filtroId.getText();
            String nome = filtroNome.getText();
            String cnpj = filtroCnpj.getText();
            String idLivroText = filtroIdLivro.getText();
            String tituloLivro = filtroTituloLivro.getText();

            Integer id = converterStringEmInteger(idText, "ID", this);
            Integer idLivro = converterStringEmInteger(idLivroText, "ID do Livro", this);

            EditoraFiltros filtros = new EditoraFiltros(id, nome, cnpj, idLivro, tituloLivro);
            List<EditoraResponse> editoras = editoraFacade.listarPorFiltros(filtros);
            editoraTable.setEditoras(editoras);
        });
    }
}
