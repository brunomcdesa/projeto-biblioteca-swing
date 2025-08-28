package biblioteca.backend.enums;

import biblioteca.backend.dto.SelectResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static biblioteca.backend.dto.SelectResponse.montarSelectResponse;

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
     * Método responsável mapear os valores do enum para o DTO SelectResponse.
     *
     * @return Um array SelectResponse.
     */
    public static SelectResponse[] getValuesParaSelect() {
        return Arrays.stream(EGenero.values())
                .map(genero -> montarSelectResponse(genero, genero.descricao))
                .toArray(SelectResponse[]::new);
    }
}
