package ma.ac.esi.voyager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClassSelector extends JPanel {
    private JRadioButton firstClassRadioButton;
    private JRadioButton secondClassRadioButton;

    public ClassSelector() {
        setLayout(new GridLayout(2, 1)); // Two radio buttons vertically arranged

        // Create radio buttons
        firstClassRadioButton = new JRadioButton("1ère classe");
        secondClassRadioButton = new JRadioButton("2ème classe");

        // Group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(firstClassRadioButton);
        buttonGroup.add(secondClassRadioButton);

        // Add radio buttons to the panel
        add(firstClassRadioButton);
        add(secondClassRadioButton);
    }

    // Method to get the selected class
    public String getSelectedClass() {
        if (firstClassRadioButton.isSelected()) {
            return firstClassRadioButton.getText();
        } else if (secondClassRadioButton.isSelected()) {
            return secondClassRadioButton.getText();
        } else {
            return ""; // No class selected
        }
    }
}
