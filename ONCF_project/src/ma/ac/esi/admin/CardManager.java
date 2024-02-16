package ma.ac.esi.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Connection;
import javax.swing.JOptionPane;

import ma.ac.esi.database.DatabaseConnection;

public class CardManager {
    
	private DatabaseConnection dbConnection;

    public CardManager() {
        dbConnection = new DatabaseConnection();
    }

    public void addCard(String cardId, String cardType, double discountPercentage, Date validityPeriod) {
        String sql = "INSERT INTO discountCards (cardId, cardType, discountPercentage, validityPeriod) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, cardId);
            pstmt.setString(2, cardType);
            pstmt.setDouble(3, discountPercentage);
            java.sql.Date sqlValidityPeriod = new java.sql.Date(validityPeriod.getTime());
            pstmt.setDate(4, sqlValidityPeriod);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Une nouvelle carte de réduction a été ajoutée avec succès.");
            } else {
                System.out.println("Aucune carte de réduction n'a été ajoutée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la carte de réduction : " + e.getMessage());
        }
    }
    

    public void deleteCard(int cardId) {
        String sql = "DELETE FROM discountCards WHERE cardId = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "La carte a été supprimée avec succès.");
            } else {
                JOptionPane.showMessageDialog(null, "Aucune carte trouvée avec cet ID.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la carte: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Main pour tester
    public static void main(String[] args) {
        CardManager manager = new CardManager();
    }

}
