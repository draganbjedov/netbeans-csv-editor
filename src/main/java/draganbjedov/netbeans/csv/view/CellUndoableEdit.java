package draganbjedov.netbeans.csv.view;

import javax.swing.table.TableModel;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/*
 * CellUndoableEdit.java
 *
 * Created on Apr 15, 2013, 11:11:50 PM
 *
 * @author Dragan Bjedov
 */
public class CellUndoableEdit extends AbstractUndoableEdit {

    private final TableModel model;
    private final Object oldValue;
    private final Object newValue;
    private final int row;
    private final int column;

    public CellUndoableEdit(TableModel model, Object oldValue, Object newValue, int row, int column) {
        this.model = model;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.row = row;
        this.column = column;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        model.setValueAt(oldValue, row, column);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        model.setValueAt(newValue, row, column);
    }
}
