package biblioteca.backend.model;

import biblioteca.backend.dto.LivroImportacaoDto;
import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.enums.EGenero;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Classe de Representação da entidade LIVRO do banco de dados (Model)
 * <p>
 * Esta classe é responsável por realizar o mapeamento
 * e representar os dados da tabela LIVRO, do banco de dados.
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

    @Column(name = "ISBN_10")
    private String isbn10;

    @Column(name = "ISBN_13")
    private String isbn13;

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
                .isbn10(livroRequest.getIsbn10())
                .isbn13(livroRequest.getIsbn13())
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
                .isbn10(livroRequest.getIsbn10())
                .isbn13(livroRequest.getIsbn13())
                .editora(editora)
                .autores(autores)
                .build();
    }

    /**
     * Método responsável por realizar a montar um Livro de acordo com os dados recebidos por parâmetro da importação.
     *
     * @return um novo Livro.
     */
    public static Livro montarLivroPorImportacao(LivroImportacaoDto livroImportacaoDto, Editora editora, Set<Autor> autores) {
        return Livro.builder()
                .titulo(livroImportacaoDto.getTitulo())
                .dataPublicacao(livroImportacaoDto.getDataPublicacao())
                .isbn10(livroImportacaoDto.getIsbn10())
                .isbn13(livroImportacaoDto.getIsbn13())
                .editora(editora)
                .autores(autores)
                .build();
    }


    /**
     * Método responsável atualizar os dados do livro solicitante, com base nos parâmetros recebidos.
     */
    public void atualizarDados(LivroRequest request, Editora editora, Set<Autor> autores,
                               Set<Livro> livrosParecidos) {
        this.setTitulo(request.getTitulo());
        this.setDataPublicacao(request.getDataPublicacao());
        this.setIsbn10(request.getIsbn10());
        this.setIsbn13(request.getIsbn13());
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

    /**
     * Método responsável atualizar os dados do livro solicitante, com base nos parâmetros recebidos.
     */
    public void atualizarDadosPorImportacao(LivroImportacaoDto livroImportacaoDto, Editora editora, Set<Autor> autores) {
        this.setTitulo(livroImportacaoDto.getTitulo());
        this.setDataPublicacao(livroImportacaoDto.getDataPublicacao());
        this.setIsbn10(livroImportacaoDto.getIsbn10());
        this.setIsbn13(livroImportacaoDto.getIsbn13());
        this.setGenero(livroImportacaoDto.getGenero());
        this.setEditora(editora);
        this.setAutores(autores);
    }
}
