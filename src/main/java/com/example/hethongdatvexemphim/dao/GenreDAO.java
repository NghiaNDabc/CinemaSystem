package com.example.hethongdatvexemphim.dao;

import com.example.hethongdatvexemphim.util.JdbcUtil;
import com.example.hethongdatvexemphim.models.Genre;

import java.sql.*;
import java.util.ArrayList;

public class GenreDAO implements IDao<Genre, Integer> {

    public static GenreDAO getInstance() {
        return new GenreDAO();
    }

    @Override
    public int insert(Genre genre) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement st = null;
        int ketQua = 0;
        try {
            String sql = "INSERT INTO genre (GenreName) VALUES (?)";
            st = conn.prepareStatement(sql);
            st.setString(1, genre.getGenreName());

            // Thực thi câu lệnh SQL
            ketQua = st.executeUpdate();
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng PreparedStatement và Connection
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
    public int update(Genre genre) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "UPDATE genre SET GenreName = ? WHERE Id_Genre = ?";
            st = con.prepareStatement(sql);
            st.setString(1, genre.getGenreName());
            st.setInt(2, genre.getId_Genre());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng PreparedStatement và Connection
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
            String sql = "DELETE FROM genre WHERE Id_Genre = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng PreparedStatement và Connection
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
    public ArrayList<Genre> selectAll() {
        ArrayList<Genre> ketQua = new ArrayList<>();
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM genre";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id_Genre");
                String name = rs.getString("GenreName");
                Genre genre = new Genre(id, name);
                ketQua.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng ResultSet, PreparedStatement và Connection
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
    public Genre selectById(Integer id) {
        Genre genre = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM genre WHERE Id_Genre = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString("GenreName");
                genre = new Genre(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng ResultSet, PreparedStatement và Connection
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return genre;
    }

    @Override
    public ArrayList<Genre> selectByCondition(String condition) {
        // Chưa triển khai
        return null;
    }
}

