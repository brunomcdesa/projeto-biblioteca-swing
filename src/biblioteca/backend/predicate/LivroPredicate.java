package biblioteca.backend.predicate;

import biblioteca.backend.enums.EGenero;

import java.time.LocalDate;

import static biblioteca.utils.StringUtils.isNotBlank;

/**
 * Classe responsável por montar os predicates da filtragem de Livro.
 * <p>
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class LivroPredicate extends PredicateBase {

    /**
     * Método responsável adicionar o predicate referente ao ID do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comId(Integer id) {
        if (id != null) {
            condicoes.add("l.id = :id");
            parametros.put("id", id);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao título do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comTitulo(String titulo) {
        if (isNotBlank(titulo)) {
            condicoes.add("UPPER(l.titulo) LIKE UPPER(:titulo)");
            parametros.put("titulo", "%" + titulo.trim() + "%");
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente à data de publicação do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comDataPublicacao(LocalDate dataPublicacao) {
        if (dataPublicacao != null) {
            condicoes.add("l.dataPublicacao = :dataPublicacao");
            parametros.put("dataPublicacao", dataPublicacao);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao título do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comIsbn(String isbn) {
        if (isNotBlank(isbn)) {
            condicoes.add("UPPER(l.isbn) LIKE UPPER(:isbn)");
            parametros.put("isbn", "%" + isbn.trim() + "%");
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao gênero do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comGenero(EGenero genero) {
        if (genero != null) {
            condicoes.add("l.genero = :genero");
            parametros.put("genero", genero);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao id da editora do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comEditoraId(Integer editoraId) {
        if (editoraId != null) {
            condicoes.add("e.id = :editoraId");
            parametros.put("editoraId", editoraId);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao id do autor do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comAutorId(Integer autorId) {
        if (autorId != null) {
            condicoes.add("a.id = :autorId");
            parametros.put("autorId", autorId);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao ID do livro parecido com o Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comIdLivroParecido(Integer idLivroParecido) {
        if (idLivroParecido != null) {
            condicoes.add("lp.id = :idLivroParecido");
            parametros.put("idLivroParecido", idLivroParecido);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao título dos livros parecidos do Livro.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public LivroPredicate comTituloLivroParecido(String tituloLivroParecido) {
        if (isNotBlank(tituloLivroParecido)) {
            condicoes.add("UPPER(lp.titulo) LIKE UPPER(:tituloLivroParecido)");
            parametros.put("tituloLivroParecido", "%" + tituloLivroParecido.trim() + "%");
        }

        return this;
    }
}
