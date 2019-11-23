package draganbjedov.netbeans.csv.view.columns;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotificationLineSupport;
import org.openide.util.NbBundle;

/*
 * AddColumnDialog.java
 *
 * Created on Jul 11, 2014, 11:44:56 AM
 *
 * @author Dragan Bjedov
 */
public class RemoveColumnDialog extends javax.swing.JPanel {

	private static RemoveColumnDialog dialog;

	private final DialogDescriptor dialogDescriptor;
	private final NotificationLineSupport notificationLineSupport;

	private Integer result;

	@SuppressWarnings("LeakingThisInConstructor")
	@NbBundle.Messages("REMOVE_DIALOG_TITLE=Select column to remove")
	private RemoveColumnDialog() {
		initComponents();

		dialogDescriptor = new DialogDescriptor(this, Bundle.REMOVE_DIALOG_TITLE(), true, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == DialogDescriptor.OK_OPTION) {
					result = columnsComboBox.getSelectedIndex();
				} else
					result = null;
			}
		});
		notificationLineSupport = dialogDescriptor.createNotificationLineSupport();
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        columnsComboBox = new javax.swing.JComboBox();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RemoveColumnDialog.class, "RemoveColumnDialog.jLabel1.text")); // NOI18N

        columnsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(columnsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(columnsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JComboBox columnsComboBox;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

	private void init(Collection<String> columnNames) {
		String[] columns = columnNames.toArray(new String[0]);
		this.columnsComboBox.setModel(new DefaultComboBoxModel(columns));
	}

	/**
	 * Show dialog for choosing column to delete
	 *
	 * @param customColumnNames column names
	 * @return new column name or <code>null</code> for same name or cancel
	 */
	public static Integer show(Collection<String> customColumnNames) {
		if (dialog == null)
			dialog = new RemoveColumnDialog();
		dialog.init(customColumnNames);
		return DialogDisplayer.getDefault().notify(dialog.dialogDescriptor) == DialogDescriptor.OK_OPTION ? dialog.result : null;
	}

}
