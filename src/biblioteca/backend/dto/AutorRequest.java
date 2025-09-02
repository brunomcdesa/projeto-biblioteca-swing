package biblioteca.backend.dto;

import biblioteca.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static biblioteca.utils.MapUtils.mapNull;

/**
 * Classe DTO que representa os dados de entrada para salvar/alterar uma entidade Autor.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorRequest {

    private String nome;
    private LocalDate dataNascimento;
    private LocalDate dataMorte;
    private String biografia;

    /**
     * Método responsável por fazer a conversão de um autor response retornado da API do OPEN LIBRARY em um AutorRequest.
     *
     * @return Um AutorRequest convertido de um OpenLibraryAutorResponse.
     */
    private static AutorRequest converterDeOpenLibraryAutorResponse(OpenLibraryAutorResponse openLibraryAutorResponse) {
        return AutorRequest.builder()
                .nome(openLibraryAutorResponse.getNome())
                .dataNascimento(mapNull(openLibraryAutorResponse.getDataNascimento(), StringUtils::converterDataEmStringEmInglesParaLocalDate))
                .dataMorte(mapNull(openLibraryAutorResponse.getDataMorte(), StringUtils::converterDataEmStringEmInglesParaLocalDate))
                .biografia(openLibraryAutorResponse.getBiografiaValue())
                .build();
    }

    /**
     * Método responsável por fazer a conversão de uma lista de autores responses retornados da API do OPEN LIBRARY em uma lista de AutorRequest.
     *
     * @return Uma lista de AutorRequest convertidos da lista de OpenLibraryAutorResponse.
     */
    public static List<AutorRequest> converterDeOpenLibraryAutorResponses(List<OpenLibraryAutorResponse> openLibraryAutorResponse) {
        return openLibraryAutorResponse.stream()
                .map(AutorRequest::converterDeOpenLibraryAutorResponse)
                .collect(Collectors.toList());
    }
}
