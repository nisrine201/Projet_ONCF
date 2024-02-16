package ma.ac.esi.voyager;

import javax.swing.*;
import java.awt.*;

public class PassengerSelector extends JPanel {
    private JLabel label;
    private JComboBox<Integer> comboBox;

    public PassengerSelector(String labelText) {
        // Set layout to FlowLayout with left alignment
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create and add label
        label = new JLabel(labelText);
        add(label);

        // Create and add combo box for selecting number of passengers
        Integer[] passengerOptions = {1, 2, 3, 4, 5}; // Example options
        comboBox = new JComboBox<>(passengerOptions);
        add(comboBox);
    }

    // Method to get the selected number of passengers
    public int getSelectedPassengers() {
        return (int) comboBox.getSelectedItem();
    }

    // Method to set the selected number of passengers
    public void setSelectedPassengers(int passengers) {
        comboBox.setSelectedItem(passengers);
    }
}
