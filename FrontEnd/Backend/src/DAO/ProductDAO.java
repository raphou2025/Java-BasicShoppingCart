// package DAO;

// import db.DBConnection;
// import model.Product;

// import java.sql.*;
// import java.time.LocalDateTime;
// import java.util.ArrayList;

// public class ProductDAO {

//     // CREATE: Add new product
//     public static boolean addProduct(Product product) {
//         String sql = "INSERT INTO e_products(name, description, price, stock, created_at, updated_at) " +
//                      "VALUES (?, ?, ?, ?, ?, ?)";
//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setString(1, product.getName());
//             ps.setString(2, product.getDescription());
//             ps.setDouble(3, product.getPrice());
//             ps.setInt(4, product.getStock());
//             ps.setTimestamp(5, Timestamp.valueOf(product.getCreatedAt()));
//             ps.setTimestamp(6, Timestamp.valueOf(product.getUpdatedAt()));

//             return ps.executeUpdate() > 0;

//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // READ: Get all products
//     public static ArrayList<Product> getAllProducts() {
//         ArrayList<Product> list = new ArrayList<>();
//         String sql = "SELECT * FROM e_products";

//         try (Connection c = DBConnection.getConnection();
//              Statement st = c.createStatement();
//              ResultSet rs = st.executeQuery(sql)) {

//             while (rs.next()) {
//                 list.add(new Product(
//                         rs.getInt("id"),
//                         rs.getString("name"),
//                         rs.getString("description"),
//                         rs.getDouble("price"),
//                         rs.getInt("stock"),
//                         rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
//                         rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
//                 ));
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return list;
//     }

//     // READ: Get a single product by ID
//     public static Product getProductById(int id) {
//         String sql = "SELECT * FROM e_products WHERE id=?";
//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setInt(1, id);
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) {
//                 return new Product(
//                         rs.getInt("id"),
//                         rs.getString("name"),
//                         rs.getString("description"),
//                         rs.getDouble("price"),
//                         rs.getInt("stock"),
//                         rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
//                         rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
//                 );
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     // UPDATE: Update a product
//     public static boolean updateProduct(Product product) {
//         String sql = "UPDATE e_products SET name=?, description=?, price=?, stock=?, updated_at=? WHERE id=?";
//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setString(1, product.getName());
//             ps.setString(2, product.getDescription());
//             ps.setDouble(3, product.getPrice());
//             ps.setInt(4, product.getStock());
//             ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
//             ps.setInt(6, product.getId());

//             return ps.executeUpdate() > 0;

//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // DELETE: Remove a product
//     public static boolean deleteProduct(int id) {
//         String sql = "DELETE FROM e_products WHERE id=?";
//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setInt(1, id);
//             return ps.executeUpdate() > 0;

//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
// }

package DAO;

import db.DBConnection;
import model.Product;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProductDAO {

    // ================= CREATE =================
    public static boolean addProduct(Product product) {
        String sql = """
            INSERT INTO e_products
            (name, description, price, stock, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setTimestamp(5, Timestamp.valueOf(product.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(product.getUpdatedAt()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= READ ALL =================
    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM e_products ORDER BY product_id";

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= READ BY ID =================
    public static Product getProductById(int productId) {
        String sql = "SELECT * FROM e_products WHERE product_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= UPDATE =================
    public static boolean updateProduct(Product product) {
        String sql = """
            UPDATE e_products
            SET name=?, description=?, price=?, stock=?, updated_at=?
            WHERE product_id=?
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, product.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE =================
    public static boolean deleteProduct(int productId) {
        String sql = "DELETE FROM e_products WHERE product_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

