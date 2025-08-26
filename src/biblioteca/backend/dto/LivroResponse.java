package biblioteca.backend.dto;

import biblioteca.backend.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponse {

    private Integer id;
    private String titulo;
    private LocalDate dataPublicacao;
    private String isbn;
    private String genero;
    private String nomeEditora;
    private List<String> nomesAutores;
    private List<String> titulosLivrosParecidos;

    public static LivroResponse converterDeLivro(Livro livro) {
        return LivroResponse.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .dataPublicacao(livro.getDataPublicacao())
                .isbn(livro.getIsbn())
                .genero(livro.getGenero().getDescricao())
                .nomeEditora(livro.getEditora().getNome())
                .nomesAutores(livro.getNomesAutores())
                .titulosLivrosParecidos(livro.getTitulosLivrosParecidos())
                .build();
    }
}
