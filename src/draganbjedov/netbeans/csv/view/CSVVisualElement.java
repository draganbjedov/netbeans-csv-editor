package draganbjedov.netbeans.csv.view;

import draganbjedov.netbeans.csv.dataobject.CSVDataObject;
import draganbjedov.netbeans.csv.options.util.OptionsUtils;
import draganbjedov.netbeans.csv.view.ccp.TableRowTransferable;
import draganbjedov.netbeans.csv.view.ccp.TableTransferHandler;
import draganbjedov.netbeans.csv.view.ccp.TransferActionListener;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
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

	private final CSVDataObject obj;
	private final JToolBar toolbar = new JToolBar();
	private transient MultiViewElementCallback callback;
	private transient CSVTableModel tableModel;
	private AbstractAction addRowAction;
	private AbstractAction deleteRowAction;
	private AbstractAction addColumnAction;
	private AbstractAction deleteColumnAction;
	private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	private AbstractAction moveTopAction;
	private AbstractAction moveUpAction;
	private AbstractAction moveDownAction;
	private AbstractAction moveBottomAction;
	private AbstractAction moveHomeAction;
	private AbstractAction moveLeftAction;
	private AbstractAction moveRightAction;
	private AbstractAction moveEndAction;
	private AbstractAction separatorChangedAction;

	@SuppressWarnings("LeakingThisInConstructor")
	public CSVVisualElement(Lookup lkp) {
		obj = lkp.lookup(CSVDataObject.class);
		assert obj != null;
		initActions();
		initComponents();
		init();
		createToolBar();
		obj.setVisualEditor(this);
		updateTable();
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
        separator1 = new javax.swing.JPopupMenu.Separator();
        addRowPopUp = new javax.swing.JMenuItem();
        deleteRowPopUp = new javax.swing.JMenuItem();
        addColumnPopUp = new javax.swing.JMenuItem();
        deleteColumnPopUp = new javax.swing.JMenuItem();
        separator3 = new javax.swing.JPopupMenu.Separator();
        moveTopPopUp = new javax.swing.JMenuItem();
        moveUpPopUp = new javax.swing.JMenuItem();
        moveDownPopUp = new javax.swing.JMenuItem();
        moveBottomPopUp = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JPopupMenu.Separator();
        moveHomePopUp = new javax.swing.JMenuItem();
        moveLeftPopUp = new javax.swing.JMenuItem();
        moveRightPopUp = new javax.swing.JMenuItem();
        moveEndPopUp = new javax.swing.JMenuItem();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable(){
            @Override
            public Component prepareEditor(TableCellEditor editor, int row, int column) {
                final Component c = super.prepareEditor(editor, row, column);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        if (c instanceof JTextComponent) {
                            ((JTextComponent) c).selectAll();
                        }
                    }
                });

                addRowAction.setEnabled(false);
                deleteRowAction.setEnabled(false);
                addColumnAction.setEnabled(false);
                deleteColumnAction.setEnabled(false);

                moveTopAction.setEnabled(false);
                moveUpAction.setEnabled(false);
                moveDownAction.setEnabled(false);
                moveBottomAction.setEnabled(false);

                moveHomeAction.setEnabled(false);
                moveLeftAction.setEnabled(false);
                moveRightAction.setEnabled(false);
                moveEndAction.setEnabled(false);

                separators.setEnabled(false);
                return c;
            }

            public boolean editCellAt(int row, int column, EventObject e){
                if(e instanceof KeyEvent){
                    int i = ((KeyEvent) e).getModifiers();
                    String s = KeyEvent.getModifiersExText(((KeyEvent) e).getModifiers());
                    //any time Control is used, disable cell editing
                    if(i == InputEvent.CTRL_MASK){
                        return false;
                    }
                }
                return super.editCellAt(row, column, e);
            }
        };

        tablePopUpMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                tablePopUpMenuPopupMenuWillBecomeVisible(evt);
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
        tablePopUpMenu.add(separator1);

        addRowPopUp.setAction(addRowAction);
        addRowPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0));
        org.openide.awt.Mnemonics.setLocalizedText(addRowPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.addRowButton.text")); // NOI18N
        tablePopUpMenu.add(addRowPopUp);

        deleteRowPopUp.setAction(deleteRowAction);
        deleteRowPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        org.openide.awt.Mnemonics.setLocalizedText(deleteRowPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.deleteRowButton.text")); // NOI18N
        tablePopUpMenu.add(deleteRowPopUp);

        addColumnPopUp.setAction(addColumnAction);
        addColumnPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(addColumnPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.addColumnButton.text")); // NOI18N
        tablePopUpMenu.add(addColumnPopUp);

        deleteColumnPopUp.setAction(deleteColumnAction);
        deleteColumnPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(deleteColumnPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.deleteColumnButton.text")); // NOI18N
        tablePopUpMenu.add(deleteColumnPopUp);
        tablePopUpMenu.add(separator3);

        moveTopPopUp.setAction(moveTopAction);
        moveTopPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveTopPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveTopPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveTopPopUp);

        moveUpPopUp.setAction(moveUpAction);
        moveUpPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveUpPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveUpPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveUpPopUp);

        moveDownPopUp.setAction(moveDownAction);
        moveDownPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveDownPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveDownPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveDownPopUp);

        moveBottomPopUp.setAction(moveBottomAction);
        moveBottomPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveBottomPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveBottomPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveBottomPopUp);
        tablePopUpMenu.add(separator2);

        moveHomePopUp.setAction(moveHomeAction);
        moveHomePopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveHomePopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveHomePopUp.text")); // NOI18N
        tablePopUpMenu.add(moveHomePopUp);

        moveLeftPopUp.setAction(moveLeftAction);
        moveLeftPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveLeftPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveLeftPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveLeftPopUp);

        moveRightPopUp.setAction(moveRightAction);
        moveRightPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveRightPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveRightPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveRightPopUp);

        moveEndPopUp.setAction(moveEndAction);
        moveEndPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        org.openide.awt.Mnemonics.setLocalizedText(moveEndPopUp, org.openide.util.NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveEndPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveEndPopUp);

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
    private javax.swing.JMenuItem addColumnPopUp;
    private javax.swing.JMenuItem addRowPopUp;
    private javax.swing.JMenuItem copyPopUp;
    private javax.swing.JMenuItem cutPopUp;
    private javax.swing.JMenuItem deleteColumnPopUp;
    private javax.swing.JMenuItem deleteRowPopUp;
    private javax.swing.JMenuItem moveBottomPopUp;
    private javax.swing.JMenuItem moveDownPopUp;
    private javax.swing.JMenuItem moveEndPopUp;
    private javax.swing.JMenuItem moveHomePopUp;
    private javax.swing.JMenuItem moveLeftPopUp;
    private javax.swing.JMenuItem moveRightPopUp;
    private javax.swing.JMenuItem moveTopPopUp;
    private javax.swing.JMenuItem moveUpPopUp;
    private javax.swing.JMenuItem pastePopUp;
    private javax.swing.JPopupMenu.Separator separator1;
    private javax.swing.JPopupMenu.Separator separator2;
    private javax.swing.JPopupMenu.Separator separator3;
    private javax.swing.JTable table;
    private javax.swing.JPopupMenu tablePopUpMenu;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables
	private JButton addRowButton;
	private JButton deleteRowButton;
	private JButton addColumnButton;
	private JButton deleteColumnButton;
	private JButton moveTop;
	private JButton moveUp;
	private JButton moveDown;
	private JButton moveBottom;
	private JButton moveHome;
	private JButton moveLeft;
	private JButton moveRight;
	private JButton moveEnd;
	private JComboBox separators;

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
		if (callback != null)
			return callback.createDefaultActions();
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
		toolbar.setFloatable(false);

		addRowButton = new JButton(addRowAction);
		addRowButton.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.addRowButton.text") + " (Insert)");
		toolbar.add(addRowButton);

		deleteRowButton = new JButton(deleteRowAction);
		deleteRowButton.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.deleteRowButton.text") + " (Delete)");
		toolbar.add(deleteRowButton);

		addColumnButton = new JButton(addColumnAction);
		addColumnButton.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.addColumnButton.text") + " (Ctrl+Insert)");
		toolbar.add(addColumnButton);

		deleteColumnButton = new JButton(deleteColumnAction);
		deleteColumnButton.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.deleteColumnButton.text") + " (Crtl+Delete)");
		toolbar.add(deleteColumnButton);

		toolbar.addSeparator();

		//Move row actions
		moveTop = new JButton(moveTopAction);
		moveTop.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveTopPopUp.text") + " (Ctrl+Shift+Up)");
		toolbar.add(moveTop);

		moveUp = new JButton(moveUpAction);
		moveUp.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveUpPopUp.text") + " (Ctrl+Up)");
		toolbar.add(moveUp);

		moveDown = new JButton(moveDownAction);
		moveDown.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveDownPopUp.text") + " (Ctrl+Down)");
		toolbar.add(moveDown);

		moveBottom = new JButton(moveBottomAction);
		moveBottom.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveBottomPopUp.text") + " (Ctrl+Shift+Down)");
		toolbar.add(moveBottom);

		toolbar.addSeparator();

		//Move column actions
		moveHome = new JButton(moveHomeAction);
		moveHome.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveHomePopUp.text") + " (Ctrl+Shift+Left)");
		toolbar.add(moveHome);

		moveLeft = new JButton(moveLeftAction);
		moveLeft.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveLeftPopUp.text") + " (Ctrl+Left)");
		toolbar.add(moveLeft);

		moveRight = new JButton(moveRightAction);
		moveRight.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveRightPopUp.text") + " (Ctrl+Right)");
		toolbar.add(moveRight);

		moveEnd = new JButton(moveEndAction);
		moveEnd.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.moveEndPopUp.text") + " (Ctrl+Shift+Left)");
		toolbar.add(moveEnd);

		toolbar.addSeparator();

		//Separator
		toolbar.add(new JLabel(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.separatorLabel.text")));

		int customSeparatorCount = OptionsUtils.readCustomSeparatorCount();
		if (customSeparatorCount > 0) {
			List<Character> s = OptionsUtils.readCustomSeparators(customSeparatorCount);
			s.add(0, ',');
			s.add(1, ';');
			separators = new JComboBox(new DefaultComboBoxModel(s.toArray()));
		} else
			separators = new JComboBox(new Character[]{',', ';'});
		toolbar.add(separators);
		separators.setPreferredSize(new Dimension(50, separators.getPreferredSize().height));
		separators.setMaximumSize(new Dimension(50, separators.getPreferredSize().height));
		separators.setMinimumSize(new Dimension(50, separators.getPreferredSize().height));
		separators.setToolTipText(NbBundle.getMessage(CSVVisualElement.class, "CSVVisualElement.separators.tooltip"));
		separators.addActionListener(separatorChangedAction);
		separators.setSelectedItem(OptionsUtils.readDefaultSeparator());
	}

	public void updateTable() {
		obj.readFile(tableModel);

		updateColumnsWidths();
		setActiveButtons();
	}

	private void setActiveButtons() {
		addRowAction.setEnabled(true);
		addColumnAction.setEnabled(true);
		separators.setEnabled(true);
		deleteRowAction.setEnabled(table.getSelectedRowCount() >= 1);
		deleteColumnAction.setEnabled(table.getSelectedColumnCount() >= 1);

		int[] rows = table.getSelectedRows();
		if (moveTop != null && moveUp != null && moveDown != null && moveBottom != null) {
			if (rows.length == 0) {
				moveTopAction.setEnabled(false);
				moveUpAction.setEnabled(false);
				moveDownAction.setEnabled(false);
				moveBottomAction.setEnabled(false);
			} else if (rows.length == 1) {
				moveTopAction.setEnabled(rows[0] != 0);
				moveUpAction.setEnabled(rows[0] != 0);
				moveDownAction.setEnabled(rows[0] != table.getRowCount() - 1);
				moveBottomAction.setEnabled(rows[0] != table.getRowCount() - 1);
			} else {
				moveTopAction.setEnabled(true);
				moveBottomAction.setEnabled(true);
				int prev = rows[0];
				for (int i = 1; i < rows.length; i++) {
					if (prev != rows[i] - 1) {
						moveUpAction.setEnabled(false);
						moveDownAction.setEnabled(false);
						return;
					} else {
						prev = rows[i];
					}
				}

				//Continious top rows
				final boolean topRows = rows[0] != 0;
				moveTopAction.setEnabled(topRows);
				moveUpAction.setEnabled(topRows);

				//Continious rows at bottom
				final boolean bottomRows = rows[rows.length - 1] != table.getRowCount() - 1;
				moveDownAction.setEnabled(bottomRows);
				moveBottomAction.setEnabled(bottomRows);
			}
		}

		int[] columns = table.getSelectedColumns();
		if (moveHome != null && moveLeft != null && moveRight != null && moveEnd != null) {
			if (columns.length == 0) {
				moveHomeAction.setEnabled(false);
				moveLeftAction.setEnabled(false);
				moveRightAction.setEnabled(false);
				moveEndAction.setEnabled(false);
			} else if (columns.length == 1) {
				moveHomeAction.setEnabled(columns[0] != 0);
				moveLeftAction.setEnabled(columns[0] != 0);
				moveRightAction.setEnabled(columns[0] != table.getColumnCount() - 1);
				moveEndAction.setEnabled(columns[0] != table.getColumnCount() - 1);
			} else {
				moveHomeAction.setEnabled(true);
				moveEndAction.setEnabled(true);
				int prev = columns[0];
				for (int i = 1; i < columns.length; i++) {
					if (prev != columns[i] - 1) {
						moveLeftAction.setEnabled(false);
						moveRightAction.setEnabled(false);
						return;
					} else {
						prev = columns[i];
					}
				}

				//Continious home columns
				final boolean homeColumns = columns[0] != 0;
				moveHomeAction.setEnabled(homeColumns);
				moveLeftAction.setEnabled(homeColumns);

				//Continious colums at the end
				final boolean endColumns = columns[columns.length - 1] != table.getColumnCount() - 1;
				moveRightAction.setEnabled(endColumns);
				moveEndAction.setEnabled(endColumns);
			}
		}
	}

	private void initActions() {
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
		deleteRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/remove-row.png"))) {
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
				if (columnName != null) {
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
			}
		};
		deleteColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/remove-column.png"))) {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] columns = table.getSelectedColumns();
				if (columns.length > 0) {
					tableModel.removeColumns(columns);
					updateColumnsWidths();
				}
			}
		};

		moveTopAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-top.png"))) {
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
		};
		moveUpAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-up.png"))) {
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
		};
		moveDownAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-down.png"))) {
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
		};
		moveBottomAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-bottom.png"))) {
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
		};

		moveHomeAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-home.png"))) {
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
		};
		moveLeftAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-left.png"))) {
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
		};
		moveRightAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-right.png"))) {
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
		};
		moveEndAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-end.png"))) {
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
		};

		separatorChangedAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		};
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

		table.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				setActiveButtons();
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
				setActiveButtons();
			}
		});

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
								selectColumn(col);
								table.requestFocusInWindow();
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

		//Toolbar buttons
		KeyStroke strokeAddRow = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0);
		KeyStroke strokeRemoveRow = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		KeyStroke strokeAddColumn = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, InputEvent.CTRL_DOWN_MASK);
		KeyStroke strokeRemoveColumn = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK);
		KeyStroke strokeEscape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

		this.getInputMap().put(strokeAddRow, "INSERT_ROW_COMMAND");
		this.getActionMap().put("INSERT_ROW_COMMAND", addRowAction);
		this.getInputMap().put(strokeRemoveRow, "DELETE_ROW_COMMAND");
		this.getActionMap().put("DELETE_ROW_COMMAND", deleteRowAction);

		table.getInputMap().put(strokeAddRow, "INSERT_ROW_COMMAND");
		table.getActionMap().put("INSERT_ROW_COMMAND", addRowAction);
		table.getInputMap().put(strokeRemoveRow, "DELETE_ROW_COMMAND");
		table.getActionMap().put("DELETE_ROW_COMMAND", deleteRowAction);

		this.getInputMap().put(strokeAddColumn, "INSERT_COLUMN_COMMAND");
		this.getActionMap().put("INSERT_COLUMN_COMMAND", addColumnAction);
		this.getInputMap().put(strokeRemoveColumn, "DELETE_COLUMN_COMMAND");
		this.getActionMap().put("DELETE_COLUMN_COMMAND", deleteColumnAction);

		table.getInputMap().put(strokeAddColumn, "INSERT_COLUMN_COMMAND");
		table.getActionMap().put("INSERT_COLUMN_COMMAND", addColumnAction);
		table.getInputMap().put(strokeRemoveColumn, "DELETE_COLUMN_COMMAND");
		table.getActionMap().put("DELETE_COLUMN_COMMAND", deleteColumnAction);

		//Move rows shortcuts
		table.getInputMap().put(moveTopPopUp.getAccelerator(), "MOVE_TOP");
		table.getActionMap().put("MOVE_TOP", moveTopAction);

		table.getInputMap().put(moveUpPopUp.getAccelerator(), "MOVE_UP");
		table.getActionMap().put("MOVE_UP", moveUpAction);

		table.getInputMap().put(moveDownPopUp.getAccelerator(), "MOVE_DOWN");
		table.getActionMap().put("MOVE_DOWN", moveDownAction);

		table.getInputMap().put(moveBottomPopUp.getAccelerator(), "MOVE_BOTTOM");
		table.getActionMap().put("MOVE_BOTTOM", moveBottomAction);

		//Move columns shortcuts
		table.getInputMap().put(moveHomePopUp.getAccelerator(), "MOVE_HOME");
		table.getActionMap().put("MOVE_HOME", moveHomeAction);

		table.getInputMap().put(moveLeftPopUp.getAccelerator(), "MOVE_LEFT");
		table.getActionMap().put("MOVE_LEFT", moveLeftAction);

		table.getInputMap().put(moveRightPopUp.getAccelerator(), "MOVE_RIGHT");
		table.getActionMap().put("MOVE_RIGHT", moveRightAction);

		table.getInputMap().put(moveEndPopUp.getAccelerator(), "MOVE_END");
		table.getActionMap().put("MOVE_END", moveEndAction);

		// Escape action (Because press on ESCAPE key when editing cell does not fire any event)
		// Handle escape key on a JTable
		Action escapeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.isEditing()) {
					int row = table.getEditingRow();
					int col = table.getEditingColumn();
					table.getCellEditor(row, col).cancelCellEditing();
				}
			}
		};
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeEscape, "ESCAPE");
		table.getActionMap().put("ESCAPE", escapeAction);

		table.setComponentPopupMenu(tablePopUpMenu);
		tableScrollPane.setComponentPopupMenu(tablePopUpMenu);

		//Cut, Copy, Paste
		table.setTransferHandler(new TableTransferHandler());

		ActionMap map = table.getActionMap();

		map.put(TransferHandler.getCutAction().getValue(Action.NAME), TransferHandler.getCutAction());
		map.put(TransferHandler.getCopyAction().getValue(Action.NAME), TransferHandler.getCopyAction());
		map.put(TransferHandler.getPasteAction().getValue(Action.NAME), TransferHandler.getPasteAction());

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
		table.requestFocusInWindow();
	}

	private void selectRowInterval(int row1, int row2) {
		Rectangle rect = table.getCellRect(row1, 0, true);
		table.scrollRectToVisible(rect);
		table.clearSelection();
		table.addRowSelectionInterval(row1, row2);
		table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
		table.requestFocusInWindow();
	}

	private void selectColumn(int column) {
		table.clearSelection();
		table.addColumnSelectionInterval(column, column);
		table.addRowSelectionInterval(0, table.getRowCount() - 1);
		table.requestFocusInWindow();
	}

	private void selectColumnInterval(int column1, int column2) {
		Rectangle rect = table.getCellRect(0, column1, true);
		table.scrollRectToVisible(rect);
		table.clearSelection();
		table.addColumnSelectionInterval(column1, column2);
		table.addRowSelectionInterval(0, table.getRowCount() - 1);
		table.requestFocusInWindow();
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

	public Character getSeparator() {
		return (Character) separators.getSelectedItem();
	}

	public void updateSeparators() {
		Character selectedItem = (Character) separators.getSelectedItem();
		boolean newModelContainsSelected;
		int customSeparatorCount = OptionsUtils.readCustomSeparatorCount();
		DefaultComboBoxModel model;
		if (customSeparatorCount > 0) {
			List<Character> s = OptionsUtils.readCustomSeparators(customSeparatorCount);
			s.add(0, ',');
			s.add(1, ';');
			model = new DefaultComboBoxModel(s.toArray());
			newModelContainsSelected = s.contains(selectedItem);
		} else {
			model = new DefaultComboBoxModel(new Character[]{',', ';'});
			newModelContainsSelected = selectedItem.equals(',') || selectedItem.equals(';');
		}
		separators.setModel(model);
		separators.setSelectedItem(selectedItem);
		if (!newModelContainsSelected) {
			separatorChangedAction.actionPerformed(null);
		}
	}
}
