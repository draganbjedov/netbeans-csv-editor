package draganbjedov.csv.view;

import draganbjedov.csv.dataobject.CSVDataObject;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
        displayName = "#LBL_CSV_VISUAL",
        iconBase = "draganbjedov/csv/icons/csv.png",
        mimeType = "text/csv",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "CSVVisual",
        position = 1000)
@Messages("LBL_CSV_VISUAL=Table")
public final class CSVVisualElement extends JPanel implements MultiViewElement {

    private CSVDataObject obj;
    private JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;

    public CSVVisualElement(Lookup lkp) {
        obj = lkp.lookup(CSVDataObject.class);
        assert obj != null;
        initComponents();
        createToolBar();
    }

    @Override
    public String getName() {
        return "CSVVisualElement";
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        table.setAutoCreateRowSorter(true);
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        tableScrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable table;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return obj.getLookup();
    }

    @Override
    public void componentOpened() {
        if (callback != null)
            callback.updateTitle(obj.getPrimaryFile().getNameExt());
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
        updateTable();
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
        if (callback != null)
            callback.updateTitle(obj.getPrimaryFile().getNameExt());
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    private void createToolBar() {
        JButton addRowButton = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/add-row.gif"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DefaultTableModel) table.getModel()).addRow(new String[table.getColumnCount()]);
            }
        });
        addRowButton.setToolTipText("Add row");
        toolbar.add(addRowButton);

        JButton removeRowButton = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/remove-row.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    ((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
                }
            }
        });
        removeRowButton.setToolTipText("Remove row");
        toolbar.add(removeRowButton);

        JButton addColumnButton = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/add-column.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String columnName = JOptionPane.showInputDialog(CSVVisualElement.this, "Enter new column name", "New column");
                ((DefaultTableModel) table.getModel()).addColumn(columnName);
            }
        });
        addColumnButton.setToolTipText("Add column");
        toolbar.add(addColumnButton);


        JButton removeColumnButton = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/remove-column.png"))) {
            @Override
            @SuppressWarnings("UseOfObsoleteCollectionType")
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedColumn() != -1) {
                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                    DefaultTableModel newTableModel = new DefaultTableModel();
                    Vector v = tableModel.getDataVector();
                    Vector newData = newDataVector(v, table.getSelectedColumn());
                    Vector newColumns = getColumnIdentifiers(tableModel, table.getSelectedColumn());
                    tableModel.setDataVector(newData, newColumns);
                }
            }
        });
        removeColumnButton.setToolTipText("Remove row");
        toolbar.add(removeColumnButton);
    }

    @SuppressWarnings({"null", "ConstantConditions"})
    private void updateTable() {
        final DefaultTableModel tableModel = obj.readFile();
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                obj.updateFile(tableModel);
            }
        });
        table.setModel(tableModel);
        table.getTableHeader().getFont();
        FontMetrics fontMetrics = table.getTableHeader().getFontMetrics(table.getTableHeader().getFont());
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            int width = SwingUtilities.computeStringWidth(fontMetrics, tableModel.getColumnName(i));
            table.getColumnModel().getColumn(i).setPreferredWidth(width + 20);
        }
    }

    @SuppressWarnings("UseOfObsoleteCollectionType")
    private Vector newDataVector(Vector v, int j) {
        Vector v1 = new Vector();
        try {
            Vector v2;
            Object[] o = v.toArray();
            int i = 0;
            while (i < o.length) {
                v2 = (Vector) o[i];
                v2.remove(j);
                v1.add(v2);
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in newvector \n" + e);
        }
        return v1;
    }

    @SuppressWarnings("UseOfObsoleteCollectionType")
    private Vector getColumnIdentifiers(DefaultTableModel tableModel, int removedColumn) {
        Vector columnIdentifiers = new Vector();
        int i = 0;
        while (i < tableModel.getColumnCount()) {
            if (i != removedColumn)
                columnIdentifiers.add(tableModel.getColumnName(i));
            i++;
        }
        return columnIdentifiers;
    }
}
