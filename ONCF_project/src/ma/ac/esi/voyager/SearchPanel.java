package ma.ac.esi.voyager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SearchPanel extends JPanel {
	private JComboBox<String> departComboBox;
    private JComboBox<String> arriveeComboBox; 
    private JTextField dateField;
    private JComboBox<Integer> classComboBox;
    private JComboBox<Integer> passengersComboBox;
    private JButton searchButton;
    private TripDatabase tripDatabase; 

    public SearchPanel() {
        setLayout(new GridLayout(0, 2, 10, 10)); // Grid layout with 2 columns and gaps

        // Initialize tripDatabase
        tripDatabase = new TripDatabase();
        
        // Departure field
        add(new JLabel("Ma gare de départ :"));
        String[] depart = {"Marrakech", "Rabat", "Casablanca"};
        departComboBox = new JComboBox<>(depart);
        add(departComboBox);
        
        // Arrival field
        add(new JLabel("Ma gare d'arrivée :"));
        String[] arrivee = {"Marrakech", "Rabat", "Casablanca"};
        arriveeComboBox = new JComboBox<>(arrivee);
        add(arriveeComboBox);
        
        // Date field
        add(new JLabel("Date de départ :"));
        dateField = new JTextField("yyyy-mm-dd");
        add(dateField);

        // Class selection
        add(new JLabel("Classe :"));
        Integer[] classes = {1,2};
        classComboBox = new JComboBox<>(classes);
        add(classComboBox);

        // Passengers selection
        add(new JLabel("Nombre de passagers :"));
        Integer[] passengers = {1, 2, 3, 4, 5};
        passengersComboBox = new JComboBox<>(passengers);
        add(passengersComboBox);

        // Search button
        searchButton = new JButton("Rechercher");
        add(searchButton);
        searchButton.addActionListener(e -> {
            // Retrieve the user input from the search panel
            String departure = getDeparture();
            String arrival = getArrival();
            java.sql.Date date = getDate();
            int selectedClass = getClassSelection();
            int passengerCount = getPassengerCount();

            // Perform the database query to retrieve matching trips
            List<Journey> matchingTrips = tripDatabase.getMatchingTrips(departure, arrival, date, selectedClass, passengerCount);

            // Once you have the matching trips, display them in a frame or dialog
            if (matchingTrips != null) {
                AvailableTripsFrame availableTripsFrame = new AvailableTripsFrame(matchingTrips);
                availableTripsFrame.setVisible(true);
            } else {
                // Handle the case where no matching trips were found
                JOptionPane.showMessageDialog(SearchPanel.this, "No matching trips found.", "No Trips", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    // Method to get the departure station
    public String getDeparture() {
        return (String) departComboBox.getSelectedItem();
    }

    // Method to get the arrival station
    public String getArrival() {
        return (String) arriveeComboBox.getSelectedItem();
    }

    // Method to get the date
    public Date getDate() {
        String dateString = dateField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = dateFormat.parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace(); // Handle or log the parsing exception
            return null; // Return null if parsing fails
        }
    }

    // Method to get the selected class
    public Integer getClassSelection() {
        return (Integer) classComboBox.getSelectedItem();
    }

    // Method to get the selected number of passengers
    public int getPassengerCount() {
        return (int) passengersComboBox.getSelectedItem();
    }

    // Method to add action listener to the search button
    public void addSearchListener(ActionListener listener) {
    	searchButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        // Retrieve the user input from the search panel
    	        String departure = getDeparture();
    	        String arrival = getArrival();
    	        java.sql.Date date = getDate();
    	        int selectedClass = getClassSelection();
    	        int passengerCount = getPassengerCount();

    	        // Perform the database query to retrieve matching trips
    	        List<Journey> matchingTrips = tripDatabase.getMatchingTrips(departure, arrival, date, selectedClass, passengerCount);

    	        // Once you have the matching trips, display them in a frame or dialog
    	        AvailableTripsFrame availableTripsFrame = new AvailableTripsFrame(matchingTrips);
    	        availableTripsFrame.setVisible(true);
    	    }
    	});

    }
}
