package view;
import javax.swing.*;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: gives every label the same width so the text fields line up.
 */

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {

    /** Wide enough for the longest label ("Confirm Password"). */
    private static final int LABEL_WIDTH = 130;

    LabelTextPanel(JLabel label, JTextField textField) {
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, label.getPreferredSize().height));
        this.add(label);
        this.add(textField);
    }
}