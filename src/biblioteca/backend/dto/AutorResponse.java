package biblioteca.backend.dto;

import biblioteca.backend.model.Autor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Método responsável por realizar a conversão de várias entidades Autor para uma lista de DTOs AutorResponse.
     *
     * @return Uma lista de DTO com dados do Autor.
     */
    public static List<AutorResponse> converterDeAutores(Set<Autor> autores) {
        return autores.stream()
                .map(AutorResponse::converterDeAutor)
                .collect(Collectors.toList());
    }
}
