package biblioteca.utils;

import lombok.experimental.UtilityClass;

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
}
