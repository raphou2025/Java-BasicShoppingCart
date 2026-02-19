// package Gui;

// import DAO.UserDAO;
// import javax.swing.*;
// import java.awt.*;

// public class LoginFormPanel extends JPanel {

//     public LoginFormPanel(JFrame parentFrame) {
//         setLayout(new GridBagLayout());
//         setBackground(Color.WHITE);
//         setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10,10,10,10);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         JLabel lblTitle = new JLabel("Login");
//         lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
//         gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
//         add(lblTitle, gbc);

//         JLabel lblUser = new JLabel("Username");
//         JTextField txtUser = new JTextField(15);
//         gbc.gridwidth = 1; gbc.gridy++;
//         gbc.gridx = 0; add(lblUser, gbc);
//         gbc.gridx = 1; add(txtUser, gbc);

//         JLabel lblPass = new JLabel("Password");
//         JPasswordField txtPass = new JPasswordField(15);
//         gbc.gridy++; gbc.gridx = 0; add(lblPass, gbc);
//         gbc.gridx = 1; add(txtPass, gbc);

//         JButton btnLogin = new JButton("Login");
//         JButton btnRegister = new JButton("Register");
//         gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
//         JPanel btnPanel = new JPanel();
//         btnPanel.add(btnLogin);
//         btnPanel.add(btnRegister);
//         add(btnPanel, gbc);

//         btnLogin.addActionListener(e -> {
//             String username = txtUser.getText().trim();
//             String password = new String(txtPass.getPassword());

//             int userId = UserDAO.login(username, password);
//             if(userId > 0) {
//                 JOptionPane.showMessageDialog(parentFrame, "Login successful!");
//                 parentFrame.dispose();
//                 new ShopFrame(userId); // open shop
//             } else {
//                 JOptionPane.showMessageDialog(parentFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         });

//         btnRegister.addActionListener(e -> new RegisterFrame());
//     }
// }

// package Gui;

// import DAO.UserDAO;

// import javax.swing.*;
// import java.awt.*;

// public class LoginFormPanel extends JPanel {

//     private JTextField txtUsername;
//     private JPasswordField txtPassword;
//     private JButton btnLogin;
//     private JFrame parent;

//     public LoginFormPanel(JFrame parent) {
//         this.parent = parent;
//         setLayout(new GridBagLayout());
//         setBackground(Color.WHITE);
//         setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(15, 5, 15, 5);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         JLabel lblTitle = new JLabel("Sign In");
//         lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
//         lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.gridwidth = 2;
//         add(lblTitle, gbc);

//         // Username
//         JLabel lblUser = new JLabel("Username:");
//         lblUser.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         gbc.gridy++;
//         gbc.gridwidth = 1;
//         add(lblUser, gbc);

//         txtUsername = new JTextField();
//         gbc.gridx = 1;
//         add(txtUsername, gbc);

//         // Password
//         gbc.gridx = 0;
//         gbc.gridy++;
//         JLabel lblPass = new JLabel("Password:");
//         lblPass.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         add(lblPass, gbc);

//         txtPassword = new JPasswordField();
//         gbc.gridx = 1;
//         add(txtPassword, gbc);

//         // Login Button
//         gbc.gridx = 0;
//         gbc.gridy++;
//         gbc.gridwidth = 2;
//         btnLogin = new JButton("Login");
//         btnLogin.setBackground(new Color(37, 99, 235));
//         btnLogin.setForeground(Color.WHITE);
//         btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
//         btnLogin.setFocusPainted(false);
//         add(btnLogin, gbc);

//         btnLogin.addActionListener(e -> doLogin());
//     }

//     private void doLogin() {
//         String username = txtUsername.getText();
//         String password = new String(txtPassword.getPassword());

//         if (username.isEmpty() || password.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         int userId = UserDAO.login(username, password);
//         if (userId > 0) {
//             JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//             parent.dispose(); // close login frame

//             // Example: Open admin dashboard (you can change logic for normal users)
//             new AdminDashboardFrame();
//         } else {
//             JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
//         }
//     }
// }


package Gui;

import DAO.UserDAO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginFormPanel extends JPanel {

    public LoginFormPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(11, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Main panel container
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(17, 24, 39));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints mpGbc = new GridBagConstraints();
        mpGbc.gridx = 0;
        mpGbc.gridy = 0;
        mpGbc.insets = new Insets(5, 0, 5, 0);
        mpGbc.fill = GridBagConstraints.HORIZONTAL;
        mpGbc.weightx = 1;

        // Title
        JLabel lblTitle = new JLabel("Welcome back!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(34, 197, 94));
        mpGbc.gridy++;
        mainPanel.add(lblTitle, mpGbc);

        // Subtitle
        JLabel lblSubtitle = new JLabel("Sign in to your account", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(156, 163, 175));
        mpGbc.gridy++;
        mainPanel.add(lblSubtitle, mpGbc);

        // Username label
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(new Color(156, 163, 175));
        mpGbc.gridy++;
        mainPanel.add(lblUser, mpGbc);

        // Username field
        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setBackground(new Color(11, 15, 20));
        txtUser.setForeground(Color.WHITE);
        txtUser.setCaretColor(Color.WHITE);
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(8, new Color(56, 189, 248)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        mpGbc.gridy++;
        mainPanel.add(txtUser, mpGbc);

        // Password label
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPass.setForeground(new Color(156, 163, 175));
        mpGbc.gridy++;
        mainPanel.add(lblPass, mpGbc);

        // Password field
        JPasswordField txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBackground(new Color(11, 15, 20));
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(8, new Color(56, 189, 248)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        mpGbc.gridy++;
        mainPanel.add(txtPass, mpGbc);

        // Options panel (Remember/Forgot)
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setOpaque(false);

        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rememberMe.setForeground(new Color(156, 163, 175));
        rememberMe.setOpaque(false);

        JLabel forgotPassword = new JLabel("Forgot password?");
        forgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        forgotPassword.setForeground(new Color(56, 189, 248));
        forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        optionsPanel.add(rememberMe, BorderLayout.WEST);
        optionsPanel.add(forgotPassword, BorderLayout.EAST);

        mpGbc.gridy++;
        mainPanel.add(optionsPanel, mpGbc);

        // Login button
        JButton btnLogin = createNeonButton("Sign In", new Color(34, 197, 94));
        mpGbc.gridy++;
        mainPanel.add(btnLogin, mpGbc);

        // Divider
        JPanel dividerPanel = new JPanel(new GridBagLayout());
        dividerPanel.setOpaque(false);

        JSeparator leftDivider = new JSeparator();
        leftDivider.setForeground(new Color(31, 41, 55));
        JSeparator rightDivider = new JSeparator();
        rightDivider.setForeground(new Color(31, 41, 55));
        JLabel orLabel = new JLabel("or", SwingConstants.CENTER);
        orLabel.setForeground(new Color(156, 163, 175));

        GridBagConstraints divGbc = new GridBagConstraints();
        divGbc.fill = GridBagConstraints.HORIZONTAL;
        divGbc.weightx = 1;
        dividerPanel.add(leftDivider, divGbc);
        divGbc.gridx = 1;
        dividerPanel.add(orLabel, divGbc);
        divGbc.gridx = 2;
        dividerPanel.add(rightDivider, divGbc);

        mpGbc.gridy++;
        mainPanel.add(dividerPanel, mpGbc);

        // Register button
        JButton btnRegister = createNeonButton("Create an Account", new Color(56, 189, 248));
        mpGbc.gridy++;
        mainPanel.add(btnRegister, mpGbc);

        // Add main panel to parent
        add(mainPanel, gbc);

        // Listeners
        btnLogin.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword());
            int userId = UserDAO.login(username, password);
            if(userId > 0) {
                JOptionPane.showMessageDialog(parentFrame, "Login successful!");
                parentFrame.dispose();
                new ShopFrame(userId);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> new RegisterFrame());

        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forgotPassword.setText("<html><u>Forgot password?</u></html>");
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                forgotPassword.setText("Forgot password?");
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(parentFrame,
                        "Please contact admin to reset your password.",
                        "Forgot Password",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Neon button
    private JButton createNeonButton(String text, Color neonColor) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = neonColor;
                if (btn.getModel().isPressed()) bg = bg.darker();
                else if (btn.getModel().isRollover()) bg = bg.brighter();
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                FontMetrics fm = g2.getFontMetrics();
                int x = (c.getWidth() - fm.stringWidth(text)) / 2;
                int y = (c.getHeight() + fm.getAscent()) / 2 - 2;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y);
                g2.dispose();
            }
        });
        return btn;
    }

    // Rounded border
    private static class RoundBorder extends LineBorder {
        private int radius;

        public RoundBorder(int radius, Color color) {
            super(color, 1);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }
}
