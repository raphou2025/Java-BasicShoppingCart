// package Gui;

// import javax.swing.*;
// import java.awt.*;

// public class PaymentFrame extends JFrame {

//     public PaymentFrame(double totalAmount) {
//         setTitle("Payment");
//         setSize(450, 300);
//         setLocationRelativeTo(null);
//         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//         setLayout(new BorderLayout(10, 10));

//         // ================= HEADER =================
//         JLabel header = new JLabel("💳 Payment", SwingConstants.CENTER);
//         header.setFont(new Font("Segoe UI", Font.BOLD, 22));
//         header.setForeground(new Color(34, 197, 94));
//         add(header, BorderLayout.NORTH);

//         // ================= TOTAL PANEL =================
//         JPanel totalPanel = new JPanel();
//         totalPanel.setBackground(new Color(17, 24, 39));
//         totalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//         totalPanel.setLayout(new GridLayout(2, 1, 5, 5));

//         JLabel lblTotalText = new JLabel("Total Amount:", SwingConstants.CENTER);
//         lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 16));
//         lblTotalText.setForeground(Color.WHITE);

//         JLabel lblTotal = new JLabel(String.format("$%.2f", totalAmount), SwingConstants.CENTER);
//         lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
//         lblTotal.setForeground(new Color(139, 92, 246)); // neon purple

//         totalPanel.add(lblTotalText);
//         totalPanel.add(lblTotal);
//         add(totalPanel, BorderLayout.CENTER);

//         // ================= PAYMENT METHOD BUTTONS =================
//         JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//         btnPanel.setBackground(new Color(11, 15, 20));

//         JButton btnCard = createNeonButton("💳 Pay with Credit Card", new Color(37, 99, 235));
//         JButton btnQR = createNeonButton("📱 Pay via QR Code", new Color(34, 197, 94));

//         btnCard.setPreferredSize(new Dimension(180, 45));
//         btnQR.setPreferredSize(new Dimension(180, 45));

//         btnCard.addActionListener(e -> {
//             JOptionPane.showMessageDialog(this,
//                     "Payment Successful via Credit Card!\nTotal: $" + totalAmount,
//                     "Payment Complete", JOptionPane.INFORMATION_MESSAGE);
//             dispose();
//         });

//         btnQR.addActionListener(e -> {
//             JOptionPane.showMessageDialog(this,
//                     "Scan this QR Code to Pay!\nTotal: $" + totalAmount,
//                     "Payment QR", JOptionPane.INFORMATION_MESSAGE);
//             dispose();
//         });

//         btnPanel.add(btnCard);
//         btnPanel.add(btnQR);
//         add(btnPanel, BorderLayout.SOUTH);

//         getContentPane().setBackground(new Color(11, 15, 20));
//         setVisible(true);
//     }

//     private JButton createNeonButton(String text, Color color) {
//         JButton btn = new JButton(text);
//         btn.setForeground(Color.WHITE);
//         btn.setBackground(color);
//         btn.setFocusPainted(false);
//         btn.setBorderPainted(false);
//         btn.setContentAreaFilled(false);
//         btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         btn.setFont(new Font("Segoe UI", Font.BOLD, 13));

//         btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
//             @Override
//             public void paint(Graphics g, JComponent c) {
//                 Graphics2D g2 = (Graphics2D) g.create();
//                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//                 Color bg = btn.getModel().isRollover() ? color.brighter() : color;
//                 if (btn.getModel().isPressed()) bg = bg.darker();

//                 g2.setColor(bg);
//                 g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 14, 14);

//                 FontMetrics fm = g2.getFontMetrics();
//                 int x = (c.getWidth() - fm.stringWidth(text)) / 2;
//                 int y = (c.getHeight() + fm.getAscent()) / 2 - 2;

//                 g2.setColor(Color.WHITE);
//                 g2.drawString(text, x, y);
//                 g2.dispose();
//             }
//         });
//         return btn;
//     }
// }

package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PaymentFrame extends JFrame {

    // ===== THEME COLORS =====
    private static final Color BG = new Color(15, 23, 42);
    private static final Color CARD = new Color(30, 41, 59);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color TEXT = new Color(240, 240, 240);
    private static final Color FIELD_BG = new Color(51, 65, 85);

    private double totalAmount;

    public PaymentFrame(double totalAmount) {
        this.totalAmount = totalAmount;

        setTitle("Secure Payment");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createTabs(), BorderLayout.CENTER);

        setContentPane(main);
        setVisible(true);
    }

    // ================= HEADER =================
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Checkout Payment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT);

        JLabel total = new JLabel("Total: $" + String.format("%.2f", totalAmount));
        total.setFont(new Font("Segoe UI", Font.BOLD, 18));
        total.setForeground(SUCCESS);

        panel.add(title, BorderLayout.WEST);
        panel.add(total, BorderLayout.EAST);

        return panel;
    }

    private JTabbedPane createTabs() {
    JTabbedPane tabs = new JTabbedPane() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(CARD); // content area background
        }
    };

    // Set font
    tabs.setFont(new Font("Segoe UI", Font.BOLD, 15));

    // Add tabs
    tabs.addTab("Visa / Credit Card", createVisaPanel());
    tabs.addTab("QR Payment", createQRPanel());

    // Custom tab color rendering
    tabs.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement,
                                          int tabIndex, int x, int y, int w, int h, boolean isSelected) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                g2.setColor(new Color(59, 130, 246)); // Neon blue for selected tab
            } else {
                g2.setColor(new Color(30, 41, 59)); // Dark gray for unselected tab
            }

            g2.fillRoundRect(x, y, w, h, 12, 12); // Rounded corners
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int tabPlacement,
                                           Rectangle[] rects, int tabIndex,
                                           Rectangle iconRect, Rectangle textRect, boolean isSelected) {
            // Do not paint default focus indicator
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            g.setColor(CARD);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font,
                                 java.awt.FontMetrics metrics, int tabIndex,
                                 String title, Rectangle textRect, boolean isSelected) {

            g.setFont(font);
            g.setColor(Color.WHITE); // Text color for all tabs
            g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
        }
    });

    return tabs;
}


    // ================= VISA PANEL =================
    private JPanel createVisaPanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);

        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.setPreferredSize(new Dimension(450, 400));

        JTextField cardNumber = createField("Card Number");
        JTextField holderName = createField("Card Holder Name");

        JPanel row = new JPanel(new GridLayout(1, 2, 15, 0));
        row.setBackground(CARD);

        JTextField expiry = createField("MM/YY");
        JTextField cvv = createField("CVV");

        row.add(expiry);
        row.add(cvv);

        JButton payBtn = createButton("Pay Securely", SUCCESS);

        payBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Payment Successful!");
            dispose();
        });

        panel.add(cardNumber);
        panel.add(Box.createVerticalStrut(20));
        panel.add(holderName);
        panel.add(Box.createVerticalStrut(20));
        panel.add(row);
        panel.add(Box.createVerticalStrut(30));
        panel.add(payBtn);

        wrapper.add(panel);
        return wrapper;
    }

    // ================= QR PANEL =================
    private JPanel createQRPanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);

        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.setPreferredSize(new Dimension(450, 500));

        JLabel instruction = new JLabel("Scan this QR Code to Pay");
        instruction.setForeground(TEXT);
        instruction.setFont(new Font("Segoe UI", Font.BOLD, 16));
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);

        // LOAD IMAGE FROM RESOURCE
        ImageIcon icon = new ImageIcon(
                getClass().getResource("/img/QR-Code.jpg")
        );

        Image scaled = icon.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
        JLabel qrLabel = new JLabel(new ImageIcon(scaled));
        qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton confirmBtn = createButton("Confirm Payment", SUCCESS);
        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "QR Payment Confirmed!");
            dispose();
        });

        panel.add(instruction);
        panel.add(Box.createVerticalStrut(30));
        panel.add(qrLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(confirmBtn);

        wrapper.add(panel);
        return wrapper;
    }

    // ================= FIELD STYLE =================
    private JTextField createField(String title) {

        JTextField field = new JTextField();
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY),
                title,
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 12),
                TEXT
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        return field;
    }

    // ================= BUTTON STYLE =================
    private JButton createButton(String text, Color bgColor) {

        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }
}
