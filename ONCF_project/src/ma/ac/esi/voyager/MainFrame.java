package ma.ac.esi.voyager;

import javax.swing.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private SearchPanel searchPanel;
    private ClassSelector classSelector;

    public MainFrame() {
        // Set the title of the window
        setTitle("ONCF Ticket Booking System");

        // Initialize the SearchPanel
        searchPanel = new SearchPanel();

        // Initialize the ClassSelector
        //classSelector = new ClassSelector();

        // Create a layout manager for the main frame
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Set automatic gap creation and alignment
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create horizontal group
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(searchPanel));
                //.addComponent(classSelector));
        layout.setHorizontalGroup(hGroup);

        // Create vertical group
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(searchPanel));
        /*vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(classSelector));*/
        layout.setVerticalGroup(vGroup);

        // Pack the frame to fit the preferred sizes of its components
        pack();

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the window visible
        setVisible(true);
    }
    
    
    

       

        private void setupLayout() {
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            // Création et définition du layout horizontal
            GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
            hGroup.addGroup(layout.createParallelGroup()
                    .addComponent(searchPanel));
                    // .addComponent(classSelector)); // Ajoutez ceci si vous utilisez classSelector
            layout.setHorizontalGroup(hGroup);

            // Création et définition du layout vertical
            GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
            vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(searchPanel));
                    // .addComponent(classSelector)); // Ajoutez ceci si vous utilisez classSelector
            layout.setVerticalGroup(vGroup);
        }

  
    
    
    

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

