package Gui;

import javax.swing.*;
import java.awt.*;

public class PaymentFrame extends JFrame {

    public PaymentFrame(double totalAmount) {
        setTitle("Payment");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= HEADER =================
        JLabel header = new JLabel("💳 Payment", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(34, 197, 94));
        add(header, BorderLayout.NORTH);

        // ================= TOTAL PANEL =================
        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(new Color(17, 24, 39));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        totalPanel.setLayout(new GridLayout(2, 1, 5, 5));

        JLabel lblTotalText = new JLabel("Total Amount:", SwingConstants.CENTER);
        lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalText.setForeground(Color.WHITE);

        JLabel lblTotal = new JLabel(String.format("$%.2f", totalAmount), SwingConstants.CENTER);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotal.setForeground(new Color(139, 92, 246)); // neon purple

        totalPanel.add(lblTotalText);
        totalPanel.add(lblTotal);
        add(totalPanel, BorderLayout.CENTER);

        // ================= PAYMENT METHOD BUTTONS =================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(new Color(11, 15, 20));

        JButton btnCard = createNeonButton("💳 Pay with Credit Card", new Color(37, 99, 235));
        JButton btnQR = createNeonButton("📱 Pay via QR Code", new Color(34, 197, 94));

        btnCard.setPreferredSize(new Dimension(180, 45));
        btnQR.setPreferredSize(new Dimension(180, 45));

        btnCard.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Payment Successful via Credit Card!\nTotal: $" + totalAmount,
                    "Payment Complete", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnQR.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Scan this QR Code to Pay!\nTotal: $" + totalAmount,
                    "Payment QR", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnPanel.add(btnCard);
        btnPanel.add(btnQR);
        add(btnPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(new Color(11, 15, 20));
        setVisible(true);
    }

    private JButton createNeonButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = btn.getModel().isRollover() ? color.brighter() : color;
                if (btn.getModel().isPressed()) bg = bg.darker();

                g2.setColor(bg);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 14, 14);

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
}
