// package Gui;

// import DAO.CartDAO;
// import DAO.ProductDAO;
// import model.Product;

// import javax.swing.*;
// import java.awt.*;
// import java.util.ArrayList;

// public class ShopFrame extends JFrame {

//     private int userId;
//     private JList<Product> productList;

//     public ShopFrame(int userId) {
//         this.userId = userId;

//         setTitle("Shop - Products");
//         setSize(800, 600);
//         setLocationRelativeTo(null);
//         setDefaultCloseOperation(EXIT_ON_CLOSE);

//         JPanel mainPanel = new JPanel(new BorderLayout(10,10));
//         mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//         mainPanel.setBackground(Color.WHITE);

//         JLabel lblTitle = new JLabel("Product Catalog");
//         lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
//         lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
//         mainPanel.add(lblTitle, BorderLayout.NORTH);

//         DefaultListModel<Product> listModel = new DefaultListModel<>();
//         ArrayList<Product> products = ProductDAO.getAllProducts();
//         for(Product p : products) listModel.addElement(p);
//         productList = new JList<>(listModel);
//         mainPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

//         JPanel btnPanel = new JPanel();
//         JButton btnAddCart = new JButton("Add to Cart");
//         JButton btnViewCart = new JButton("View Cart");
//         btnPanel.add(btnAddCart);
//         btnPanel.add(btnViewCart);
//         mainPanel.add(btnPanel, BorderLayout.SOUTH);

//         btnAddCart.addActionListener(e -> {
//             Product selected = productList.getSelectedValue();
//             if(selected != null) {
//                 if(CartDAO.addToCart(userId, selected.getId(), 1))
//                     JOptionPane.showMessageDialog(this,"Added to cart!");
//                 else
//                     JOptionPane.showMessageDialog(this,"Failed!");
//             } else JOptionPane.showMessageDialog(this,"Select a product.");
//         });

//         btnViewCart.addActionListener(e -> new CartFrame(userId));

//         setContentPane(mainPanel);
//         setVisible(true);
//     }
// }

package Gui;

import DAO.CartDAO;
import DAO.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopFrame extends JFrame {

    private int userId;
    private JList<Product> productList;

    public ShopFrame(int userId) {
        this.userId = userId;

        setTitle("Shop - Products");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Product Catalog");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        DefaultListModel<Product> listModel = new DefaultListModel<>();
        ArrayList<Product> products = ProductDAO.getAllProducts();
        for(Product p : products) listModel.addElement(p);
        productList = new JList<>(listModel);
        mainPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAddCart = new JButton("Add to Cart");
        JButton btnViewCart = new JButton("View Cart");
        btnPanel.add(btnAddCart);
        btnPanel.add(btnViewCart);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        // ================= ACTION LISTENERS =================
       // Inside the btnAddCart listener in ShopFrame.java
        btnAddCart.addActionListener(e -> {
            Product selected = productList.getSelectedValue();
            if(selected != null) {
                // Corrected to .getId() to match your Product.java
                if(CartDAO.addToCart(userId, selected.getId(), 1)) {
                    JOptionPane.showMessageDialog(this, "Added to cart!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a product.");
            }
        });

        btnViewCart.addActionListener(e -> {
            // This opens your Cart window and passes the current user's ID
            new CartFrame(this.userId); 
        });

        setContentPane(mainPanel);
        setVisible(true);
    }
}