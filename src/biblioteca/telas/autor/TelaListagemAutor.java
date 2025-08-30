package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.facade.AutorFacade;
import biblioteca.telas.autor.table.AutorTable;
import lombok.extern.java.Log;

import javax.swing.*;
import java.util.List;

import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.*;

/**
 * Tela de listagem de autores do sistema.
 * <p>
 * Esta classe é responsável por renderizar em uma tabela, os dados dos Autores cadastrados no sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class TelaListagemAutor extends JFrame {

    private final JFrame telaAnterior;
    private final AutorFacade autorFacade;
    private final JButton botaoRecarregarDados = criarBotao("Recarregar Dados");
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoPesquisar = criarBotao("Pesquisar");
    private final JButton botaoDeletar = criarBotao("Deletar");
    private final JButton botaoEditar = criarBotao("Editar");
    private final JButton botaoCadastrar = criarBotao("Cadastrar");
    private final AutorTable autorTable = new AutorTable();
    private final JTable tabela = new JTable(autorTable);

    public TelaListagemAutor(JFrame telaAnterior, AutorFacade autorFacade) {
        super("Listagem de Autores");
        this.telaAnterior = telaAnterior;
        this.autorFacade = autorFacade;

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
        this.carregarDados();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipalListagem(tabela);

        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        adicionarConfiguracoesPadroesTela(this, painelPrincipal);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = criarPainelBotoesListagem(botaoVoltar, botaoRecarregarDados, botaoPesquisar, botaoDeletar,
                botaoEditar, botaoCadastrar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoAtualizar();
        this.configurarAcaoEditar();
        this.configurarAcaoBotaoDeletar();
        this.configurarAcaoBotaoCadastrar();
        this.configurarAcaoBotaoPesquisar();
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
     * Efetua a configuração da ação do botão de atualizar,
     * para que os dados sejam atualizados na tela quando clicado.
     */
    private void configurarAcaoBotaoAtualizar() {
        botaoRecarregarDados.addActionListener(listener -> {
            carregarDados();
        });
    }

    /**
     * Efetua a configuração da ação do botão de editar,
     * para que a linha selecionada seja editada.
     */
    private void configurarAcaoEditar() {
        botaoEditar.addActionListener(listener -> {
            int linhaSelecionada = tabela.getSelectedRow();
            boolean isLinhaValida = validarLinhaSelecionada(linhaSelecionada, this,
                    "Por favor, selecione um autor para editar.", "Nenhum autor selecionado");

            if (isLinhaValida) {
                AutorResponse autor = autorTable.getAutor(linhaSelecionada);
                TelaFormularioAutor formulario = new TelaFormularioAutor(this, autorFacade, autor);
                formulario.setVisible(true);
                this.setVisible(false);
            }
        });
    }

    /**
     * Efetua a configuração da ação do botão de deletar,
     * para que a linha selecionada seja deletada do banco de dados.
     */
    private void configurarAcaoBotaoDeletar() {
        botaoDeletar.addActionListener(listener -> {
            try {
                int linhaSelecionada = tabela.getSelectedRow();
                boolean isLinhaValida = validarLinhaSelecionada(linhaSelecionada, this,
                        "Por favor, selecione um autor para deletar.", "Nenhum autor selecionado");

                if (isLinhaValida) {
                    AutorResponse autor = autorTable.getAutor(linhaSelecionada);

                    autorFacade.deletarAutor(autor.getId());
                    showMessageDialog(this, "Autor deletado com sucesso!", "Sucesso", INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                showMessageDialog(this, ex.getMessage(), "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de cadastro de Autor.
     */
    private void configurarAcaoBotaoCadastrar() {
        botaoCadastrar.addActionListener(listener -> {
            TelaFormularioAutor telaCadastroAutor = new TelaFormularioAutor(this, autorFacade);
            telaCadastroAutor.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a configuração da ação do botão de pesquisar,
     * para que seja aberta uma nova tela de pesquisa de autores por filtros.
     */
    private void configurarAcaoBotaoPesquisar() {
        botaoPesquisar.addActionListener(listener -> {
            TelaPesquisaAutor telaPesquisaAutor = new TelaPesquisaAutor(this, autorFacade);
            telaPesquisaAutor.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a busca dos dados da listagem.
     */
    private void carregarDados() {
        try {
            List<AutorResponse> autores = autorFacade.listarTodosAutores();
            autorTable.setAutores(autores);
        } catch (Exception ex) {
            showMessageDialog(this, "Erro ao carregar autores do banco de dados.", "Erro", ERROR_MESSAGE);
            log.severe(ex.getMessage());
        }
    }
}
