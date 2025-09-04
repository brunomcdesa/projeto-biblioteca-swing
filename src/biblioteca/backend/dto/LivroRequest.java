package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import biblioteca.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static biblioteca.utils.MapUtils.mapStringBlankNull;

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
    private String isbn;
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
                .isbn(livroResponse.getIsbn().get(0))
                .dataPublicacao(mapStringBlankNull(livroResponse.getDataPublicacao(), StringUtils::converterDataEmStringParaLocalDate))
                .build();
    }
}
