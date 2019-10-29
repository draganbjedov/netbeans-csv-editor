package draganbjedov.netbeans.csv.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dragan Bjedov
 */
public class OddEvenCellRenderer extends DefaultTableCellRenderer {

	protected Color oddRowColor = new Color(128, 128, 128, 35);

	public OddEvenCellRenderer() {
	}

	public OddEvenCellRenderer(Color oddRowColor) {
		this.oddRowColor = oddRowColor;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!isSelected) {
			if (row % 2 == 0) {
				comp.setBackground(table.getBackground());
			} else {
				comp.setBackground(oddRowColor);
			}
		}
		return comp;
	}

}
