package biblioteca.telas.editora;

import biblioteca.backend.dto.EditoraRequest;
import biblioteca.backend.dto.EditoraResponse;
import biblioteca.backend.facade.EditoraFacade;

import javax.swing.*;
import java.awt.*;

import static biblioteca.utils.TelasUtils.*;
import static biblioteca.utils.MapUtils.mapNullComBackup;
import static biblioteca.utils.StringUtils.isBlank;
import static biblioteca.utils.StringUtils.isCnpjValido;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.JOptionPane.*;

/**
 * Tela de cadastro de Editora.
 * <p>
 * Esta classe é responsável por renderizar a tela referente a o cadastro de Editora,
 * onde vai ser mostrado os campos para que uma nova editora seja cadastrada no sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class TelaFormularioEditora extends JFrame {

    private final JFrame telaAnterior;
    private final EditoraFacade editoraFacade;
    private final JButton botaoSalvar = new JButton("Salvar");
    private final JButton botaoVoltar = new JButton("Voltar");

    private JTextField campoNome;
    private JTextField campoCnpj;

    public TelaFormularioEditora(JFrame telaAnterior, EditoraFacade editoraFacade) {
        this(telaAnterior, editoraFacade, null);
    }

    public TelaFormularioEditora(JFrame telaAnterior, EditoraFacade editoraFacade,  EditoraResponse editora) {
        super(mapNullComBackup(editora, "Editar Editora","Cadastrar Editora"));
        this.telaAnterior = telaAnterior;
        this.editoraFacade = editoraFacade;

        this.inicializarComponentes(editora);
        this.configurarAcoesDosBotoes(editora);
    }

    /**
     * Inicializa e configura os componentes visuais da tela.
     */
    private void inicializarComponentes(EditoraResponse editora) {
        JPanel painelPrincipal = criarPainelPrincipalFormulario("Preencha os dados da Editora:");
        this.aplicarConfiguracoesFormulario(painelPrincipal, editora);
        this.aplicarConfiguracoesVisuaisBotoes(painelPrincipal);

        adicionarConfiguracoesPadroesTela(this, painelPrincipal);
    }

    /**
     * Adiciona configurações dos dados iniciais dos campos do formuário.
     */
    private void aplicarConfiguracoesFormulario(JPanel painelPrincipal, EditoraResponse editora) {
        JPanel painelFormulario = criarPainelPadrao();
        this.configurarCamposFormulario(editora, painelFormulario);
        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelContainer.add(painelFormulario);
        painelPrincipal.add(painelContainer, CENTER);
    }

    /**
     * Cria e define os campos do formulário.
     */
    private void configurarCamposFormulario(EditoraResponse editora, JPanel painelFormulario) {
        this.campoNome = criarTextField(mapNullComBackup(editora, EditoraResponse::getNome, ""));
        this.campoCnpj = criarTextField(mapNullComBackup(editora, EditoraResponse::getCnpj, ""));

        painelFormulario.add(criarLinhaFormulario("Nome:", this.campoNome));
        painelFormulario.add(criarLinhaSeparacao());
        painelFormulario.add(criarLinhaFormulario("CNPJ:", this.campoCnpj));
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
    private void configurarAcoesDosBotoes(EditoraResponse editora) {
        this.configurarAcaoBotaoVoltar();
        this.configurarAcaoBotaoSalvar(editora);
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
     * Configura a ação de salvar uma nova editora ou de editar uma editora.
     * Envia os dados para a service de Editora, para salvar no banco de dados.
     */
    private void configurarAcaoBotaoSalvar(EditoraResponse editora) {
        botaoSalvar.addActionListener(listener -> {
            String nome = campoNome.getText();
            String cnpj = campoCnpj.getText();

            if (isBlank(nome) || isBlank(cnpj)) {
                showMessageDialog(this, "Todos os campos são obrigatórios!",
                        "Erro de Validação", ERROR_MESSAGE);
                return;
            }

            if (!isCnpjValido(cnpj)) {
                showMessageDialog(this, "CNPJ inválido! Insira o CNPJ com o formato XX.XXX.XXX/XXXX-XX",
                        "Erro de Formato", ERROR_MESSAGE);
                return;
            }

            EditoraRequest request = new EditoraRequest(nome, cnpj);

            if (editora == null) {
                editoraFacade.salvarEditora(request);
            } else {
                editoraFacade.editarEditora(editora.getId(), request);
            }
            showMessageDialog(this, "Editora salva com sucesso!", "Sucesso", INFORMATION_MESSAGE);

            telaAnterior.setVisible(true);
            dispose();
        });
    }
}
