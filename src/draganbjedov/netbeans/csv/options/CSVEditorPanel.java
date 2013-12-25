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
	private DefaultListModel separatorsListModel;
	private DefaultListModel escapeCharsListModel;

	CSVEditorPanel(CSVEditorOptionsPanelController controller) {
		this.controller = controller;
		initComponents();
		init();
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        separatorsPanel = new javax.swing.JPanel();
        defaultSeparatorLabel = new javax.swing.JLabel();
        removeSButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        defaultSeparator = new javax.swing.JList();
        addSButton = new javax.swing.JButton();
        customSeparator = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        escapeCharsPanel = new javax.swing.JPanel();
        defaultEscapeCharLabel = new javax.swing.JLabel();
        customEscapeChar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        defaultEscapeChar = new javax.swing.JList();
        removeEButton = new javax.swing.JButton();
        addEButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(1, 0));

        separatorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.separatorsPanel.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(defaultSeparatorLabel, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.defaultSeparatorLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(removeSButton, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.removeSButton.text")); // NOI18N
        removeSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSButtonActionPerformed(evt);
            }
        });

        defaultSeparator.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { ",", ";" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(defaultSeparator);

        org.openide.awt.Mnemonics.setLocalizedText(addSButton, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.addSButton.text")); // NOI18N
        addSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout separatorsPanelLayout = new javax.swing.GroupLayout(separatorsPanel);
        separatorsPanel.setLayout(separatorsPanelLayout);
        separatorsPanelLayout.setHorizontalGroup(
            separatorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, separatorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(separatorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(defaultSeparatorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, separatorsPanelLayout.createSequentialGroup()
                        .addGroup(separatorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(removeSButton)
                            .addComponent(addSButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customSeparator)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                .addContainerGap())
        );

        separatorsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addSButton, removeSButton});

        separatorsPanelLayout.setVerticalGroup(
            separatorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(separatorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(separatorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addSButton)
                    .addComponent(customSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeSButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(defaultSeparatorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(separatorsPanel);

        escapeCharsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.escapeCharsPanel.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(defaultEscapeCharLabel, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.defaultEscapeCharLabel.text")); // NOI18N

        defaultEscapeChar.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "\"" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(defaultEscapeChar);

        org.openide.awt.Mnemonics.setLocalizedText(removeEButton, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.removeEButton.text")); // NOI18N
        removeEButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addEButton, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.addEButton.text")); // NOI18N
        addEButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout escapeCharsPanelLayout = new javax.swing.GroupLayout(escapeCharsPanel);
        escapeCharsPanel.setLayout(escapeCharsPanelLayout);
        escapeCharsPanelLayout.setHorizontalGroup(
            escapeCharsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escapeCharsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escapeCharsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(escapeCharsPanelLayout.createSequentialGroup()
                        .addGroup(escapeCharsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addEButton)
                            .addComponent(removeEButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customEscapeChar, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(defaultEscapeCharLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        escapeCharsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addEButton, removeEButton});

        escapeCharsPanelLayout.setVerticalGroup(
            escapeCharsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escapeCharsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escapeCharsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addEButton)
                    .addComponent(customEscapeChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeEButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(defaultEscapeCharLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(escapeCharsPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void addSButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSButtonActionPerformed
		Character c = customSeparator.getText().charAt(0);
		if (!separatorsListModel.contains(c)) {
			separatorsListModel.addElement(c);
			controller.changed();
		} else {
			NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(
					NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.error.addSeparator"),
					NotifyDescriptor.ERROR_MESSAGE);
			DialogDisplayer.getDefault().notifyLater(notifyDescriptor);
		}
    }//GEN-LAST:event_addSButtonActionPerformed

    private void removeSButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSButtonActionPerformed
		int index = defaultSeparator.getSelectedIndex();
		if (index > 1) {
			separatorsListModel.remove(index);
			controller.changed();
		} else {
			NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(
					NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.error.removeSeparator"),
					NotifyDescriptor.ERROR_MESSAGE);
			DialogDisplayer.getDefault().notifyLater(notifyDescriptor);
		}
    }//GEN-LAST:event_removeSButtonActionPerformed

    private void addEButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEButtonActionPerformed
		Character c = customEscapeChar.getText().charAt(0);
		if (!escapeCharsListModel.contains(c)) {
			escapeCharsListModel.addElement(c);
			controller.changed();
		} else {
			NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(
					NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.error.addEscapeChar"),
					NotifyDescriptor.ERROR_MESSAGE);
			DialogDisplayer.getDefault().notifyLater(notifyDescriptor);
		}
    }//GEN-LAST:event_addEButtonActionPerformed

    private void removeEButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEButtonActionPerformed
		int index = defaultEscapeChar.getSelectedIndex();
		if (index > 0) {
			escapeCharsListModel.remove(index);
			controller.changed();
		} else {
			NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(
					NbBundle.getMessage(CSVEditorPanel.class, "CSVEditorPanel.error.removeEscapeChar"),
					NotifyDescriptor.ERROR_MESSAGE);
			DialogDisplayer.getDefault().notifyLater(notifyDescriptor);
		}
    }//GEN-LAST:event_removeEButtonActionPerformed

	void load() {
		// Example:        
		// someCheckBox.setSelected(Preferences.userNodeForPackage(CSVEditorPanel.class).getBoolean("someFlag", false));
		// or for org.openide.util with API spec. version >= 7.4:
		// someCheckBox.setSelected(NbPreferences.forModule(CSVEditorPanel.class).getBoolean("someFlag", false));
		// or:
		// someTextField.setText(SomeSystemOption.getDefault().getSomeStringProperty());
		separatorsListModel.removeAllElements();
		separatorsListModel.addElement(',');
		separatorsListModel.addElement(';');
		int count = OptionsUtils.readCustomSeparatorCount();
		if (count > 0) {
			List<Character> chars = OptionsUtils.readCustomSeparators(count);
			for (Character c : chars)
				separatorsListModel.addElement(c);
		}
		defaultSeparator.setSelectedValue(OptionsUtils.readDefaultSeparator(), true);

		escapeCharsListModel.removeAllElements();
		escapeCharsListModel.addElement('"');
		count = OptionsUtils.readCustomEscapeCharCount();
		if (count > 0) {
			List<Character> chars = OptionsUtils.readCustomEscapeChars(count);
			for (Character c : chars)
				escapeCharsListModel.addElement(c);
		}
		defaultEscapeChar.setSelectedValue(OptionsUtils.readDefaultEscapeChar(), true);

		//Clean up text fields
		customSeparator.setText(null);
		customEscapeChar.setText(null);
	}

	void store() {
		// Example:
		// Preferences.userNodeForPackage(CSVEditorPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
		// or for org.openide.util with API spec. version >= 7.4:
		// NbPreferences.forModule(CSVEditorPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
		// or:
		// SomeSystemOption.getDefault().setSomeStringProperty(someTextField.getText());
		int count = separatorsListModel.size() - 2;
		OptionsUtils.saveCustomSeparatorCount(count);
		if (count > 0) {
			Character[] chars = new Character[count];
			for (int i = 0; i < chars.length; i++) {
				chars[i] = (Character) separatorsListModel.get(i + 2);
			}
			OptionsUtils.saveCustomSeparators(chars);
		}
		OptionsUtils.saveDefaultSeparator((Character) defaultSeparator.getSelectedValue());

		count = escapeCharsListModel.size() - 1;
		OptionsUtils.saveCustomEscapeCharCount(count);
		if (count > 0) {
			Character[] chars = new Character[count];
			for (int i = 0; i < chars.length; i++) {
				chars[i] = (Character) escapeCharsListModel.get(i + 1);
			}
			OptionsUtils.saveCustomEscapeChars(chars);
		}
		OptionsUtils.saveDefaultEscapeChar((Character) defaultEscapeChar.getSelectedValue());
	}

	boolean valid() {
		return defaultSeparator.getSelectedIndex() != -1 && defaultEscapeChar.getSelectedIndex() != -1;
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEButton;
    private javax.swing.JButton addSButton;
    private javax.swing.JTextField customEscapeChar;
    private javax.swing.JTextField customSeparator;
    private javax.swing.JList defaultEscapeChar;
    private javax.swing.JLabel defaultEscapeCharLabel;
    private javax.swing.JList defaultSeparator;
    private javax.swing.JLabel defaultSeparatorLabel;
    private javax.swing.JPanel escapeCharsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeEButton;
    private javax.swing.JButton removeSButton;
    private javax.swing.JPanel separatorsPanel;
    // End of variables declaration//GEN-END:variables

	private void init() {
		separatorsListModel = new DefaultListModel();
		separatorsListModel.addElement(',');
		separatorsListModel.addElement(';');
		defaultSeparator.setModel(separatorsListModel);
		customSeparator.setDocument(new LimitedPlainDocument(1));
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
				addSButton.setEnabled(!customSeparator.getText().trim().isEmpty());
			}
		});
		defaultSeparator.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				removeSButton.setEnabled(defaultSeparator.getSelectedIndex() != -1);
				controller.changed();
			}
		});
		addSButton.setEnabled(false);

		escapeCharsListModel = new DefaultListModel();
		escapeCharsListModel.addElement('"');
		defaultEscapeChar.setModel(escapeCharsListModel);
		customEscapeChar.setDocument(new LimitedPlainDocument(1));
		customEscapeChar.getDocument().addDocumentListener(new DocumentListener() {
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
				addEButton.setEnabled(!customEscapeChar.getText().trim().isEmpty());
			}
		});
		defaultEscapeChar.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				removeEButton.setEnabled(defaultEscapeChar.getSelectedIndex() != -1);
				controller.changed();
			}
		});
		addEButton.setEnabled(false);
	}
}
