package biblioteca.telas.editora;

import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.facade.EditoraFacade;
import biblioteca.telas.editora.table.EditoraTable;
import lombok.extern.java.Log;

import javax.swing.*;
import java.util.List;

import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.*;

/**
 * Tela de Listagem de Editoras.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a listagem das editoras
 * e também efetuar o gerenciamento de cadastro/edição/deleção de uma editora.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class TelaListagemEditora extends JFrame {

    private final JFrame telaAnterior;
    private final EditoraFacade editoraFacade;
    private final JButton botaoRecarregarDados = criarBotao("Recarregar Dados");
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoPesquisar = criarBotao("Pesquisar");
    private final JButton botaoDeletar = criarBotao("Deletar");
    private final JButton botaoEditar = criarBotao("Editar");
    private final JButton botaoCadastrar = criarBotao("Cadastrar");
    private final EditoraTable editoraTable = new EditoraTable();
    private final JTable tabela = new JTable(editoraTable);


    public TelaListagemEditora(JFrame telaAnterior, EditoraFacade editoraFacade) {
        super("Listagem de Editoras");
        this.telaAnterior = telaAnterior;
        this.editoraFacade = editoraFacade;

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
        JPanel painelBotoes = criarPainelBotoesListagem(botaoVoltar, botaoRecarregarDados, botaoPesquisar,
                botaoDeletar, botaoEditar, botaoCadastrar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoAtualizar();
        this.configurarAcaoBotaoEditar();
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
            this.dispose();
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
    private void configurarAcaoBotaoEditar() {
        botaoEditar.addActionListener(listener -> {
            int linhaSelecionada = tabela.getSelectedRow();
            boolean isLinhaValida = validarLinhaSelecionada(linhaSelecionada, this,
                    "Por favor, selecione uma editora para editar.", "Nenhuma editora selecionado");

            if (isLinhaValida) {
                EditoraResponse editora = editoraTable.getEditora(linhaSelecionada);
                TelaFormularioEditora formulario = new TelaFormularioEditora(this, editoraFacade, editora);
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
                        "Por favor, selecione uma editora para deletar.", "Nenhuma editora selecionada");

                if (isLinhaValida) {
                    EditoraResponse editora = editoraTable.getEditora(linhaSelecionada);

                    editoraFacade.deletarEditora(editora.getId());
                    showMessageDialog(this, "Editora deletada com sucesso!", "Sucesso", INFORMATION_MESSAGE);
                    carregarDados();
                }
            } catch (Exception ex) {
                showMessageDialog(this, ex.getMessage(), "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }

    /**
     * Efetua a configuração da ação do botão de cadastrar, para que redirecione para o formulário de cadastro.
     */
    private void configurarAcaoBotaoCadastrar() {
        botaoCadastrar.addActionListener(listener -> {
            TelaFormularioEditora formularioEditora = new TelaFormularioEditora(this, editoraFacade);
            formularioEditora.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a configuração da ação do botão de pesquisar,
     * para que seja aberta uma nova tela de pesquisa de editoras por filtros.
     */
    private void configurarAcaoBotaoPesquisar() {
        botaoPesquisar.addActionListener(listener -> {
            TelaPesquisaEditora telaPesquisaEditora = new TelaPesquisaEditora(this, editoraFacade);
            telaPesquisaEditora.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a busca dos dados da listagem.
     */
    private void carregarDados() {
        try {
            List<EditoraResponse> editoras = editoraFacade.listarTodasEditoras();
            editoraTable.setEditoras(editoras);
        } catch (Exception ex) {
            showMessageDialog(this, "Erro ao carregar editoras do banco de dados.", "Erro", ERROR_MESSAGE);
            log.severe(ex.getMessage());
        }
    }
}
