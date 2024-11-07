package org.example.group404;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Customer {
    private int customer_id;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Customer(int customer_id, String name, String email, String phone, String username, String password) {
        this.customer_id = customer_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }
    // Utility method to show JavaFX Alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Customer() {
    }

    Customer(int customer_id, String name, String phone, String email) {
        this.customer_id = customer_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

            String sql = "SELECT * FROM customers WHERE username = ? AND password = ?";
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
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
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

            String sql = "INSERT INTO customers (name, email, phone, username, password) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.phone);
            pstmt.setString(4, this.username);
            pstmt.setString(5, this.password);

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public boolean searchCustomer(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM customers WHERE name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setCustomer_id(rs.getInt("customer_id"));
                this.setName(rs.getString("name"));
                this.setEmail(rs.getString("email"));
                this.setPhone(rs.getString("phone"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isFound;
    }

    public boolean deleteCustomer(String name) {
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "DELETE FROM customers WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            int rowsAffected = pstmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isDeleted;
    }

    public boolean updateProfile(String currentUsername) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, username = ?, password = ? WHERE username = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, this.name);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.phone);
            pstmt.setString(4, this.username);
            pstmt.setString(5, this.password);
            pstmt.setString(6, currentUsername);

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean searchProfile(String username) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM customers WHERE username LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setName(rs.getString("name"));
                this.setEmail(rs.getString("email"));
                this.setPhone(rs.getString("phone"));
                this.setUsername(rs.getString("username"));
                this.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isFound;
    }
}
