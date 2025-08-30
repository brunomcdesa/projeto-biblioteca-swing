package biblioteca.backend.predicate;

import static biblioteca.utils.StringUtils.isNotBlank;

/**
 * Classe responsável por montar os predicates da filtragem de Editora.
 * <p>
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class EditoraPredicate extends PredicateBase {

    /**
     * Método responsável adicionar o predicate referente ao nome da Editora.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public EditoraPredicate comNome(String nome) {
        if (isNotBlank(nome)) {
            condicoes.add("UPPER(e.nome) LIKE UPPER(:nome)");
            parametros.put("nome", "%" + nome.trim() + "%");
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao cnpj da Editora.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public EditoraPredicate comCnpj(String cnpj) {
        if (isNotBlank(cnpj)) {
            condicoes.add("e.cnpj LIKE :cnpj");
            parametros.put("cnpj", "%" + cnpj.trim() + "%");
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao título de um livro vinculado com a Editora.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public EditoraPredicate comTituloLivro(String tituloLivro) {
        if (isNotBlank(tituloLivro)) {
            condicoes.add("UPPER(l.titulo) LIKE UPPER(:tituloLivro)");
            parametros.put("tituloLivro", "%" + tituloLivro.trim() + "%");
        }

        return this;
    }
}
