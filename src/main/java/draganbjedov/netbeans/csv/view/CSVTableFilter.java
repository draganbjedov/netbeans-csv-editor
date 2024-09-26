package draganbjedov.netbeans.csv.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.JXTable;
import org.oxbow.swingbits.table.filter.JTableFilter;

public class CSVTableFilter extends JTableFilter {

    public CSVTableFilter(JXTable table) {
        super(table);
    }

    @Override
    public void modelChanged(TableModel model) {
        // Tri-state table sorter
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model) {
            @Override
            public void toggleSortOrder(int column) {
                if (!isSortable(column)) {
                    super.toggleSortOrder(column);
                    return;
                }
                List<SortKey> keys = new ArrayList<>(getSortKeys());
                if (!keys.isEmpty()) {
                    SortKey sortKey = keys.get(0);
                    if (sortKey.getColumn() == column && sortKey.getSortOrder() == SortOrder.DESCENDING) {
                        setSortKeys(null);
                        return;
                    }
                }
                super.toggleSortOrder(column);
            }
        };
        sorter.setSortsOnUpdates(true);
        getTable().setRowSorter(sorter);
    }

    public boolean hasActiveFilters() {
        for (int i = 0; i < getTable().getColumnCount(); i++) {
            if (isFiltered(i))
                return true;
        }
        return false;
    }
}
