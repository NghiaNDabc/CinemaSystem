package com.example.hethongdatvexemphim.dao;

import com.example.hethongdatvexemphim.database.JdbcUtil;
import com.example.hethongdatvexemphim.models.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements IDao<User, String> {

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    @Override
    public int insert(User user) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement st = null;
        int ketQua = 0;
        try {
            String sql = "INSERT INTO user (UserName, PassWord, Role, FullName, Dob, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setString(1, user.getUserName());
            st.setString(2, user.getPassWord());
            st.setString(3, user.getRole());
            st.setString(4, user.getFullName());
            st.setDate(5, user.getDob());
            st.setString(6, user.getPhoneNumber());
            st.setString(7, user.getEmail());

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
    public int update(User user) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "UPDATE user SET PassWord = ?, Role = ?, FullName = ?, Dob = ?, PhoneNumber = ?, Email = ? WHERE UserName = ?";
            st = con.prepareStatement(sql);
            st.setString(1, user.getPassWord());
            st.setString(2, user.getRole());
            st.setString(3, user.getFullName());
            st.setDate(4, user.getDob());
            st.setString(5, user.getPhoneNumber());
            st.setString(6, user.getEmail());
            st.setString(7, user.getUserName());
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
    public int delete(String userName) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "DELETE FROM user WHERE UserName = ?";
            st = con.prepareStatement(sql);
            st.setString(1, userName);
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
    public ArrayList<User> selectAll() {
        ArrayList<User> ketQua = new ArrayList<>();
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM user";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("UserName");
                String passWord = rs.getString("PassWord");
                String role = rs.getString("Role");
                String fullName = rs.getString("FullName");
                Date dob = rs.getDate("Dob");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");

                User user = new User(userName, passWord, role, fullName, dob, phoneNumber, email);
                ketQua.add(user);
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
    public User selectById(String userName) {
        User user = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM user WHERE UserName = ?";
            st = con.prepareStatement(sql);
            st.setString(1, userName);
            rs = st.executeQuery();
            if (rs.next()) {
                String passWord = rs.getString("PassWord");
                String role = rs.getString("Role");
                String fullName = rs.getString("FullName");
                Date dob = rs.getDate("Dob");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");

                user = new User(userName, passWord, role, fullName, dob, phoneNumber, email);
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
        return user;
    }

    public User selectByUserPass(String userName, String password) {
        User user = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM user WHERE UserName = ? and PassWord = ?";
            st = con.prepareStatement(sql);
            st.setString(1, userName);
            st.setString(2, password);
            rs = st.executeQuery();
            if (rs.next()) {
                String passWord = rs.getString("PassWord");
                String role = rs.getString("Role");
                String fullName = rs.getString("FullName");
                Date dob = rs.getDate("Dob");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");

                user = new User(userName, passWord, role, fullName, dob, phoneNumber, email);
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
        return user;
    }

    @Override
    public ArrayList<User> selectByCondition(String condition) {
        // Chưa triển khai
        return null;
    }
}

