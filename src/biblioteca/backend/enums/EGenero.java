package biblioteca.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enum definido para representar alguns gêneros de Livros
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum EGenero {

    TERROR("Terror"),
    COMEDIA("Comédia"),
    ROMANCE("Romance"),
    SUSPENSE("Suspense"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    BIOGRAFIA("Biografia");

    private final String descricao;

    /**
     * Método responsável mapear as descrições de todos os gêneros.
     *
     * @return Um array de descriçõe dos gêneros.
     */
    public static String[] getValuesDescricoes() {
        return Arrays.stream(EGenero.values())
                .map(EGenero::getDescricao)
                .toArray(String[]::new);
    }
}
