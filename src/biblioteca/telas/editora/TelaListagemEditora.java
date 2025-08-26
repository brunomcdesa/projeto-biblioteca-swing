package biblioteca.telas.editora;

import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.service.EditoraService;
import biblioteca.telas.editora.table.EditoraTable;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static biblioteca.utils.FormUtils.validarLinhaSelecionada;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.*;

@Log
public class TelaListagemEditora extends JFrame {

    private final JFrame telaAnterior;
    private final EditoraService editoraService;
    private final JButton botaoAtualizar = new JButton("Atualizar");
    private final JButton botaoVoltar = new JButton("Voltar");
    private final JButton botaoDeletar = new JButton("Deletar");
    private final JButton botaoEditar = new JButton("Editar");
    private final EditoraTable editoraTable = new EditoraTable();
    private final JTable tabela = new JTable(editoraTable);


    public TelaListagemEditora(JFrame telaAnterior) {
        super("Listagem de Editoras");
        this.telaAnterior = telaAnterior;
        this.editoraService = new EditoraService();

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
        this.carregarDados();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(tabela);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);
        add(painelPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = new JPanel(new FlowLayout(RIGHT));

        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoDeletar);
        painelBotoes.add(botaoEditar);

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
        botaoAtualizar.addActionListener(listener -> {
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
                    "Por favor, selecione uma editora para editar.", "Nenhuma editora selecionado");

            if (isLinhaValida) {
                EditoraResponse editora = editoraTable.getEditora(linhaSelecionada);
                TelaFormularioEditora formulario = new TelaFormularioEditora(this, editora);
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
                    EditoraResponse editora = editoraTable.getEditora(linhaSelecionada);

                    editoraService.deletar(editora.getId());
                    showMessageDialog(this, "Editora deletada com sucesso!", "Sucesso", INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                showMessageDialog(this, "Erro ao deletar autor do banco de dados.", "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }

    /**
     * Efetua a busca dos dados da listagem.
     */
    private void carregarDados() {
        try {
            List<EditoraResponse> editoras = editoraService.listarTodos();
            editoraTable.setEditoras(editoras);
        } catch (Exception ex) {
            showMessageDialog(this, "Erro ao carregar editoras do banco de dados.", "Erro", ERROR_MESSAGE);
            log.severe(ex.getMessage());
        }
    }
}
