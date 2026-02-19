
package Gui;

import DAO.CartDAO;
import model.CartItem;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class CartFrame extends JFrame {

    private final int userId;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<CartItem> cartItems;
    private JLabel lblTotalAmount;

    // ================= CYBER THEME COLORS =================
    private static final Color BG_MAIN = new Color(11, 15, 20);
    private static final Color CARD_BG = new Color(17, 24, 39);
    private static final Color NEON_GREEN = new Color(34, 197, 94);
    private static final Color NEON_BLUE = new Color(56, 189, 248);
    private static final Color NEON_PURPLE = new Color(139, 92, 246);
    private static final Color TEXT_MUTED = new Color(156, 163, 175);
    private static final Color BORDER = new Color(31, 41, 55);

    public CartFrame(int userId) {
        this.userId = userId;

        setTitle("🛒 CYBER.TECH Cart");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_MAIN);

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);
        main.add(createSidebar(), BorderLayout.EAST);

        loadCartItems();

        setContentPane(main);
        setVisible(true);
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("🛒 My Cart");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(NEON_GREEN);

        JButton back = createNeonButton("← Back to Shop", NEON_BLUE);
        back.setPreferredSize(new Dimension(150, 36));
        back.addActionListener(e -> {
            new ShopFrame(userId);
            dispose();
        });

        header.add(title, BorderLayout.WEST);
        header.add(back, BorderLayout.EAST);
        return header;
    }

    // ================= CONTENT =================
    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(BG_MAIN);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ================= TABLE =================
        String[] cols = {"ID", "Product", "Price", "Qty", "Total", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionBackground(new Color(34, 45, 60));
        table.setSelectionForeground(NEON_GREEN);
        table.setGridColor(BORDER);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {

                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                if (!sel) setBackground(r % 2 == 0 ? CARD_BG : BG_MAIN);

                if ((c == 2 || c == 4) && v instanceof Number)
                    setText(String.format("$%.2f", ((Number) v).doubleValue()));

                if (c == 5) {
                    if ("active".equals(v)) setForeground(NEON_GREEN);
                    else if ("ordered".equals(v)) setForeground(NEON_BLUE);
                    else setForeground(NEON_PURPLE);
                } else setForeground(Color.WHITE);

                return this;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new LineBorder(BORDER));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(new CompoundBorder(
                new LineBorder(BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        tablePanel.add(sp);

        content.add(tablePanel, BorderLayout.CENTER);
        content.add(createSummaryPanel(), BorderLayout.SOUTH);

        return content;
    }

    // ================= SUMMARY =================
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        lblTotalAmount = new JLabel("$0.00");
        lblTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotalAmount.setForeground(NEON_GREEN);

        JLabel lbl = new JLabel("Total:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(TEXT_MUTED);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(lbl);
        left.add(lblTotalAmount);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        JButton edit = createNeonButton("✏️ Edit", NEON_BLUE);
        JButton remove = createNeonButton("🗑 Remove", NEON_PURPLE);
        JButton checkout = createNeonButton("✅ Checkout", NEON_GREEN);

        edit.addActionListener(e -> editCartItem());
        remove.addActionListener(e -> removeCartItem());
        checkout.addActionListener(e -> {
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }
            double total = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
            new PaymentFrame(total); // Open PaymentFrame
        });

        right.add(edit);
        right.add(remove);
        right.add(checkout);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    // ================= SIDEBAR =================
    private JPanel createSidebar() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(CARD_BG);
        side.setPreferredSize(new Dimension(250, 0));
        side.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblSidebar = new JLabel("Cart Summary");
        lblSidebar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSidebar.setForeground(TEXT_MUTED);
        side.add(lblSidebar);
        side.add(Box.createVerticalStrut(20));

        JButton refresh = createNeonButton("🔄 Refresh", NEON_BLUE);
        refresh.setMaximumSize(new Dimension(200, 38));
        refresh.addActionListener(e -> loadCartItems());

        JButton clear = createNeonButton("🗑 Clear All", NEON_PURPLE);
        clear.setMaximumSize(new Dimension(200, 38));
        clear.addActionListener(e -> clearAllItems());

        side.add(refresh);
        side.add(Box.createVerticalStrut(10));
        side.add(clear);

        return side;
    }

    // ================= DATA HANDLING =================
    private void loadCartItems() {
        tableModel.setRowCount(0);
        cartItems = CartDAO.getCartItems(userId);
        double total = 0;

        for (CartItem i : cartItems) {
            tableModel.addRow(new Object[]{
                    i.getId(),
                    i.getProduct().getName(),
                    i.getProduct().getPrice(),
                    i.getQuantity(),
                    i.getTotal(),
                    i.getStatus()
            });
            total += i.getTotal();
        }
        lblTotalAmount.setText(String.format("$%.2f", total));
    }

    private void editCartItem() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Select an item first!");
            return;
        }
        CartItem i = cartItems.get(table.getSelectedRow());
        String qty = JOptionPane.showInputDialog(this, "New quantity:", i.getQuantity());
        if (qty != null && !qty.isEmpty()) {
            CartDAO.updateCartItem(i.getId(), Integer.parseInt(qty), i.getStatus());
            loadCartItems();
        }
    }

    private void removeCartItem() {
        if (table.getSelectedRow() == -1) return;
        CartDAO.deleteCartItem(cartItems.get(table.getSelectedRow()).getId());
        loadCartItems();
    }

    private void clearAllItems() {
        for (CartItem i : cartItems)
            CartDAO.deleteCartItem(i.getId());
        loadCartItems();
    }

    // ================= NEON BUTTON =================
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
