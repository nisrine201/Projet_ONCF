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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthenticationDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    

    public AuthenticationDialog(JFrame parent) {
        super(parent, "Authentification", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Nom d'administrateur:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Se connecter");
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check authentication
                authenticate();
            }
        });
        add(new JLabel()); // Pour l'alignement
        add(loginButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    private void authenticate() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Query to check if the username and password exist in the admin table
        String sql = "SELECT * FROM Admins WHERE login = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // If there is a record, authentication is successful
            if (rs.next()) {
                System.out.println("Authentication successful for username: " + username);
                dispose(); // Close the authentication dialog
                openAdminWindow(); // Open the admin window
            } else {
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect.", "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'authentification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAdminWindow() {
        JFrame adminFrame = new JFrame("Espace Administrateur");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 1)); // Correction pour s'aligner sur le nombre de boutons
        String[] adminButtons = {"Gestion des trajets", "Rapport", "Gestion de cartes de réduction"};

        for (String buttonLabel : adminButtons) {
            JButton button = new JButton(buttonLabel);
            if ("Gestion des trajets".equals(buttonLabel)) {
                button.addActionListener(e -> openTripManagementWindow());
            } else if ("Rapport".equals(buttonLabel)) {
            	button.addActionListener(e -> openRapportManagementWindow());;
            } else if ("Gestion de cartes de réduction".equals(buttonLabel)) {
                button.addActionListener(e -> openCardManagementWindow());
            }
            panel.add(button);
        }

        adminFrame.add(panel);
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null); // Centre la fenêtre
        adminFrame.setVisible(true);
    }
    private void openTripManagementWindow() {
        JFrame tripFrame = new JFrame("Gestion des trajets");
        tripFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(4, 1));

        String[] tripButtons = {"Ajouter", "Modifier", "Supprimer"};
        for (String buttonLabel : tripButtons) {
            JButton button = new JButton(buttonLabel);
            if ("Ajouter".equals(buttonLabel)) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Action pour ouvrir la boîte de dialogue d'ajout de trajet
                        AddTripDialog addDialog = new AddTripDialog(tripFrame); // Utilise tripFrame comme parent
                        addDialog.setVisible(true);
                    }
                });
            } else if ("Modifier".equals(buttonLabel)) {
                button.addActionListener(e -> {
                    String tripIdStr = JOptionPane.showInputDialog(tripFrame, "Entrez l'ID du trajet à modifier:");
                    if (tripIdStr != null && !tripIdStr.isEmpty()) {
                        try {
                            int tripId = Integer.parseInt(tripIdStr);
                            EditTripDialog editDialog = new EditTripDialog(tripFrame, tripId);
                            editDialog.setVisible(true);
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(tripFrame, "ID invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            } else if ("Supprimer".equals(buttonLabel)) {
                button.addActionListener(e -> {
                    String tripIdStr = JOptionPane.showInputDialog(tripFrame, "Entrez l'ID du trajet à supprimer:");
                    if (tripIdStr != null && !tripIdStr.isEmpty()) {
                        try {
                            int tripId = Integer.parseInt(tripIdStr);
                            DeleteTripDialog.deleteTrip(tripFrame, tripId);
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(tripFrame, "ID invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }

            panel.add(button);
        }

        tripFrame.add(panel);
        tripFrame.pack();
        tripFrame.setLocationRelativeTo(null); // Centre la fenêtre
        tripFrame.setVisible(true);
    }
    private void openCardManagementWindow() {
        JFrame cardFrame = new JFrame("Gestion des cartes de réduction");
        cardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 1)); // Pour "Ajouter", "Modifier", et "Afficher"
        CardManager cardManager = new CardManager(); // Assurez-vous que CardManager est bien défini ailleurs dans votre package

        JButton addButton = new JButton("Ajouter une nouvelle carte de réduction");
        addButton.addActionListener(e -> {
            String cardId = JOptionPane.showInputDialog(cardFrame, "Entrez l'id de lacarte:");
            String cardType = JOptionPane.showInputDialog(cardFrame, "Entrez le type de carte:");
            String discountPercentageStr = JOptionPane.showInputDialog(cardFrame, "Entrez le pourcentage de réduction:");
            String validityPeriodStr = JOptionPane.showInputDialog(cardFrame, "Entrez la période de validité (yyyy-mm-jj):");
            
            try {
                double discountPercentage = Double.parseDouble(discountPercentageStr);
                Date validityPeriod = new SimpleDateFormat("yyyy-MM-dd").parse(validityPeriodStr);
                cardManager.addCard(cardId, cardType, discountPercentage, validityPeriod); // Utilise cardManager pour ajouter une nouvelle carte
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cardFrame, "Erreur de format dans les entrées", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException e1) {
                e1.printStackTrace(); // Print the stack trace for debugging purposes
                JOptionPane.showMessageDialog(cardFrame, "Erreur lors de la conversion de la période de validité", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        });
        JButton modifyButton = new JButton("Modifier la période de validité");
        modifyButton.addActionListener(e -> {
            String cardIdStr = JOptionPane.showInputDialog(cardFrame, "Entrez l'ID de la carte à modifier:");
            String newValidityPeriodStr = JOptionPane.showInputDialog(cardFrame, "Entrez la nouvelle période de validité (yyyy-MM-dd):"); // Prompt user for the new validity period in the specified format
            try {
                int cardId = Integer.parseInt(cardIdStr);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newValidityPeriod = dateFormat.parse(newValidityPeriodStr); // Parse the string into a Date object
                new CardEditManager().editCard(cardId, newValidityPeriod); // Utilize CardEditManager to modify the card
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cardFrame, "Erreur de format dans l'ID", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(cardFrame, "Erreur lors de la conversion de la période de validité", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

       
        //////////////
        JButton deleteButton = new JButton("Supprimer une carte");
        deleteButton.addActionListener(e -> {
            String cardIdStr = JOptionPane.showInputDialog(cardFrame, "Entrez l'ID de la carte à supprimer:");
            try {
                int cardId = Integer.parseInt(cardIdStr);
                cardManager.deleteCard(cardId); // Utilise cardManager pour supprimer une carte
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cardFrame, "Erreur de format dans l'ID", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
      

        cardFrame.add(panel);
        cardFrame.pack();
        cardFrame.setLocationRelativeTo(null);
        cardFrame.setVisible(true);
    }
    

    //////////////////////////////////////////////////////////////////////
    private void openRapportManagementWindow() {
        JFrame rapportFrame = new JFrame("Rapport");
        rapportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rapportFrame.setSize(600, 400);
        rapportFrame.setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea(); // Utilisez un JTextArea pour afficher le contenu du rapport
        JScrollPane scrollPane = new JScrollPane(textArea); // Ajoutez une barre de défilement si nécessaire
        rapportFrame.add(scrollPane, BorderLayout.CENTER);

        // Créer le bouton pour générer le rapport
        JButton generateReportButton = new JButton("Générer Rapport");
        rapportFrame.add(generateReportButton, BorderLayout.SOUTH);

        // Ajouter un action listener au bouton pour appeler generateRapport() sur l'action
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RapportManager rapportManager = new RapportManager();
                String reportContent = rapportManager.generateRapport();
                textArea.setText(reportContent); // Mettez à jour le contenu du JTextArea avec le rapport généré
            }
        });

        rapportFrame.setVisible(true);
    }



}

