package biblioteca.telas.editora.table;

import biblioteca.backend.dto.EditoraResponse;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabela de Editora
 * <p>
 * Esta classe é responsável por definir os dados da tabela de Editora,
 * que vai ser utilizada para mostrar a listagem das editoras do sistema.
 *
 * @author Bruno Cardoso
 * @version 1.0
 */
public class EditoraTable extends AbstractTableModel {

    private final String[] colunas = {"ID", "Nome", "CNPJ"};
    private List<EditoraResponse> editoras;

    public EditoraTable() {
        this.editoras = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return editoras.size();
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
        EditoraResponse editora = editoras.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return editora.getId();
            case 1:
                return editora.getNome();
            case 2:
                return editora.getCnpj();
            default:
                return null;
        }
    }

    public void setEditoras(List<EditoraResponse> editoras) {
        this.editoras = editoras;
        fireTableDataChanged();
    }

    public EditoraResponse getEditora(int index) {
        return editoras.get(index);
    }
}
