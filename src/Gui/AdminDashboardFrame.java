// package Gui;

// import DAO.ProductDAO;
// import model.Product;

// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.time.LocalDateTime;
// import java.util.ArrayList;

// public class AdminDashboardFrame extends JFrame {

//     private JTable table;
//     private DefaultTableModel model;

//     public AdminDashboardFrame() {
//         setTitle("Admin Dashboard");
//         setSize(1000, 600);
//         setLocationRelativeTo(null);
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());

//         add(createSidebar(), BorderLayout.WEST);
//         add(createMainPanel(), BorderLayout.CENTER);

//         loadProducts();
//         setVisible(true);
//     }

//     // ================= SIDEBAR =================
//     private JPanel createSidebar() {
//         JPanel sidebar = new JPanel();
//         sidebar.setBackground(new Color(30, 41, 59));
//         sidebar.setPreferredSize(new Dimension(200, 0));
//         sidebar.setLayout(new GridLayout(6, 1, 0, 15));

//         JLabel title = new JLabel("ADMIN", SwingConstants.CENTER);
//         title.setForeground(Color.WHITE);
//         title.setFont(new Font("SansSerif", Font.BOLD, 22));

//         JButton btnProducts = sidebarButton("Products");
//         JButton btnUsers = sidebarButton("Users");
//         JButton btnOrders = sidebarButton("Orders");

//         sidebar.add(title);
//         sidebar.add(btnProducts);
//         sidebar.add(btnUsers);
//         sidebar.add(btnOrders);

//         return sidebar;
//     }

//     private JButton sidebarButton(String text) {
//         JButton btn = new JButton(text);
//         btn.setFocusPainted(false);
//         btn.setForeground(Color.WHITE);
//         btn.setBackground(new Color(51, 65, 85));
//         btn.setFont(new Font("SansSerif", Font.BOLD, 14));
//         return btn;
//     }

//     // ================= MAIN PANEL =================
//     private JPanel createMainPanel() {
//         JPanel panel = new JPanel(new BorderLayout(15, 15));
//         panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//         panel.setBackground(new Color(244, 246, 248));

//         JLabel header = new JLabel("Product Management");
//         header.setFont(new Font("SansSerif", Font.BOLD, 26));

//         JButton btnAdd = new JButton("+ Add Product");
//         btnAdd.setBackground(new Color(37, 99, 235));
//         btnAdd.setForeground(Color.WHITE);
//         btnAdd.setFocusPainted(false);

//         btnAdd.addActionListener(e -> showProductDialog(null));

//         JPanel top = new JPanel(new BorderLayout());
//         top.setOpaque(false);
//         top.add(header, BorderLayout.WEST);
//         top.add(btnAdd, BorderLayout.EAST);

//         panel.add(top, BorderLayout.NORTH);

//         model = new DefaultTableModel(
//                 new String[]{"ID", "Name", "Description", "Price", "Stock", "Actions"}, 0
//         ) {
//             public boolean isCellEditable(int r, int c) {
//                 return c == 5; // Only "Actions" column editable
//             }
//         };

//         table = new JTable(model);
//         table.setRowHeight(40);
//         table.getColumn("Actions").setCellRenderer(new ActionRenderer());
//         table.getColumn("Actions").setCellEditor(new ActionEditor());

//         panel.add(new JScrollPane(table), BorderLayout.CENTER);

//         return panel;
//     }

//     // ================= LOAD DATA =================
//     private void loadProducts() {
//         model.setRowCount(0);
//         ArrayList<Product> products = ProductDAO.getAllProducts();

//         for (Product p : products) {
//             model.addRow(new Object[]{
//                     p.getId(),
//                     p.getName(),
//                     p.getDescription(),
//                     "$" + p.getPrice(),
//                     p.getStock(),
//                     "Edit | Delete"
//             });
//         }
//     }

//     // ================= ACTION BUTTONS =================
//     class ActionRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
//         JButton edit = new JButton("Edit");
//         JButton delete = new JButton("Delete");

//         ActionRenderer() {
//             setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
//             edit.setBackground(new Color(37, 99, 235));
//             edit.setForeground(Color.WHITE);
//             delete.setBackground(new Color(220, 38, 38));
//             delete.setForeground(Color.WHITE);
//             add(edit);
//             add(delete);
//         }

//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             return this;
//         }
//     }

//     class ActionEditor extends AbstractCellEditor implements javax.swing.table.TableCellRenderer, javax.swing.table.TableCellEditor {
//         JPanel panel = new JPanel();
//         JButton edit = new JButton("Edit");
//         JButton delete = new JButton("Delete");

//         ActionEditor() {
//             panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

//             edit.setBackground(new Color(37, 99, 235));
//             edit.setForeground(Color.WHITE);

//             delete.setBackground(new Color(220, 38, 38));
//             delete.setForeground(Color.WHITE);

//             panel.add(edit);
//             panel.add(delete);

//             edit.addActionListener(e -> {
//                 int row = table.getSelectedRow();
//                 if (row != -1) {
//                     int id = (int) table.getValueAt(row, 0);
//                     Product p = ProductDAO.getById(id);
//                     showProductDialog(p);
//                     stopCellEditing();
//                 }
//             });

//             delete.addActionListener(e -> {
//                 int row = table.getSelectedRow();
//                 if (row != -1) {
//                     int id = (int) table.getValueAt(row, 0);
//                     int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete Product", JOptionPane.YES_NO_OPTION);
//                     if (confirm == JOptionPane.YES_OPTION) {
//                         ProductDAO.delete(id);
//                         loadProducts();
//                     }
//                     stopCellEditing();
//                 }
//             });
//         }

//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             return panel;
//         }

//         public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//             return panel;
//         }

//         public Object getCellEditorValue() {
//             return null;
//         }
//     }

//     // ================= PRODUCT DIALOG =================
//     private void showProductDialog(Product product) {
//         boolean isEdit = product != null;

//         JTextField txtName = new JTextField(isEdit ? product.getName() : "");
//         JTextField txtDesc = new JTextField(isEdit ? product.getDescription() : "");
//         JTextField txtPrice = new JTextField(isEdit ? String.valueOf(product.getPrice()) : "");
//         JTextField txtStock = new JTextField(isEdit ? String.valueOf(product.getStock()) : "");

//         JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
//         panel.add(new JLabel("Name:"));
//         panel.add(txtName);
//         panel.add(new JLabel("Description:"));
//         panel.add(txtDesc);
//         panel.add(new JLabel("Price:"));
//         panel.add(txtPrice);
//         panel.add(new JLabel("Stock:"));
//         panel.add(txtStock);

//         int result = JOptionPane.showConfirmDialog(this, panel, isEdit ? "Edit Product" : "Add Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

//         if (result == JOptionPane.OK_OPTION) {
//             try {
//                 String name = txtName.getText();
//                 String desc = txtDesc.getText();
//                 double price = Double.parseDouble(txtPrice.getText());
//                 int stock = Integer.parseInt(txtStock.getText());

//                 if (isEdit) {
//                     product.setName(name);
//                     product.setDescription(desc);
//                     product.setPrice(price);
//                     product.setStock(stock);
//                     ProductDAO.update(product);
//                 } else {
//                     Product newProduct = new Product(0, name, desc, price, stock, LocalDateTime.now(), LocalDateTime.now());
//                     ProductDAO.add(newProduct);
//                 }
//                 loadProducts();
//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(this, "Invalid input!");
//             }
//         }
//     }
// }

package Gui;

import DAO.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdminDashboardFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        loadProducts();
        setVisible(true);
    }

    // ================= SIDEBAR =================
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 41, 59));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new GridLayout(6, 1, 0, 15));

        JLabel title = new JLabel("ADMIN", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JButton btnProducts = sidebarButton("Products");
        JButton btnUsers = sidebarButton("Users");
        JButton btnOrders = sidebarButton("Orders");

        sidebar.add(title);
        sidebar.add(btnProducts);
        sidebar.add(btnUsers);
        sidebar.add(btnOrders);

        return sidebar;
    }

    private JButton sidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(51, 65, 85));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    // ================= MAIN PANEL =================
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(244, 246, 248));

        JLabel header = new JLabel("Product Management");
        header.setFont(new Font("SansSerif", Font.BOLD, 26));

        JButton btnAdd = new JButton("+ Add Product");
        btnAdd.setBackground(new Color(37, 99, 235));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);

        btnAdd.addActionListener(e -> showProductDialog(null));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(header, BorderLayout.WEST);
        top.add(btnAdd, BorderLayout.EAST);

        panel.add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Description", "Price", "Stock", "Image", "Actions"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return c == 6; // Only "Actions" column editable
            }
        };

        table = new JTable(model);
        table.setRowHeight(60);
        table.getColumn("Actions").setCellRenderer(new ActionRenderer());
        table.getColumn("Actions").setCellEditor(new ActionEditor());

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    // ================= LOAD DATA =================
    private void loadProducts() {
        model.setRowCount(0);
        ArrayList<Product> products = ProductDAO.getAllProducts();

        for (Product p : products) {
            ImageIcon icon = null;
            try {
                icon = new ImageIcon(new ImageIcon(p.getImagePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            } catch (Exception ignored) {
            }

            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    "$" + p.getPrice(),
                    p.getStock(),
                    icon,
                    "Edit | Delete"
            });
        }
    }

    // ================= ACTION BUTTONS =================
    class ActionRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
            edit.setBackground(new Color(37, 99, 235));
            edit.setForeground(Color.WHITE);
            delete.setBackground(new Color(220, 38, 38));
            delete.setForeground(Color.WHITE);
            add(edit);
            add(delete);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ActionEditor extends AbstractCellEditor implements javax.swing.table.TableCellRenderer, javax.swing.table.TableCellEditor {
        JPanel panel = new JPanel();
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        ActionEditor() {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

            edit.setBackground(new Color(37, 99, 235));
            edit.setForeground(Color.WHITE);

            delete.setBackground(new Color(220, 38, 38));
            delete.setForeground(Color.WHITE);

            panel.add(edit);
            panel.add(delete);

            edit.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    Product p = ProductDAO.getById(id);
                    showProductDialog(p);
                    stopCellEditing();
                }
            });

            delete.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete Product", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        ProductDAO.delete(id);
                        loadProducts();
                    }
                    stopCellEditing();
                }
            });
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        public Object getCellEditorValue() {
            return null;
        }
    }

    // ================= PRODUCT DIALOG =================
    private void showProductDialog(Product product) {
        boolean isEdit = product != null;

        JTextField txtName = new JTextField(isEdit ? product.getName() : "");
        JTextField txtDesc = new JTextField(isEdit ? product.getDescription() : "");
        JTextField txtPrice = new JTextField(isEdit ? String.valueOf(product.getPrice()) : "");
        JTextField txtStock = new JTextField(isEdit ? String.valueOf(product.getStock()) : "");
        JTextField txtImage = new JTextField(isEdit ? product.getImagePath() : "");

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Description:"));
        panel.add(txtDesc);
        panel.add(new JLabel("Price:"));
        panel.add(txtPrice);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);
        panel.add(new JLabel("Image Path:"));
        panel.add(txtImage);

        int result = JOptionPane.showConfirmDialog(this, panel, isEdit ? "Edit Product" : "Add Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = txtName.getText();
                String desc = txtDesc.getText();
                double price = Double.parseDouble(txtPrice.getText());
                int stock = Integer.parseInt(txtStock.getText());
                String imagePath = txtImage.getText();

                if (isEdit) {
                    product.setName(name);
                    product.setDescription(desc);
                    product.setPrice(price);
                    product.setStock(stock);
                    product.setImagePath(imagePath);
                    ProductDAO.update(product);
                } else {
                    Product newProduct = new Product(0, name, desc, price, stock, LocalDateTime.now(), LocalDateTime.now(), imagePath);
                    ProductDAO.add(newProduct);
                }
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }
}

