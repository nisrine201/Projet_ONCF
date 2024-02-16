package ma.ac.esi.voyager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import database.DatabaseConnection;

public class TripDatabase {
    private Connection connection; // Connection to the database

    public TripDatabase() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Journey> getMatchingTrips(String departure, String arrival, Date date, Integer selectedClass, int passengerCount) {
        List<Journey> matchingTrips = new ArrayList<>();

       
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Journeys WHERE origin = ? AND destination = ? AND departureDate = ? AND seatClass = ? AND seatsLeft >= ?")) {
            pstmt.setString(1, departure);
            pstmt.setString(2, arrival);
            pstmt.setDate(3, date);
            pstmt.setInt(4, selectedClass);
            pstmt.setInt(5, passengerCount);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Journey journey = new Journey();
                journey.setId(rs.getInt("id"));
                journey.setOrigin(rs.getString("origin"));
                journey.setDestination(rs.getString("destination"));
                journey.setDepartureDate(rs.getDate("departureDate"));
                journey.setDepartureTime(rs.getString("departureTime"));
                journey.setArrivalTime(rs.getString("arrivalTime"));
                journey.setSeatClass(rs.getInt("seatClass"));
                journey.setFare(rs.getDouble("fare"));
                journey.setSeatsLeft(rs.getInt("seatsLeft"));

                matchingTrips.add(journey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchingTrips;
    }
}

