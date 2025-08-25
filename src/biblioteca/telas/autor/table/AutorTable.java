package biblioteca.telas.autor.table;

import biblioteca.backend.dto.AutorResponse;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabela de Autor
 * <p>
 * Esta classe é responsável por definir os dados da tabela de Autor,
 * que vai ser utilizada para mostrar a listagem dos autores do sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class AutorTable extends AbstractTableModel {

    private final String[] colunas = {"ID", "Nome", "Idade"};
    private List<AutorResponse> autores;

    public AutorTable() {
        this.autores = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return autores.size();
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
        AutorResponse autor = autores.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return autor.getId();
            case 1:
                return autor.getNome();
            case 2:
                return autor.getIdade();
            default:
                return null;
        }
    }

    public void setAutores(List<AutorResponse> autores) {
        this.autores = autores;
        fireTableDataChanged();
    }

    public AutorResponse getAutor(int index) {
        return autores.get(index);
    }
}
