package ma.ac.esi.admin;

import javax.swing.*;

import ma.ac.esi.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class RapportManager {
        private DatabaseConnection dbConnection;

        public RapportManager() {
            this.dbConnection = new DatabaseConnection();
        }

        public String generateRapport() {
            StringBuilder reportContent = new StringBuilder("Rapports des activités:\n\n");

            try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, customerId, journeyId, timestamp, amount FROM transactions")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String customerId = rs.getString("customerId");
                int journeyId = rs.getInt("journeyId");
                Date timestamp = rs.getDate("timestamp");
                double amount = rs.getDouble("amount");

                reportContent.append("ID: ").append(id).append(", Customer ID: ").append(customerId).append(", Journey ID: ").append(journeyId).append(", Timestamp: ").append(timestamp).append(", Amount: ").append(amount).append("\n");
            }

            if (reportContent.toString().equals("Rapports des activités:\n\n")) {
                reportContent.append("Aucune transaction trouvée.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            reportContent.append("Erreur lors de la génération du rapport: ").append(e.getMessage());
        }

        return reportContent.toString();
    }
}
