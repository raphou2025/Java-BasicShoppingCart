// package Gui;

// import DAO.CartDAO;
// import model.CartItem;

// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.util.ArrayList;

// public class CartFrame extends JFrame {

//     private final int userId;
//     private JTable table;
//     private DefaultTableModel tableModel;
//     private ArrayList<CartItem> cartItems;

//     public CartFrame(int userId) {
//         this.userId = userId;

//         System.out.println("CartFrame userId = " + userId); // DEBUG

//         setTitle("My Cart");
//         setSize(700, 500);
//         setLocationRelativeTo(null);
//         setDefaultCloseOperation(DISPOSE_ON_CLOSE);

//         JPanel mainPanel = new JPanel(new BorderLayout(10,10));
//         mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

//         JLabel lblTitle = new JLabel("My Cart");
//         lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
//         lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
//         mainPanel.add(lblTitle, BorderLayout.NORTH);

//         tableModel = new DefaultTableModel(
//                 new String[]{"Cart ID","Product","Price","Quantity","Total"},
//                 0
//         ) {
//             public boolean isCellEditable(int r, int c) { return false; }
//         };

//         table = new JTable(tableModel);
//         table.setRowHeight(25);
//         mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

//         JPanel btnPanel = new JPanel();

//         JButton btnUpdate = new JButton("Update Quantity");
//         JButton btnRemove = new JButton("Remove Item");

//         btnPanel.add(btnUpdate);
//         btnPanel.add(btnRemove);
//         mainPanel.add(btnPanel, BorderLayout.SOUTH);

//         // ===== UPDATE =====
//         btnUpdate.addActionListener(e -> {
//             int row = table.getSelectedRow();
//             if (row == -1) {
//                 JOptionPane.showMessageDialog(this,"Select an item!");
//                 return;
//             }

//             String qtyStr = JOptionPane.showInputDialog(this,"New quantity:");
//             try {
//                 int qty = Integer.parseInt(qtyStr);
//                 if (qty <= 0) throw new NumberFormatException();

//                 CartItem item = cartItems.get(row);
//                 CartDAO.updateQuantity(item.getId(), qty);
//                 loadCartItems();

//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(this,"Invalid quantity!");
//             }
//         });

//         // ===== DELETE =====
//         btnRemove.addActionListener(e -> {
//             int row = table.getSelectedRow();
//             if (row == -1) {
//                 JOptionPane.showMessageDialog(this,"Select an item!");
//                 return;
//             }

//             CartItem item = cartItems.get(row);
//             CartDAO.deleteCartItem(item.getId());
//             loadCartItems();
//         });

//         loadCartItems();
//         setContentPane(mainPanel);
//         setVisible(true);
//     }

//     private void loadCartItems() {
//         tableModel.setRowCount(0);
//         cartItems = CartDAO.getCartItems(userId);

//         for (CartItem i : cartItems) {
//             tableModel.addRow(new Object[]{
//                     i.getId(),
//                     i.getProduct().getName(),
//                     i.getProduct().getPrice(),
//                     i.getQuantity(),
//                     i.getTotal()
//             });
//         }

//         if (cartItems.isEmpty()) {
//             System.out.println("Cart is empty for userId = " + userId);
//         }
//     }
// }

package Gui;

import DAO.CartDAO;
import model.CartItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CartFrame extends JFrame {
    private final int userId;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<CartItem> cartItems;

    public CartFrame(int userId) {
        this.userId = userId;
        setTitle("My Cart");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("My Cart");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"Cart ID", "Product", "Price", "Quantity", "Total"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnUpdate = new JButton("Update Quantity");
        JButton btnRemove = new JButton("Remove Item");
        btnPanel.add(btnUpdate);
        btnPanel.add(btnRemove);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String qtyStr = JOptionPane.showInputDialog(this, "New quantity:");
                try {
                    int qty = Integer.parseInt(qtyStr);
                    if (CartDAO.updateQuantity(cartItems.get(row).getId(), qty)) loadCartItems();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid quantity!"); }
            }
        });

        btnRemove.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                if (CartDAO.deleteCartItem(cartItems.get(row).getId())) loadCartItems();
            }
        });

        loadCartItems();
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void loadCartItems() {
        tableModel.setRowCount(0);
        cartItems = CartDAO.getCartItems(userId);
        System.out.println("GUI DEBUG: Loaded " + cartItems.size() + " items for user " + userId);
        
        for (CartItem i : cartItems) {
            tableModel.addRow(new Object[]{
                i.getId(),
                i.getProduct().getName(),
                i.getProduct().getPrice(),
                i.getQuantity(),
                i.getTotal()
            });
        }
        table.revalidate();
        table.repaint();
    }
}