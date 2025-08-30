package biblioteca.telas.autor;

import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.facade.AutorFacade;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.converterStringEmInteger;
import static biblioteca.utils.StringUtils.validarCamposStringObrigatorios;
import static biblioteca.utils.TelasUtils.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;
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
        super(mapNullComBackup(autor, "Editar Autor", "Cadastro de Autor"));
        this.telaAnterior = telaAnterior;
        this.autorFacade = autorFacade;

        this.inicializarComponentes(autor);
        this.configurarAcoesDosBotoes(autor);
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes(AutorResponse autor) {
        JPanel painelPrincipal = criarPainelPrincipalFormulario("Preencha os dados do autor:");
        this.aplicarConfiguracoesFormulario(painelPrincipal, autor);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        adicionarConfiguracoesPadroesTela(this, painelPrincipal);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal, AutorResponse autor) {
        JPanel painelFormulario = criarPainelPadrao();
        this.configurarCamposFormulario(autor, painelFormulario);
        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    /**
     * Cria e define os campos do formulário.
     */
    private void configurarCamposFormulario(AutorResponse autor, JPanel painelFormulario) {
        this.campoNome = criarTextField(mapNullComBackup(autor, AutorResponse::getNome, ""));
        this.campoIdade = criarTextField(mapNullComBackup(autor, response -> response.getIdade().toString(), ""));

        painelFormulario.add(criarLinhaFormulario("Nome:", this.campoNome));
        painelFormulario.add(criarLinhaSeparacao());
        painelFormulario.add(criarLinhaFormulario("Idade:", this.campoIdade));
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

            validarCamposStringObrigatorios(this, nome, idadeText);

            Integer idade = converterStringEmInteger(idadeText, "Idade", this);

            AutorRequest request = new AutorRequest(nome, idade);

            if (autor == null) {
                autorFacade.salvarNovoAutor(request);
            } else {
                autorFacade.editarAutor(autor.getId(), request);
            }
            showMessageDialog(this, "Autor salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            telaAnterior.setVisible(true);
            dispose();
        });
    }
}
