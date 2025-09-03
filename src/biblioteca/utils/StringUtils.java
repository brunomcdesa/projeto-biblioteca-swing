package biblioteca.utils;

import biblioteca.backend.enums.EPadraoData;
import biblioteca.backend.exceptions.ValidacaoException;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static biblioteca.utils.MapUtils.mapNullComBackup;
import static java.lang.String.format;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Classe utilitária para operações e validações de Strings.
 * <p>
 * Esta classe é responsável por realizar validações e operações com Strings que são utilizadas várias vezes no projeto.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@UtilityClass
public class StringUtils {

    /**
     * Método responsável por validar se uma String é blank.
     * <p>
     * Uma String será considerada blank, caso seja null, empty("") ou blank("    ")
     *
     * @return true: Se a String for considerada blank. false: Se a string não for considerada blank
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * Método responsável por validar se uma String não é blank.
     * <p>
     * Inverte o retorno do método isBlank();
     *
     * @return true: Se a String não for considerada blank. false: Se a string for considerada blank
     */
    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    /**
     * Método responsável por validar um CNPJ
     * <p>
     * O cnpj será considerado válido se não for blank e se estiver dentro do padrão de cnpj: XX.XXX.XXX/XXXX-XX
     *
     * @return true: Se O cnpj não for blank e estiver com o padrão correto. false: Se o cnpj for blank ou se não estiver dentro do padrãro de cnpj.
     */
    public static boolean isCnpjValido(String cnpj) {
        return isNotBlank(cnpj) && cnpj.matches("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$");
    }

    /**
     * Método responsável por formatar a data passada por parâmetro.
     * <p>
     * A data será formatada para o padrão dd/MM/yyyy.
     *
     * @return Uma String de data no formato dd/MM/yyyy. Ex: "28/05/2025".
     */
    public static String formatarData(LocalDate data) {
        return mapNullComBackup(data, dataValida -> dataValida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "");
    }

    /**
     * Método responsável por verificar se a data enviada no formato de String é válida.
     * <p>
     * A data será considerada válida, se a String não for Blank e a sequência de caracteres atender ao padrão dd/MM/yyyy
     *
     * @return true caso a data não for Blank e estiver no padrão definido. false caso a data for Blank ou não estiver no formato definido.
     */
    public static boolean isDataInvalida(String data) {
        return !isNotBlank(data) || !data.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    }

    /**
     * Método responsável por converter uma String em um objeto LocalDate.
     * <p>
     * A data será convertida para um LocalDate, quando a String estiver no formato dd/MM/yyyy.
     *
     * @return Uma data convertida para LocalDate.
     * @throws ValidacaoException se a data não estiver em um formato válido.
     */
    public static LocalDate converterCampoStringParaLocalDate(String data, String nomeDoCampo, Component parent) {
        if (isDataInvalida(data)) {
            String mensagem = format("%s inválida! Insira a data de publicação no formato dd/MM/yyyy.", nomeDoCampo);
            showMessageDialog(parent, mensagem, "Erro de Formato", ERROR_MESSAGE);
            throw new ValidacaoException(mensagem);
        }
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Método responsável por converter uma String em um objeto LocalDate, de acordo com seu padrão em Inglês.
     * <p>
     * A data será convertida para um LocalDate, quando a String estiver em algum dos formatos mapeados pelo enum EPadraoData.
     *
     * @return Uma data convertida para LocalDate.
     */
    public static LocalDate converterDataEmStringEmInglesParaLocalDate(String data) {
       return EPadraoData.converterDataEmPadrao(data);
    }

    /**
     * Converte o texto de um campo para Integer.
     * Se o formato for inválido, exibe uma mensagem de erro e lança uma ValidacaoException.
     *
     * @return O Integer convertido, ou null se o texto for nulo ou em branco.
     * @throws ValidacaoException se o texto não for um número válido.
     */
    public static Integer converterStringEmInteger(String texto, String nomeDoCampo, Component parent) {
        if (isNotBlank(texto)) {
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException ex) {
                String mensagem = format("%s deve ser um número válido!", nomeDoCampo);
                showMessageDialog(parent, mensagem, "Erro de Formato", ERROR_MESSAGE);
                throw new ValidacaoException(mensagem);
            }
        }

        return null;
    }

    /**
     * Método responsável por efetuar a validação dos campos String obrigatórios, de acordo com os campos passados por parâmetro.
     *
     * @throws ValidacaoException se possuir algum campo obrigatório inválido.
     */
    public static void validarCamposStringObrigatorios(Component parent, String... campos) {
        boolean possuiCampoInvalido = Arrays.stream(campos)
                .anyMatch(StringUtils::isBlank);
        if (possuiCampoInvalido) {
            String mensagem = "Campos obrigatórios inválidos!";
            showMessageDialog(parent, mensagem, "Erro de Validação", ERROR_MESSAGE);
            throw new ValidacaoException(mensagem);
        }
    }
}
