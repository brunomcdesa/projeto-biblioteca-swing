package biblioteca.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static biblioteca.utils.MapUtils.mapNull;

/**
 * Classe DTO que representa os dados do Autor, retornados da API do OPEN LIBRARY.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryAutorResponse {

    @JsonProperty("name")
    private String nome;
    @JsonProperty("birth_date")
    private String dataNascimento;
    @JsonProperty("death_date")
    private String dataMorte;
    @JsonProperty("bio")
    private OpenLibraryAutorBioResponse biografia;

    /**
     * Método responsável por mapear o value do campo de biografia do Autor.
     *
     * @return Value da biografia, caso possua esse dado no objeto. Caso esse dado seja null, é retornado null.
     */
    public String getBiografiaValue() {
        return mapNull(this.biografia, OpenLibraryAutorBioResponse::getValue);
    }
}
