package biblioteca.backend.model;

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
}
