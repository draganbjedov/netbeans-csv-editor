package draganbjedov.csv.view.ccp;

import draganbjedov.csv.view.CSVTableModel;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public class TableTransferHandler extends TransferHandler {

    /**
     * Perform the actual data import.
     */
    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        //If we can't handle the import, bail now.
        if (!canImport(info)) {
            return false;
        }
        List<List<String>> rows;
        //Fetch the data -- bail if this fails
        try {
            rows = (List<List<String>>) info.getTransferable().getTransferData(TableRowTransferable.CSV_ROWS_DATA_FLAVOR);
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
            return false;
        } catch (IOException ioe) {
            System.out.println("importData: I/O exception");
            return false;
        }

//        if (info.isDrop()) { //This is a drop
//            JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
//            int index = dl.getIndex();
//            if (dl.isInsert()) {
//                model.add(index, data);
//                return true;
//            } else {
//                model.set(index, data);
//                return true;
//            }
//        } else 
//        { //This is a paste
        if (rows != null) {
            JTable table = (JTable) info.getComponent();
            CSVTableModel model = (CSVTableModel) table.getModel();
            int index = table.getSelectedRow();


            if (index != -1) {
                model.addRows(index + 1, rows);

            } else {
                index = model.getRowCount() - 1;
                model.addRows(rows);
            }

            table.clearSelection();
            table.addRowSelectionInterval(index + 1, index + rows.size());
            table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
            return true;
        }
        return false;
//        }
    }

    /**
     * Bundle up the data for export.
     */
    @Override
    protected Transferable createTransferable(JComponent component) {
        JTable table = (JTable) component;
        int[] rows = table.getSelectedRows();
        if (rows.length > 0) {
            List<List<String>> rowsData = new ArrayList<List<String>>(rows.length);
            int columnCount = table.getColumnCount();
            for (int row : rows) {
                ArrayList<String> rowData = new ArrayList<String>(columnCount);
                for (int i = 0; i < columnCount; i++)
                    rowData.add((String) table.getValueAt(row, i));
                rowsData.add(rowData);
            }
            return new TableRowTransferable(rowsData);
        }
        return null;
    }

    /**
     * The list handles both copy and move actions.
     */
    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    /**
     * When the export is complete, remove the old list entry if the action was
     * a move.
     */
    @Override
    protected void exportDone(JComponent component, Transferable data, int action) {
        if (action != MOVE) {
            return;
        }
        JTable table = (JTable) component;
        CSVTableModel model = (CSVTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        model.removeRows(rows);
    }

    /**
     * We only support importing strings.
     */
    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(TableRowTransferable.CSV_ROWS_DATA_FLAVOR);
    }
}
