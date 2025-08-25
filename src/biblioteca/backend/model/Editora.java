package biblioteca.backend.model;

import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.EditoraRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Classe de Representação da entidade EDITORA do banco de dados (Model)
 * <p>
 * Esta classe é responsável por realizar o mapeamento
 * e representar os dados da tabela EDITORA, do banco de dados.
 *
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EDITORA")
public class Editora {

    @Id
    @GeneratedValue(generator = "SEQ_EDITORA", strategy = SEQUENCE)
    @SequenceGenerator(name = "SEQ_EDITORA", sequenceName = "SEQ_EDITORA", allocationSize = 1)
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CNPJ")
    private String cnpj;

    /**
     * Método responsável por realizar a conversão de um EditoraRequest em uma entidade Editora.
     */
    public static Editora converterDeRequet(EditoraRequest editoraRequest) {
        return Editora.builder()
                .nome(editoraRequest.getNome())
                .cnpj(editoraRequest.getCnpj())
                .build();
    }

    /**
     * Método responsável por atualizar os dados da entidade Autor, de acordo com os novos dados da request.
     */
    public void atualizarDados(EditoraRequest request) {
        this.setNome(request.getNome());
        this.setCnpj(request.getCnpj());
    }
}
