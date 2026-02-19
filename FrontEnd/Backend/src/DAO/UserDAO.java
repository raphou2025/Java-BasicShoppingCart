package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import db.DBConnection;
import java.time.LocalDateTime;

public class UserDAO {

    // =====================
    // LOGIN: return user ID if successful, -1 if failed
    // =====================
    public static int login(String username, String password) {
        String sql = "SELECT e_id FROM e_users WHERE username=? AND password=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("e_id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // =====================
    // REGISTER: returns true if successful
    // =====================
    public static boolean register(User user) {
        String sql = "INSERT INTO e_users(username,password,full_name,email,phone) VALUES(?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // ✅ Important! This will show why registration failed
            return false;
        }
    }

    // =====================
    // CREATE: for admin or programmatic use
    // =====================
    public void create(User u) {
        String sql = "INSERT INTO e_users(username,password,full_name,email,phone) VALUES(?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhone());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =====================
    // READ: get all users
    // =====================
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM e_users ORDER BY e_id";

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
               list.add(new User(
        rs.getInt("e_id"),
        rs.getString("username"),
        rs.getString("password"),
        rs.getString("full_name"),
        rs.getString("email"),
        rs.getString("phone"),
        rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
        rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =====================
    // UPDATE: update user info
    // =====================
    public boolean update(User u) {
        String sql = "UPDATE e_users SET username=?, password=?, full_name=?, email=?, phone=? WHERE e_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhone());
            ps.setInt(6, u.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =====================
    // DELETE: remove user
    // =====================
    public boolean delete(int id) {
        String sql = "DELETE FROM e_users WHERE e_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

