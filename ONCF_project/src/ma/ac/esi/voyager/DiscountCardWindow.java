package ma.ac.esi.voyager;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DiscountCardWindow extends JFrame {
    private JTextField cardIdField;
    private JTextField cardTypeField;
    private String selectedJourneyId;
    private DatabaseConnection dbConnection;
    
    

    public DiscountCardWindow() throws HeadlessException {
		super();
        dbConnection = new DatabaseConnection(); // Initialize DatabaseConnection
	}

	public DiscountCardWindow(String selectedJourneyId) {
        this.selectedJourneyId = selectedJourneyId; // Set selectedJourneyId

        setTitle("Discount Card Information");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel cardIdLabel = new JLabel("Card ID:");
        panel.add(cardIdLabel);
        cardIdField = new JTextField();
        panel.add(cardIdField);

        JLabel cardTypeLabel = new JLabel("Card Type:");
        panel.add(cardTypeLabel);
        cardTypeField = new JTextField();
        panel.add(cardTypeField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardId = cardIdField.getText();
                String cardType = cardTypeField.getText();

                // Check if the card information exists in the database
                if (checkCardInfo(cardId, cardType)) {
                    dispose(); // Close the current window

                    // Pass selectedJourneyId to PersonalInfoWindow constructor
                    PersonalInfoWindow personalInfoWindow = new PersonalInfoWindow(selectedJourneyId, cardId, cardType);
                    personalInfoWindow.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Card information not found!");
                }
            }
        });
        panel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
            }
        });
        panel.add(cancelButton);

        getContentPane().add(panel);
        setLocationRelativeTo(null);
    }

    private boolean checkCardInfo(String cardId, String cardType) {
        // Replace the placeholders with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/MyDB";
        String username = "root";
        String password = "";

        try {
        	Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM discountCards WHERE cardId = ? AND cardType = ?");
            statement.setString(1, cardId);
            statement.setString(2, cardType);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // True if a matching row is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setSelectedJourneyId(String journeyId) {
        this.selectedJourneyId = journeyId;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DiscountCardWindow window = new DiscountCardWindow();
                window.setVisible(true);
            }
        });
    }
}
