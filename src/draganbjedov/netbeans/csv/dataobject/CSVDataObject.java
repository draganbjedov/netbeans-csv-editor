package draganbjedov.netbeans.csv.dataobject;

import draganbjedov.netbeans.csv.options.util.OptionsUtils;
import draganbjedov.netbeans.csv.view.CSVTableModel;
import draganbjedov.netbeans.csv.view.CSVVisualElement;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.lib.editor.util.PriorityListenerList;
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
			id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
			position = 100,
			separatorAfter = 200),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
			position = 300),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
			position = 400,
			separatorAfter = 500),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
			position = 600),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
			position = 700,
			separatorAfter = 800),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
			position = 900,
			separatorAfter = 1000),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
			position = 1100,
			separatorAfter = 1200),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
			position = 1300),
	@ActionReference(
			path = "Loaders/text/csv/Actions",
			id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
			position = 1400)
})
public class CSVDataObject extends MultiDataObject {

	private static final Logger LOG = Logger.getLogger(CSVDataObject.class.getName());

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
	private final UndoRedo.Manager undoRedoManager;
	private final DocumentListener documentListener;
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
		documentListener = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateTable();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateTable();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateTable();
			}

			private void updateTable() {
				if (visualEditor != null && !visualEditor.isActivated())
					visualEditor.updateTable();
			}
		};
		registerEditor("text/csv", true);

		this.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(DataObject.PROP_MODIFIED) && ((Boolean) evt.getNewValue())) {
					initDocument();
				}
			}
		});
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
				initDocument(document);

				int length = document.getLength();
				if (length > 0) {
					String text = document.getText(0, length);
					String[] s = text.split("\n");
					boolean first = true;
					List<List<String>> values = new ArrayList<>(s.length);
					char separator;
					char escapeChar = OptionsUtils.readDefaultEscapeChar();
					if (visualEditor != null)
						separator = visualEditor.getSeparator();
					else
						separator = OptionsUtils.readDefaultSeparator();
					for (String ss : s) {
						if (first) {
							model.setHeaders(splitLine(ss, separator, escapeChar));
							first = false;
							continue;
						}
						values.add(splitLine(ss, separator, escapeChar));
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

			initDocument(document);

			try {
				char separator;
				char escapeChar = OptionsUtils.readDefaultEscapeChar();
				if (visualEditor != null)
					separator = visualEditor.getSeparator();
				else
					separator = OptionsUtils.readDefaultSeparator();
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < model.getColumnCount(); i++) {
					final String columnName = model.getColumnName(i);
					if (columnName.indexOf(separator) != -1) {
						stringBuilder.append(escapeChar).append(columnName).append(escapeChar);
					} else {
						stringBuilder.append(columnName);
					}
					if (i + 1 < model.getColumnCount())
						stringBuilder.append(separator);
				}
				stringBuilder.append("\n");
				for (int i = 0; i < model.getRowCount(); i++) {
					for (int j = 0; j < model.getColumnCount(); j++) {
						String value = model.getValueAt(i, j);
						if (value == null)
							value = "";
						if (value.indexOf(separator) != -1) {
							stringBuilder.append(escapeChar).append(value).append(escapeChar);
						} else {
							stringBuilder.append(value);
						}
						if (j + 1 < model.getColumnCount())
							stringBuilder.append(separator);
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

	private void initDocument(NbEditorDocument document) {
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
		}

		DocumentListener[] documentListeners = document.getDocumentListeners();
		found = false;
		if (documentListeners.length > 0) {
			loopDocumentListener:
			for (DocumentListener dl : documentListeners) {
				if (dl.equals(documentListener)) {
					found = true;
					break;
				} else if (dl instanceof PriorityListenerList) {
					PriorityListenerList pll = (PriorityListenerList) dl;
					EventListener[][] listenersArray = pll.getListenersArray();
					for (EventListener[] row : listenersArray) {
						for (EventListener el : row) {
							if (el.equals(documentListener)) {
								found = true;
								break loopDocumentListener;
							}
						}
					}
				}
			}
		}
		if (!found) {
			document.addDocumentListener(documentListener);
		}
	}

	/**
	 * Init document listeners
	 */
	public void initDocument() {
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
		if (document != null)
			initDocument(document);
	}

	public void updateSeparators() {
		if (visualEditor != null)
			visualEditor.updateSeparators();
	}

	private static List<String> splitLine(String s, char separatorChar, char escapeChar) {
		List<String> split;
		if (s.indexOf(escapeChar) != -1) {
			split = new ArrayList<>();
			boolean escape = false;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == escapeChar) {
					if (!escape) {
						if (s.indexOf(escapeChar, i + 1) != -1)
							escape = true;
						else
							sb.append(c);
					} else
						escape = false;
				} else if (c == separatorChar) {
					if (escape)
						sb.append(c);
					else {
						split.add(sb.toString());
						sb.setLength(0);
					}
				} else {
					sb.append(c);
				}
			}
			split.add(sb.toString());
		} else {
			String separator = separatorChar + "";
			separator = separator.replaceAll("([\\\\\\.\\[\\{\\(\\*\\+\\?\\^\\$\\|])", "\\\\$1");
//			if (separatorChar == '|')
//				separator = "\\" + separator;
			String[] ss = s.split(separator);
			split = new ArrayList<>(ss.length);
			Collections.addAll(split, ss);
			if (s.endsWith(separator))
				split.add("");
		}
		return split;
	}
}
