package ma.ac.esi.voyager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JourneyDAO {
    private Connection connection;

    public JourneyDAO(Connection connection) {
        this.connection = connection;
    }

    public void addJourney(Journey journey) throws SQLException {
        String sql = "INSERT INTO Journeys (origin, destination, departureDate, departureTime, arrivalTime, seatClass, fare, seatsLeft) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, journey.getOrigin());
            statement.setString(2, journey.getDestination());
            statement.setDate(3, new java.sql.Date(journey.getDepartureDate().getTime()));
            statement.setString(4, journey.getDepartureTime());
            statement.setString(5, journey.getArrivalTime());
            statement.setInt(6, journey.getSeatClass());
            statement.setDouble(7, journey.getFare());
            statement.setInt(8, journey.getSeatsLeft());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding journey: " + e.getMessage());
            throw e;
        }
    }


    public List<Journey> getAllJourneys() throws SQLException {
        List<Journey> journeys = new ArrayList<>();
        String sql = "SELECT * FROM Journeys";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Journey journey = new Journey();
                journey.setId(resultSet.getInt("id"));
                journey.setOrigin(resultSet.getString("origin"));
                journey.setDestination(resultSet.getString("destination"));
                journey.setDepartureDate(resultSet.getDate("departureDate"));
                journey.setDepartureTime(resultSet.getString("departureTime"));
                journey.setArrivalTime(resultSet.getString("arrivalTime"));
                journey.setSeatClass(resultSet.getInt("seatClass"));
                journey.setFare(resultSet.getDouble("fare"));
                journey.setSeatsLeft(resultSet.getInt("seatsLeft"));
                journeys.add(journey);
            }
        }
        return journeys;
    }

    // Ajoutez des méthodes pour la mise à jour et la suppression des trajets si nécessaire
}
