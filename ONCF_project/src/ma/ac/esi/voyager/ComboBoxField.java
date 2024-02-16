package ma.ac.esi.voyager;
import javax.swing.*;
import java.awt.*;

public class ComboBoxField extends JPanel {
    private JLabel label;
    private JComboBox<String> comboBox;

    public ComboBoxField(String labelText, String[] options) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        label = new JLabel(labelText);
        add(label);

        comboBox = new JComboBox<>(options);
        add(comboBox);
    }

    // Method to get the selected item from the combo box
    public String getSelectedOption() {
        return (String) comboBox.getSelectedItem();
    }

    // Method to set the selected item in the combo box
    public void setSelectedOption(String option) {
        comboBox.setSelectedItem(option);
    }
}
