package Gui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {
        setTitle("Register");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ================= CYBER THEME COLORS =================
        Color BG_MAIN = new Color(11, 15, 20);
        Color CARD_BG = new Color(17, 24, 39);
        Color NEON_GREEN = new Color(34, 197, 94);
        Color TEXT_MUTED = new Color(156, 163, 175);
        Color BORDER = new Color(31, 41, 55);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(BG_MAIN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ================= TITLE =================
        JLabel lblTitle = new JLabel("Create Account");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(NEON_GREEN);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        // ================= FORM FIELDS =================
        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JTextField txtFullName = new JTextField(15);
        JTextField txtEmail = new JTextField(15);
        JTextField txtPhone = new JTextField(15);

        JTextField[] fields = {txtUsername, txtFullName, txtEmail, txtPhone};
        JPasswordField[] passwords = {txtPassword};

        // Style all fields
        for (JTextField f : fields) styleField(f, CARD_BG, TEXT_MUTED, BORDER);
        for (JPasswordField f : passwords) styleField(f, CARD_BG, TEXT_MUTED, BORDER);

        gbc.gridwidth = 1;

        gbc.gridy++; mainPanel.add(createLabel("Username", TEXT_MUTED), gbc);
        gbc.gridx = 1; mainPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(createLabel("Password", TEXT_MUTED), gbc);
        gbc.gridx = 1; mainPanel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(createLabel("Full Name", TEXT_MUTED), gbc);
        gbc.gridx = 1; mainPanel.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(createLabel("Email", TEXT_MUTED), gbc);
        gbc.gridx = 1; mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++; mainPanel.add(createLabel("Phone", TEXT_MUTED), gbc);
        gbc.gridx = 1; mainPanel.add(txtPhone, gbc);

        // ================= REGISTER BUTTON =================
        JButton btnRegister = new JButton("Register");
        styleButton(btnRegister, NEON_GREEN);
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

    // ================= UTILITY METHODS =================
    private JLabel createLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(color);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return lbl;
    }

    private void styleField(JTextField field, Color bg, Color fg, Color border) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(border, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setCaretColor(fg);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(false);

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = btn.getModel().isRollover() ? color.brighter() : color;
                if (btn.getModel().isPressed()) bg = bg.darker();

                g2.setColor(bg);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 12, 12);

                FontMetrics fm = g2.getFontMetrics();
                String text = btn.getText();
                int x = (c.getWidth() - fm.stringWidth(text)) / 2;
                int y = (c.getHeight() + fm.getAscent()) / 2 - 2;

                g2.setColor(Color.WHITE);
                g2.setFont(btn.getFont());
                g2.drawString(text, x, y);

                g2.dispose();
            }
        });
    }
}
