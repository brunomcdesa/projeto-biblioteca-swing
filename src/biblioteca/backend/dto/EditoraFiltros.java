package biblioteca.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditoraFiltros {

    private String nome;
    private String cnpj;
    private String nomeLivro;
}
