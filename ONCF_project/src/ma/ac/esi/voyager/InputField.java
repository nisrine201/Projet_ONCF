package ma.ac.esi.voyager;

import javax.swing.*;
import java.awt.*;

public class InputField extends JPanel {
    private JLabel label;
    private JTextField textField;

    public InputField(String labelText) {
        // Set layout to FlowLayout with left alignment
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create and add label
        label = new JLabel(labelText);
        add(label);

        // Create and add text field
        textField = new JTextField(20); // Set preferred width
        add(textField);
    }

    // Method to get the text from the text field
    public String getText() {
        return textField.getText();
    }

    // Method to set text to the text field
    public void setText(String text) {
        textField.setText(text);
    }
}
