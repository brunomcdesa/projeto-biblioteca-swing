package biblioteca.backend.model;

import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.enums.EGenero;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;
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
    private LocalDate dataPublicacao;

    @Column(name = "ISBN")
    private String isbn;

    @Enumerated(STRING)
    @Column(name = "GENERO")
    private EGenero genero;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FK_EDITORA", referencedColumnName = "ID",
        foreignKey = @ForeignKey(name = "FK_EDITORA"))
    private Editora editora;

    @ManyToMany
    @JoinTable(name = "LIVRO_AUTOR", joinColumns = {
        @JoinColumn(name = "FK_LIVRO", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "FK_LIVRO"))}, inverseJoinColumns = {
        @JoinColumn(name = "FK_AUTOR", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "FK_AUTOR"))})
    private Set<Autor> autores;

    @ManyToMany
    @JoinTable(
            name = "LIVRO_PARECIDO",
            joinColumns = @JoinColumn(name = "LIVRO_ID", referencedColumnName = "ID",
                    foreignKey = @ForeignKey(name = "FK_LIVRO_ORIGEM")),
            inverseJoinColumns = @JoinColumn(name = "LIVRO_PARECIDO_ID", referencedColumnName = "ID",
                    foreignKey = @ForeignKey(name = "FK_LIVRO_PARECIDO")))
    private Set<Livro> livrosParecidos;

    /**
     * Método responsável por realizar a montar um Livro de acordo com os dados recebidos por parâmetro.
     *
     * @return um novo Livro.
     */
    public static Livro montarLivro(LivroRequest livroRequest, Editora editora, Set<Autor> autores,
                                    Set<Livro> livrosParecidos) {
        return Livro.builder()
                .titulo(livroRequest.getTitulo())
                .dataPublicacao(livroRequest.getDataPublicacao())
                .genero(livroRequest.getGenero())
                .isbn(livroRequest.getIsbn())
                .editora(editora)
                .autores(autores)
                .livrosParecidos(livrosParecidos)
                .build();
    }

    /**
     * Método responsável por realizar a montar um Livro de acordo com os dados recebidos por parâmetro.
     *
     * @return um novo Livro.
     */
    public static Livro montarLivro(LivroRequest livroRequest, Editora editora, Set<Autor> autores) {
        return Livro.builder()
                .titulo(livroRequest.getTitulo())
                .dataPublicacao(livroRequest.getDataPublicacao())
                .isbn(livroRequest.getIsbn())
                .editora(editora)
                .autores(autores)
                .build();
    }

    /**
     * Método responsável por buscar os títulos dos livros parecidos com o livro solicitante.
     *
     * @return uma lista de títulos de livros parecidos.
     */
    public List<String> getTitulosLivrosParecidos() {
        return livrosParecidos.stream()
                .map(Livro::getTitulo)
                .collect(Collectors.toList());
    }

    /**
     * Método responsável atualizar os dados do livro solicitante, com base nos parâmetros recebidos.
     */
    public void atualizarDados(LivroRequest request, Editora editora, Set<Autor> autores,
                               Set<Livro> livrosParecidos) {
        this.setTitulo(request.getTitulo());
        this.setDataPublicacao(request.getDataPublicacao());
        this.setIsbn(request.getIsbn());
        this.setGenero(request.getGenero());
        this.setEditora(editora);
        this.setAutores(autores);
        this.setLivrosParecidos(livrosParecidos);
        livrosParecidos.forEach(livroParecido -> livroParecido.atualizarLivrosParecidos(this));
    }

    /**
     * Método responsável atualizar os dados dos livros parecidos, adicionando o livro passado
     */
    private void atualizarLivrosParecidos(Livro livro) {
        this.livrosParecidos.add(livro);
    }
}
