package biblioteca.telas.livro.table;

import biblioteca.backend.dto.LivroResponse;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Tabela de Livro
 * <p>
 * Esta classe é responsável por definir os dados da tabela de Livro,
 * que vai ser utilizada para mostrar a listagem dos livros do sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class LivroTable extends AbstractTableModel {

    private final String[] colunas = {"ID", "Título", "Data de Publicação", "ISBN", "Gênero", "Editora",
            "Autores", "Livros Parecidos"};
    private List<LivroResponse> livros;

    public LivroTable() {
        this.livros = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return livros.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LivroResponse livro = livros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return livro.getId();
            case 1:
                return livro.getTitulo();
            case 2:
                return livro.getDataPublicacao().format(ofPattern("dd/MM/yyyy"));
            case 3:
                return livro.getIsbn();
            case 4:
                return livro.getGenero().getDescricao();
            case 5:
                return livro.getEditora().getNome();
            case 6:
                return String.join(", ", livro.getAutoresNomes());
            case 7:
                return livro.possuiTitulosParecidos() ? String.join(", ", livro.getTitulosLivrosParecidos()) : "-";
            default:
                return null;
        }
    }

    public void setLivros(List<LivroResponse> livros) {
        this.livros = livros;
        fireTableDataChanged();
    }

    public LivroResponse getLivro(int index) {
        return livros.get(index);
    }
}
