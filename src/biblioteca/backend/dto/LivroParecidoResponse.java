package biblioteca.backend.dto;

import biblioteca.backend.model.Livro;
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
public class LivroParecidoResponse {

    public Integer id;
    public String titulo;

    /**
     * Método responsável por realizar a conversão de uma entidade Livro para um DTO LivroParecidoResponse.
     *
     * @return Um DTO com dados do Livro.
     */
    private static LivroParecidoResponse converterDeLivro(Livro livro) {
        return LivroParecidoResponse.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .build();
    }

    /**
     * Método responsável por realizar a conversão de várias entidades Livro para uma lista de DTOs LivroParecidoResponse.
     *
     * @return Uma lista de DTO com dados dos livros parecidos.
     */
    public static List<LivroParecidoResponse> converterDeLivros(Set<Livro> livros) {
        return livros.stream()
                .map(LivroParecidoResponse::converterDeLivro)
                .collect(Collectors.toList());
    }
}
