package ma.ac.esi.admin;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditTripDialog extends JDialog {

    private JTextField originField, destinationField, departureTimeField, arrivalTimeField, classField, fareField, seatsLeftField;
    private JFormattedTextField departureDateField;
    private JButton saveButton;
    private int tripId;
    
    
    public EditTripDialog(Frame owner, int tripId) {
        super(owner, "Modifier un trajet", true);
        this.tripId = tripId;
        initUI();
        loadTripData();
        setupActions();
        pack();
        setLocationRelativeTo(owner);
    }


    private void initUI() {
        setLayout(new GridLayout(0, 2)); // 0 pour n'importe quel nombre de lignes, 2 colonnes
        
        add(new JLabel("Origine:"));
        originField = new JTextField();
        add(originField);
        
        add(new JLabel("Destination:"));
        destinationField = new JTextField();
        add(destinationField);
     
        add(new JLabel("Date de départ:"));
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

    private void loadTripData() {
        // Charger les données du trajet depuis la base de données en utilisant tripId
        String sql = "SELECT origin, destination, departureDate, departureTime, arrivalTime, seatClass, fare, seatsLeft FROM journeys WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tripId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Assumer que les champs existent dans l'interface
                    originField.setText(rs.getString("origin"));
                    destinationField.setText(rs.getString("destination"));
                    java.sql.Date departureDate = rs.getDate("departureDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDepartureDate = dateFormat.format(departureDate);
                    departureDateField.setText(formattedDepartureDate);
                    departureTimeField.setText(rs.getString("departureTime"));
                    arrivalTimeField.setText(rs.getString("arrivalTime"));
                    classField.setText(rs.getString("seatClass"));
                    fareField.setText(String.valueOf(rs.getDouble("fare")));
                    seatsLeftField.setText(String.valueOf(rs.getInt("seatsLeft")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement du trajet.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupActions() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTrip();
            }
        });
    }

    private void updateTrip() {
        // Mettre à jour le trajet dans la base de données
        String sql = "UPDATE journeys SET origin = ?, destination = ?, departureDate = ?, departureTime = ?, arrivalTime = ?, seatClass = ?, fare = ?, seatsLeft = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the parameters of the query
            pstmt.setString(1, originField.getText());
            pstmt.setString(2, destinationField.getText());
            
            // Parse the departure date text into a java.util.Date object
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDepartureDate = dateFormat.parse(departureDateField.getText());
            
            // Convert the java.util.Date to java.sql.Date
            java.sql.Date sqlDepartureDate = new java.sql.Date(utilDepartureDate.getTime());
            
            // Set the departureDate parameter in the PreparedStatement
            pstmt.setDate(3, sqlDepartureDate);
            
            pstmt.setString(4, departureTimeField.getText());
            pstmt.setString(5, arrivalTimeField.getText());
            pstmt.setInt(6, Integer.parseInt(classField.getText()));
            pstmt.setDouble(7, Double.parseDouble(fareField.getText()));
            pstmt.setInt(8, Integer.parseInt(seatsLeftField.getText()));
            pstmt.setInt(9, tripId); // Set the trip ID for the WHERE clause

            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();
            
            // Check if any rows were affected
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Trajet modifié avec succès.");
                dispose(); // Close the dialog after successful update
            } else {
                JOptionPane.showMessageDialog(this, "Aucun trajet n'a été modifié.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du trajet.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez vérifier les données saisies.", "Erreur de format", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la conversion de la date.", "Erreur de conversion", JOptionPane.ERROR_MESSAGE);
        }
    }


}
