package ma.ac.esi.admin;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTripDialog extends JDialog {
    private JTextField originField, destinationField, departureTimeField, arrivalTimeField, classField, fareField, seatsLeftField;
    private JFormattedTextField departureDateField;
    private JButton saveButton;

    public AddTripDialog(Frame owner) {
        super(owner, "Ajouter un trajet", true);
        initUI();
        setupActions();
        pack();
        setLocationRelativeTo(owner);
    }//comment

    private void initUI() {
        setLayout(new GridLayout(0, 2)); // 0 pour n'importe quel nombre de lignes, 2 colonnes
        
        add(new JLabel("Origine:"));
        originField = new JTextField();
        add(originField);
        
        add(new JLabel("Destination:"));
        destinationField = new JTextField();
        add(destinationField);
     
        add(new JLabel("Date de départ (yyyy-MM-dd):"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        departureDateField = new JFormattedTextField(dateFormat);
        add(departureDateField);
        
        add(new JLabel("Heure de départ:"));
        departureTimeField = new JTextField();
        add(departureTimeField);
        
        add(new JLabel("Heure d'arrivée:"));
        arrivalTimeField = new JTextField();
        add(arrivalTimeField);
        
        add(new JLabel("Classe:"));
        classField = new JTextField();
        add(classField);
        
        add(new JLabel("Nombre de places restantes:"));
        seatsLeftField = new JTextField();
        add(seatsLeftField);
        
        add(new JLabel("Tarif:"));
        fareField = new JTextField();
        add(fareField);
        
        saveButton = new JButton("Enregistrer");
        add(saveButton);
    }

    private void setupActions() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTrip();
            }
        });
    }

private void saveTrip() {
    String sql = "INSERT INTO journeys (origin, destination, departureDate, departureTime, arrivalTime, seatClass, fare, seatsLeft) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, originField.getText());
        pstmt.setString(2, destinationField.getText());
        
        // Get the date from the formatted text field
        Object value = departureDateField.getValue();
        if (value instanceof Date) {
            pstmt.setDate(3, new java.sql.Date(((Date) value).getTime()));
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une date valide.", "Erreur de format", JOptionPane.ERROR_MESSAGE);
            return; // Return if the date is not valid
        }
        
        pstmt.setString(4, departureTimeField.getText());
        pstmt.setString(5, arrivalTimeField.getText());
        pstmt.setInt(6, Integer.parseInt(classField.getText()));
        pstmt.setDouble(7, Double.parseDouble(fareField.getText()));
        pstmt.setInt(8, Integer.parseInt(seatsLeftField.getText()));

        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Trajet ajouté avec succès.");
        dispose(); // Fermer la fenêtre après l'ajout
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du trajet.", "Erreur", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Veuillez vérifier que le nombre de places et le tarif sont corrects.", "Erreur de format", JOptionPane.ERROR_MESSAGE);
    }
}
}
