package ma.ac.esi.admin;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class CardEditManager {
	private DatabaseConnection dbConnection;

    public CardEditManager() {
        dbConnection = new DatabaseConnection();
    }

    public void editCard(int cardId, Date newValidityPeriod) {
        String sql = "UPDATE discountCards SET validityPeriod = ? WHERE cardId = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	  java.sql.Date sqlValidityPeriod = new java.sql.Date(newValidityPeriod.getTime());
              pstmt.setDate(1, sqlValidityPeriod);
        	
            pstmt.setInt(2, cardId);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "La période de validité de la carte a été mise à jour avec succès.");
            } else {
                JOptionPane.showMessageDialog(null, "Aucune carte trouvée avec cet ID.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour de la carte: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
