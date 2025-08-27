package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.facade.AutorFacade;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.FormUtils.*;
import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.isBlank;
import static java.awt.BorderLayout.*;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Tela de cadastro de Autor.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a o cadastro de Autor,
 * onde vai ser mostrado os campos para que um novo autor seja cadastrado no sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaFormularioAutor extends JFrame {

    private final JFrame telaAnterior;
    private final AutorFacade autorFacade;
    private final JButton botaoSalvar = criarBotao("Salvar");
    private final JButton botaoVoltar = criarBotao("Voltar");

    private JTextField campoNome;
    private JTextField campoIdade;

    public TelaFormularioAutor(JFrame telaAnterior, AutorFacade autorFacade) {
        this(telaAnterior, autorFacade, null);
    }

    public TelaFormularioAutor(JFrame telaAnterior, AutorFacade autorFacade, AutorResponse autor) {
        super(autor == null ? "Cadastro de Autor" : "Editar Autor");
        this.telaAnterior = telaAnterior;
        this.autorFacade = autorFacade;

        this.inicializarComponentes(autor);
        this.configurarAcoesDosBotoes(autor);
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes(AutorResponse autor) {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(createEmptyBorder(50, 50, 50, 50));
        painelPrincipal.add(new JLabel("Preencha os dados do autor:", SwingConstants.CENTER), NORTH);
        this.aplicarConfiguracoesFormulario(painelPrincipal, autor);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);


        add(painelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal, AutorResponse autor) {
        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));

        this.configurarCamposFormulario(autor, painelFormulario);
        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    private void configurarCamposFormulario(AutorResponse autor, JPanel painelFormulario) {
        JLabel labelNome = criarLabel("Nome:");
        this.campoNome = criarTextField(mapNullComBackup(autor, AutorResponse::getNome, ""));
        JLabel labelIdade = criarLabel("Idade:");
        this.campoIdade = criarTextField(mapNullComBackup(autor, response -> response.getIdade().toString(), ""));

        painelFormulario.add(labelNome);
        painelFormulario.add(campoNome);
        painelFormulario.add(labelIdade);
        painelFormulario.add(campoIdade);
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
    private void configurarAcoesDosBotoes(AutorResponse autor) {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoSalvar(autor);
    }

    /**
     * Configura a ação do botão de voltar para tela anterior.
     */
    private void configurarAcaoBotaoVoltar() {
        botaoVoltar.addActionListener(listener -> {
            telaAnterior.setVisible(true);
            dispose();
        });
    }

    /**
     * Configura a ação de salvar um novo Autor ou de editar um autor.
     * Envia os dados para a service de Autor, para salvar no banco de dados.
     */
    private void configurarAcaoBotaoSalvar(AutorResponse autor) {
        botaoSalvar.addActionListener(listener -> {
            String nome = campoNome.getText();
            String idadeText = campoIdade.getText();

            if (isBlank(nome) || isBlank(idadeText)) {
                showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Validação", ERROR_MESSAGE);
                return;
            }

            int idade;
            try {
                idade = Integer.parseInt(idadeText);
            } catch (NumberFormatException e) {
                showMessageDialog(this, "A idade deve ser um número válido!", "Erro de Formato", ERROR_MESSAGE);
                return;
            }

            AutorRequest request = new AutorRequest(nome, idade);

            if (autor == null) {
                autorFacade.salvarNovoAutor(request);
            } else {
                autorFacade.editarAutor(autor.getId(), request);
            }
            showMessageDialog(this, "Autor salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            campoNome.setText("");
            campoIdade.setText("");
        });
    }
}
