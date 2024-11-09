package org.example.group404.ClassPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.group404.Database.dbConnector;


public class Admin {
    private final SimpleIntegerProperty admin_id;
    private String name;
    private String email;
    private String username;
    private String password;

    public Admin(int admin_id, String name, String email, String username, String password) {
        this.admin_id = new SimpleIntegerProperty(admin_id);
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public Admin() {
        this.admin_id = new SimpleIntegerProperty(0);
    }

    public Admin(SimpleIntegerProperty adminId) {
        this.admin_id = adminId;
    }

    public int getAdmin_id() {
        return admin_id.get();
    }

    public void setAdmin_id(int adminId) {
        this.admin_id.set(adminId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validateLogin(String username, String password) {
        boolean isValid = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            dbConnector db = new dbConnector();
            connection = db.getConnection();

            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Login Error", "Database connection error: " + ex.getMessage(), AlertType.ERROR);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return isValid;
    }
    public boolean saveToDatabase() {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();

            String sql = "INSERT INTO admin (name, email, username, password) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.username);
            pstmt.setString(4, this.password);

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) {  }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) {  }
            }
        }

        return result;
    }


    public List<Customer> viewAllUsers() {
        List<Customer> customerList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            dbConnector db = new dbConnector();
            connection = db.getConnection();

            String sql = "SELECT * FROM customers";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customer_id = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");

                customerList.add(new Customer(customer_id, name, phone, email ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Database connection error: " + ex.getMessage(), AlertType.ERROR);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return customerList;
    }

    public List<Reservation> viewAllReservation() {
        List<Reservation> reservationList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            dbConnector db = new dbConnector();
            connection = db.getConnection();

            String sql = "SELECT * FROM reservations";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int reservation_id = resultSet.getInt("reservation_id");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                int no_of_people = resultSet.getInt("guests");

                reservationList.add(new Reservation(reservation_id, name, date, time, no_of_people));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Database connection error: " + ex.getMessage(), AlertType.ERROR);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return reservationList;
    }
    public boolean addNewAdmin() {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();

            String sql = "INSERT INTO admin (name, email, username, password) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, this.name);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.username);
            pstmt.setString(4, this.password);

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }
        return result;
    }

    public boolean searchAdmin(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM admin WHERE username LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setAdmin_id(rs.getInt("admin_id"));
                this.setName(rs.getString("name"));
                this.setEmail(rs.getString("email"));
                this.setUsername(rs.getString("username"));
                this.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isFound;
    }
    public boolean deleteAdmin(String username) {
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "DELETE FROM admin WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            int rowsAffected = pstmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isDeleted;
    }
}

