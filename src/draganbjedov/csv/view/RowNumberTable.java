package draganbjedov.csv.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
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
    private MatteBorder lineBorder;

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

        font = new Font("Arial", Font.BOLD, table.getTableHeader().getFont().getSize());
        table.getTableHeader().setFont(font);
        this.getTableHeader().setFont(font);

        lineBorder = new MatteBorder(new Insets(0, 0, 1, 2), new Color(222, 222, 223));
//        lineBorder = new DBLineBorder(new Color(222, 222, 223));
//        lineBorder.setInsets(new Insets(0, 2, 1, 2));
//        lineBorder.setTopBorderVisible(false);
//        lineBorder.setLeftBorderVisible(false);

        table.getTableHeader().setForeground(Color.DARK_GRAY);
        this.getTableHeader().setForeground(Color.DARK_GRAY);

        table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
        this.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));

        TableColumn column = new TableColumn();
        column.setHeaderValue(header);
        addColumn(column);
        column.setCellRenderer(new RowNumberRenderer());

        int width = 30;
        if (header != null && !header.isEmpty()) {
            FontMetrics metrics = new FontMetrics(table.getTableHeader().getFont()) {
            };
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
        if (s != null && !s.isEmpty()) {
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

    /*
     * Borrow the renderer from JDK1.4.2 table header
     */
    private final class RowNumberRenderer extends DefaultTableCellRenderer {

        public RowNumberRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setFont(font);
            setBorder(lineBorder);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (table != null) {
                if (table.isEnabled()) {
                    if (isSelected) {
                        setForeground(Color.WHITE);
                        setBackground(table.getSelectionBackground());
                    } else {
                        setForeground(Color.DARK_GRAY);
                        setBackground(Color.WHITE);
                    }
                } else {
                    setForeground(Color.LIGHT_GRAY);
                    setBackground(Color.WHITE);
                }
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private final class HeaderRenderer implements TableCellRenderer {

        private TableCellRenderer renderer;

        public HeaderRenderer(JTable table) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel comp = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            comp.setForeground(table.isEnabled() ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            comp.setHorizontalAlignment(SwingConstants.CENTER);
            return comp;
        }
    }
}
