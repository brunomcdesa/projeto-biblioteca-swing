package biblioteca.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static biblioteca.utils.MapUtils.mapNullComBackup;

/**
 * Classe DTO que representa os dados do Livro, retornados da API do OPEN LIBRARY.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryLivroResponse {

    @JsonProperty("title")
    private String titulo;
    @JsonProperty("authors")
    private List<OpenLibraryKeyResponse> keysAutores;
    @JsonProperty("publish_date")
    private String dataPublicacao;
    @JsonProperty("publishers")
    private List<String> editoras;
    @JsonProperty("number_of_pages")
    private Integer numeroPaginas;
    @JsonProperty("isbn_10")
    private List<String> isbn10;
    @JsonProperty("isbn_13")
    private List<String> isbn13;

    /**
     * Método responsável por mapear o o campo key, do campo de keys dos autores do livro.
     *
     * @return Lista de Keys dos autores.
     */
    public List<String> getKeysDosAutores() {
        return mapNullComBackup(this.keysAutores,
                keys -> keys.stream()
                        .map(OpenLibraryKeyResponse::getKey)
                        .collect(Collectors.toList()),
                Collections.emptyList());
    }
}
