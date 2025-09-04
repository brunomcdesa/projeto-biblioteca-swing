package biblioteca.backend.enums;

import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.exceptions.ValidacaoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static biblioteca.backend.dto.SelectResponse.montarSelectResponse;
import static java.lang.String.format;

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
    BIOGRAFIA("Biografia"),
    FANTASIA("Fantasia"),
    HQ("História em Quadrinhos"),
    MANGA("Mangá"),
    AUTO_AJUDA("Auto Ajuda"),
    ACAO("Ação"),
    AVENTURA("Aventura"),
    ENCICLOPEDIA("Enciclopédia");

    private final String descricao;

    /**
     * Método responsável mapear os valores do enum para o DTO SelectResponse.
     *
     * @return Uma lista de SelectResponse.
     */
    public static List<SelectResponse> getValuesParaSelect() {
        return Arrays.stream(EGenero.values())
                .map(genero -> montarSelectResponse(genero, genero.descricao))
                .collect(Collectors.toList());
    }

    /**
     * Método responsável mapear um EGenero através do name.
     *
     * @return Um EGenero correspondente ao name.
     * @throws ValidacaoException caso o name não esteja mapeado no sistema.
     */
    public static EGenero valueOfName(String name) {
        try {
            return EGenero.valueOf(name);
        } catch (Exception ex) {
            throw new ValidacaoException(format("Gênero não mapeado no sistema: %s", name));
        }
    }
}
