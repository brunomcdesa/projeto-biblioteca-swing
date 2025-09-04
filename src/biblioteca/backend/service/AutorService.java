package biblioteca.backend.service;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.dto.AutorFiltros;
import biblioteca.backend.dto.AutorRequest;
import biblioteca.backend.dto.AutorResponse;
import biblioteca.backend.dto.SelectResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.exceptions.ValidacaoException;
import biblioteca.backend.model.Autor;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static biblioteca.backend.dto.SelectResponse.montarSelectResponse;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Classe de serviço para Autor.
 * <p>
 * Esta classe é responsável por guardar a logica de validação e conversão dos dados,
 * e também é a classe que vai chamar as operações do banco de dados através do DAO.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class AutorService {

    private final IAutorDAO autorDAO;

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     *
     * @return O autor salvo no banco de dados
     */
    public Autor salvar(AutorRequest autorRequest) {
        Autor autor = Autor.converterDeRequest(autorRequest);
        return autorDAO.salvar(autor);
    }

    /**
     * Método responsável por listar todos os Autores do sistema.
     *
     * @return uma lista de Autores.
     */
    public List<AutorResponse> listarTodos() {
        return autorDAO.listarTodos().stream()
                .map(AutorResponse::converterDeAutor)
                .collect(toList());
    }

    /**
     * Método responsável por listar todos os Autores do sistema por filtros.
     *
     * @return uma lista de Autores de acordo com os filtros passados por parametro.
     */
    public List<AutorResponse> listarTodosPorFiltros(AutorFiltros filtros) {
        return autorDAO.listarTodosPorPredicate(filtros.toPredicate()).stream()
                .map(AutorResponse::converterDeAutor)
                .collect(toList());
    }

    /**
     * Método responsável por editar um autor específico, de acordo com os novos dados da request.
     */
    public void editar(Integer id, AutorRequest request) {
        Autor autor = this.findById(id);
        autor.atualizarDados(request);

        autorDAO.salvar(autor);
    }

    /**
     * Método responsável por editar um autor específico durante a importação, de acordo com os novos dados da request.
     */
    public Autor editarPorImportacao(Autor autor, AutorRequest request) {
        if (request != null) {
            autor.atualizarDados(request);

            return autorDAO.salvar(autor);
        }
        return null;
    }

    /**
     * Método responsável por deletar um autor específico do banco de dados.
     */
    public void deletar(Integer id) {
        Autor autor = this.findById(id);
        validarAutorComLivrosVinculados(autor);

        autorDAO.deletar(id);
    }

    /**
     * Método responsável por buscar um Autor pelo ID dele.
     * <p>
     * Caso não encontre nenhum Autor com o mesmo ID, será lançado uma excepion.
     *
     * @return um Autor.
     */
    private Autor findById(Integer id) {
        return autorDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Autor não encontrado."));
    }

    /**
     * Método responsável por buscar uma lista de Autores pelos IDs deles.
     *
     * @return um Set de Autores.
     */
    public Set<Autor> findByIdIn(List<Integer> autoresIds) {
        return new HashSet<>(autorDAO.findByIdIn(autoresIds));
    }

    /**
     * Método responsável por buscar uma lista dos Autores e converter para o DTO SelectResponse.
     *
     * @return Uma lista de SelectResponse.
     */
    public List<SelectResponse> buscarAutoresSelect() {
        return autorDAO.listarTodos().stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .map(autor -> montarSelectResponse(autor.getId(), autor.getNome()))
                .collect(toList());
    }

    /**
     * Método responsável por buscar os autores de acordo com os nomes deles no banco de dados, e retornar eles caso já estejam cadastrados.
     * Caso não estejam cadastrados, deve ser criado um novo autor com os nomes infomados e retornar estes novos autores.
     *
     * @return Um Set de Autores já existentes no banco de acordo com os nomes, ou um Set de Autores recém criados com os nomes novos.
     */
    public Set<Autor> buscarAutoresOuCriarAutores(List<AutorRequest> autoresRequests) {
        List<String> nomesAutores = autoresRequests.stream()
                .map(AutorRequest::getNome)
                .map(String::toUpperCase)
                .collect(toList());
        Set<Autor> autores = new HashSet<>(autorDAO.findByNomes(nomesAutores));

        return autores.isEmpty()
                ? autoresRequests.stream()
                .map(this::salvar)
                .collect(toSet())
                : autores;
    }

    /**
     * Método responsável por buscar o autor de acordo com o nome dele no banco de dados, e retornar ele caso já esteja cadastrado.
     * Caso não esteja cadastrado, deve ser criado um novo autor com o nome infomado e retornar este novo autore.
     *
     * @return Um Autor já existentes no banco de acordo co o nome, ou um Autor recém criado com dados da request.
     */
    public Autor buscarAutorEEditarOuCriarAutor(AutorRequest autorRequest) {
        return autorDAO.findByNome(autorRequest.getNome())
                .map(autor -> this.editarPorImportacao(autor, autorRequest))
                .orElseGet(() -> this.salvar(autorRequest));
    }

    /**
     * Método responsável por validar se o autor tem livros vinculados.
     *
     * @throws ValidacaoException caso o autor possua livros vinculados a ele.
     */
    private void validarAutorComLivrosVinculados(Autor autor) {
        if (autor.possuiLivrosVinculados()) {
            throw new ValidacaoException("Autor possui livros vinculados. Por favor remova o vínculo com os livros antes de excluir o autor.");
        }
    }
}
