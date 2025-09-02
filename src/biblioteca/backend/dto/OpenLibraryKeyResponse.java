package biblioteca.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa os da key, retornados da API do OPEN LIBRARY.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenLibraryKeyResponse {

    @JsonProperty("key")
    private String key;
}
