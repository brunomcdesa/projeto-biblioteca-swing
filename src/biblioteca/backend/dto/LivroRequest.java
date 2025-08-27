package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
}
