package biblioteca.backend.service;

import biblioteca.backend.dao.contract.ILivroDAO;
import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.model.Autor;
import biblioteca.backend.model.Editora;
import biblioteca.backend.model.Livro;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Classe de serviço para Livro.
 * <p>
 * Esta classe é responsável por guardar a logica de validação e conversão dos dados,
 * e também é a classe que vai chamar as operações do banco de dados através do DAO.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
@RequiredArgsConstructor
public class LivroService {

    private final ILivroDAO livroDAO;

    /**
     * Método responsável por converter a request em uma entidade,
     * e salvar esta nova entidade no banco de dados.
     */
    public void salvar(LivroRequest livroRequest, Editora editora, Set<Autor> autores) {
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByGenero(livroRequest.getGenero()));
        Livro novoLivro = Livro.montarLivro(livroRequest, editora, autores, livrosParecidos);

        livroDAO.salvar(novoLivro);
    }

    /**
     * Método responsável por listar todos os Livros do sistema.
     *
     * @return uma lista de Livros.
     */
    public List<LivroResponse> listarTodos() {
        return livroDAO.listarTodos().stream()
                .map(LivroResponse::converterDeLivro)
                .collect(toList());
    }

    /**
     * Método responsável por editar um livro específico, de acordo com os novos dados da request.
     */
    public void editar(Integer id, LivroRequest livroRequest, Editora editora, Set<Autor> autores) {
        Livro livro = this.findById(id);
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByGenero(livroRequest.getGenero()));

        livro.atualizarDados(livroRequest, editora, autores, livrosParecidos);

        livroDAO.salvar(livro);
    }

    /**
     * Método responsável por deletar um livro específico do banco de dados.
     */
    public void deletar(Integer id) {
        livroDAO.deletar(id);
    }

    /**
     * Método responsável por buscar um Livro pelo ID dele.
     * <p>
     * Caso não encontre nenhum Livro com o mesmo ID, será lançado uma excepion.
     *
     * @return um Livro.
     */
    private Livro findById(Integer id) {
        return livroDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Livro não encontrado."));
    }
}
