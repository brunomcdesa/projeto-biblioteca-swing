package biblioteca.backend.service;

import biblioteca.backend.dao.contract.ILivroDAO;
import biblioteca.backend.dao.impl.LivroDAOImpl;
import biblioteca.backend.dto.LivroRequest;
import biblioteca.backend.dto.LivroResponse;
import biblioteca.backend.exceptions.NaoEncontradoException;
import biblioteca.backend.model.Autor;
import biblioteca.backend.model.Editora;
import biblioteca.backend.model.Livro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class LivroService {

    private final ILivroDAO livroDAO;
    private final AutorService autorService;
    private final EditoraService editoraService;

    public LivroService() {
        this.livroDAO = new LivroDAOImpl();
        this.autorService = new AutorService();
        this.editoraService = new EditoraService();
    }

    public void salvar(LivroRequest livroRequest) {
        Editora editora = editoraService.findById(livroRequest.getEditoraId());
        Set<Autor> autores = autorService.findByIdIn(livroRequest.getAutoresIds());
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByGenero(livroRequest.getGenero()));
        Livro novoLivro = Livro.montarLivro(livroRequest, editora, autores, livrosParecidos);

        livroDAO.salvar(novoLivro);
    }

    public List<LivroResponse> listarTodos() {
        return livroDAO.listarTodos().stream()
                .map(LivroResponse::converterDeLivro)
                .collect(toList());
    }

    /**
     * Método responsável por editar uma editora específica, de acordo com os novos dados da request.
     */
    public void editar(Integer id, LivroRequest livroRequest) {
        Livro livro = this.findById(id);
        Editora editora = editoraService.findById(livroRequest.getEditoraId());
        Set<Autor> autores = autorService.findByIdIn(livroRequest.getAutoresIds());
        Set<Livro> livrosParecidos = new HashSet<>(livroDAO.findByGenero(livroRequest.getGenero()));

        livro.atualizarDados(livroRequest, editora, autores, livrosParecidos);

        livroDAO.salvar(livro);
    }

    /**
     * Método responsável por deletar uma editora específica do banco de dados.
     */
    public void deletar(Integer id) {
        livroDAO.deletar(id);
    }

    /**
     * Método responsável por buscar um Autor pelo ID dele.
     * <p>
     * Caso não encontre nenhum Autor com o mesmo ID, será lançado uma excepion.
     *
     * @return uma Editora.
     */
    private Livro findById(Integer id) {
        return livroDAO.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Livro não encontrado."));
    }
}
