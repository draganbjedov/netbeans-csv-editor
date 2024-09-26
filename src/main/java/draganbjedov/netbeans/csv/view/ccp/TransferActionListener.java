package draganbjedov.netbeans.csv.view.ccp;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JComponent;

/**
 * A class that tracks the focused component. This is necessary to delegate the
 * menu cut/copy/paste commands to the right component. An instance of this
 * class is listening and when the user fires one of these commands, it calls
 * the appropriate action on the currently focused component.
 */
public class TransferActionListener implements ActionListener, PropertyChangeListener {

    private JComponent focusOwner = null;

    @SuppressWarnings("LeakingThisInConstructor")
    public TransferActionListener() {
        super();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addPropertyChangeListener("permanentFocusOwner", this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        Object o = e.getNewValue();
        if (o instanceof JComponent jComponent) {
            focusOwner = jComponent;
        } else {
            focusOwner = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (focusOwner == null) {
            return;
        }
        String action = e.getActionCommand();
        Action a = focusOwner.getActionMap().get(action);
        if (a != null) {
            a.actionPerformed(new ActionEvent(focusOwner,
                    ActionEvent.ACTION_PERFORMED,
                    null));
        }
    }
}
