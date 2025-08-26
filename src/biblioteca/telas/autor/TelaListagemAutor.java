package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.service.AutorService;
import biblioteca.telas.autor.table.AutorTable;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static biblioteca.utils.FormUtils.validarLinhaSelecionada;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;
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
    private final AutorService autorService;
    private final JButton botaoAtualizar = new JButton("Atualizar");
    private final JButton botaoVoltar = new JButton("Voltar");
    private final JButton botaoDeletar = new JButton("Deletar");
    private final JButton botaoEditar = new JButton("Editar");
    private final JButton botaoCadastrar = new JButton("Cadastrar");
    private final AutorTable autorTable = new AutorTable();
    private final JTable tabela = new JTable(autorTable);

    public TelaListagemAutor(JFrame telaAnterior) {
        super("Listagem de Autores");
        this.telaAnterior = telaAnterior;
        this.autorService = new AutorService();

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
        painelBotoes.add(botaoCadastrar);

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
                    "Por favor, selecione um autor para editar.", "Nenhum autor selecionado");

            if (isLinhaValida) {
                AutorResponse autor = autorTable.getAutor(linhaSelecionada);
                TelaFormularioAutor formulario = new TelaFormularioAutor(this, autor);
                formulario.setVisible(true);
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

                    autorService.deletar(autor.getId());
                    showMessageDialog(this, "Autor deletado com sucesso!", "Sucesso", INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                showMessageDialog(this, "Erro ao deletar autor do banco de dados.", "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }

    /**
     * Adiciona um listener para interceptar o evento de cadastro de Autor.
     */
    private void configurarAcaoBotaoCadastrar() {
        botaoCadastrar.addActionListener(listener -> {
            TelaFormularioAutor telaCadastroAutor = new TelaFormularioAutor(this);
            telaCadastroAutor.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a busca dos dados da listagem.
     */
    private void carregarDados() {
        try {
            List<AutorResponse> autores = autorService.listarTodos();
            autorTable.setAutores(autores);
        } catch (Exception ex) {
            showMessageDialog(this, "Erro ao carregar autores do banco de dados.", "Erro", ERROR_MESSAGE);
            log.severe(ex.getMessage());
        }
    }
}
