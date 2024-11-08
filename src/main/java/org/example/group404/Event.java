package org.example.group404;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private int event_id;
    private String name;
    private String description;
    private String date;
    private String time;
    private short seats_available;

    public Event(int event_id, String name, String description, String date, String time, short seats_available) {
        this.event_id = event_id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.seats_available = seats_available;
    }

    public Event(String name, String description, String date, String time, short seats) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.seats_available = seats;
    }

    // Utility method to show JavaFX Alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public Event() {
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeats_available() {
        return seats_available;
    }

    public void setSeats_available(short seats_available) {
        this.seats_available = seats_available;
    }

    public List<Event> viewAllEvents() {
        List<Event> eventList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            dbConnector db = new dbConnector();
            connection = db.getConnection();

            String sql = "SELECT event_id, name, description, date, time, seats_available FROM events";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int event_id = resultSet.getInt("event_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                short seats_available = resultSet.getShort("seats_available");

                eventList.add(new Event(event_id, name, description, date, time, seats_available));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Login Error", "Database connection error: " + ex.getMessage(), Alert.AlertType.ERROR);
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

        return eventList;
    }

    public boolean addNewEvent() {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();

            String sql = "INSERT INTO events (name, date, time, seats_available, description) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, this.name);
            pstmt.setString(2, this.date);
            pstmt.setString(3, this.time);
            pstmt.setShort(4, this.seats_available);
            pstmt.setString(5, this.description);

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

    public boolean searchEvent(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM events WHERE event_id LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setEvent_id(rs.getInt("event_id"));
                this.setName(rs.getString("name"));
                this.setDescription(rs.getString("description"));
                this.setDate(rs.getString("date"));
                this.setTime(rs.getString("time"));
                this.setSeats_available(rs.getShort("seats_available"));
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

    public boolean updateEvent() {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "UPDATE events SET name = ?, date = ?, time = ?, seats_available = ?, description = ? WHERE event_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, this.name);
            pstmt.setString(2, this.date);
            pstmt.setString(3, this.time);
            pstmt.setShort(4, this.seats_available);
            pstmt.setString(5, this.description);
            pstmt.setInt(6, this.event_id);

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

    public boolean deleteEvent(String eventid) {
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "DELETE FROM events WHERE event_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, eventid);

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
}
