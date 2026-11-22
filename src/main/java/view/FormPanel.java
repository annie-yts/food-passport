package view;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that lays out labelled text fields in rows, with the labels in one
 * column and the fields in another, so the fields always line up.
 */
class FormPanel extends JPanel {

    private int rows;

    FormPanel() {
        super(new GridBagLayout());
    }

    /**
     * Adds one row to the form.
     * @param label the text shown to the left of the field
     * @param field the text field
     */
    void addRow(String label, JTextField field) {
        final GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = rows;
        labelConstraints.anchor = GridBagConstraints.LINE_END;
        labelConstraints.insets = new Insets(5, 5, 5, 5);
        this.add(new JLabel(label), labelConstraints);

        final GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = rows;
        fieldConstraints.anchor = GridBagConstraints.LINE_START;
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        this.add(field, fieldConstraints);

        rows++;
    }
}