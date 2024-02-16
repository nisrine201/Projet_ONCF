package ma.ac.esi.admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    public ButtonPanel(String[] names) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS alignment
        for (String name : names) {
            JButton button = new JButton(name);
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button horizontally
            button.setAlignmentY(Component.CENTER_ALIGNMENT); // Center align button vertically
            button.setMaximumSize(new Dimension(200, 50)); // Set maximum size
            button.setMinimumSize(new Dimension(200, 50)); // Set minimum size
            button.setPreferredSize(new Dimension(200, 50)); // Set preferred size
            add(button);
            add(Box.createVerticalStrut(10)); // Add some vertical spacing between buttons
        }
    }

    public ButtonPanel(String[] names, int lin, int col) {
        this(names);
        // Not setting layout here as it's already set in the constructor above
    }

    public void setActionListener(ActionListener al) {
        Component[] cps = getComponents();
        for (Component cp : cps) {
            if (cp instanceof JButton) {
                JButton b = (JButton) cp;
                b.addActionListener(al);
            }
        }
    }
}

