

import javax.swing.*;
import Gui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        // Always start Swing apps on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to porcessing!");
            }

            // Start application with Login screen
            new LoginFrame();
        });
    }
}

