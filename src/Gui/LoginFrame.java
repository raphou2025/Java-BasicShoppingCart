

package Gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("CyberTech - Login");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // LEFT: Login Form Panel
        LoginFormPanel loginFormPanel = new LoginFormPanel(this);
        loginFormPanel.setPreferredSize(new Dimension(400, 500));

        // RIGHT: Decorative Side Panel
        LoginSidePanel sidePanel = new LoginSidePanel();

        add(loginFormPanel, BorderLayout.WEST);
        add(sidePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new LoginFrame();
        });
    }
}