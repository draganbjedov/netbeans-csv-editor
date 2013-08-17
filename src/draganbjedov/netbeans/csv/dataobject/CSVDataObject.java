package draganbjedov.netbeans.csv.dataobject;

import draganbjedov.netbeans.csv.view.CSVTableModel;
import draganbjedov.netbeans.csv.view.CSVVisualElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.modules.editor.NbEditorDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.UndoRedo;
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
        iconBase = "draganbjedov/netbeans/csv/icons/csv.png",
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
            iconBase = "draganbjedov/netbeans/csv/icons/csv.png",
            mimeType = "text/csv",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "CSV",
            position = 2000)
    @Messages("LBL_CSV_EDITOR=Text")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }
    private UndoRedo.Manager undoRedoManager;
    private boolean addedUndoRedoManager = false;
    private CSVTableModel model;
    private CSVVisualElement visualEditor;

    public CSVDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        undoRedoManager = new UndoRedo.Manager() {
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                updateTable();
            }

            @Override
            protected void undoTo(UndoableEdit edit) throws CannotUndoException {
                super.undoTo(edit);
                updateTable();
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                updateTable();
            }

            @Override
            protected void redoTo(UndoableEdit edit) throws CannotRedoException {
                super.redoTo(edit);
                updateTable();
            }

            @Override
            public void undoOrRedo() throws CannotRedoException, CannotUndoException {
                super.undoOrRedo();
                updateTable();
            }

            private void updateTable() {
                if (visualEditor != null && visualEditor.isVisible()) {
                    visualEditor.updateTable();
                }
            }
        };
        registerEditor("text/csv", true);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @SuppressWarnings({"null", "ConstantConditions"})
    public void readFile(CSVTableModel model) {
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
                //<editor-fold defaultstate="collapsed" desc="comment">
                //                File file = FileUtil.toFile(this.getPrimaryFile());
                //                if (file.length() != 0) {
                //                    try {
                //                        List<String> s = this.getPrimaryFile().asLines();
                //                        boolean first = true;
                //                        List<List<String>> values = new ArrayList<List<String>>(s.size());
                //                        for (String ss : s) {
                //                            if (first) {
                //                                String[] split = ss.split(",");
                //                                ArrayList<String> headers = new ArrayList<String>(split.length);
                //                                Collections.addAll(headers, split);
                //                                tableModel.setHeaders(headers);
                //                                first = false;
                //                                continue;
                //                            }
                //                            String[] split = ss.split(",");
                //                            ArrayList<String> rowData = new ArrayList<String>(split.length);
                //                            Collections.addAll(rowData, split);
                //                            values.add(rowData);
                //                        }
                //                        tableModel.setValues(values);
                //                    } catch (IOException ex) {
                //                        Exceptions.printStackTrace(ex);
                //                    }
                //                } else {
                //                    tableModel.setHeaders(new ArrayList<String>());
                //                    tableModel.setValues(new ArrayList<List<String>>());
                //                }
                //</editor-fold>
            }
            if (document != null) {
                addUndoableEditListener(document);

                int length = document.getLength();
                if (length > 0) {
                    String text = document.getText(0, length);
                    String[] s = text.split("\n");
                    boolean first = true;
                    List<List<String>> values = new ArrayList<List<String>>(s.length);
                    for (String ss : s) {
                        if (first) {
                            String[] split = ss.split(",");
                            ArrayList<String> headers = new ArrayList<String>(split.length);
                            Collections.addAll(headers, split);
                            if (ss.endsWith(","))
                                headers.add("");
                            model.setHeaders(headers);
                            first = false;
                            continue;
                        }
                        String[] split = ss.split(",");
                        ArrayList<String> rowData = new ArrayList<String>(split.length);
                        Collections.addAll(rowData, split);
                        if (ss.endsWith(","))
                            rowData.add("");
                        values.add(rowData);
                    }
                    model.setValues(values);
                } else {
                    model.setHeaders(new ArrayList<String>());
                    model.setValues(new ArrayList<List<String>>());
                }
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.model = model.clone();
        model.fireTableStructureChanged();
    }

    public void updateFile(CSVTableModel model) {
        if (!this.model.equals(model)) {
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

            addUndoableEditListener(document);

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
                        String value = model.getValueAt(i, j);
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
                    this.model = model.clone();
                }
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    public UndoRedo.Manager getUndoRedoManager() {
        return undoRedoManager;
    }

    public void setVisualEditor(CSVVisualElement visualEditor) {
        this.visualEditor = visualEditor;
    }

    private void addUndoableEditListener(NbEditorDocument document) {
        if (!addedUndoRedoManager) {
            UndoableEditListener[] undoableEditListeners = document.getUndoableEditListeners();
            boolean found = false;
            if (undoableEditListeners.length > 0) {
                for (UndoableEditListener uel : undoableEditListeners) {
                    if (uel.equals(undoRedoManager)) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                document.addUndoableEditListener(undoRedoManager);
                addedUndoRedoManager = true;
            }
        }
    }
}
