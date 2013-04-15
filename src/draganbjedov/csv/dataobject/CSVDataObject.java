package draganbjedov.csv.dataobject;

import draganbjedov.csv.view.CSVTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.text.BadLocationException;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.modules.editor.NbEditorDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.text.DataEditorSupport;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_CSV_LOADER=Files of CSV"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_CSV_LOADER",
        mimeType = "text/csv",
        extension = {"csv", "CSV"})
@DataObject.Registration(
        mimeType = "text/csv",
        iconBase = "draganbjedov/csv/icons/csv.png",
        displayName = "#LBL_CSV_LOADER",
        position = 300)
@ActionReferences({
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300),
    @ActionReference(
            path = "Loaders/text/csv/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400)
})
public class CSVDataObject extends MultiDataObject {

    @MultiViewElement.Registration(
            displayName = "#LBL_CSV_EDITOR",
            iconBase = "draganbjedov/csv/icons/csv.png",
            mimeType = "text/csv",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "CSV",
            position = 2000)
    @Messages("LBL_CSV_EDITOR=Text")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    public CSVDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/csv", true);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @SuppressWarnings({"null", "ConstantConditions"})
    public CSVTableModel readFile() {
        CSVTableModel tableModel = null;
        try {
            Lookup lookup = getCookieSet().getLookup();
            DataEditorSupport dataEditorSupport = lookup.lookup(DataEditorSupport.class);
            NbEditorDocument document = null;
            if (dataEditorSupport.isDocumentLoaded()) {
                document = (NbEditorDocument) dataEditorSupport.getDocument();
            } else {
                try {
                    document = (NbEditorDocument) dataEditorSupport.openDocument();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            if (document != null) {
                int length = document.getLength();
                if (length > 0) {
                    String text = document.getText(0, length);
                    String[] s = text.split("\n");
                    boolean first = true;
                    for (String ss : s) {
                        if (first) {
                            String[] split = ss.split(",");
                            ArrayList<String> headers = new ArrayList<String>(split.length);
                            Collections.addAll(headers, split);
                            tableModel = new CSVTableModel(headers);
                            first = false;
                            continue;
                        }
                        String[] split = ss.split(",");
                        ArrayList<String> rowData = new ArrayList<String>(split.length);
                        Collections.addAll(rowData, split);
                        tableModel.addRow(rowData);
                    }
                }
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        return tableModel != null ? tableModel : new CSVTableModel();
    }

    public void updateFile(CSVTableModel model) {
        Lookup lookup = getCookieSet().getLookup();
        DataEditorSupport dataEditorSupport = lookup.lookup(DataEditorSupport.class);
        NbEditorDocument document;
        if (dataEditorSupport.isDocumentLoaded())
            document = (NbEditorDocument) dataEditorSupport.getDocument();
        else {
            try {
                document = (NbEditorDocument) dataEditorSupport.openDocument();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
                //TODO Unable to open file
                return;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < model.getColumnCount(); i++) {
                stringBuilder.append(model.getColumnName(i));
                if (i + 1 < model.getColumnCount())
                    stringBuilder.append(",");
            }
            stringBuilder.append("\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    String value = (String) model.getValueAt(i, j);
                    stringBuilder.append(value == null ? "" : value);
                    if (j + 1 < model.getColumnCount())
                        stringBuilder.append(",");
                }
                if (i + 1 < model.getRowCount())
                    stringBuilder.append("\n");
            }

            int length = document.getLength();
            String s = stringBuilder.toString();
            if (!document.getText(0, length).equals(s)) {
                document.replace(0, length, s, null);
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
