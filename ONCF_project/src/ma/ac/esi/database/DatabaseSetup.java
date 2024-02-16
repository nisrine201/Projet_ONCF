package ma.ac.esi.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Create the Journeys table
            createJourneysTable(connection);

            // Create the DiscountCards table
            createDiscountCardsTable(connection);

            // Create the Transactions table
            createTransactionsTable(connection);

            // Create the Admins table
            createAdminsTable(connection);

            System.out.println("Database setup completed successfully!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database");
            e.printStackTrace();
        }
    }
    private static void createJourneysTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Journeys (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "origin VARCHAR(255) NOT NULL," +
                "destination VARCHAR(255) NOT NULL," +
                "departureDate DATE NOT NULL," +
                "departureTime VARCHAR(255) NOT NULL," +
                "arrivalTime VARCHAR(255) NOT NULL," +
                "class INT NOT NULL," +
                "fare DOUBLE NOT NULL," +
                "seatsLeft INT NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private static void createDiscountCardsTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS DiscountCards (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "cardId VARCHAR(255) NOT NULL," +
                "cardType VARCHAR(255) NOT NULL," +
                "discountPercentage DOUBLE NOT NULL," +
                "validityPeriod DATE NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private static void createTransactionsTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "customerId VARCHAR(255) NOT NULL," +
                "journeyId VARCHAR(255) NOT NULL," +
                "timestamp VARCHAR(255) NOT NULL," +
                "amount DOUBLE NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
    
    private static void createAdminsTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Admins (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "login VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
    
}
