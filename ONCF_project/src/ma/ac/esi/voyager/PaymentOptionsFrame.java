package ma.ac.esi.voyager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentOptionsFrame extends JFrame {
    private Journey journey;
    private String selectedJourneyId;

    public PaymentOptionsFrame(Journey journey, String selectedJourneyId) {
        this.journey = journey;
        this.selectedJourneyId = selectedJourneyId; 

        setTitle("Payment Options");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("Selected Journey: " + journey.getOrigin() + " to " + journey.getDestination() + " for " + journey.getFare() + " dh"));

        JButton discountPaymentButton = new JButton("Payment with discount card");
        discountPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DiscountCardWindow discountCardWindow = new DiscountCardWindow(selectedJourneyId); // Pass selectedJourneyId
                discountCardWindow.setVisible(true);
                dispose(); // Close the current window
            }
        });

        panel.add(discountPaymentButton);

        JButton regularPaymentButton = new JButton("Payment without card");
        regularPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedJourneyId != null) {
                    PersonalInfoWindow personalInfoWindow = new PersonalInfoWindow(selectedJourneyId, null, null);
                    personalInfoWindow.setVisible(true);
                    dispose(); // Close the current window
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a journey first.");
                }
            }
        });
        panel.add(regularPaymentButton);

        add(panel);
    }

    public void setSelectedJourneyId(String selectedJourneyId) {
        this.selectedJourneyId = selectedJourneyId;
    }
}
