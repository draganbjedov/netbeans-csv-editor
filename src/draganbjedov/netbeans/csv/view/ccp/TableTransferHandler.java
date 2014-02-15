package draganbjedov.netbeans.csv.view.ccp;

import draganbjedov.netbeans.csv.view.CSVTableModel;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import org.openide.util.Pair;

public class TableTransferHandler extends TransferHandler {

	private boolean isDrop;
	private boolean isCopy = true;

	/**
	 * Perform the actual data import.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean importData(TransferHandler.TransferSupport support) {
		//If we can't handle the import, bail now.
		if (!canImport(support)) {
			return false;
		}
		List<Pair<Integer, ArrayList<String>>> rows;
		//Fetch the data -- bail if this fails
		try {
			rows = (List<Pair<Integer, ArrayList<String>>>) support.getTransferable().getTransferData(TableRowTransferable.CSV_ROWS_DATA_FLAVOR);
		} catch (UnsupportedFlavorException ufe) {
			System.out.println("importData: unsupported data flavor");
			return false;
		} catch (IOException ioe) {
			System.out.println("importData: I/O exception");
			return false;
		}

		JTable table = (JTable) support.getComponent();
		CSVTableModel model = (CSVTableModel) table.getModel();

		if (support.isDrop()) { //This is a dropInsert
			isDrop = true;
			JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
			int index = dl.getRow();
			if (dl.isInsertRow()) {
				int index1;
				switch (model.dropInsert(rows, index)) {
					case MOVED_UP:
						index1 = index + rows.size() - 1;
						break;
					case MOVED_DOWN:
						index1 = index - 1;
						index -= rows.size();
						break;
					default:
						return false;
				}
				table.clearSelection();
				table.addRowSelectionInterval(index, index1);
				table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
				table.scrollRectToVisible(table.getCellRect(index, 0, true));
				return true;
			} else if (rows.size() == 1) {
				model.dropOn(index, rows.get(0).second());
				table.clearSelection();
				table.addRowSelectionInterval(index, index);
				table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
				return true;
			} else {
				return false;
			}
		} else { //This is a paste
			isDrop = false;
			if (rows != null) {
				int index = table.getSelectedRow();
				if (index != -1) {
					model.paste(rows, index, isCopy);
				} else {
					index = model.getRowCount() - 1;
					model.paste(rows, isCopy);
				}

				table.clearSelection();
				table.addRowSelectionInterval(index + 1, index + rows.size());
				table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
				table.scrollRectToVisible(table.getCellRect(index + 1, 0, true));
				return true;
			}
			return false;
		}
	}

	/**
	 * Bundle up the data for export.
	 */
	@Override
	protected Transferable createTransferable(JComponent component) {
		isDrop = false;
		isCopy = true;
		JTable table = (JTable) component;
		int[] rows = table.getSelectedRows();
		if (rows.length > 0) {
			List<Pair<Integer, ArrayList<String>>> rowsData = new ArrayList<Pair<Integer, ArrayList<String>>>(rows.length);
			int columnCount = table.getColumnCount();
			for (int row : rows) {
				ArrayList<String> rowData = new ArrayList<String>(columnCount);
				for (int i = 0; i < columnCount; i++)
					rowData.add((String) table.getValueAt(row, i));
				rowsData.add(Pair.of(row, rowData));
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
		isCopy = action != MOVE;
		if (isCopy) {
			return;
		}
		if (!isDrop) {
			JTable table = (JTable) component;
			CSVTableModel model = (CSVTableModel) table.getModel();
			int[] rows = table.getSelectedRows();
			model.removeRows(rows);
		}
	}

	/**
	 * We only support importing CSV_ROWS_DATA_FLAVOR.
	 */
	@Override
	public boolean canImport(TransferHandler.TransferSupport support) {
		final boolean dataFlavorSupported = support.isDataFlavorSupported(TableRowTransferable.CSV_ROWS_DATA_FLAVOR);
		if (!dataFlavorSupported)
			return false;
		else if (support.isDrop()) {
			JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
			if (dl.isInsertRow()) {
				return true;
			} else {
				try {
					List<Pair<Integer, ArrayList<String>>> rows = (List<Pair<Integer, ArrayList<String>>>) support.getTransferable().getTransferData(TableRowTransferable.CSV_ROWS_DATA_FLAVOR);
					return rows.size() == 1;
				} catch (UnsupportedFlavorException ufe) {
					return false;
				} catch (IOException ioe) {
					return false;
				}
			}
		} else {
			return true;
		}
	}
}
