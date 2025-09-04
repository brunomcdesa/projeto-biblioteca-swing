package biblioteca.telas.livro;

import biblioteca.backend.facade.LivroFacade;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.StringUtils.validarCamposStringObrigatorios;
import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.JOptionPane.*;

/**
 * Tela de cadastro de Livro por ISBN.
 * <p>
 * Esta classe é responsável por renderizar a tela referente ao cadastro de livro através do ISBN informado.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class TelaCadastroLivroIsbn extends JFrame {

    private final LivroFacade livroFacade;
    private final JButton botaoSalvar = criarBotao("Salvar");
    private final JButton botaoVoltar = criarBotao("Voltar");

    private JTextField campoIsbn;

    public TelaCadastroLivroIsbn(LivroFacade livroFacade) {
        super("Cadastrar Livro por ISBN");
        this.livroFacade = livroFacade;

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipalFormulario("Informe o ISBN do Livro (Campos com * são obrigatórios):");
        this.aplicarConfiguracoesFormulario(painelPrincipal);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        add(painelPrincipal);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal) {
        JPanel painelFormulario = criarPainelPadrao();
        this.configurarCamposFormulario(painelFormulario);

        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    /**
     * Cria e define os campos do formulário.
     */
    private void configurarCamposFormulario(JPanel painelFormulario) {
        this.campoIsbn = criarTextField("");

        painelFormulario.add(criarLinhaFormulario("ISBN *:", campoIsbn));
    }

    /**
     * Adiciona configurações visuais dos botoes da tela.
     */
    private void aplicarConfiguracoesVisuaisBotoes(JPanel painelPrincipal) {
        JPanel painelBotoes = new JPanel(new FlowLayout(RIGHT));
        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoSalvar);

        painelPrincipal.add(painelBotoes, SOUTH);
    }

    /**
     * Configura todas as ações dos botões da tela.
     */
    private void configurarAcoesDosBotoes() {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoSalvar();
    }

    /**
     * Configura a ação do botão de voltar para tela anterior.
     */
    private void configurarAcaoBotaoVoltar() {
        botaoVoltar.addActionListener(listener -> {
            this.dispose();
        });
    }

    /**
     * Configura a ação de salvar um livro por ISBN.
     * Valida e envia o ISBN para o facade, para que os dados do livro sejam buscados e salvos no banco de dados do sistema.
     */
    private void configurarAcaoBotaoSalvar() {
        botaoSalvar.addActionListener(listener -> {
            String isbn = campoIsbn.getText();
            validarCamposStringObrigatorios(this, isbn);

            try {
                isbn = isbn.replace("-", "");
                livroFacade.cadastrarLivroPorIsbn(isbn.trim());
                showMessageDialog(this, "Livro salvo com sucesso!", "Sucesso", INFORMATION_MESSAGE);
                this.dispose();
            } catch (Exception ex) {
                showMessageDialog(this, ex.getMessage(), "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }
}
