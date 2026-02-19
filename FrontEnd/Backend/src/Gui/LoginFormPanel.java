package Gui;

import DAO.UserDAO;
import javax.swing.*;
import java.awt.*;

public class LoginFormPanel extends JPanel {

    public LoginFormPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        JLabel lblUser = new JLabel("Username");
        JTextField txtUser = new JTextField(15);
        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0; add(lblUser, gbc);
        gbc.gridx = 1; add(txtUser, gbc);

        JLabel lblPass = new JLabel("Password");
        JPasswordField txtPass = new JPasswordField(15);
        gbc.gridy++; gbc.gridx = 0; add(lblPass, gbc);
        gbc.gridx = 1; add(txtPass, gbc);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnLogin);
        btnPanel.add(btnRegister);
        add(btnPanel, gbc);

        btnLogin.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword());

            int userId = UserDAO.login(username, password);
            if(userId > 0) {
                JOptionPane.showMessageDialog(parentFrame, "Login successful!");
                parentFrame.dispose();
                new ShopFrame(userId); // open shop
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> new RegisterFrame());
    }
}
