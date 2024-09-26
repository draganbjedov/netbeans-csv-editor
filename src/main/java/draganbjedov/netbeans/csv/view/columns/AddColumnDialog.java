package draganbjedov.netbeans.csv.view.columns;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
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
@NbBundle.Messages({
    "DIALOG_TITLE=Add new column"
})
public class AddColumnDialog extends javax.swing.JPanel implements DocumentListener {

    private static AddColumnDialog dialog;

    private final DialogDescriptor dialogDescriptor;
    private final NotificationLineSupport notificationLineSupport;

    private Pair<Integer, String> result;

    @SuppressWarnings("LeakingThisInConstructor")
    private AddColumnDialog() {
        initComponents();

        dialogDescriptor = new DialogDescriptor(this, Bundle.DIALOG_TITLE(), true,
                (ActionEvent e) -> {
                    if (e.getSource() == DialogDescriptor.OK_OPTION) {
                        result = Pair.of(
                                columnsComboBox.getSelectedIndex() + (afterRadioButton.isSelected() ? 1 : 0),
                                columnNameTextField.getText().trim()
                        );
                    } else
                        result = null;
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
        beforeRadioButton = new javax.swing.JRadioButton();
        afterRadioButton = new javax.swing.JRadioButton();
        separator = new javax.swing.JSeparator();
        chooseLabel = new javax.swing.JLabel();
        columnsComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(labelColName, org.openide.util.NbBundle.getMessage(AddColumnDialog.class, "AddColumnDialog.labelColName.text")); // NOI18N

        buttonGroup.add(beforeRadioButton);
        beforeRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(beforeRadioButton, org.openide.util.NbBundle.getMessage(AddColumnDialog.class, "AddColumnDialog.beforeRadioButton.text")); // NOI18N

        buttonGroup.add(afterRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(afterRadioButton, org.openide.util.NbBundle.getMessage(AddColumnDialog.class, "AddColumnDialog.afterRadioButton.text")); // NOI18N

        separator.setBackground(new java.awt.Color(153, 153, 153));

        org.openide.awt.Mnemonics.setLocalizedText(chooseLabel, org.openide.util.NbBundle.getMessage(AddColumnDialog.class, "AddColumnDialog.chooseLabel.text")); // NOI18N

        columnsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        columnsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                columnsComboBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AddColumnDialog.class, "AddColumnDialog.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(separator)
                    .addComponent(beforeRadioButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(afterRadioButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chooseLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelColName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(columnNameTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(columnsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, labelColName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelColName)
                    .addComponent(columnNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(columnsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chooseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(beforeRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(afterRadioButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void columnsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_columnsComboBoxActionPerformed

    }//GEN-LAST:event_columnsComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton afterRadioButton;
    private javax.swing.JRadioButton beforeRadioButton;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel chooseLabel;
    private javax.swing.JTextField columnNameTextField;
    private javax.swing.JComboBox columnsComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelColName;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables

    private Collection<String> columnNames;

    @NbBundle.Messages({
        "# {0} - Selected column name",
        "INFO_MSG=<html>Selected column is \"{0}\".",
        "# {0} - Selected column name",
        "INFO_MSG_AFTER=<html>New column will be inserted <u>after</u> column \"{0}\""
    })
    private void init(List<String> columnNames, boolean tableHasHeaderRow) {
        this.columnNames = columnNames;

        columnsComboBox.setModel(new DefaultComboBoxModel(columnNames.toArray()));

        columnsComboBoxActionPerformed(null);

        labelColName.setVisible(tableHasHeaderRow);
        columnNameTextField.setVisible(tableHasHeaderRow);
        separator.setVisible(tableHasHeaderRow);

        columnNameTextField.setText(tableHasHeaderRow ? "" : "default");

        textChanged();
    }

    /**
     * Shows dialog to enter new column name and position
     *
     * @param columnNames column names
     * @param tableHasHeaderRow flag indicating does table has header row or not
     * @return Pair column - name of column after which new one should be added
     */
    public static Pair<Integer, String> show(List<String> columnNames, boolean tableHasHeaderRow) {
        if (dialog == null)
            dialog = new AddColumnDialog();
        dialog.init(columnNames, tableHasHeaderRow);
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
        "ERR_COL_NAME_EMPTY=Column name can not be empty",
        "ERR_COL_NAME_EXISTS=Column with specified name already exists"
    })
    private void textChanged() {
        final String columnName = columnNameTextField.getText().trim();
        if (columnName.length() == 0) {
            dialogDescriptor.setValid(false);
            notificationLineSupport.setErrorMessage(Bundle.ERR_COL_NAME_EMPTY());
        } else if (columnNames != null && columnNames.contains(columnName)) {
            dialogDescriptor.setValid(true);
            notificationLineSupport.setWarningMessage(Bundle.ERR_COL_NAME_EXISTS());
        } else {
            dialogDescriptor.setValid(true);
            notificationLineSupport.clearMessages();
        }
    }

}
