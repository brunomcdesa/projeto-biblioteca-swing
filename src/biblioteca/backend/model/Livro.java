package biblioteca.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Classe de Representação da entidade LIVRO do banco de dados (Model)
 * <p>
 * Esta classe é responsável por realizar o mapeamento
 * e representar os dados da tabela LIVRO, do banco de dados.
 *
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LIVRO")
public class Livro {

    @Id
    @GeneratedValue(generator = "SEQ_LIVRO", strategy = SEQUENCE)
    @SequenceGenerator(name = "SEQ_LIVRO", sequenceName = "SEQ_LIVRO", allocationSize = 1)
    private Integer id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "DATA_PUBLICACAO")
    private LocalDateTime dataPublicacao;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FK_EDITORA", referencedColumnName = "ID",
        foreignKey = @ForeignKey(name = "FK_EDITORA"), nullable = false)
    private Editora editora;

    @ManyToMany
    @JoinTable(name = "LIVRO_AUTOR", joinColumns = {
        @JoinColumn(name = "FK_LIVRO", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "FK_LIVRO"))}, inverseJoinColumns = {
        @JoinColumn(name = "FK_AUTOR", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "FK_AUTOR"))})
    private List<Autor> autores;
}
