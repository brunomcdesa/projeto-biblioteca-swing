package biblioteca.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Classe DTO que representa os dados do predicate que será montado nas listagens filtradas
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class PredicateResult {
    private final String whereClause;
    private final Map<String, Object> params;
}
