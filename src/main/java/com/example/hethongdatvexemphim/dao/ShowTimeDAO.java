package com.example.hethongdatvexemphim.dao;

import com.example.hethongdatvexemphim.database.JdbcUtil;
import com.example.hethongdatvexemphim.models.ShowTime;

import java.sql.*;
import java.util.ArrayList;

public class ShowTimeDAO implements IDao<ShowTime, Integer> {

    public static ShowTimeDAO getInstance() {
        return new ShowTimeDAO();
    }

    @Override
    public int insert(ShowTime showTime) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement st = null;
        int ketQua = 0;
        try {
            String sql = "INSERT INTO showtime (Id_Room, Id_Movie, Seats, StartTime, EndTime, Date) VALUES (?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, showTime.getId_Room());
            st.setInt(2, showTime.getId_Movie());
            st.setString(3, showTime.getSeats());
            st.setTime(4, showTime.getStartTime());
            st.setTime(5, showTime.getEndTime());
            st.setDate(6, showTime.getDate());

            ketQua = st.executeUpdate();
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    @Override
    public int update(ShowTime showTime) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "UPDATE showtime SET Id_Room = ?, Id_Movie = ?, Seats = ?, StartTime = ?, EndTime = ?, Date = ? WHERE Id_ShowTime = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, showTime.getId_Room());
            st.setInt(2, showTime.getId_Movie());
            st.setString(3, showTime.getSeats());
            st.setTime(4, showTime.getStartTime());
            st.setTime(5, showTime.getEndTime());
            st.setDate(6, showTime.getDate());
            st.setInt(7, showTime.getId_ShowTime());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    @Override
    public int delete(Integer id) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "DELETE FROM showtime WHERE Id_ShowTime = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    @Override
    public ArrayList<ShowTime> selectAll() {
        ArrayList<ShowTime> ketQua = new ArrayList<>();
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM showtime";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                int id_ShowTime = rs.getInt("Id_ShowTime");
                int id_Room = rs.getInt("Id_Room");
                int id_Movie = rs.getInt("Id_Movie");
                String seats = rs.getString("Seats");
                Time startTime = rs.getTime("StartTime");
                Time endTime = rs.getTime("EndTime");
                Date date = rs.getDate("Date");

                ShowTime showTime = new ShowTime(id_ShowTime, id_Room, id_Movie, seats, startTime, endTime, date);
                ketQua.add(showTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    @Override
    public ShowTime selectById(Integer id) {
        ShowTime showTime = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM showtime WHERE Id_ShowTime = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                int id_Room = rs.getInt("Id_Room");
                int id_Movie = rs.getInt("Id_Movie");
                String seats = rs.getString("Seats");
                Time startTime = rs.getTime("StartTime");
                Time endTime = rs.getTime("EndTime");
                Date date = rs.getDate("Date");

                showTime = new ShowTime(id, id_Room, id_Movie, seats, startTime, endTime, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return showTime;
    }

    @Override
    public ArrayList<ShowTime> selectByCondition(String condition) {
        // Chưa triển khai
        return null;
    }
}

