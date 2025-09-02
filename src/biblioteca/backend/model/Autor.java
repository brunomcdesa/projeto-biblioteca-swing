package biblioteca.backend.model;

import biblioteca.backend.dto.AutorRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static biblioteca.utils.MapUtils.mapNull;
import static biblioteca.utils.MapUtils.mapNullComBackup;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Classe de Representação da entidade AUTOR do banco de dados (Model)
 * <p>
 * Esta classe é responsável por realizar o mapeamento
 * e representar os dados da tabela AUTOR, do banco de dados.
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

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "DATA_MORTE")
    private LocalDate dataMorte;

    @Column(name = "BIOGRAFIA", length = 400)
    private String biografia;

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

    /**
     * Método responsável por realizar a conversão de um AutorRequest em uma entidade Autor.
     *
     * @return um novo Autor.
     */
    public static Autor converterDeRequest(AutorRequest request) {
        return Autor.builder()
                .nome(request.getNome())
                .dataNascimento(request.getDataNascimento())
                .dataMorte(request.getDataMorte())
                .idade(mapNull(request.getDataNascimento(),
                        dataNascimento -> calcularIdade(dataNascimento, mapNullComBackup(request.getDataMorte(), data -> data, LocalDate.now()))))
                .biografia(request.getBiografia())
                .build();
    }

    /**
     * Método responsável por atualizar os dados da entidade Autor, de acordo com os novos dados da request.
     */
    public void atualizarDados(AutorRequest request) {
        this.setNome(request.getNome());
        this.setDataNascimento(request.getDataNascimento());
        this.setDataMorte(request.getDataMorte());
        this.setIdade(mapNull(request.getDataNascimento(),
                dataNascimento -> calcularIdade(dataNascimento, mapNullComBackup(request.getDataMorte(), data -> data, LocalDate.now()))));
        this.setBiografia(request.getBiografia());
    }

    /**
     * Método responsável por verifiacar se o Autor possui livros vinculados a ele.
     *
     * @return true se o Autor possuir livros vinculados. false se o Autor não possuir livros vinculados.
     */
    public boolean possuiLivrosVinculados() {
        return !this.livros.isEmpty();
    }

    /**
     * Método responsável por calcular a idade do Autor, de acordo com a data de nascimento e a data final informada.
     * A data final pode ser a data de morte do Autor, ou a data atual.
     *
     * @return anos de idade que autor teve, ou tem atualmente.
     */
    private static Integer calcularIdade(LocalDate dataNascimento, LocalDate dataFinal) {
        return Period.between(dataNascimento, dataFinal).getYears();
    }
}
