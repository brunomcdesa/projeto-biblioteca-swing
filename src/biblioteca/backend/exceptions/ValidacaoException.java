package biblioteca.backend.exceptions;

/**
 * Exception responsável por indicar um erro de validação.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String message) {
        super(message);
    }
}
