package draganbjedov.netbeans.csv.options;

import draganbjedov.netbeans.csv.options.util.OptionsUtils;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

public final class CSVEditorPanel extends javax.swing.JPanel {

    private final CSVEditorOptionsPanelController controller;
    private DefaultListModel listModel;

    CSVEditorPanel(CSVEditorOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        init();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        defaultSeparatorLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        defaultSeparator = new javax.swing.JList();
        addButton = new javax.swing.JButton();
        customSeparator = new javax.swing.JTextField();
        removeButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(defaultSeparatorLabel, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.defaultSeparatorLabel.text")); // NOI18N

        defaultSeparator.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { ",", ";" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(defaultSeparator);

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.addButton.text")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(defaultSeparatorLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(removeButton)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, removeButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(defaultSeparatorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addButton)
                            .addComponent(customSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String s = customSeparator.getText();
        if (!listModel.contains(s)) {
            listModel.addElement(s);
            controller.changed();
        } else {
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(
                    NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.error.add"),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(notifyDescriptor);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int index = defaultSeparator.getSelectedIndex();
        if (index > 1) {
            listModel.remove(index);
            controller.changed();
        } else {
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(
                    NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.error.remove"),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(notifyDescriptor);
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    void load() {
        // Example:        
        // someCheckBox.setSelected(Preferences.userNodeForPackage(CSVEditorPanel.class).getBoolean("someFlag", false));
        // or for org.openide.util with API spec. version >= 7.4:
        // someCheckBox.setSelected(NbPreferences.forModule(CSVEditorPanel.class).getBoolean("someFlag", false));
        // or:
        // someTextField.setText(SomeSystemOption.getDefault().getSomeStringProperty());
        listModel.removeAllElements();
        listModel.addElement(",");
        listModel.addElement(";");
        int count = OptionsUtils.readCustomSeparatorCount();
        if (count > 0) {
            List<String> s = OptionsUtils.readCustomSeparators(count);
            for (String ss : s)
                listModel.addElement(ss);
        }
        defaultSeparator.setSelectedValue(OptionsUtils.readDefaultSeparator(), true);
    }

    void store() {
        // Example:
        // Preferences.userNodeForPackage(CSVEditorPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or for org.openide.util with API spec. version >= 7.4:
        // NbPreferences.forModule(CSVEditorPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or:
        // SomeSystemOption.getDefault().setSomeStringProperty(someTextField.getText());
        int count = listModel.size() - 2;
        OptionsUtils.saveCustomSeparatorCount(count);
        if (count > 0) {
            String[] s = new String[count];
            for (int i = 0; i < s.length; i++) {
                s[i] = (String) listModel.get(i + 2);
            }
            OptionsUtils.saveCustomSeparators(s);
        }
        OptionsUtils.saveDefaultSeparator(defaultSeparator.getSelectedValue().toString());
    }

    boolean valid() {
        return defaultSeparator.getSelectedIndex() != -1;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField customSeparator;
    private javax.swing.JList defaultSeparator;
    private javax.swing.JLabel defaultSeparatorLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables

    private void init() {
        listModel = new DefaultListModel();
        listModel.addElement(",");
        listModel.addElement(";");
        defaultSeparator.setModel(listModel);
        customSeparator.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isAddEnabled();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isAddEnabled();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isAddEnabled();
            }

            private void isAddEnabled() {
                addButton.setEnabled(!customSeparator.getText().trim().isEmpty());
            }
        });
        defaultSeparator.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeButton.setEnabled(defaultSeparator.getSelectedIndex() != -1);
                controller.changed();
            }
        });
        addButton.setEnabled(false);
    }
}
