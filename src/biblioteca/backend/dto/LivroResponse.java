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
import static biblioteca.backend.dto.LivroParecidoResponse.converterDeLivros;

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
    private String isbn10;
    private String isbn13;
    private EGenero genero;
    private EditoraResponse editora;
    private List<AutorResponse> autores;
    private List<LivroParecidoResponse> livrosParecidos;

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
                .isbn10(livro.getIsbn10())
                .isbn13(livro.getIsbn13())
                .genero(livro.getGenero())
                .editora(converterDeEditora(livro.getEditora()))
                .autores(converterDeAutores(livro.getAutores()))
                .livrosParecidos(converterDeLivros(livro.getLivrosParecidos()))
                .build();
    }

    /**
     * Método responsável por verificar se o livro tem ou não livros parecidos.
     *
     * @return true se o livro possuir livros parecidos. false se o livro não possuir livros parecidos.
     */
    public boolean possuiTitulosParecidos() {
        return !this.livrosParecidos.isEmpty();
    }

    /**
     * Método responsável por mapear os nomes dos autores do livro.
     *
     * @return uma lista de nomes de Autores.
     */
    public List<String> getAutoresNomes() {
        return this.autores.stream()
                .map(AutorResponse::getNome)
                .sorted()
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

    /**
     * Método responsável por mapear os IDs dos livros parecidos do livro e forma de Objeto.
     *
     * @return uma lista de Ids de Livros em forma de Objeto.
     */
    public List<Object> getLivrosParecidosIdsObjects() {
        return this.livrosParecidos.stream()
                .map(LivroParecidoResponse::getId)
                .map(id -> (Object) id)
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por mapear os títulos dos livros parecidos por ordem alfabética.
     *
     * @return uma lista de títulos dos livros parecidos ordenados por ordem alfabética.
     */
    public List<String> getTitulosLivroParecidosOrdenados() {
        return this.livrosParecidos.stream()
                .map(LivroParecidoResponse::getTitulo)
                .sorted()
                .collect(Collectors.toList());
    }
}
