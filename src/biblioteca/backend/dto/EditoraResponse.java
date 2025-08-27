package biblioteca.backend.dto;

import biblioteca.backend.model.Editora;
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
public class EditoraResponse {

    private Integer id;
    private String nome;
    private String cnpj;

    /**
     * Método responsável por realizar a conversão de uma entidade Editora para um DTO EditoraResponse.
     *
     * @return Um DTO com dados da Editora.
     */
    public static EditoraResponse converterDeEditora(Editora editora) {
        return EditoraResponse.builder()
                .id(editora.getId())
                .nome(editora.getNome())
                .cnpj(editora.getCnpj())
                .build();
    }
}
