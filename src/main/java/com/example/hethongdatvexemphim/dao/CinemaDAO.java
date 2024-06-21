package com.example.hethongdatvexemphim.dao;

import com.example.hethongdatvexemphim.util.JdbcUtil;
import com.example.hethongdatvexemphim.models.Cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CinemaDAO implements IDao<Cinema,Integer>{
    public static CinemaDAO getInstance() {
        return new CinemaDAO();
    }
    @Override
    public int insert(Cinema cinema) {
        Connection conn = JdbcUtil.getConnection();
        int ketQua = 0;
        try{
            String sql = "INSERT INTO cinema( CinemaName, Address, Status) VALUES (?,?,?)";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, cinema.getCinemaName());
            st.setString(2, cinema.getAddress());
            st.setString(3, cinema.getStatus());

            // Bước 3: thực thi câu lệnh SQL
             ketQua = st.executeUpdate();
            // Bước 4:
            System.out.println("Bạn đã thực thi: "+ sql);
            System.out.println("Có "+ ketQua+" dòng bị thay đổi!");
            // Bước 5:
            JdbcUtil.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ketQua ;
    }

    @Override
    public int update(Cinema cinema) {
        //UPDATE cinema SET CinemaName=?,Address=?,Status= WHERE 1
        int ketQua = 0;
        try{
            Connection con = JdbcUtil.getConnection();
            String sql = "Update cinema set CinemaName=?, Address=?, Status=? WHERE Id_Cinema = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, cinema.getCinemaName());
            st.setString(2, cinema.getAddress());
            st.setString(3, cinema.getStatus());
            st.setInt(4, cinema.getId_Cinema());
            ketQua = st.executeUpdate();
            JdbcUtil.closeConnection(con);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(Integer id) {
        int ketQua = 0;
        try{
            Connection con = JdbcUtil.getConnection();
            String sql = "DELETE from cinema WHERE Id_Cinema = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ketQua = st.executeUpdate();
            JdbcUtil.closeConnection(con);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ketQua;
    }
    @Override
    public ArrayList<Cinema> selectAll() {
        ArrayList ketQua = new ArrayList();
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JdbcUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT * FROM Cinema";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while(rs.next()) {
                int id = rs.getInt("Id_Cinema");
                String Name = rs.getString("CinemaName");
                String Address = rs.getString("Address");
                String Status = rs.getString("Status");

                Cinema cinema = new Cinema(id, Name, Address, Status);
                ketQua.add(cinema);
            }
            // Bước 5:
            JdbcUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }
    @Override
    public Cinema selectById(Integer id) {
        Cinema cinema = null;
        try{
            Connection con = JdbcUtil.getConnection();
            String sql = "SELECT * FROM cinema WHERE Id_Cinema = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                String Name = rs.getString("CinemaName");
                String Address = rs.getString("Address");
                String Status = rs.getString("Status");

                cinema = new Cinema(id, Name, Address, Status);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cinema;
    }
    @Override
    public ArrayList<Cinema> selectByCondition(String condition) {
        return null;
    }
}
