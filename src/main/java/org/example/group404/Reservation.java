package org.example.group404;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reservation {
    private int reservation_id;
    private String customer_name;
    private String date;
    private String time;
    private int no_of_people;

    public Reservation(int reservation_id, String customer_name, String date, String time, int no_of_people) {
        this.reservation_id = reservation_id;
        this.customer_name = customer_name;
        this.date = date;
        this.time = time;
        this.no_of_people = no_of_people;

    }

    public Reservation() {
    }

    Reservation(int reservation_id, String name, String date, String time, short guests) {
        this.reservation_id = reservation_id;
        this.date = date;
        this.time = time;
        this.no_of_people = guests;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public int getNo_of_people() {
        return no_of_people;
    }

    public void setNo_of_people(int no_of_people) {
        this.no_of_people = no_of_people;
    }

    public int addNewRes() {
        int reservationId = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();

            String sql = "INSERT INTO reservations (name, date, time, guests) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, this.customer_name);
            pstmt.setString(2, this.date);
            pstmt.setString(3, this.time);
            pstmt.setInt(4, this.no_of_people);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reservationId = generatedKeys.getInt(1);
                }
            }
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
        return reservationId;
    }

    public boolean searchDate(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM reservations WHERE date LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setReservation_id(rs.getInt("reservation_id"));
                this.setDate(rs.getString("date"));
                this.setTime(rs.getString("time"));
                this.setNo_of_people(rs.getInt("guests"));
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

    public boolean searchID(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM reservations WHERE reservation_id LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setReservation_id(rs.getInt("reservation_id"));
                this.setCustomer_name(rs.getString("name"));
                this.setDate(rs.getString("date"));
                this.setTime(rs.getString("time"));
                this.setNo_of_people(rs.getInt("guests"));
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

    public boolean searchName(String keyword) {
        boolean isFound = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM reservations WHERE name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isFound = true;
                this.setReservation_id(rs.getInt("reservation_id"));
                this.setCustomer_name(rs.getString("name"));
                this.setDate(rs.getString("date"));
                this.setTime(rs.getString("time"));
                this.setNo_of_people(rs.getInt("guests"));
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

    public boolean updateRes(int reservationId) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "UPDATE reservations SET name = ?, date = ?, time = ?, guests = ? WHERE reservation_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, this.customer_name);
            pstmt.setString(2, this.date);
            pstmt.setString(3, this.time);
            pstmt.setInt(4, this.no_of_people);
            pstmt.setInt(5, reservationId);

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

    public boolean deleteReservation(String reservationid) {
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            String sql = "DELETE FROM reservations WHERE reservation_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reservationid);

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
