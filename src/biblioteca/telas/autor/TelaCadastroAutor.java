package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.service.AutorService;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.StringUtils.isBlank;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
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
public class TelaCadastroAutor extends JFrame {

    private final JFrame telaAnterior;
    private final AutorService autorService;

    private JTextField campoNome;
    private JTextField campoIdade;
    private JButton botaoSalvar;
    private JButton botaoVoltar;

    public TelaCadastroAutor(JFrame telaAnterior) {
        super("Cadastro de Autor");
        this.telaAnterior = telaAnterior;
        this.autorService = new AutorService();

        this.inicializarComponentes();
        this.configurarAcoesDosBotoes();
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(createEmptyBorder(50, 50, 50, 50));

        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel labelNome = new JLabel("Nome:");
        campoNome = new JTextField();
        JLabel labelIdade = new JLabel("Idade:");
        campoIdade = new JTextField();

        painelFormulario.add(labelNome);
        painelFormulario.add(campoNome);
        painelFormulario.add(labelIdade);
        painelFormulario.add(campoIdade);

        JPanel painelBotoes = new JPanel(new FlowLayout(RIGHT));
        botaoSalvar = new JButton("Salvar");
        botaoVoltar = new JButton("Voltar");
        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoSalvar);

        painelPrincipal.add(new JLabel("Preencha os dados do autor:", SwingConstants.CENTER), NORTH);
        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, SOUTH);


        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
            telaAnterior.setVisible(true);
            dispose();
        });
    }

    /**
     * Configura a ação de salvar um novo Autor.
     * Envia os dados para a service de Autor, para salvar no banco de dados.
     */
    private void configurarAcaoBotaoSalvar() {
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

            autorService.salvar(new AutorRequest(nome, idade));
            showMessageDialog(this, "Livro salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            campoNome.setText("");
            campoIdade.setText("");
        });

    }
}
