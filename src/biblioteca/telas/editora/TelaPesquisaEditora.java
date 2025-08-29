package biblioteca.telas.editora;

import biblioteca.backend.dto.EditoraFiltros;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.facade.EditoraFacade;
import biblioteca.telas.editora.table.EditoraTable;

import javax.swing.*;

import java.util.List;

import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

public class TelaPesquisaEditora extends JFrame {

    private final JFrame telaAnterior;
    private final EditoraFacade editoraFacade;
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoBuscar = criarBotao("Buscar");
    private final EditoraTable editoraTable = new EditoraTable();
    private final JTable tabela = new JTable(editoraTable);

    private JTextField filtroNome;
    private JTextField filtroCnpj;
    private JTextField filtroNomeLivro;

    public TelaPesquisaEditora(JFrame telaAnterior, EditoraFacade editoraFacade) {
        super("Pesquisar editoras");
        this.telaAnterior = telaAnterior;
        this.editoraFacade = editoraFacade;

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipalListagem(tabela);

        this.aplicarConfiguracoesFiltros(painelPrincipal);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);
        add(painelPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = criarPainelBotoesListagem(botaoVoltar, botaoBuscar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    private void aplicarConfiguracoesFiltros(JPanel painelPrincipal) {
        this.filtroNome = criarTextField("");
        this.filtroCnpj = criarTextField("");
        this.filtroNomeLivro = criarTextField("");
        JPanel painelFiltros = criarPainelFiltros(
                criarLinhaFiltro("Nome: ", filtroNome),
                criarLinhaFiltro("Cnpj: ", filtroCnpj),
                criarLinhaFiltro("Nome do Livro: ",  filtroNomeLivro)
        );

        painelPrincipal.add(painelFiltros, NORTH);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoBotaoVoltar();
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
     * Efetua a configuração da ação do botao de buscar dados.
     */
    private void configurarAcaoBotaoBuscar() {
        botaoBuscar.addActionListener(listener -> {
            String nome = filtroNome.getText();
            String cnpj = filtroCnpj.getText();
            String nomeLivro = filtroNomeLivro.getText();

            EditoraFiltros filtros = new EditoraFiltros(nome, cnpj, nomeLivro);
            List<EditoraResponse> editoras = editoraFacade.listarPorFiltros(filtros);
            editoraTable.setEditoras(editoras);
        });
    }
}
