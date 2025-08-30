package biblioteca.backend.predicate;

import biblioteca.backend.dto.PredicateResult;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Classe responsável por montar a base dos predicates das buscas filtradas.
 * <p>
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Getter
public class PredicateBase {

    protected final StringJoiner condicoes;
    protected final Map<String, Object> parametros;

    public PredicateBase() {
        this.condicoes = new StringJoiner(" AND ");
        this.parametros = new HashMap<>();
    }

    /**
     * Método responsável efetuar o build do predicate de fato, de acordo com os filtros montados nas classes de predicates que extenderem esta classe.
     *
     * @return um PredicateResult que contém as condições e parametros dos filtros.
     */
    public PredicateResult build() {
        return condicoes.length() == 0
                ? new PredicateResult("", new HashMap<>())
                : new PredicateResult(String.format("WHERE %s ", condicoes), parametros);
    }
}
