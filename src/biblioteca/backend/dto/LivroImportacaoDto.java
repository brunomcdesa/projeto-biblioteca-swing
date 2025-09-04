package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import biblioteca.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static biblioteca.utils.MapUtils.mapStringBlankNull;

/**
 * Classe DTO que representa os dados do arquivo de importação de um Livro.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroImportacaoDto {

    private String titulo;
    private LocalDate dataPublicacao;
    private String isbn;
    private EGenero genero;
    private String nomeEditora;
    private String cnpjEditora;
    private String nomeAutor;
    private LocalDate dataNascimentoAutor;
    private LocalDate dataMorteAutor;
    private String biografiaAutor;

    /**
     * Método responsável converter o array de campos de uma linha do arquivo de importação, na classe DTO LivroImportacaoDto.
     *
     * @return Uma LivroImportacaoDto.
     */
    public static LivroImportacaoDto converterDeArrayString(String[] camposLinha) {
        return LivroImportacaoDto.builder()
                .titulo(camposLinha[0])
                .dataPublicacao(mapStringBlankNull(camposLinha[1], StringUtils::converterDataEmStringParaLocalDate))
                .isbn(mapStringBlankNull(camposLinha[2], isbn -> isbn))
                .genero(mapStringBlankNull(camposLinha[3], EGenero::valueOfName))
                .nomeEditora(mapStringBlankNull(camposLinha[4], nomeEditora -> nomeEditora))
                .cnpjEditora(mapStringBlankNull(camposLinha[5], cnpjEditora -> cnpjEditora))
                .nomeAutor(mapStringBlankNull(camposLinha[6], nomeAutor -> nomeAutor))
                .dataNascimentoAutor(mapStringBlankNull(camposLinha[7], StringUtils::converterDataEmStringParaLocalDate))
                .dataMorteAutor(mapStringBlankNull(camposLinha[8], StringUtils::converterDataEmStringParaLocalDate))
                .biografiaAutor(mapStringBlankNull(camposLinha[9], biografia -> biografia))
                .build();
    }


}
