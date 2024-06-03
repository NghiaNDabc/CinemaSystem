package com.example.hethongdatvexemphim.dao;

import com.example.hethongdatvexemphim.database.JdbcUtil;
import com.example.hethongdatvexemphim.models.Ticket;

import java.sql.*;
import java.util.ArrayList;

public class TicketDAO implements IDao<Ticket, Integer> {

    public static TicketDAO getInstance() {
        return new TicketDAO();
    }

    @Override
    public int insert(Ticket ticket) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement st = null;
        int ketQua = 0;
        try {
            String sql = "INSERT INTO ticket (Id_ShowTime, UserName, Date, Status) VALUES (?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, ticket.getId_ShowTime());
            st.setString(2, ticket.getUserName());
            st.setTimestamp(3, ticket.getDate());
            st.setString(4, ticket.getStatus());

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
    public int update(Ticket ticket) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "UPDATE ticket SET Id_ShowTime = ?, UserName = ?, Date = ?, Status = ? WHERE Id_Ticket = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, ticket.getId_ShowTime());
            st.setString(2, ticket.getUserName());
            st.setTimestamp(3, ticket.getDate());
            st.setString(4, ticket.getStatus());
            st.setInt(5, ticket.getId_Ticket());
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
            String sql = "DELETE FROM ticket WHERE Id_Ticket = ?";
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
    public ArrayList<Ticket> selectAll() {
        ArrayList<Ticket> ketQua = new ArrayList<>();
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM ticket";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                int id_Ticket = rs.getInt("Id_Ticket");
                int id_ShowTime = rs.getInt("Id_ShowTime");
                String userName = rs.getString("UserName");
                Timestamp date = rs.getTimestamp("Date");
                String status = rs.getString("Status");

                Ticket ticket = new Ticket(id_Ticket, id_ShowTime, userName, date, status);
                ketQua.add(ticket);
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
    public Ticket selectById(Integer id) {
        Ticket ticket = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM ticket WHERE Id_Ticket = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                int id_ShowTime = rs.getInt("Id_ShowTime");
                String userName = rs.getString("UserName");
                Timestamp date = rs.getTimestamp("Date");
                String status = rs.getString("Status");

                ticket = new Ticket(id, id_ShowTime, userName, date, status);
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
        return ticket;
    }

    @Override
    public ArrayList<Ticket> selectByCondition(String condition) {
        // Chưa triển khai
        return null;
    }
}

