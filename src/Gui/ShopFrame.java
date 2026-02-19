

package Gui;

import DAO.ProductDAO;
import DAO.CartDAO;
import model.Product;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ShopFrame extends JFrame {

    private int userId;
    private Product selectedProduct;
    private JPanel productGrid;

    // ================= CYBER THEME COLORS =================
    private static final Color BG_MAIN = new Color(11, 15, 20);
    private static final Color CARD_BG = new Color(17, 24, 39);
    private static final Color NEON_GREEN = new Color(34, 197, 94);
    private static final Color NEON_BLUE = new Color(56, 189, 248);
    private static final Color NEON_PURPLE = new Color(139, 92, 246);
    private static final Color NEON_RED = new Color(239, 68, 68);
    private static final Color TEXT_MUTED = new Color(156, 163, 175);
    private static final Color BORDER = new Color(31, 41, 55);

    public ShopFrame(int userId) {
        this.userId = userId;

        setTitle("CYBER.TECH Store");
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_MAIN);

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);
        main.add(createStatsPanel(), BorderLayout.EAST);

        setContentPane(main);
        setVisible(true);
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(5, 8, 13));
        header.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));

        ImageIcon icon = new ImageIcon("resources/img/gamepad.png");
        JLabel logo = new JLabel("CYBER.TECH", icon, JLabel.LEFT);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(NEON_GREEN);


        JLabel subtitle = new JLabel("WHERE GAMING EVOLVES");
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(logo, BorderLayout.NORTH);
        left.add(subtitle, BorderLayout.SOUTH);

        JButton logout = createNeonButton("Logout", NEON_RED);
        logout.setPreferredSize(new Dimension(90, 36));
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        header.add(left, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);
        return header;
    }

    // ================= CONTENT =================
    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(BG_MAIN);
        content.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel title = new JLabel("All Products");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("High-performance gear for gamers");
        sub.setForeground(TEXT_MUTED);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(sub, BorderLayout.SOUTH);

        content.add(titlePanel, BorderLayout.NORTH);

        productGrid = new JPanel(new GridLayout(0, 3, 24, 24));
        productGrid.setBackground(BG_MAIN);

        JScrollPane scroll = new JScrollPane(productGrid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(BG_MAIN);

        content.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        btnPanel.setOpaque(false);

        JButton add = createNeonButton("🛒 Add to Cart", NEON_GREEN);
        JButton cart = createNeonButton("View Cart", NEON_BLUE);

        add.setPreferredSize(new Dimension(180, 45));
        cart.setPreferredSize(new Dimension(180, 45));

        add.addActionListener(e -> {
            if (selectedProduct == null) {
                showError("Select a product first");
                return;
            }
            CartDAO.addToCart(userId, selectedProduct.getId(), 1);
            showSuccess("Added to cart!");
        });

        cart.addActionListener(e -> {
            new CartFrame(userId);
            dispose();
        });

        btnPanel.add(add);
        btnPanel.add(cart);
        content.add(btnPanel, BorderLayout.SOUTH);

        loadProducts(); // load product cards
        return content;
    }

    // ================= LOAD PRODUCTS =================
    private void loadProducts() {
        productGrid.removeAll();
        List<Product> products = ProductDAO.getAllProducts();
        for (Product p : products) {
            productGrid.add(createProductCard(p));
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    // ================= PRODUCT CARD =================
    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_BG);
        card.setBorder(new LineBorder(BORDER, 1));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ================= IMAGE =================
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(200, 150));

        try {
            if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(p.getImagePath());
                Image scaled = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            } else {
                imgLabel.setIcon(new ImageIcon(new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB)));
            }
        } catch (Exception e) {
            imgLabel.setIcon(new ImageIcon(new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB)));
        }

        // ================= NAME & PRICE =================
        JLabel name = new JLabel(p.getName());
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel price = new JLabel("$" + String.format("%.2f", p.getPrice()));
        price.setForeground(NEON_GREEN);
        price.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(name, BorderLayout.NORTH);
        top.add(price, BorderLayout.SOUTH);

        // ================= DESCRIPTION =================
        JLabel desc = new JLabel("<html><p style='width:200px;color:#9CA3AF'>" +
                p.getDescription() + "</p></html>");

        // ================= BUTTON PANEL =================
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionPanel.setOpaque(false);

        JButton btnEdit = createNeonButton("Edit", NEON_BLUE);
        JButton btnDelete = createNeonButton("Delete", NEON_RED);
        JButton btnImage = createNeonButton("Change Image", NEON_PURPLE);

        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);
        actionPanel.add(btnImage);

        // ================= CARD LAYOUT =================
        card.add(imgLabel, BorderLayout.NORTH);
        card.add(top, BorderLayout.CENTER);
        card.add(desc, BorderLayout.SOUTH);
        card.add(actionPanel, BorderLayout.PAGE_END);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================= HIGHLIGHT =================
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedProduct = p;
                highlight(card);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(NEON_BLUE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (selectedProduct != p)
                    card.setBorder(new LineBorder(BORDER, 1));
            }
        });

        // ================= BUTTON ACTIONS =================
        btnEdit.addActionListener(e -> editProduct(p));
        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete Product", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ProductDAO.delete(p.getId());
                loadProducts();
                showSuccess("Product deleted!");
            }
        });

        btnImage.addActionListener(e -> changeProductImage(p, imgLabel));

        return card;
    }

    private void highlight(JPanel selected) {
        Container parent = selected.getParent();
        for (Component c : parent.getComponents()) {
            if (c instanceof JPanel)
                ((JPanel) c).setBorder(new LineBorder(BORDER, 1));
        }
        selected.setBorder(new LineBorder(NEON_GREEN, 2));
    }

    // ================= EDIT PRODUCT =================
    private void editProduct(Product p) {
        JTextField txtName = new JTextField(p.getName());
        JTextField txtDesc = new JTextField(p.getDescription());
        JTextField txtPrice = new JTextField(String.valueOf(p.getPrice()));
        JTextField txtStock = new JTextField(String.valueOf(p.getStock()));

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Description:"));
        panel.add(txtDesc);
        panel.add(new JLabel("Price:"));
        panel.add(txtPrice);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                p.setName(txtName.getText());
                p.setDescription(txtDesc.getText());
                p.setPrice(Double.parseDouble(txtPrice.getText()));
                p.setStock(Integer.parseInt(txtStock.getText()));
                ProductDAO.update(p);
                loadProducts();
                showSuccess("Product updated!");
            } catch (Exception ex) {
                showError("Invalid input!");
            }
        }
    }

    // ================= CHANGE PRODUCT IMAGE =================
    private void changeProductImage(Product p, JLabel imgLabel) {
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            p.setImagePath(file.getAbsolutePath());
            ProductDAO.update(p);
            try {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image scaled = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
                showSuccess("Image updated!");
            } catch (Exception ex) {
                showError("Failed to load image!");
            }
        }
    }

    // ================= STATS =================
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(5, 8, 13));
        panel.setPreferredSize(new Dimension(260, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        panel.add(stat("📦 Products", ProductDAO.getAllProducts().size(), NEON_BLUE));
        panel.add(Box.createVerticalStrut(20));
        panel.add(stat("🛒 Cart Items", CartDAO.getCartItemCount(userId), NEON_GREEN));
        panel.add(Box.createVerticalStrut(20));
        panel.add(stat("💰 Total", "$" + CartDAO.getCartTotal(userId), NEON_PURPLE));

        return panel;
    }

    private JPanel stat(String title, Object value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel t = new JLabel(title);
        t.setForeground(TEXT_MUTED);

        JLabel v = new JLabel(value.toString());
        v.setForeground(color);
        v.setFont(new Font("Segoe UI", Font.BOLD, 22));

        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        return card;
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

    // ================= SHOW MESSAGES =================
    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
