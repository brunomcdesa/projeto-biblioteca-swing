package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDate;

import static biblioteca.utils.MapUtils.mapStringBlankNull;
import static biblioteca.utils.StringUtils.mapearData;

/**
 * Classe DTO que representa os dados do arquivo de importação de um Livro.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Log
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroImportacaoDto {

    private String titulo;
    private LocalDate dataPublicacao;
    private String isbn10;
    private String isbn13;
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
                .dataPublicacao(mapearData(camposLinha[1]))
                .isbn10(mapStringBlankNull(camposLinha[2], isbn -> isbn))
                .isbn13(mapStringBlankNull(camposLinha[3], isbn -> isbn))
                .genero(mapStringBlankNull(camposLinha[4], EGenero::valueOfName))
                .nomeEditora(mapStringBlankNull(camposLinha[5], nomeEditora -> nomeEditora))
                .cnpjEditora(mapStringBlankNull(camposLinha[6], cnpjEditora -> cnpjEditora))
                .nomeAutor(mapStringBlankNull(camposLinha[7], nomeAutor -> nomeAutor))
                .dataNascimentoAutor(mapearData(camposLinha[8]))
                .dataMorteAutor(mapearData(camposLinha[9]))
                .biografiaAutor(mapStringBlankNull(camposLinha[10], biografia -> biografia))
                .build();
    }
}
