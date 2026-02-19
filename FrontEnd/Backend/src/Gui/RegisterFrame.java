package Gui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {
        setTitle("Register");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Create Account");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JTextField txtFullName = new JTextField(15);
        JTextField txtEmail = new JTextField(15);
        JTextField txtPhone = new JTextField(15);

        gbc.gridwidth = 1;
        gbc.gridy++; mainPanel.add(new JLabel("Username"), gbc);
        gbc.gridx = 1; mainPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(new JLabel("Password"), gbc);
        gbc.gridx = 1; mainPanel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(new JLabel("Full Name"), gbc);
        gbc.gridx = 1; mainPanel.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1; mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(new JLabel("Phone"), gbc);
        gbc.gridx = 1; mainPanel.add(txtPhone, gbc);

        JButton btnRegister = new JButton("Register");
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        mainPanel.add(btnRegister, gbc);

        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String fullName = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();

            User user = new User(username, password, fullName, email, phone);
            if(UserDAO.register(user)) {
                JOptionPane.showMessageDialog(this,"Registration successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Registration failed!");
            }
        });

        setContentPane(mainPanel);
        setVisible(true);
    }
}
