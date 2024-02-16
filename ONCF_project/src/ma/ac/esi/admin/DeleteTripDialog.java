package ma.ac.esi.admin;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTripDialog {
    
    public static void deleteTrip(JFrame owner, int tripId) {
        int confirmation = JOptionPane.showConfirmDialog(owner, "Êtes-vous sûr de vouloir supprimer le trajet ID " + tripId + " ?", "Supprimer un trajet", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM journeys WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, tripId);
                //
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(owner, "Trajet supprimé avec succès.");
                } else {
                    JOptionPane.showMessageDialog(owner, "Aucun trajet trouvé avec cet ID.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(owner, "Erreur lors de la suppression du trajet.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
