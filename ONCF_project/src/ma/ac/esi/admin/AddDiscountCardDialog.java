package ma.ac.esi.admin;
import javax.swing.*;
import ma.ac.esi.database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddDiscountCardDialog extends JDialog {
    private JTextField idField, cardTypeField, discountPercentageField, validityPeriodField;
    private JButton saveButton;

    public AddDiscountCardDialog(Frame owner) {
        super(owner, "Ajouter une nouvelle carte de réduction", true);
        initUI();
        setupActions();
        pack();
        setLocationRelativeTo(owner);
    }
    
    private void initUI() {
        setLayout(new GridLayout(0, 2)); 
        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Type de carte:"));
        cardTypeField = new JTextField();
        add(cardTypeField);

        add(new JLabel("Pourcentage de réduction (%):"));
        discountPercentageField = new JTextField();
        add(discountPercentageField);

        add(new JLabel("Période de validité (jours):"));
        validityPeriodField = new JTextField();
        add(validityPeriodField);

        saveButton = new JButton("Enregistrer");
        add(new JLabel()); // Pour l'alignement
        add(saveButton);
    }

    private void setupActions() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDiscountCard();
            }
        });
    }

    private void saveDiscountCard() {
        String sql = "INSERT INTO discount_cards (id, cardType, discountPercentage, validityPeriod) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); // Assurez-vous que cette méthode existe et est correcte
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(idField.getText()));
            pstmt.setString(2, cardTypeField.getText());
            pstmt.setFloat(3, Float.parseFloat(discountPercentageField.getText()));
            pstmt.setInt(4, Integer.parseInt(validityPeriodField.getText()));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Carte de réduction ajoutée avec succès.");
            dispose(); // Ferme la boîte de dialogue
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la carte de réduction.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erreur de format dans l'un des champs.", "Erreur de format", JOptionPane.ERROR_MESSAGE);
        }
        String cardType = cardTypeField.getText();
        float discountPercentage;
        int validityPeriod;
        try {
            discountPercentage = Float.parseFloat(discountPercentageField.getText());
            validityPeriod = Integer.parseInt(validityPeriodField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur de format dans le pourcentage de réduction ou la période de validité.", "Erreur de format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql1 = "INSERT INTO discount_cards (cardType, discountPercentage, validityPeriod) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            // pstmt.setInt(1, id); Supposons que l'ID est auto-incrémenté dans votre base de données
            pstmt.setString(1, cardType);
            pstmt.setFloat(2, discountPercentage);
            pstmt.setInt(3, validityPeriod);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Carte de réduction ajoutée avec succès.");
            dispose(); // Ferme la boîte de dialogue
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la carte de réduction.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
