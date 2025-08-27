package biblioteca.utils;

import lombok.experimental.UtilityClass;

import java.util.function.Function;

/**
 * Classe utilitária para operações de mapeamento.
 * <p>
 * Esta classe é responsável por realizar validações de mmapeamento tanto nas telas, quanto no backend.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@UtilityClass
public class MapUtils {

    /**
     * Método genérico com foco em realizar o mapeamento passado como Function, caso o value não seja null.
     * <p>
     * Caso o value for null, será retornado o valor passado como backup.
     *
     * @return Valor do mapeamento realizado pela Function, caso o value não for null. Valor passado como backup caso o value for null.
     */
    public static <T, C> C mapNullComBackup(T value, Function<T, C> function, C backup) {
        return (value != null) ? function.apply(value) : backup;
    }

    /**
     * Método genérico com foco em realizar o mapeamento passado como returnValue, caso o value não seja null.
     * <p>
     * Caso o value for null, será retornado o valor passado como backup.
     *
     * @return Valor passado por parametro como returnValue, caso o não value for null. Valor passado como backup caso o value for null.
     */
    public static <T, C> C mapNullComBackup(T value, C returnValue, C backup) {
        return (value != null) ? returnValue : backup;
    }
}
