package Gui;

import javax.swing.*;
import java.awt.*;

public class LoginSidePanel extends JPanel {

    public LoginSidePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 144, 255));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel lblTitle = new JLabel("Welcome Back!");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSubtitle = new JLabel("<html><p style='width:300px;'>Login to manage products, shopping cart, and account.</p></html>");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitle.setForeground(Color.WHITE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(30, 144, 255));

        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(lblTitle);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(lblSubtitle);

        add(centerPanel, BorderLayout.CENTER);
    }
}
