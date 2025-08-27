package biblioteca;

import biblioteca.backend.dao.contract.IAutorDAO;
import biblioteca.backend.dao.contract.IEditoraDAO;
import biblioteca.backend.dao.contract.ILivroDAO;
import biblioteca.backend.dao.impl.AutorDAOImpl;
import biblioteca.backend.dao.impl.EditoraDAOImpl;
import biblioteca.backend.dao.impl.LivroDAOImpl;
import biblioteca.backend.facade.AutorFacade;
import biblioteca.backend.facade.EditoraFacade;
import biblioteca.backend.facade.LivroFacade;
import biblioteca.backend.service.AutorService;
import biblioteca.backend.service.EditoraService;
import biblioteca.backend.service.LivroService;
import biblioteca.backend.utils.JpaUtil;
import biblioteca.telas.TelaPrincipal;

import javax.swing.*;

/**
 * Classe Main do projeto
 * <p>
 * Esta classe é responsável inicializar o projeto.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        JpaUtil.getEntityManager().close();

        IAutorDAO autorDAO = new AutorDAOImpl();
        AutorService autorService = new AutorService(autorDAO);
        AutorFacade autorFacade = new AutorFacade(autorService);

        IEditoraDAO editoraDAO = new EditoraDAOImpl();
        EditoraService editoraService = new EditoraService(editoraDAO);
        EditoraFacade editoraFacade = new EditoraFacade(editoraService);

        ILivroDAO livroDAO = new LivroDAOImpl();
        LivroService livroService = new LivroService(livroDAO);
        LivroFacade livroFacade =  new LivroFacade(livroService, autorService, editoraService);


        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal(autorFacade, editoraFacade, livroFacade);
            tela.setVisible(true);
        });
    }
}
