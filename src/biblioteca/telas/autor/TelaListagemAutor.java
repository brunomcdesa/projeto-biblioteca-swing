package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.service.AutorService;
import biblioteca.telas.autor.table.AutorTable;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    private JTable tabela;
    private AutorTable autorTable;
    private JButton botaoAtualizar;
    private JButton botaoVoltar;
    private JButton botaoEditar;

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

        autorTable = new AutorTable();
        tabela = new JTable(autorTable);

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
        botaoEditar = new JButton("Editar");
        botaoAtualizar = new JButton("Atualizar");
        botaoVoltar = new JButton("Voltar");

        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoAtualizar);
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

            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um autor para editar.",
                        "Nenhum autor selecionado", WARNING_MESSAGE);
                return;
            }

            AutorResponse autor = autorTable.getAutor(linhaSelecionada);
            log.info("Autor selecionado: " + autor);
            TelaFormularioAutor formulario = new TelaFormularioAutor(this, autor);
            formulario.setVisible(true);
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
