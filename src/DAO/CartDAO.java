
package DAO;

import db.DBConnection;
import model.CartItem;
import model.Product;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CartDAO {

    // ================= ADD TO CART =================
    public static boolean addToCart(int userId, int productId, int quantity) {
        String sql = """
            INSERT INTO e_carts (user_id, product_id, quantity, status, added_at)
            VALUES (?, ?, ?, 'active', ?)
        """;

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

    // ================= SMART ADD TO CART =================
    // Adds or updates quantity if already exists
    public static boolean smartAddToCart(int userId, int productId, int quantity) {
        if (itemExistsInCart(userId, productId)) {
            return updateCartItemQuantity(userId, productId, quantity);
        } else {
            return addToCart(userId, productId, quantity);
        }
    }

    // ================= CHECK ITEM IN CART =================
    public static boolean itemExistsInCart(int userId, int productId) {
        String sql = "SELECT COUNT(*) as count FROM e_carts WHERE user_id=? AND product_id=? AND status='active'";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= UPDATE CART ITEM QUANTITY =================
    public static boolean updateCartItemQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE e_carts SET quantity = quantity + ? WHERE user_id=? AND product_id=? AND status='active'";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= VIEW CART =================
    public static ArrayList<CartItem> getCartItems(int userId) {
        ArrayList<CartItem> list = new ArrayList<>();
        String sql = """
            SELECT 
                c.cart_id, c.user_id, c.quantity, c.status, c.added_at,
                p.product_id, p.name, p.description, p.price, p.stock, p.image_path, p.created_at, p.updated_at
            FROM e_carts c
            JOIN e_products p ON c.product_id = p.product_id
            WHERE c.user_id=? AND c.status='active'
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
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_path") // <-- include image path
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

    // ================= GET CART ITEM COUNT =================
    public static int getCartItemCount(int userId) {
        String sql = "SELECT COUNT(*) as count FROM e_carts WHERE user_id=? AND status='active'";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt("count");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ================= GET CART TOTAL =================
    public static double getCartTotal(int userId) {
        String sql = """
            SELECT SUM(p.price * c.quantity) as total
            FROM e_carts c
            JOIN e_products p ON c.product_id = p.product_id
            WHERE c.user_id=? AND c.status='active'
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDouble("total");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    // ================= UPDATE CART ITEM =================
    public static boolean updateCartItem(int cartId, int quantity, String status) {
        String sql = "UPDATE e_carts SET quantity=?, status=? WHERE cart_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setString(2, status);
            ps.setInt(3, cartId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE CART ITEM =================
    public static boolean deleteCartItem(int cartId) {
        String sql = "DELETE FROM e_carts WHERE cart_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= CLEAR USER CART =================
    public static boolean clearUserCart(int userId) {
        String sql = "DELETE FROM e_carts WHERE user_id=? AND status='active'";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= GET CART ITEM BY ID =================
    public static CartItem getCartItemById(int cartId) {
        String sql = """
            SELECT 
                c.cart_id, c.user_id, c.quantity, c.status, c.added_at,
                p.product_id, p.name, p.description, p.price, p.stock, p.image_path, p.created_at, p.updated_at
            FROM e_carts c
            JOIN e_products p ON c.product_id = p.product_id
            WHERE c.cart_id=?
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_path")
                );

                return new CartItem(
                        rs.getInt("cart_id"),
                        rs.getInt("user_id"),
                        product,
                        rs.getInt("quantity"),
                        rs.getString("status"),
                        rs.getTimestamp("added_at").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= GET ALL CARTS (ADMIN) =================
    public static ArrayList<CartItem> getAllCarts() {
        ArrayList<CartItem> list = new ArrayList<>();
        String sql = """
            SELECT 
                c.cart_id, c.user_id, c.quantity, c.status, c.added_at,
                p.product_id, p.name, p.description, p.price, p.stock, p.image_path, p.created_at, p.updated_at
            FROM e_carts c
            JOIN e_products p ON c.product_id = p.product_id
            ORDER BY c.added_at DESC
        """;

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_path")
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
}
