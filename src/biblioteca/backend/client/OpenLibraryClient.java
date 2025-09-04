package biblioteca.backend.client;

import biblioteca.backend.dto.OpenLibraryAutorResponse;
import biblioteca.backend.dto.OpenLibraryLivroResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.exceptions.ValidacaoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Classe de client para a api do OPEN LIBRARY.
 * <p>
 * Esta classe é responsável por efetuar as requisições para a API da OPEN LIBRARY, para buscar os dados dos livros.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class OpenLibraryClient {

    private static final String OPEN_LIBRARY_URL = "https://openlibrary.org";

    private final OkHttpClient httpClient;
    private final ObjectMapper mapper;

    /**
     * Método responsável por buscar na API da OPEN LIBRARY os dados do livro que possuir o mesmo ISBN passado por parâmetro.
     *
     * @return Opcional de OpenLibraryLivroResponse, para que retorne os dados da api relevantes para o sistema caso encontre o livro, e Optional vazio caso nao obtenha nenhum dado.
     * @throws ValidacaoException     caso ocorra algum erro durante a requisição.
     * @throws NaoEncontradoException caso não encontre o livro na API e seja retornado o status 404.
     */
    public Optional<OpenLibraryLivroResponse> buscarLivroPorIsbn(String isbn) {
        Request request = new Request.Builder()
                .url(format(OPEN_LIBRARY_URL + "/isbn/%s.json", isbn))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.code() == 404) {
                    throw new NaoEncontradoException(format("Livro não encontrado com o ISBN: %s", isbn));
                }

                throw new ValidacaoException(format("Erro na requisição: %s", response.code()));
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String jsonResponse = responseBody.string();
                OpenLibraryLivroResponse livroResponse = mapper.readValue(jsonResponse, OpenLibraryLivroResponse.class);
                return Optional.of(livroResponse);
            }
            return Optional.empty();
        } catch (IOException ex) {
            throw new ValidacaoException("Erro ao tentar buscar livros por ISBN.");
        }

    }

    /**
     * Método responsável por buscar na API da OPEN LIBRARY os dados dos autores que possuirem as mesmas keys que as informadas por parametro.
     *
     * @return Lista de OpenLibraryAutorResponse, para que retorne os dados da api relevantes para o sistema caso encontre os autores pelas keyus informadas.
     * @throws ValidacaoException caso ocorra algum erro durante a requisição.
     */
    public List<OpenLibraryAutorResponse> buscarAutoresPorKeys(List<String> autoreskeys) {
        return autoreskeys.stream()
                .map(autorKey -> {
                    Request request = new Request.Builder()
                            .url(format(OPEN_LIBRARY_URL + "%s.json", autorKey))
                            .build();

                    try (Response response = httpClient.newCall(request).execute()) {
                        if (!response.isSuccessful()) {
                            throw new ValidacaoException(format("Erro na requisição: %s", response.code()));
                        }

                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String jsonResponse = responseBody.string();
                            return mapper.readValue(jsonResponse, OpenLibraryAutorResponse.class);
                        }
                        return null;
                    } catch (IOException ex) {
                        throw new ValidacaoException("Erro ao tentar buscar dados dos Autores.");
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
