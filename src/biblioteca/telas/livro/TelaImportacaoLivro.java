package biblioteca.telas.livro;

import biblioteca.backend.exceptions.ValidacaoException;
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
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Tela de importação Livro por arquivos TXT e CSV.
 * <p>
 * Esta classe é responsável por renderizar a tela referente à importação de livro através do arquivo selecionado.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
public class TelaImportacaoLivro extends JFrame {

    private final LivroFacade livroFacade;
    private final JButton botaoSalvar = criarBotao("Salvar");
    private final JButton botaoVoltar = criarBotao("Voltar");
    private final JButton botaoSelecionarArquivo = criarBotao("Selecionar Arquivo");

    private final JLabel labelNomeArquivo = new JLabel();
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
        labelNomeArquivo.setText("Nenhum arquivo selecionado");
        labelNomeArquivo.setHorizontalAlignment(SwingConstants.CENTER);
        labelNomeArquivo.setPreferredSize(new Dimension(220, labelNomeArquivo.getPreferredSize().height));

        painelFormulario.add(criarLinhaFormulario("", botaoSelecionarArquivo));
        painelFormulario.add(criarLinhaFormulario("Nome do Arquivo:", labelNomeArquivo));
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
            if (arquivo == null) {
                String mensagem = "Por favor, selecione um arquivo.";
                showMessageDialog(this, mensagem, "Erro", ERROR_MESSAGE);
                throw new ValidacaoException(mensagem);
            }
            try {
                livroFacade.cadastrarLivroPorImportacao(arquivo);
                showMessageDialog(this, "Livros importados com sucesso!");
                this.dispose();
            } catch (Exception ex) {
                showMessageDialog(this, ex.getMessage(), "Erro", ERROR_MESSAGE);
                log.severe(ex.getMessage());
            }
        });
    }

    /**
     * Configura a ação do botão de selecionar arquivo.
     */
    private void configurarAcaoBotaoSelecionarArquivo() {
        botaoSelecionarArquivo.addActionListener(listener -> {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos Suportados (*.txt, *.csv)", "txt", "csv");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filtro);
            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File arquivoSelecionado = fileChooser.getSelectedFile();

                if (!isExtensaoValida(arquivoSelecionado)) {
                    showMessageDialog(this, "Extensão de arquivo inválida. Por favor, selecione um arquivo .txt ou .csv.", "Erro de Arquivo", ERROR_MESSAGE);
                }

                arquivo = arquivoSelecionado;
                labelNomeArquivo.setText(arquivo.getName());
            }
        });
    }

    /**
     * Valida se a extensão do arquivo selecionado é .txt ou .csv.
     *
     * @return true se a extensão for válida, false caso contrário.
     */
    private boolean isExtensaoValida(File file) {
        String nomeArquivo = file.getName();
        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toLowerCase();

        return extensao.equals("txt") || extensao.equals("csv");
    }
}
