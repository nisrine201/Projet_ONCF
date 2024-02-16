package main;

import javax.swing.*;
import java.awt.*;

public class MainApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApplication::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color of the frame to red
        frame.getContentPane().setBackground(Color.RED);
        frame.setBackground(Color.RED);

        // Add large title
        JLabel titleLabel = new JLabel("ONCF VOYAGES", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 60)); // Set font and size
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add empty border for space
        frame.add(titleLabel, BorderLayout.NORTH); // Add title label to the top of the frame

        String[] mainButtons = {"Voyageur", "Administrateur"};
        ButtonPanel mainPanel = new ButtonPanel(mainButtons, 1, 2);
        mainPanel.setActionListener(new ButtonActionListener(frame));
        mainPanel.setBackground(Color.RED);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(700, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
