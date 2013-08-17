package draganbjedov.netbeans.csv.view;

import draganbjedov.netbeans.csv.dataobject.CSVDataObject;
import draganbjedov.netbeans.csv.view.ccp.TableRowTransferable;
import draganbjedov.netbeans.csv.view.ccp.TableTransferHandler;
import draganbjedov.netbeans.csv.view.ccp.TransferActionListener;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
        displayName = "#LBL_CSV_VISUAL",
        iconBase = "draganbjedov/netbeans/csv/icons/csv.png",
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
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    @SuppressWarnings("LeakingThisInConstructor")
    public CSVVisualElement(Lookup lkp) {
        obj = lkp.lookup(CSVDataObject.class);
        assert obj != null;
        initComponents();
        init();
        createToolBar();
        obj.setVisualEditor(this);
    }

    @Override
    public String getName() {
        return "CSVVisualElement";
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePopUpMenu = new javax.swing.JPopupMenu();
        copyPopUp = new javax.swing.JMenuItem();
        cutPopUp = new javax.swing.JMenuItem();
        pastePopUp = new javax.swing.JMenuItem();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        tablePopUpMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                tablePopUpMenuPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        copyPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/copy.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(copyPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.copyPopUp.text")); // NOI18N
        tablePopUpMenu.add(copyPopUp);

        cutPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/cut.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(cutPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.cutPopUp.text")); // NOI18N
        tablePopUpMenu.add(cutPopUp);

        pastePopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pastePopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/paste.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(pastePopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.pastePopUp.text")); // NOI18N
        tablePopUpMenu.add(pastePopUp);

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
        table.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tableScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableScrollPaneMouseClicked
        table.clearSelection();
    }//GEN-LAST:event_tableScrollPaneMouseClicked

    private void tablePopUpMenuPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_tablePopUpMenuPopupMenuWillBecomeVisible
        boolean enabled = table.getSelectedRowCount() > 0;
        copyPopUp.setEnabled(enabled);
        cutPopUp.setEnabled(enabled);

        pastePopUp.setEnabled(clipboard.isDataFlavorAvailable(TableRowTransferable.CSV_ROWS_DATA_FLAVOR));
    }//GEN-LAST:event_tablePopUpMenuPopupMenuWillBecomeVisible
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem copyPopUp;
    private javax.swing.JMenuItem cutPopUp;
    private javax.swing.JMenuItem pastePopUp;
    private javax.swing.JTable table;
    private javax.swing.JPopupMenu tablePopUpMenu;
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
    private JButton moveHome;
    private JButton moveLeft;
    private JButton moveRight;
    private JButton moveEnd;

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
        return obj.getUndoRedoManager();
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
        removeRowButton.setToolTipText("Remove row(s)" + " (Delete)");
        toolbar.add(removeRowButton);

        addColumnButton = new JButton(addColumnAction);
        addColumnButton.setToolTipText("Add column" + " (Ctrl+Insert)");
        toolbar.add(addColumnButton);

        removeColumnButton = new JButton(removeColumnAction);
        removeColumnButton.setToolTipText("Remove column(s)" + " (Crtl+Delete)");
        toolbar.add(removeColumnButton);

        toolbar.addSeparator();

        //Move row actions
        moveTop = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-top.png"))) {
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
        moveTop.setToolTipText("Move rows to the top");
        toolbar.add(moveTop);

        moveUp = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-up.png"))) {
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
        moveUp.setToolTipText("Move rows up");
        toolbar.add(moveUp);

        moveDown = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-down.png"))) {
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
        moveDown.setToolTipText("Move rows down");
        toolbar.add(moveDown);

        moveBottom = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-bottom.png"))) {
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
        moveBottom.setToolTipText("Move rows to the bottom");
        toolbar.add(moveBottom);

        toolbar.addSeparator();

        //Move column actions
        moveHome = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-home.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] columns = table.getSelectedColumns();
                for (int i = 0; i < columns.length; i++) {
                    int column = columns[i];
                    int to = i;

                    TableColumnModel columnModel = table.getColumnModel();

                    HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
                    tableModel.moveColumn(column, to);
                    setColumnsWidths(columnModel, columnWidthHashMap);
                }
                tableModel.fireTableDataChanged();
                selectColumnInterval(0, columns.length - 1);
            }
        });
        moveHome.setToolTipText("Move columns to the begining");
        toolbar.add(moveHome);

        moveLeft = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-left.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] columns = table.getSelectedColumns();
                if (table.getSelectedColumn() != 0 && table.getSelectedColumn() != -1) {
                    int column = table.getSelectedColumn() - 1;
                    int to = columns[columns.length - 1];
                    if (column >= 0) {
                        TableColumnModel columnModel = table.getColumnModel();

                        HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
                        tableModel.moveColumn(column, to);
                        setColumnsWidths(columnModel, columnWidthHashMap);

                        tableModel.fireTableDataChanged();
                        selectColumnInterval(columns[0] - 1, columns[columns.length - 1] - 1);
                    }
                }
            }
        });
        moveLeft.setToolTipText("Move columns left");
        toolbar.add(moveLeft);

        moveRight = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-right.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] columns = table.getSelectedColumns();
                if (table.getSelectedColumn() != -1) {
                    int column = columns[columns.length - 1] + 1;
                    int to = table.getSelectedColumn();
                    if (column <= table.getColumnCount() - 1) {
                        TableColumnModel columnModel = table.getColumnModel();

                        HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
                        tableModel.moveColumn(column, to);
                        setColumnsWidths(columnModel, columnWidthHashMap);

                        tableModel.fireTableDataChanged();
                        selectColumnInterval(columns[0] + 1, columns[columns.length - 1] + 1);
                    }
                }
            }
        });
        moveRight.setToolTipText("Move columns right");
        toolbar.add(moveRight);

        moveEnd = new JButton(new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-end.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] columns = table.getSelectedColumns();
                for (int i = 0; i < columns.length; i++) {
                    int column = columns[i] - i;

                    TableColumnModel columnModel = table.getColumnModel();

                    HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
                    tableModel.moveColumn(column, table.getColumnCount() - 1);
                    setColumnsWidths(columnModel, columnWidthHashMap);
                }
                tableModel.fireTableDataChanged();
                selectColumnInterval(table.getColumnCount() - columns.length, table.getColumnCount() - 1);
            }
        });
        moveEnd.setToolTipText("Move to columns to the end");
        toolbar.add(moveEnd);
    }

    public void updateTable() {
        obj.readFile(tableModel);

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

                //Continious top rows
                final boolean topRows = rows[0] != 0;
                moveTop.setEnabled(topRows);
                moveUp.setEnabled(topRows);

                //Continious rows at bottom
                final boolean bottomRows = rows[rows.length - 1] != table.getRowCount() - 1;
                moveDown.setEnabled(bottomRows);
                moveBottom.setEnabled(bottomRows);
            }
        }

        int[] columns = table.getSelectedColumns();
        if (moveHome != null && moveLeft != null && moveRight != null && moveEnd != null) {
            if (columns.length == 0) {
                moveHome.setEnabled(false);
                moveLeft.setEnabled(false);
                moveRight.setEnabled(false);
                moveEnd.setEnabled(false);
            } else if (columns.length == 1) {
                moveHome.setEnabled(columns[0] != 0);
                moveLeft.setEnabled(columns[0] != 0);
                moveRight.setEnabled(columns[0] != table.getColumnCount() - 1);
                moveEnd.setEnabled(columns[0] != table.getColumnCount() - 1);
            } else {
                moveHome.setEnabled(true);
                moveEnd.setEnabled(true);
                int prev = columns[0];
                for (int i = 1; i < columns.length; i++) {
                    if (prev != columns[i] - 1) {
                        moveLeft.setEnabled(false);
                        moveRight.setEnabled(false);
                        return;
                    } else {
                        prev = columns[i];
                    }
                }

                //Continious home columns
                final boolean homeColumns = columns[0] != 0;
                moveHome.setEnabled(homeColumns);
                moveLeft.setEnabled(homeColumns);

                //Continious colums at the end
                final boolean endColumns = columns[columns.length - 1] != table.getColumnCount() - 1;
                moveRight.setEnabled(endColumns);
                moveEnd.setEnabled(endColumns);
            }
        }
    }

    private void init() {
        //Table
        RowNumberTable rowNumberTable = new RowNumberTable(table, false, "#");
        tableScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumberTable.getTableHeader());
        tableScrollPane.setRowHeaderView(rowNumberTable);

        final ListSelectionListener listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setActiveButtons();
            }
        };
        table.getSelectionModel().addListSelectionListener(listSelectionListener);
        table.getColumnModel().getSelectionModel().addListSelectionListener(listSelectionListener);

        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);

        final JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = tableHeader.columnAtPoint(e.getPoint());
                if (tableHeader.getCursor().getType() == Cursor.E_RESIZE_CURSOR)
                    e.consume();
                else {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (e.getClickCount() != 2)
                            selectColumn(col);
                        else {
                            final TableColumn column = tableHeader.getColumnModel().getColumn(col);
                            String header = (String) column.getHeaderValue();
                            String newColumnName = JOptionPane.showInputDialog(CSVVisualElement.this, "Enter new column name", header);
                            if (newColumnName != null && !newColumnName.equals(header)) {
                                tableModel.renameColumn(col, newColumnName);
                                column.setHeaderValue(newColumnName);
                                updateColumnWidth(col);
                                tableModel.fireTableDataChanged();
                            }
                        }
                    }
                }
            }
        });

        tableModel = new CSVTableModel();
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                obj.updateFile(tableModel);
            }
        });
        table.setModel(tableModel);
        tableScrollPane.setViewportView(table);

        //Actions
        addRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/add-row.gif"))) {
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
        removeRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/remove-row.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                if (rows.length > 0) {
                    tableModel.removeRows(rows);
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
        addColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/add-column.png"))) {
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
        removeColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/remove-column.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] columns = table.getSelectedColumns();
                if (columns.length > 0) {
                    tableModel.removeColumns(columns);
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

//        InputMap im = table.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
//        im.put(ctrlS, "clearSelection");

        //cut, copy, paste

        table.setComponentPopupMenu(tablePopUpMenu);
        tableScrollPane.setComponentPopupMenu(tablePopUpMenu);

        table.setTransferHandler(new TableTransferHandler());

        ActionMap map = table.getActionMap();

        map.put(TransferHandler.getCutAction().getValue(Action.NAME),
                TransferHandler.getCutAction());

        map.put(TransferHandler.getCopyAction().getValue(Action.NAME),
                TransferHandler.getCopyAction());

        map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
                TransferHandler.getPasteAction());

        TransferActionListener ccpAction = new TransferActionListener();

        copyPopUp.setActionCommand((String) TransferHandler.getCopyAction().getValue(Action.NAME));
        copyPopUp.addActionListener(ccpAction);

        cutPopUp.setActionCommand((String) TransferHandler.getCutAction().getValue(Action.NAME));
        cutPopUp.addActionListener(ccpAction);

        pastePopUp.setActionCommand((String) TransferHandler.getPasteAction().getValue(Action.NAME));
        pastePopUp.addActionListener(ccpAction);

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

    private void selectColumnInterval(int column1, int column2) {
        Rectangle rect = table.getCellRect(0, column1, true);
        table.scrollRectToVisible(rect);
        table.clearSelection();
        table.addColumnSelectionInterval(column1, column2);
        table.addRowSelectionInterval(0, table.getRowCount() - 1);
    }

    private void updateColumnsWidths() {
        table.getTableHeader().getFont();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            updateColumnWidth(i);
        }
    }

    private HashMap<String, Integer> getColumnsWidths(TableColumnModel columnModel) {
        HashMap<String, Integer> columnWidthHashMap = new HashMap<String, Integer>();
        for (int j = 0; j < columnModel.getColumnCount(); j++) {
            TableColumn tableColumn = columnModel.getColumn(j);
            columnWidthHashMap.put((String) tableColumn.getHeaderValue(), tableColumn.getPreferredWidth());
        }
        return columnWidthHashMap;
    }

    private void setColumnsWidths(TableColumnModel columnModel, HashMap<String, Integer> columnWidthHashMap) {
        for (int j = 0; j < columnModel.getColumnCount(); j++) {
            final TableColumn tableColumn = columnModel.getColumn(j);
            final String columnName = tableModel.getColumnName(j);
            tableColumn.setHeaderValue(columnName);
            tableColumn.setPreferredWidth(columnWidthHashMap.get(columnName));
        }
    }

    private void updateColumnWidth(int colIndex) {
        FontMetrics fontMetrics = table.getTableHeader().getFontMetrics(table.getTableHeader().getFont());
        int width = SwingUtilities.computeStringWidth(fontMetrics, tableModel.getColumnName(colIndex));
        for (int j = 0; j < tableModel.getRowCount(); j++) {
            int w = SwingUtilities.computeStringWidth(fontMetrics, tableModel.getValueAt(j, colIndex));
            if (w > width)
                width = w;
        }
        table.getColumnModel().getColumn(colIndex).setPreferredWidth(width + 25);
    }
}
