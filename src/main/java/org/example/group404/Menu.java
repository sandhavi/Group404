package org.example.group404;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Menu {
    private final SimpleIntegerProperty item_id;
    private String name;
    private String description;
    private double price;
    private String category;

    public Menu(int item_id, String name, String description, double price, String category) {
        this.item_id = new SimpleIntegerProperty(item_id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Menu() {
        this.item_id = new SimpleIntegerProperty(0);
    }

    public Menu(SimpleIntegerProperty itemId) {
        this.item_id = itemId;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int getItem_id() {
        return item_id.get();
    }

    public void setitem_id(int itemId) {
        this.item_id.set(itemId);
    }

    public SimpleIntegerProperty item_idProperty() {
        return item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Menu> viewAllMenu() {
        List<Menu> menuList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            dbConnector db = new dbConnector();
            connection = db.getConnection();

            String sql = "SELECT item_id, name, description, price, category FROM menu_items";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int item_id = resultSet.getInt("item_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String category = resultSet.getString("category");

                menuList.add(new Menu(item_id, name, description, price, category));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Database Error", "Database connection error: " + ex.getMessage(), AlertType.ERROR);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return menuList;
    }

    public boolean addNewItem() {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "INSERT INTO menu_items (name, category, price, description) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.category);
            pstmt.setDouble(3, this.price);
            pstmt.setString(4, this.description);

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
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
        return result;
    }

    public boolean searchMenu(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM menu_items WHERE name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();
            if (rs.next()) {
                isFound = true;
                this.setitem_id(rs.getInt("item_id"));
                this.setName(rs.getString("name"));
                this.setDescription(rs.getString("description"));
                this.setPrice(rs.getDouble("price"));
                this.setCategory(rs.getString("category"));
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

    public boolean searchMenu2(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM menu_items WHERE item_id LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setitem_id(rs.getInt("item_id"));
                this.setName(rs.getString("name"));
                this.setDescription(rs.getString("description"));
                this.setPrice(rs.getDouble("price"));
                this.setCategory(rs.getString("category"));
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

    public boolean deleteMenu(String item_id) {
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "DELETE FROM menu_items WHERE item_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(item_id));

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

    public List<Menu> getItemsByCategory(String category) {
        List<Menu> items = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM menu_items WHERE category = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Menu item = new Menu();
                item.setitem_id(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setCategory(rs.getString("category"));
                items.add(item);
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
        return items;
    }

    public boolean updateMenu() {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "UPDATE menu_items SET name = ?, category = ?, price = ?, description = ? WHERE item_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.category);
            pstmt.setDouble(3, this.price);
            pstmt.setString(4, this.description);
            pstmt.setInt(5, this.getItem_id());

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
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
        return result;
    }
}
