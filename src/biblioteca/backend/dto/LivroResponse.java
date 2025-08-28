package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import biblioteca.backend.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static biblioteca.backend.dto.AutorResponse.converterDeAutores;
import static biblioteca.backend.dto.EditoraResponse.converterDeEditora;

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
public class LivroResponse {

    private Integer id;
    private String titulo;
    private LocalDate dataPublicacao;
    private String isbn;
    private EGenero genero;
    private EditoraResponse editora;
    private List<AutorResponse> autores;
    private List<String> titulosLivrosParecidos;

    /**
     * Método responsável por realizar a conversão de uma entidade Livro para um DTO LivroResponse.
     *
     * @return Um DTO com dados do Livro.
     */
    public static LivroResponse converterDeLivro(Livro livro) {
        return LivroResponse.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .dataPublicacao(livro.getDataPublicacao())
                .isbn(livro.getIsbn())
                .genero(livro.getGenero())
                .editora(converterDeEditora(livro.getEditora()))
                .autores(converterDeAutores(livro.getAutores()))
                .titulosLivrosParecidos(livro.getTitulosLivrosParecidos())
                .build();
    }

    /**
     * Método responsável por verificar se o livro tem ou não livros parecidos.
     *
     * @return true se o livro possuir livros parecidos. false se o livro não possuir livros parecidos.
     */
    public boolean possuiTitulosParecidos() {
        return !this.titulosLivrosParecidos.isEmpty();
    }

    /**
     * Método responsável por mapear os nomes dos autores do livro.
     *
     * @return uma lista de nomes de Autores.
     */
    public List<String> getAutoresNomes() {
        return this.autores.stream()
                .map(AutorResponse::getNome)
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por mapear os IDs dos autores do livro e forma de Objeto.
     *
     * @return uma lista de Ids de Autores em forma de Objeto.
     */
    public List<Object> getAutoresIdsObjects() {
        return this.autores.stream()
                .map(AutorResponse::getId)
                .map(id -> (Object) id)
                .collect(Collectors.toList());
    }
}
