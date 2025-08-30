package biblioteca.backend.predicate;

import static biblioteca.utils.StringUtils.isNotBlank;

/**
 * Classe responsável por montar os predicates da filtragem de Autor.
 * <p>
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class AutorPredicate extends PredicateBase {

    /**
     * Método responsável adicionar o predicate referente ao nome do Autor.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public AutorPredicate comNome(String nome) {
        if (isNotBlank(nome)) {
            condicoes.add("UPPER(a.nome) LIKE UPPER(:nome)");
            parametros.put("nome", "%" + nome.trim() + "%");
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente à idade do Autor.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public AutorPredicate comIdade(Integer idade) {
        if (idade != null) {
            condicoes.add("a.idade = :idade");
            parametros.put("idade", idade);
        }

        return this;
    }

    /**
     * Método responsável adicionar o predicate referente ao título de um livro vinculado com o Autor.
     *
     * @return a própria classe com a condição adicionada, caso esteja presente.
     */
    public AutorPredicate comTituloLivro(String tituloLivro) {
        if (isNotBlank(tituloLivro)) {
            condicoes.add("UPPER(l.titulo) LIKE UPPER(:tituloLivro)");
            parametros.put("tituloLivro", "%" + tituloLivro.trim() + "%");
        }

        return this;
    }
}
