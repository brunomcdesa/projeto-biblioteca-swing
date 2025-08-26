package biblioteca.telas.livro.table;

import biblioteca.backend.dto.LivroResponse;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

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
                return livro.getGenero();
            case 5:
                return livro.getNomeEditora();
            case 6:
                return livro.getNomesAutores();
            case 7:
                return livro.getTitulosLivrosParecidos();
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
