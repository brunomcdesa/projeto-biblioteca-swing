package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static biblioteca.utils.MapUtils.mapNull;
import static biblioteca.utils.StringUtils.mapearData;

/**
 * Classe DTO que representa os dados de entrada para salvar/alterar uma entidade Livro.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {

    private String titulo;
    private String isbn10;
    private String isbn13;
    private EGenero genero;
    private LocalDate dataPublicacao;
    private Integer editoraId;
    private List<Integer> autoresIds;
    private List<Integer> livrosSelecionadosIds;

    /**
     * Método responsável por converter um DTO OpenLibraryLivroResponse em um LivroResponse.
     *
     * @return Um LivroRequest convertido do DTO OpenLibraryLivroResponse.
     */
    public static LivroRequest converterDeOpenLibraryResponse(OpenLibraryLivroResponse livroResponse) {
        return LivroRequest.builder()
                .titulo(livroResponse.getTitulo())
                .isbn10(mapNull(livroResponse.getIsbn10(), isbns -> isbns.get(0)))
                .isbn13(mapNull(livroResponse.getIsbn13(), isbns -> isbns.get(0)))
                .dataPublicacao(mapearData(livroResponse.getDataPublicacao()))
                .build();
    }
}
