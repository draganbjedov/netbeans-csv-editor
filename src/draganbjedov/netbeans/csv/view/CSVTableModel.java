package draganbjedov.netbeans.csv.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/*
 * BACnetObjectsTableModel.java
 *
 * Created on 29.03.2012., 16.00.35
 *
 * @author Dragan Bjedov
 */
public class CSVTableModel extends AbstractTableModel {

    private List<List<String>> values;
    private List<String> headers;
//    private UndoRedo.Manager undoRedoManager;

    public CSVTableModel() {
        headers = new ArrayList<String>();
        values = new ArrayList<List<String>>();
    }

    public CSVTableModel(List<String> headers) {
        this.headers = headers;
        values = new ArrayList<List<String>>();
    }

    public CSVTableModel(List<List<String>> values, List<String> headers) {
        this.values = values;
        this.headers = headers;
    }

    /**
     * Adds empty row at end of table
     */
    public void addRow() {
        List<String> data = new ArrayList<String>(headers.size());
        for (int i = 0; i < headers.size(); i++) {
            try {
                data.add("");
            } catch (Exception ex) {
                System.err.println("Add row Exception: " + ex);
            }
        }
        values.add(data);
        fireTableRowsInserted(values.size() - 1, values.size() - 1);
    }

    /**
     * Adds row at end of table
     *
     * @param data Row data
     */
    public void addRow(List<String> data) {
        while (data.size() < headers.size()) {
            data.add("");
        }
        values.add(data);
        fireTableRowsInserted(values.size() - 1, values.size() - 1);
    }

    /**
     * Adds empty row to specific row
     *
     * @param row Row index
     */
    public void insertRow(int row) {
        List<String> data = new ArrayList<String>(headers.size());
        for (int i = 0; i < headers.size(); i++) {
            try {
                data.add("");
            } catch (Exception ex) {
                System.err.println("Add row Exception: " + ex);
            }
        }
        try {
            values.add(row, data);
        } catch (Exception e) {
            addRow(data);
            return;
        }
        fireTableRowsInserted(row, row);
    }

    /**
     * Adds row to specific row
     *
     * @param row Row index
     * @param data Row data
     */
    public void insertRow(int row, List<String> data) {
        while (data.size() < headers.size()) {
            data.add("");
        }
        values.add(row, data);
        fireTableRowsInserted(row, row);
    }

    /**
     * Removes specific row
     *
     * @param row Row index
     */
    public void removeRow(int row) {
        if (row >= 0 && row < values.size()) {
            values.remove(row);
            if (values.size() > 0)
                fireTableRowsDeleted(0, values.size() - 1);
            else
                fireTableDataChanged();
        }
    }

    public void removeRows(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            values.remove(rows[i] - i);
        }
        if (values.size() > 0)
            fireTableRowsDeleted(0, values.size() - 1);
        else
            fireTableDataChanged();
    }

    public void addRows(int row, List<List<String>> rows) {
        try {
            values.addAll(row, rows);
            fireTableRowsInserted(row, row + rows.size() - 1);
        } catch (Exception e) {
            addRows(rows);
        }
    }

    public void addRows(List<List<String>> rows) {
        int row = values.size();
        values.addAll(rows);
        fireTableRowsInserted(row, row + rows.size() - 1);
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= values.size() || columnIndex >= headers.size())
            return null;
        final List<String> data = values.get(rowIndex);
        while (data.size() < headers.size()) {
            data.add("");
        }
        return data.get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return headers.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (row < values.size() && column < headers.size()) {
            List<String> data = values.get(row);
            while (data.size() < headers.size()) {
                data.add("");
            }
//            String oldValue = data.get(column);
            data.set(column, (String) value);
            fireTableCellUpdated(row, column);
//            if (!oldValue.equals(value))
//                undoRedoManager.undoableEditHappened(new UndoableEditEvent(this, new CellUndoableEdit(this, oldValue, value, row, column)));
        }
    }

    public void moveRow(int from, int to) {
        if (from > to) {
            List<String> row = values.remove(from);
            values.add(to, row);
        } else {
            List<String> row = values.remove(from);
            if (to < getRowCount() - 1) {
                values.add(to, row);
            } else {
                values.add(row);
            }
        }
    }

    public void moveColumn(int from, int to) {
        if (from > to) {
            String header = headers.remove(from);
            headers.add(to, header);
            for (int i = 0; i < getRowCount(); i++) {
                List<String> row = values.get(i);
                String value = row.remove(from);
                row.add(to, value);
            }
        } else {
            String header = headers.remove(from);
            if (to < headers.size() - 1) {
                headers.add(to, header);
            } else {
                headers.add(header);
            }
            for (int i = 0; i < getRowCount(); i++) {
                List<String> row = values.get(i);
                String value = row.remove(from);
                if (to < row.size() - 1) {
                    row.add(to, value);
                } else {
                    row.add(value);
                }
            }
        }
    }

    public void removeColumn(int column) {
        headers.remove(column);

        for (int i = 0; i < values.size(); i++) {
            values.get(i).remove(column);
        }
        fireTableStructureChanged();
    }

    public void removeColumns(int[] columns) {
        for (int i = 0; i < columns.length; i++) {
            int column = columns[i] - i;
            headers.remove(column);

            for (int j = 0; j < values.size(); j++) {
                values.get(j).remove(column);
            }
        }
        if (columns.length > 0)
            fireTableStructureChanged();
    }

    public void addColumn(String columnName) {
        headers.add(columnName);

        for (int i = 0; i < values.size(); i++) {
            values.get(i).add("");
        }
        fireTableStructureChanged();
    }

    public void addColumn(String columnName, int index) {
        try {
            headers.add(index, columnName);
        } catch (Exception e) {
            addColumn(columnName);
            return;
        }

        for (int i = 0; i < values.size(); i++) {
            values.get(i).add(index, "");
        }
        fireTableStructureChanged();
    }

    public void renameColumn(int col, String newColumnName) {
        headers.set(col, newColumnName);
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.values != null ? this.values.hashCode() : 0);
        hash = 67 * hash + (this.headers != null ? this.headers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CSVTableModel other = (CSVTableModel) obj;
        if (this.values != other.values && (this.values == null || !this.values.equals(other.values)))
            return false;
        if (this.headers != other.headers && (this.headers == null || !this.headers.equals(other.headers)))
            return false;
        return true;
    }

    @Override
    public CSVTableModel clone() {
        CSVTableModel model = new CSVTableModel();
        if (headers != null) {
            List<String> cloned = new ArrayList<String>(headers.size());
            for (String header : headers)
                cloned.add(header);
            model.setHeaders(cloned);
        }
        if (values != null) {
            List<List<String>> cloned = new ArrayList<List<String>>(values.size());
            for (List<String> row : values) {
                List<String> clonedRow = new ArrayList<String>(row.size());
                for (String s : row)
                    clonedRow.add(s);
                cloned.add(clonedRow);
            }
            model.setValues(cloned);
        }
        return model;
    }
}
