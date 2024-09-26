package draganbjedov.netbeans.csv.view;

import com.bulenkov.iconloader.util.ColorUtil;
import com.bulenkov.iconloader.util.Gray;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.table.TableModel;

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

        updateWidth(header);

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
        if (c instanceof JViewport viewport) {
            viewport.addChangeListener(this);
        }
    }

    /**
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

    /**
     * This table does not use any data from the main TableModel, so just return
     * a value based on the row parameter.
     */
    @Override
    public Object getValueAt(int row, int column) {
        if (values == null || values.length <= row) {
            return rowHeader + Integer.toString(row + (countFromZero ? 0 : 1));
        } else {
            return values[row];
        }
    }

    /**
     * Don't edit data in the main TableModel by mistake
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // Keep the scrolling of the row table in sync with main table

        JViewport viewport = (JViewport) e.getSource();
        JScrollPane scrollPane = (JScrollPane) viewport.getParent();
        scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
    }

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
        final TableModel model = table.getModel();
        model.addTableModelListener(e -> updateWidth(String.valueOf(model.getRowCount())));
        setModel(model);
    }

    private void updateWidth(String s) {
        int width = 15;
        if (!s.isEmpty()) {
            FontMetrics metrics = new FontMetrics(table.getTableHeader().getFont()) {
            };
            Rectangle2D bounds = metrics.getStringBounds(s, null);
            width += (int) bounds.getWidth();
        }

        final int currentWidth = getColumnModel().getColumn(0).getPreferredWidth();
        if (currentWidth != width) {
            getColumnModel().getColumn(0).setPreferredWidth(width);
            setPreferredScrollableViewportSize(getPreferredSize());
        }
    }

    private void updateSelectionModel() {
        setSelectionModel(table.getSelectionModel());
    }

    public JTable getTable() {
        return table;
    }

    @Override
    public boolean isEnabled() {
        if (table != null) {
            return table.isEnabled();
        }
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
            if (s.length() < ss.length()) {
                s = ss;
            }
        }
        updateWidth(s);
    }

    private final class RowNumberRenderer extends DefaultTableCellRenderer {

        private final boolean opaque;
        private final boolean darculaLAF;

        public RowNumberRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            final String lafName = UIManager.getLookAndFeel().getName();
            opaque = lafName.equals("Metal") || lafName.equals("Windows Classic");
            darculaLAF = lafName.startsWith("Darcula");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (!darculaLAF && table != null) {
                JTableHeader header = table.getTableHeader();
                if (header != null) {
                    TableCellRenderer defaultRenderer = header.getDefaultRenderer();
                    if (defaultRenderer != null) {
                        Component component = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        if (component instanceof JComponent jComponent) {
                            jComponent.setOpaque(opaque);
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

            setFont(getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN));
            setText(value == null ? "" : value.toString());
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));

            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (darculaLAF) {
                // Modified code take from Darcula LAF implemenantion
                // https://github.com/bulenkov/Darcula/blob/master/src/com/bulenkov/darcula/DarculaTableHeaderUI.java
                final Graphics2D g2D = (Graphics2D) g;
                final Color bg = getBackground();
                g2D.setPaint(new GradientPaint(0, 0, ColorUtil.shift(bg, 1.4), 0, getHeight(), ColorUtil.shift(bg, 0.9)));
                final int h = this.getHeight();
                final int w = this.getWidth();
                g2D.fillRect(0, 0, w, h);
                g2D.setPaint(ColorUtil.shift(bg, 0.75));
                g2D.drawLine(0, h - 1, w, h - 1);
                g2D.drawLine(w - 1, 0, w - 1, h - 1);

                final Color lineColor = ColorUtil.shift(bg, 0.7);
                final Color shadow = Gray._255.withAlpha(30);
                g2D.setColor(lineColor);
                g2D.drawLine(w - 1, 1, w - 1, h - 3);
                g2D.setColor(shadow);
                g2D.drawLine(w, 1, w, h - 3);
            }
            super.paintComponent(g);
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
            comp.setOpaque(true);
            this.table.getTableHeader().repaint();
            return comp;
        }
    }
}
