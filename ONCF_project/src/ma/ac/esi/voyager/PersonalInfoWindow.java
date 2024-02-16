package ma.ac.esi.voyager;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PersonalInfoWindow extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField creditCardNumberField;
    private String journeyId;
    private String cardId;
    private String cardType;
    private DatabaseConnection dbConnection;

    public PersonalInfoWindow(String journeyId, String cardId, String cardType) {
        this.journeyId = journeyId;
        this.cardId = cardId;
        this.cardType = cardType;

        setTitle("Enter Personal Information");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel firstNameLabel = new JLabel("First Name:");
        panel.add(firstNameLabel);
        firstNameField = new JTextField();
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        panel.add(lastNameLabel);
        lastNameField = new JTextField();
        panel.add(lastNameField);

        JLabel creditCardNumberLabel = new JLabel("Credit Card Number:");
        panel.add(creditCardNumberLabel);
        creditCardNumberField = new JTextField();
        panel.add(creditCardNumberField);

        JButton buyTicketButton = new JButton("Buy Ticket");
        buyTicketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buyTicket(journeyId); // Pass journeyId to buyTicket method
            }
        });

        panel.add(buyTicketButton);

        getContentPane().add(panel);
        setLocationRelativeTo(null);
        dbConnection = new DatabaseConnection(); // Initialize DatabaseConnection
    }

    private void buyTicket(String journeyId) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String creditCardNumber = creditCardNumberField.getText();

        // Calculate total price
        double totalPrice = calculateTotalPrice();

        // Insert transaction into the database
        if (insertTransaction(firstName, lastName, creditCardNumber, totalPrice)) {
            String ticketSummary = "Ticket Summary:\n";
            ticketSummary += "Name: " + firstName + " " + lastName + "\n";
            ticketSummary += "Journey ID: " + journeyId + "\n";
            ticketSummary += "Total Price: dh" + totalPrice + "\n";

            JOptionPane.showMessageDialog(null, ticketSummary);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to buy ticket. Please try again.");
        }
    }

    private double calculateTotalPrice() {
        double price = 0.0;

        // Retrieve journey details
        try {
        	 Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT fare FROM journeys WHERE id = ?");
             statement.setString(1, journeyId);
             ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                price = resultSet.getDouble("fare");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Apply discount if applicable
        if (cardId != null && cardType != null) {
            double discountPercentage = getDiscountPercentage();
            price *= (1 - discountPercentage / 100);
        }

        return price;
    }

    private double getDiscountPercentage() {
        double discountPercentage = 0.0;

        try {
        	   Connection connection = dbConnection.getConnection();
               PreparedStatement statement = connection.prepareStatement("SELECT discountPercentage FROM discountcards WHERE id = ? AND cardType = ?");
               statement.setString(1, cardId);
               statement.setString(2, cardType);
               ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                discountPercentage = resultSet.getDouble("discountPercentage");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discountPercentage;
    }

    private boolean insertTransaction(String firstName, String lastName, String creditCardNumber, double totalPrice) {
        try {
        	  Connection connection = dbConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement("INSERT INTO transactions (customerId, journeyId, timestamp, amount) VALUES (?, ?, NOW(), ?)");
              statement.setString(1, firstName + " " + lastName);
              statement.setString(2, journeyId);
              statement.setDouble(3, totalPrice);
              int rowsInserted = statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
