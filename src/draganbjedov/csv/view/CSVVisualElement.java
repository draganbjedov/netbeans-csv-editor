package draganbjedov.csv.view;

import draganbjedov.csv.dataobject.CSVDataObject;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
    private transient CSVTableModel tableModel;
    private AbstractAction addRowAction;
    private AbstractAction removeRowAction;
    private AbstractAction addColumnAction;
    private AbstractAction removeColumnAction;

    public CSVVisualElement(Lookup lkp) {
        obj = lkp.lookup(CSVDataObject.class);
        assert obj != null;
        initComponents();
        init();
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

        tableScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableScrollPaneMouseClicked(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tableScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableScrollPaneMouseClicked
        table.clearSelection();
    }//GEN-LAST:event_tableScrollPaneMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable table;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables
    private JButton addRowButton;
    private JButton removeRowButton;
    private JButton addColumnButton;
    private JButton removeColumnButton;
    private JButton moveTop;
    private JButton moveUp;
    private JButton moveDown;
    private JButton moveBottom;

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
        addRowButton = new JButton(addRowAction);
        addRowButton.setToolTipText("Add row" + " (Insert)");
        toolbar.add(addRowButton);

        removeRowButton = new JButton(removeRowAction);
        removeRowButton.setToolTipText("Remove row" + " (Delete)");
        toolbar.add(removeRowButton);

        addColumnButton = new JButton(addColumnAction);
        addColumnButton.setToolTipText("Add column");
        toolbar.add(addColumnButton);

        removeColumnButton = new JButton(removeColumnAction);
        removeColumnButton.setToolTipText("Remove row");
        toolbar.add(removeColumnButton);

        toolbar.addSeparator();

        //Move actions
        moveTop = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/go-top.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    int row = rows[i];
                    int to = i;
                    tableModel.moveRow(row, to);
                }
                tableModel.fireTableDataChanged();
                selectRowInterval(0, rows.length - 1);
            }
        });
        moveTop.setToolTipText("Move to the top");
        toolbar.add(moveTop);

        moveUp = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/go-up.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                if (table.getSelectedRow() != 0 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow() - 1;
                    int to = rows[rows.length - 1];
                    if (row >= 0) {
                        tableModel.moveRow(row, to);
                        tableModel.fireTableDataChanged();
                        selectRowInterval(rows[0] - 1, rows[rows.length - 1] - 1);
                    }
                }
            }
        });
        moveUp.setToolTipText("Move up");
        toolbar.add(moveUp);

        moveDown = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/go-down.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                if (table.getSelectedRow() != -1) {
                    int row = rows[rows.length - 1] + 1;
                    int to = table.getSelectedRow();
                    if (row <= table.getRowCount() - 1) {
                        tableModel.moveRow(row, to);
                        tableModel.fireTableDataChanged();
                        selectRowInterval(rows[0] + 1, rows[rows.length - 1] + 1);
                    }
                }
            }
        });
        moveDown.setToolTipText("Move down");
        toolbar.add(moveDown);

        moveBottom = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/go-bottom.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    int row = rows[i] - i;
                    tableModel.moveRow(row, table.getRowCount() - 1);
                }
                tableModel.fireTableDataChanged();
                selectRowInterval(table.getRowCount() - rows.length, table.getRowCount() - 1);
            }
        });
        moveBottom.setToolTipText("Move to the bottom");
        toolbar.add(moveBottom);

    }

    private void updateTable() {
        tableModel = obj.readFile();
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                obj.updateFile(tableModel);
            }
        });
        table.setModel(tableModel);
        updateColumnsWidths();
        setActiveButtons();
    }

    private void setActiveButtons() {
        final boolean enableRemove = table.getSelectedRowCount() >= 1;
        removeRowButton.setEnabled(enableRemove);
        removeColumnButton.setEnabled(enableRemove);
        int[] rows = table.getSelectedRows();
        if (moveTop != null && moveUp != null && moveDown != null && moveBottom != null) {
            if (rows.length == 0) {
                moveTop.setEnabled(false);
                moveUp.setEnabled(false);
                moveDown.setEnabled(false);
                moveBottom.setEnabled(false);
            } else if (rows.length == 1) {
                moveTop.setEnabled(rows[0] != 0);
                moveUp.setEnabled(rows[0] != 0);
                moveDown.setEnabled(rows[0] != table.getRowCount() - 1);
                moveBottom.setEnabled(rows[0] != table.getRowCount() - 1);
            } else {
                moveTop.setEnabled(true);
                moveBottom.setEnabled(true);
                int prev = rows[0];
                for (int i = 1; i < rows.length; i++) {
                    if (prev != rows[i] - 1) {
                        moveUp.setEnabled(false);
                        moveDown.setEnabled(false);
                        return;
                    } else {
                        prev = rows[i];
                    }
                }
                moveUp.setEnabled(true);
                moveDown.setEnabled(true);
            }
        }
    }

    private void init() {
        RowNumberTable rowNumberTable = new RowNumberTable(table, false, "#");
        tableScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumberTable.getTableHeader());
        tableScrollPane.setRowHeaderView(rowNumberTable);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setActiveButtons();
            }
        });
        tableScrollPane.setViewportView(table);

        addRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/add-row.gif"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    tableModel.addRow();
                    selectRow(table.getRowCount() - 1);
                } else {
                    tableModel.insertRow(selectedRow + 1);
                    selectRow(selectedRow + 1);
                }
            }
        };
        removeRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/remove-row.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    tableModel.removeRow(table.convertRowIndexToModel(rows[i] - i));
                }
                if (rows.length > 0) {
                    int row = rows[0] - 1;
                    if (row < 0) {
                        if (table.getRowCount() > 0)
                            selectRow(0);
                    } else {
                        selectRow(row);
                    }
                }
            }
        };
        addColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/add-column.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String columnName = JOptionPane.showInputDialog(CSVVisualElement.this, "Enter new column name", "New column");
                int selectedColumn = table.getSelectedColumn();
                if (selectedColumn == -1) {
                    tableModel.addColumn(columnName);
                    selectColumn(table.getColumnCount() - 1);
                } else {
                    tableModel.addColumn(columnName, selectedColumn + 1);
                    selectColumn(selectedColumn + 1);
                }

                updateColumnsWidths();
            }
        };
        removeColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/csv/icons/remove-column.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedColumn() != -1) {
                    tableModel.removeColumn(table.getSelectedColumn());
                    updateColumnsWidths();
                }
            }
        };

        KeyStroke strokeAddRow = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0);
        KeyStroke strokeRemoveRow = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        KeyStroke strokeAddColumn = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, InputEvent.CTRL_DOWN_MASK);
        KeyStroke strokeRemoveColumn = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK);

        this.getInputMap().put(strokeAddRow, "INSERT_ROW_COMMAND");
        this.getActionMap().put("INSERT_ROW_COMMAND", addRowAction);
        this.getInputMap().put(strokeRemoveRow, "DELETE_ROW_COMMAND");
        this.getActionMap().put("DELETE_ROW_COMMAND", removeRowAction);

        table.getInputMap().put(strokeAddRow, "INSERT_ROW_COMMAND");
        table.getActionMap().put("INSERT_ROW_COMMAND", addRowAction);
        table.getInputMap().put(strokeRemoveRow, "DELETE_ROW_COMMAND");
        table.getActionMap().put("DELETE_ROW_COMMAND", removeRowAction);

        this.getInputMap().put(strokeAddColumn, "INSERT_COLUMN_COMMAND");
        this.getActionMap().put("INSERT_COLUMN_COMMAND", addColumnAction);
        this.getInputMap().put(strokeRemoveColumn, "DELETE_COLUMN_COMMAND");
        this.getActionMap().put("DELETE_COLUMN_COMMAND", removeColumnAction);

        table.getInputMap().put(strokeAddColumn, "INSERT_COLUMN_COMMAND");
        table.getActionMap().put("INSERT_COLUMN_COMMAND", addColumnAction);
        table.getInputMap().put(strokeRemoveColumn, "DELETE_COLUMN_COMMAND");
        table.getActionMap().put("DELETE_COLUMN_COMMAND", removeColumnAction);
    }

    private void selectRow(int row) {
        Rectangle rect = table.getCellRect(row, 0, true);
        table.scrollRectToVisible(rect);
        table.clearSelection();
        table.addRowSelectionInterval(row, row);
        table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
    }

    private void selectRowInterval(int row1, int row2) {
        Rectangle rect = table.getCellRect(row1, 0, true);
        table.scrollRectToVisible(rect);
        table.clearSelection();
        table.addRowSelectionInterval(row1, row2);
        table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
    }

    private void selectColumn(int column) {
        table.clearSelection();
        table.addColumnSelectionInterval(column, column);
        table.addRowSelectionInterval(0, table.getRowCount() - 1);
    }

    private void updateColumnsWidths() {
        table.getTableHeader().getFont();
        FontMetrics fontMetrics = table.getTableHeader().getFontMetrics(table.getTableHeader().getFont());
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            int width = SwingUtilities.computeStringWidth(fontMetrics, tableModel.getColumnName(i));
            table.getColumnModel().getColumn(i).setPreferredWidth(width + 20);
        }
    }
}
