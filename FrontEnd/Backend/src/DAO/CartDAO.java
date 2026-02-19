// package DAO;

// import db.DBConnection;
// import model.CartItem;
// import model.Product;

// import java.sql.*;
// import java.time.LocalDateTime;
// import java.util.ArrayList;

// public class CartDAO {

//     // ================= ADD TO CART =================
//     public static boolean addToCart(int userId, int productId, int quantity) {
//         String sql = """
//             INSERT INTO e_carts (user_id, product_id, quantity, status, added_at)
//             VALUES (?, ?, ?, 'active', ?)
//         """;

//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setInt(1, userId);
//             ps.setInt(2, productId);
//             ps.setInt(3, quantity);
//             ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

//             return ps.executeUpdate() > 0;

//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // ================= VIEW CART =================
//     public static ArrayList<CartItem> getCartItems(int userId) {
//         ArrayList<CartItem> list = new ArrayList<>();

//         String sql = """
//             SELECT
//                 c.id AS cart_id,
//                 c.user_id,
//                 c.quantity,
//                 c.status,
//                 c.added_at,

//                 p.product_id,
//                 p.name,
//                 p.description,
//                 p.price,
//                 p.stock,
//                 p.created_at,
//                 p.updated_at
//             FROM e_carts c
//             JOIN e_products p ON c.product_id = p.product_id
//             WHERE c.user_id = ? AND c.status = 'active'
//         """;

//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setInt(1, userId);
//             ResultSet rs = ps.executeQuery();

//             while (rs.next()) {
//                 Product product = new Product(
//                         rs.getInt("product_id"),
//                         rs.getString("name"),
//                         rs.getString("description"),
//                         rs.getDouble("price"),
//                         rs.getInt("stock"),
//                         rs.getTimestamp("created_at").toLocalDateTime(),
//                         rs.getTimestamp("updated_at").toLocalDateTime()
//                 );

//                 CartItem item = new CartItem(
//                         rs.getInt("cart_id"),
//                         rs.getInt("user_id"),
//                         product,
//                         rs.getInt("quantity"),
//                         rs.getString("status"),
//                         rs.getTimestamp("added_at").toLocalDateTime()
//                 );

//                 list.add(item);
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return list;
//     }

//     // ================= UPDATE QUANTITY =================
//     public static boolean updateQuantity(int cartId, int quantity) {
//         String sql = "UPDATE e_carts SET quantity=? WHERE id=?";

//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setInt(1, quantity);
//             ps.setInt(2, cartId);
//             return ps.executeUpdate() > 0;

//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // ================= DELETE ITEM =================
//     public static boolean deleteCartItem(int cartId) {
//         String sql = "DELETE FROM e_carts WHERE id=?";

//         try (Connection c = DBConnection.getConnection();
//              PreparedStatement ps = c.prepareStatement(sql)) {

//             ps.setInt(1, cartId);
//             return ps.executeUpdate() > 0;

//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
// }

package DAO;

import db.DBConnection;
import model.CartItem;
import model.Product;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CartDAO {

    // ================= VIEW CART =================
    public static ArrayList<CartItem> getCartItems(int userId) {
        ArrayList<CartItem> list = new ArrayList<>();
        String sql = """
            SELECT c.cart_id, c.user_id, c.quantity, c.status, c.added_at,
                   p.product_id, p.name, p.description, p.price, p.stock, p.created_at, p.updated_at
            FROM e_carts c
            JOIN e_products p ON c.product_id = p.product_id
            WHERE c.user_id = ? AND LOWER(c.status) = 'active'
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );

                CartItem item = new CartItem(
                    rs.getInt("cart_id"), 
                    rs.getInt("user_id"),
                    product,
                    rs.getInt("quantity"),
                    rs.getString("status"),
                    rs.getTimestamp("added_at").toLocalDateTime()
                );
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= ADD TO CART =================
    public static boolean addToCart(int userId, int productId, int quantity) {
        String sql = "INSERT INTO e_carts (user_id, product_id, quantity, status, added_at) VALUES (?, ?, ?, 'active', ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= UPDATE QUANTITY =================
    public static boolean updateQuantity(int cartId, int quantity) {
        // Updated to use cart_id based on your pgAdmin screenshot
        String sql = "UPDATE e_carts SET quantity = ? WHERE cart_id = ?"; 
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE ITEM =================
    public static boolean deleteCartItem(int cartId) {
        // Updated to use cart_id based on your pgAdmin screenshot
        String sql = "DELETE FROM e_carts WHERE cart_id = ?"; 
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}