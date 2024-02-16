package ma.ac.esi.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ma.ac.esi.voyager.MainFrame;

public class ButtonActionListener implements ActionListener {
    private JFrame frame;

    public ButtonActionListener(JFrame frame) {
        this.frame = frame;
    }
//comment
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Administrateur":
                // Afficher la fenêtre d'authentification
                AuthenticationDialog authDialog = new AuthenticationDialog(frame);
                authDialog.setVisible(true);
                break;
            case "Voyageur":
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                break;
        }
    }
}
