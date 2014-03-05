package draganbjedov.netbeans.csv.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/*
 * RowNumberTable.java
 *
 * Created on Apr 15, 2013, 8:57:27 PM
 *
 * @author Dragan Bjedov
 */
public class RowNumberTable extends JTable implements ChangeListener, PropertyChangeListener {

	private final JTable table;
	private final boolean countFromZero;
	private final String rowHeader;
	private Font font;
	private String[] values;

	public RowNumberTable(JTable table) {
		this(table, false, "");
	}

	public RowNumberTable(final JTable table, boolean countFromZero, String header) {
		this(table, countFromZero, null, header);
	}

	public RowNumberTable(final JTable table, boolean countFromZero, String rowHeader, String header) {
		this.table = table;
		this.countFromZero = countFromZero;
		this.rowHeader = rowHeader == null || rowHeader.isEmpty() ? "" : rowHeader + " ";
		init(header);
	}

	private void init(String header) {
		table.addPropertyChangeListener(this);

		setFocusable(false);
		setAutoCreateColumnsFromModel(false);
		setShowGrid(false);

		updateRowHeight();
		updateModel();
		updateSelectionModel();

		font = table.getTableHeader().getFont()/*.deriveFont(Font.BOLD)*/;
		table.getTableHeader().setFont(font);
		this.getTableHeader().setFont(font);

		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
		this.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));

		TableColumn column = new TableColumn();
		column.setHeaderValue(header);
		addColumn(column);
		column.setCellRenderer(new RowNumberRenderer());

		int width = 30;
		if (header != null && !header.isEmpty()) {
			FontMetrics metrics = table.getFontMetrics(table.getTableHeader().getFont().deriveFont(Font.BOLD));
			Rectangle2D bounds = metrics.getStringBounds(header, null);
			if ((int) bounds.getWidth() > 50) {
				width = (int) bounds.getWidth();
			}
			width += 10;
		}

		getColumnModel().getColumn(0).setPreferredWidth(width);
		setPreferredScrollableViewportSize(getPreferredSize());

		getTableHeader().setReorderingAllowed(false);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int row = RowNumberTable.this.rowAtPoint(me.getPoint());
				if (row != -1) {
					table.addRowSelectionInterval(row, row);
					table.addColumnSelectionInterval(0, table.getModel().getColumnCount() - 1);
				} else {
					table.clearSelection();
				}
			}
		});

		setIntercellSpacing(new Dimension(0, 0));
	}

	@Override
	public void addNotify() {
		super.addNotify();

		Component c = getParent();

		// Keep scrolling of the row table in sync with the main table.
		if (c instanceof JViewport) {
			JViewport viewport = (JViewport) c;
			viewport.addChangeListener(this);
		}
	}

	/*
	 * Delegate method to main table
	 */
	@Override
	public int getRowCount() {
		return table.getRowCount();
	}

	@Override
	public int getRowHeight(int row) {
		return table.getRowHeight(row);
	}

	/*
	 * This table does not use any data from the main TableModel, so just return
	 * a value based on the row parameter.
	 */
	@Override
	public Object getValueAt(int row, int column) {
		if (values == null || values.length <= row)
			return rowHeader + Integer.toString(row + (countFromZero ? 0 : 1));
		else
			return values[row];
	}

	/*
	 * Don't edit data in the main TableModel by mistake
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	// implements ChangeListener
	@Override
	public void stateChanged(ChangeEvent e) {
		// Keep the scrolling of the row table in sync with main table

		JViewport viewport = (JViewport) e.getSource();
		JScrollPane scrollPane = (JScrollPane) viewport.getParent();
		scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
	}

	// implements PropertyChangeListener
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// Keep the row table in sync with the main table

		if ("rowHeight".equals(e.getPropertyName())) {
			updateRowHeight();
		}

		if ("selectionModel".equals(e.getPropertyName())) {
			updateSelectionModel();
		}

		if ("model".equals(e.getPropertyName())) {
			updateModel();
		}
	}

	private void updateRowHeight() {
		setRowHeight(table.getRowHeight());
	}

	private void updateModel() {
		setModel(table.getModel());
	}

	private void updateSelectionModel() {
		setSelectionModel(table.getSelectionModel());
	}

	public JTable getTable() {
		return table;
	}

	@Override
	public boolean isEnabled() {
		if (table != null)
			return table.isEnabled();
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.getTableHeader().setEnabled(enabled);
		table.getTableHeader().setEnabled(enabled);
		table.getTableHeader().repaint();
		getTableHeader().repaint();
		repaint();
	}

	public void setValues(String[] values) {
		this.values = values;
		String s = "";
		for (String ss : values) {
			if (s.length() < ss.length())
				s = ss;
		}
		int width = 50;
		if (!s.isEmpty()) {
			FontMetrics metrics = new FontMetrics(table.getTableHeader().getFont()) {
			};
			Rectangle2D bounds = metrics.getStringBounds(s, null);
			if ((int) bounds.getWidth() > 50) {
				width = (int) bounds.getWidth();
			}
			width += 10;
		}
		getColumnModel().getColumn(0).setPreferredWidth(width);
		setPreferredScrollableViewportSize(getPreferredSize());
	}

	private final class RowNumberRenderer extends DefaultTableCellRenderer {

		public RowNumberRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (table != null) {
				JTableHeader header = table.getTableHeader();
				if (header != null) {
					TableCellRenderer defaultRenderer = header.getDefaultRenderer();
					if (defaultRenderer != null) {
						Component component = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						if (component instanceof JComponent) {
							JComponent jComponent = (JComponent) component;
							jComponent.setOpaque(false);
							jComponent.setFont(isSelected ? font.deriveFont(Font.BOLD) : font);
						}
						return component;
					} else {
						setForeground(header.getForeground());
						setBackground(header.getBackground());
						setFont(header.getFont());
					}
				}
			}

			if (isSelected) {
				setFont(getFont().deriveFont(Font.BOLD));
			}

			setText((value == null) ? "" : value.toString());
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));

			return this;
		}
	}

	private final class HeaderRenderer implements TableCellRenderer {

		private final JTable table;
		private final TableCellRenderer renderer;

		public HeaderRenderer(JTable table) {
			this.table = table;
			renderer = table.getTableHeader().getDefaultRenderer();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			boolean selected = table == RowNumberTable.this ? this.table.isRowSelected(row) : this.table.isColumnSelected(col);
			JLabel comp = (JLabel) renderer.getTableCellRendererComponent(table, value, selected, hasFocus, row, col);
			comp.setHorizontalAlignment(SwingConstants.CENTER);
			comp.setFont(selected ? font.deriveFont(Font.BOLD) : font);
			this.table.getTableHeader().repaint();
			return comp;
		}
	}
}
