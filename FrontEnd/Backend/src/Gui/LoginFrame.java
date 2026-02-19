package Gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Shopping Cart - Login");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // LEFT: login form
        LoginFormPanel loginFormPanel = new LoginFormPanel(this);
        loginFormPanel.setPreferredSize(new Dimension(400, 500));

        // RIGHT: side panel
        LoginSidePanel sidePanel = new LoginSidePanel();

        mainPanel.add(loginFormPanel, BorderLayout.WEST);
        mainPanel.add(sidePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
