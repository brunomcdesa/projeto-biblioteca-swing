package biblioteca.telas.livro;

import biblioteca.backend.facade.LivroFacade;
import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;

@Log
public class TelaImportacaoLivro extends JFrame {

    private final LivroFacade livroFacade;
    private final JButton botaoSalvar = criarBotao("Salvar");
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoSelecionarArquivo = criarBotao("Selecionar Arquivo");

    private File arquivo;

    public TelaImportacaoLivro(LivroFacade livroFacade) {
        super("Importar Livro");
        this.livroFacade = livroFacade;

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipalFormulario("Selecione o arquivo para importar:");
        this.aplicarConfiguracoesFormulario(painelPrincipal);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        add(painelPrincipal);
        setSize(600, 300);
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
        painelFormulario.add(criarLinhaFormulario("", botaoSelecionarArquivo));
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
        this.configurarAcaoBotaoSelecionarArquivo();
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
     * Configura a ação de salvar a importação de livro por arquivo.
     */
    private void configurarAcaoBotaoSalvar() {
        botaoSalvar.addActionListener(listener -> {
                String nomeArqivo = arquivo.getName();
        });
    }

    /**
     * Configura a ação do botão de selecionar arquivo.
     */
    private void configurarAcaoBotaoSelecionarArquivo() {
        botaoSelecionarArquivo.addActionListener(listener -> {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filtro);
            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                arquivo = fileChooser.getSelectedFile();
            }
        });
    }
}
