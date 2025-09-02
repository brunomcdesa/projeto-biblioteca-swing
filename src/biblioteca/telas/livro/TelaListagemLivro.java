package biblioteca.telas.livro;

import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.facade.LivroFacade;
import biblioteca.telas.livro.table.LivroTable;
import lombok.extern.java.Log;

import javax.swing.*;
import java.util.List;

import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.*;

/**
 * Tela de Listagem de Livros.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a listagem das livros
 * e também efetuar o gerenciamento de cadastro/edição/deleção de uma livros.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class TelaListagemLivro extends JFrame {

    private final JFrame telaAnterior;
    private final LivroFacade livroFacade;
    private final JButton botaoRecarregarDados = criarBotao("Recarregar Dados");
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoPesquisar = criarBotao("Pesquisar");
    private final JButton botaoDeletar = criarBotao("Deletar");
    private final JButton botaoEditar = criarBotao("Editar");
    private final JButton botaoCadastrar = criarBotao("Cadastrar");
    private final JButton botaoCadastrarPorIsbn = criarBotao("Cadastrar por ISBN");
    private final LivroTable livroTable = new LivroTable();
    private final JTable tabela = new JTable(livroTable);

    public TelaListagemLivro(JFrame telaAnterior, LivroFacade livroFacade) {
        super("Listagem de Livros");
        this.telaAnterior = telaAnterior;
        this.livroFacade = livroFacade;

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
               botaoEditar, botaoCadastrar, botaoCadastrarPorIsbn);

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
        this.configurarAcaoBotaoCadastrarPorIsbn();
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
    private void configurarAcaoBotaoEditar() {
        botaoEditar.addActionListener(listener -> {
            int linhaSelecionada = tabela.getSelectedRow();
            boolean isLinhaValida = validarLinhaSelecionada(linhaSelecionada, this,
                    "Por favor, selecione um livro para editar.", "Nenhum livro selecionado");

            if (isLinhaValida) {
                LivroResponse livro = livroTable.getLivro(linhaSelecionada);
                TelaFormularioLivro formulario = new TelaFormularioLivro(this, livroFacade, livro);
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
                        "Por favor, selecione um livro para deletar.", "Nenhum livro selecionado");

                if (isLinhaValida) {
                    LivroResponse livro = livroTable.getLivro(linhaSelecionada);

                    livroFacade.deletarLivro(livro.getId());
                    showMessageDialog(this, "Livro deletado com sucesso!", "Sucesso", INFORMATION_MESSAGE);
                    carregarDados();
                }
            } catch (Exception ex) {
                showMessageDialog(this, "Erro ao deletar autor do banco de dados.", "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }

    /**
     * Efetua a configuração da ação do botão de cadastrar, para que redirecione para o formulário de cadastro.
     */
    private void configurarAcaoBotaoCadastrar() {
        botaoCadastrar.addActionListener(listener -> {
            TelaFormularioLivro formularioLivro = new TelaFormularioLivro(this, livroFacade);
            formularioLivro.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a configuração da ação do botão de cadastrar por ISBN, para que redirecione para o formulário de cadastro de livro por ISBN.
     */
    private void configurarAcaoBotaoCadastrarPorIsbn() {
        botaoCadastrarPorIsbn.addActionListener(listener -> {
            TelaCadastroLivroIsbn telaCadastroLivroIsbn = new TelaCadastroLivroIsbn(livroFacade);
            telaCadastroLivroIsbn.setLocationRelativeTo(this);
            telaCadastroLivroIsbn.setVisible(true);
        });
    }

    /**
     * Efetua a configuração da ação do botão de pesquisar,
     * para que seja aberta uma nova tela de pesquisa de livros por filtros.
     */
    private void configurarAcaoBotaoPesquisar() {
        botaoPesquisar.addActionListener(listener -> {
            TelaPesquisaLivro telaPesquisaLivro = new TelaPesquisaLivro(this, livroFacade);
            telaPesquisaLivro.setVisible(true);
            this.setVisible(false);
        });
    }

    /**
     * Efetua a busca dos dados da listagem.
     */
    private void carregarDados() {
        try {
            List<LivroResponse> livros = livroFacade.listarTodosLivros();
            livroTable.setLivros(livros);
        } catch (Exception ex) {
            showMessageDialog(this, "Erro ao carregar livros do banco de dados.", "Erro", ERROR_MESSAGE);
            log.severe(ex.getMessage());
        }
    }
}
