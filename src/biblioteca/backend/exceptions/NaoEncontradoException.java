package biblioteca.backend.exceptions;

/**
 * Exception responsável por indicar que alguma entidade não foi encontrada no banco de dados.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String message) {
        super(message);
    }
}
