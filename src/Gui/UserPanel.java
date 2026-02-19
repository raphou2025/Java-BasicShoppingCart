package Gui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserPanel extends JPanel {

    private JTable table;
    private JTextField txtId, txtUsername, txtPassword, txtFullName, txtEmail, txtPhone;
    private final UserDAO dao = new UserDAO();

    public UserPanel() {
        setLayout(new BorderLayout(10, 10));

        // TABLE
        table = new JTable();
        refreshTable();
        table.getSelectionModel().addListSelectionListener(e -> loadSelected());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // FORM
        JPanel form = new JPanel(new GridLayout(4, 4, 8, 8));

        txtId = new JTextField(); txtId.setEnabled(false);
        txtUsername = new JTextField();
        txtPassword = new JTextField();
        txtFullName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();

        JButton btnCreate = new JButton("Create");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        btnCreate.addActionListener(e -> {
            dao.create(new User(
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtFullName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText()
            ));
            refreshTable();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            dao.update(new User(
                    Integer.parseInt(txtId.getText()),
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtFullName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    null, null
            ));
            refreshTable();
        });

        btnDelete.addActionListener(e -> {
            dao.delete(Integer.parseInt(txtId.getText()));
            refreshTable();
            clear();
        });

        btnClear.addActionListener(e -> clear());

        form.add(new JLabel("ID")); form.add(txtId);
        form.add(new JLabel("Username")); form.add(txtUsername);
        form.add(new JLabel("Password")); form.add(txtPassword);
        form.add(new JLabel("Full Name")); form.add(txtFullName);
        form.add(new JLabel("Email")); form.add(txtEmail);
        form.add(new JLabel("Phone")); form.add(txtPhone);
        form.add(btnCreate); form.add(btnUpdate);
        form.add(btnDelete); form.add(btnClear);

        add(form, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Username", "Full Name", "Email", "Phone", "Created"}, 0);

        for (User u : dao.findAll()) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getUsername(),
                    u.getFullName(),
                    u.getEmail(),
                    u.getPhone(),
                    u.getCreatedAt()
            });
        }
        table.setModel(model);
    }

    private void loadSelected() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            txtId.setText(table.getValueAt(r, 0).toString());
            txtUsername.setText(table.getValueAt(r, 1).toString());
            txtFullName.setText(table.getValueAt(r, 2).toString());
            txtEmail.setText(table.getValueAt(r, 3).toString());
            txtPhone.setText(table.getValueAt(r, 4).toString());
        }
    }

    private void clear() {
        txtId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }
}

