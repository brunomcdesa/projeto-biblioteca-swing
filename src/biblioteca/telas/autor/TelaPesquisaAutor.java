package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorFiltros;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.facade.AutorFacade;
import biblioteca.telas.autor.table.AutorTable;

import javax.swing.*;
import java.util.List;

import static biblioteca.utils.StringUtils.isNotBlank;
import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Tela de Listagem de Autores de acordo com os filtros informados.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a listagem dos autores
 * de acordo com os filtros passados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaPesquisaAutor extends JFrame {

    private final JFrame telaAnterior;
    private final AutorFacade autorFacade;
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoBuscar = criarBotao("Buscar");
    private final AutorTable autorTable = new AutorTable();
    private final JTable tabela = new JTable(autorTable);

    private JTextField filtroNome;
    private JTextField filtroIdade;
    private JTextField filtroTituloLivro;

    public TelaPesquisaAutor(JFrame telaAnterior, AutorFacade autorFacade) {
        super("Pesquisar autores");
        this.telaAnterior = telaAnterior;
        this.autorFacade = autorFacade;

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
        JPanel painelBotoes = criarPainelBotoesListagem(botaoVoltar, botaoBuscar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    /**
     * Adiciona configurações de cmapos de filtragem.
     */
    private void aplicarConfiguracoesFiltros(JPanel painelPrincipal) {
        this.filtroNome = criarTextField("");
        this.filtroIdade = criarTextField("");
        this.filtroTituloLivro = criarTextField("");
        JPanel painelFiltros = criarPainelFiltros(
                criarLinhaFiltro("Nome: ", filtroNome),
                criarLinhaFiltro("Idade: ", filtroIdade),
                criarLinhaFiltro("Título do Livro: ", filtroTituloLivro)
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
            String idadeText = filtroIdade.getText();
            String tituloLivro = filtroTituloLivro.getText();

            Integer idade = null;
            if (isNotBlank(idadeText)) {
                try {
                    idade = Integer.parseInt(idadeText);
                } catch (NumberFormatException e) {
                    showMessageDialog(this, "A idade deve ser um número válido!", "Erro de Formato", ERROR_MESSAGE);
                    return;
                }
            }

            AutorFiltros filtros = new AutorFiltros(nome, idade, tituloLivro);
            List<AutorResponse> autores = autorFacade.listarPorFiltros(filtros);
            autorTable.setAutores(autores);
        });
    }
}
