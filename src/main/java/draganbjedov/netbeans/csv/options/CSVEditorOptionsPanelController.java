package draganbjedov.netbeans.csv.options;

import draganbjedov.netbeans.csv.dataobject.CSVDataObject;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;
import javax.swing.JComponent;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@OptionsPanelController.SubRegistration(
		location = "Advanced",
		displayName = "#AdvancedOption_DisplayName_CSVEditor",
		keywords = "#AdvancedOption_Keywords_CSVEditor",
		keywordsCategory = "Advanced/CSVEditor")
@org.openide.util.NbBundle.Messages({"AdvancedOption_DisplayName_CSVEditor=CSV Editor", "AdvancedOption_Keywords_CSVEditor=csv editor"})
public final class CSVEditorOptionsPanelController extends OptionsPanelController {

	private CSVEditorPanel panel;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private boolean changed;

	@Override
	public void update() {
		getPanel().load();
		changed = false;
	}

	@Override
	public void applyChanges() {
		getPanel().store();
		Set<TopComponent> opened = WindowManager.getDefault().getRegistry().getOpened();
		for (TopComponent tc : opened) {
			CSVDataObject csvDataObject = tc.getLookup().lookup(CSVDataObject.class);
			if (csvDataObject != null) {
				csvDataObject.updateSeparators();
			}
		}
		changed = false;
	}

	@Override
	public void cancel() {
		// need not do anything special, if no changes have been persisted yet
	}

	@Override
	public boolean isValid() {
		return getPanel().valid();
	}

	@Override
	public boolean isChanged() {
		return changed;
	}

	@Override
	public HelpCtx getHelpCtx() {
		return null; // new HelpCtx("...ID") if you have a help set
	}

	@Override
	public JComponent getComponent(Lookup masterLookup) {
		return getPanel();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}

	private CSVEditorPanel getPanel() {
		if (panel == null) {
			panel = new CSVEditorPanel(this);
		}
		return panel;
	}

	void changed() {
		if (!changed) {
			changed = true;
			pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
		}
		pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
	}
}
