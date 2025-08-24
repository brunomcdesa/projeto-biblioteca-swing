package biblioteca.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa os dados de entrada para salvar/alterar uma entidade Autor
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorRequest {

    private String nome;
    private Integer idade;
}
