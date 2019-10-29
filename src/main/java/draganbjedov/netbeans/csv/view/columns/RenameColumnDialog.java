package draganbjedov.netbeans.csv.view.columns;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotificationLineSupport;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

/*
 * AddColumnDialog.java
 *
 * Created on Jul 11, 2014, 11:44:56 AM
 *
 * @author Dragan Bjedov
 */
public class RenameColumnDialog extends javax.swing.JPanel implements DocumentListener {

	private static RenameColumnDialog dialog;

	private final DialogDescriptor dialogDescriptor;
	private final NotificationLineSupport notificationLineSupport;

	private Pair<Integer, String> result;

	@SuppressWarnings("LeakingThisInConstructor")
	private RenameColumnDialog() {
		initComponents();

		dialogDescriptor = new DialogDescriptor(this, Bundle.DIALOG_TITLE(), true, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == DialogDescriptor.OK_OPTION) {
					final String selectedColumnName = (String) columnsComboBox.getSelectedItem();
					final String newColumnName = columnNameTextField.getText().trim();
					result = selectedColumnName.equals(newColumnName) ? null : Pair.of(columnsComboBox.getSelectedIndex(), newColumnName);
				} else
					result = null;
			}
		});
		notificationLineSupport = dialogDescriptor.createNotificationLineSupport();

		columnNameTextField.getDocument().addDocumentListener(this);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        labelColName = new javax.swing.JLabel();
        columnNameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        columnsComboBox = new javax.swing.JComboBox();

        org.openide.awt.Mnemonics.setLocalizedText(labelColName, org.openide.util.NbBundle.getMessage(RenameColumnDialog.class, "RenameColumnDialog.labelColName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RenameColumnDialog.class, "RenameColumnDialog.jLabel1.text")); // NOI18N

        columnsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelColName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(columnNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(columnsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, labelColName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(columnsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelColName)
                    .addComponent(columnNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JTextField columnNameTextField;
    private javax.swing.JComboBox columnsComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelColName;
    // End of variables declaration//GEN-END:variables

	private Collection<String> columnNames;

	private void init(Collection<String> columnNames) {
		this.columnNames = columnNames;

		this.columnsComboBox.setModel(new DefaultComboBoxModel(columnNames.toArray(new String[0])));

		this.columnNameTextField.setText(null);

		textChanged();
	}

	/**
	 * Prikazuje dijalog za unos novog imena kolone
	 *
	 * @param customColumnNames
	 * @param selectedColumnName
	 * @return ime kolone ili null za isto ime ili cancel
	 */
	public static Pair<Integer, String> show(Collection<String> customColumnNames) {
		if (dialog == null)
			dialog = new RenameColumnDialog();
		dialog.init(customColumnNames);
		return DialogDisplayer.getDefault().notify(dialog.dialogDescriptor) == DialogDescriptor.OK_OPTION ? dialog.result : null;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		textChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChanged();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		textChanged();
	}

	@NbBundle.Messages({
		"ERR_COL_NAME_EQUALS=Column name not changed"
	})
	private void textChanged() {
		final String columnName = columnNameTextField.getText().trim();
		if (columnName.length() == 0) {
			dialogDescriptor.setValid(false);
			notificationLineSupport.setErrorMessage(Bundle.ERR_COL_NAME_EMPTY());
		} else if (columnNames != null && columnName.equals((String) columnsComboBox.getSelectedItem())) {
			dialogDescriptor.setValid(false);
			notificationLineSupport.setWarningMessage(Bundle.ERR_COL_NAME_EQUALS());
		} else if (columnNames != null && columnNames.contains(columnName)) {
			dialogDescriptor.setValid(true);
			notificationLineSupport.setWarningMessage(Bundle.ERR_COL_NAME_EXISTS());
		} else {
			dialogDescriptor.setValid(true);
			notificationLineSupport.clearMessages();
		}
	}

}
