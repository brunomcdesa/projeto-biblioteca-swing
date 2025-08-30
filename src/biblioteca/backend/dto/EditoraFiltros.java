package biblioteca.backend.dto;

import biblioteca.backend.predicate.EditoraPredicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa os dados de filtros da listagem de Editora
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditoraFiltros {

    private Integer id;
    private String nome;
    private String cnpj;
    private Integer idLivro;
    private String tituloLivro;

    /**
     * Método responsável montar todo o predicate que será utilizar na listagem filtrada das Editoras, de acordo com os filtros recebidos.
     *
     * @return a classe PredicateResult, que contém todos os filtros que serão adicionados na listagem filtrada.
     */
    public PredicateResult toPredicate() {
        return new EditoraPredicate()
                .comId(this.id)
                .comNome(this.nome)
                .comCnpj(this.cnpj)
                .comIdLivro(this.idLivro)
                .comTituloLivro(this.tituloLivro)
                .build();
    }
}
