package biblioteca.backend.enums;

import biblioteca.backend.exceptions.ValidacaoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

/**
 * Enum definido para representar alguns formatos de datas para realizar o mapeamento.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum EPadraoData {

    PADRAO_DATA_D_MMMM_YYYY("d MMMM yyyy"),
    PADRAO_DATA_D_MMM_YYYY("d MMM yyyy"),
    PADRAO_DATA_MMMM_D_YYYY("MMMM d, yyyy"),
    PADRAO_DATA_MMM_D_YYYY("MMM d, yyyy"),
    PADRAO_DATA_YYYY("yyyy"),
    PADRAO_DATA_DD_MM_YYYY("dd/MM/yyyy"),
    PADRAO_DATA_YYYY_MM_DD("yyyy-MM-dd");

    private final String padrao;

    /**
     * Método responsável por efetuar a conversão de uma data em String para uma das datas mapeadas no enum.
     *
     * @return Uma data convertida para LocalDate.
     * @throws ValidacaoException Caso a data em String não estiver em nenhum dos formatos já mapeados no enum.
     */
    public static LocalDate converterDataEmPadrao(String data) {
        return Arrays.stream(EPadraoData.values())
                .map(padraoData -> {
                    try {
                        return LocalDate.parse(data, DateTimeFormatter.ofPattern(padraoData.getPadrao(), ENGLISH));
                    } catch (Exception ignored) {
                        try {
                            return Year.parse(data, DateTimeFormatter.ofPattern(padraoData.getPadrao())).atDay(1);
                        } catch (Exception ignoredYearException) {
                            return null;
                        }
                    }
                }).filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ValidacaoException(format("Formato informado não mapeado: %s", data)));
    }
}
