package biblioteca.backend.dto;

import biblioteca.backend.predicate.AutorPredicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa os dados de filtros da listagem de Autor
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorFiltros {

    private String nome;
    private Integer idade;
    private String tituloLivro;

    /**
     * Método responsável montar todo o predicate que será utilizar na listagem filtrada dos Autores, de acordo com os filtros recebidos.
     *
     * @return a classe PredicateResult, que contém todos os filtros que serão adicionados na listagem filtrada.
     */
    public PredicateResult toPredicate() {
        return new AutorPredicate()
                .comNome(this.nome)
                .comIdade(this.idade)
                .comTituloLivro(this.tituloLivro)
                .build();
    }
}
