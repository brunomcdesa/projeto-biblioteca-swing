package biblioteca.backend.dto;

import biblioteca.backend.model.Autor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa os dados de retorno para as telas.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorResponse {

    public Integer id;
    public String nome;
    public Integer idade;

    /**
     * Método responsável por realizar a conversão de uma entidade Autor para um DTO AutorResponse.
     *
     * @return Um DTO com dados do Autor.
     */
    public static AutorResponse converterDeAutor(Autor autor) {
        return AutorResponse.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .idade(autor.getIdade())
                .build();
    }
}
