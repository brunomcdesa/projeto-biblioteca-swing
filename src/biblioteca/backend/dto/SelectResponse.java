package biblioteca.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa os dados para os campos Select.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectResponse {

    private Object value;
    private String label;

    /**
     * Método responsável por montar o DTO de SelectResponse de acordo com os parametros recebidos.
     *
     * @return Um DTO de SelectResponse.
     */
    public static SelectResponse montarSelectResponse(Object value, String label) {
        return SelectResponse.builder()
                .value(value)
                .label(label)
                .build();
    }

    /**
     * Método sobreescrito do toString(), para que os campos selects mostrem apenas a label, ao inves do objeto completo.
     *
     * @return Label do SelectResponse;
     */
    @Override
    public String toString() {
        return this.label;
    }
}
