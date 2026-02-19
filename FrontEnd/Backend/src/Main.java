

import javax.swing.*;
import Gui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Use the Swing event thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Optional: Set system look & feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Launch login screen
            new LoginFrame();
        });
    }
}
