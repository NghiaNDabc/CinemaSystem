package com.example.hethongdatvexemphim.dao;
import com.example.hethongdatvexemphim.models.Room;
import com.example.hethongdatvexemphim.database.JdbcUtil;


import java.sql.*;
import java.util.ArrayList;

public class RoomDAO implements IDao<Room, Integer> {

    public static RoomDAO getInstance() {
        return new RoomDAO();
    }

    @Override
    public int insert(Room room) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement st = null;
        int ketQua = 0;
        try {
            String sql = "INSERT INTO room (Id_Cinema, RoomName) VALUES (?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, room.getId_Cinema());
            st.setString(2, room.getRoomName());

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
    public int update(Room room) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "UPDATE room SET Id_Cinema = ?, RoomName = ? WHERE Id_Room = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, room.getId_Cinema());
            st.setString(2, room.getRoomName());
            st.setInt(3, room.getId_Room());
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
            String sql = "DELETE FROM room WHERE Id_Room = ?";
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
    public ArrayList<Room> selectAll() {
        ArrayList<Room> ketQua = new ArrayList<>();
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM room";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                int id_Room = rs.getInt("Id_Room");
                int id_Cinema = rs.getInt("Id_Cinema");
                String roomName = rs.getString("RoomName");

                Room room = new Room(id_Room, id_Cinema, roomName);
                ketQua.add(room);
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
    public Room selectById(Integer id) {
        Room room = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM room WHERE Id_Room = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                int id_Cinema = rs.getInt("Id_Cinema");
                String roomName = rs.getString("RoomName");

                room = new Room(id, id_Cinema, roomName);
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
        return room;
    }

    @Override
    public ArrayList<Room> selectByCondition(String condition) {
        // Chưa triển khai
        return null;
    }
}

