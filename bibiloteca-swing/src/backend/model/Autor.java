package backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Classe de Representação da entidade AUTOR do banco de dados (Model)
 * <p>
 * Esta classe é responsável por realizar o mapeamento
 * e representar os dados da tabela AUTOR, do banco de dados.
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
@Table(name = "AUTOR")
public class Autor {

    @Id
    @GeneratedValue(generator = "SEQ_AUTOR", strategy = SEQUENCE)
    @SequenceGenerator(name = "SEQ_AUTOR", sequenceName = "SEQ_AUTOR", allocationSize = 1)
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "IDADE")
    private Integer idade;

    /**
     * Atributo que representa o relacionamento Many To Many entre LIVRO e AUTOR
     * <p>
     * Este mapeamento é necessário pois um livro pode pertencer a vários autores
     * e um autor pode ter vários livros relacionados a ele.
     */
    @ManyToMany
    @JoinTable(name = "LIVRO_AUTOR", joinColumns = {
        @JoinColumn(name = "FK_AUTOR", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "FK_AUTOR"))}, inverseJoinColumns = {
        @JoinColumn(name = "FK_LIVRO", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "FK_LIVRO"))})
    private List<Livro> livros;
}
