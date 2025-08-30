package biblioteca.backend.dto;

import biblioteca.backend.enums.EGenero;
import biblioteca.backend.predicate.LivroPredicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe DTO que representa os dados de filtros da listagem de Livro
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroFiltros {

    private String titulo;
    private LocalDate dataPublicacao;
    private String isbn;
    private EGenero genero;
    private Integer editoraId;
    private Integer autorId;
    private String tituloLivroParecido;

    /**
     * Método responsável montar todo o predicate que será utilizar na listagem filtrada dos Livros, de acordo com os filtros recebidos.
     *
     * @return a classe PredicateResult, que contém todos os filtros que serão adicionados na listagem filtrada.
     */
    public PredicateResult toPredicate() {
        return new LivroPredicate()
                .comTitulo(this.titulo)
                .comDataPublicacao(this.dataPublicacao)
                .comIsbn(this.isbn)
                .comGenero(this.genero)
                .comEditoraId(this.editoraId)
                .comAutorId(this.autorId)
                .comTituloLivroParecido(tituloLivroParecido)
                .build();
    }
}
