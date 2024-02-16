package ma.ac.esi.voyager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AvailableTripsFrame extends JFrame {
    private JTable table;
    private String selectedJourneyId;

    public AvailableTripsFrame(List<Journey> matchingTrips) {
        setTitle("Available Trips");
        setSize(600, 400);
        setLocationRelativeTo(null);

        Object[][] data = new Object[matchingTrips.size()][9];
        for (int i = 0; i < matchingTrips.size(); i++) {
            Journey journey = matchingTrips.get(i);
            data[i] = new Object[]{journey.getId(), journey.getOrigin(), journey.getDestination(),
                    journey.getDepartureDate(), journey.getDepartureTime(), journey.getArrivalTime(),
                    journey.getSeatClass(), journey.getFare(), journey.getSeatsLeft()};
        }

        String[] columnNames = {"ID", "Origin", "Destination", "Departure Date", "Departure Time", "Arrival Time", "Class", "Fare", "Seats Left"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add a listener to detect clicks on the trips
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the selected journey ID from the table
                        selectedJourneyId = table.getValueAt(selectedRow, 0).toString(); // Convert to String if necessary
                        Journey selectedJourney = matchingTrips.get(selectedRow);
                        PaymentOptionsFrame paymentOptionsFrame = new PaymentOptionsFrame(selectedJourney, selectedJourneyId);
                        paymentOptionsFrame.setVisible(true);
                    }
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


	public String getSelectedJourneyId() {
        return selectedJourneyId;
    }



	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            List<Journey> matchingTrips = new ArrayList<>(); 
	            AvailableTripsFrame window = new AvailableTripsFrame(matchingTrips);
	            window.setVisible(true);
	        }
	    });
	}
}

