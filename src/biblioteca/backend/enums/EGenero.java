package biblioteca.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EGenero {

    TERROR("Terror"),
    COMEDIA("Comédia"),
    ROMANCE("Romance"),
    SUSPENSE("Suspense"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    BIOGRAFIA("Biografia");

    private final String descricao;
}
