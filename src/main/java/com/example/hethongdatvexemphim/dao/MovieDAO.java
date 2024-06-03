package com.example.hethongdatvexemphim.dao;

import com.example.hethongdatvexemphim.database.JdbcUtil;
import com.example.hethongdatvexemphim.models.Movie;

import java.sql.*;
import java.util.ArrayList;

public class MovieDAO implements IDao<Movie, Integer> {

    public static MovieDAO getInstance() {
        return new MovieDAO();
    }

    @Override
    public int insert(Movie movie) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement st = null;
        int ketQua = 0;
        try {
            String sql = "INSERT INTO movie (Id_Genre, MovieName, ReleasedDate, Country, Duration, Director, Language, Censorship, Version, Price, Descripsion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, movie.getId_Genre());
            st.setString(2, movie.getMovieName());
            st.setDate(3, movie.getReleasedDate());
            st.setString(4, movie.getCountry());
            st.setTime(5, movie.getDuration());
            st.setString(6, movie.getDirector());
            st.setString(7, movie.getLanguage());
            st.setInt(8, movie.getCensorship());
            st.setFloat(9, movie.getVersion());
            st.setFloat(10, movie.getPrice());
            st.setString(11, movie.getDescripsion());

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
    public int update(Movie movie) {
        int ketQua = 0;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        try {
            String sql = "UPDATE movie SET Id_Genre = ?, MovieName = ?, ReleasedDate = ?, Country = ?, Duration = ?, Director = ?, Language = ?, Censorship = ?, Version = ?, Price = ?, Descripsion = ? WHERE Id_Movie = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, movie.getId_Genre());
            st.setString(2, movie.getMovieName());
            st.setDate(3, movie.getReleasedDate());
            st.setString(4, movie.getCountry());
            st.setTime(5, movie.getDuration());
            st.setString(6, movie.getDirector());
            st.setString(7, movie.getLanguage());
            st.setInt(8, movie.getCensorship());
            st.setFloat(9, movie.getVersion());
            st.setFloat(10, movie.getPrice());
            st.setString(11, movie.getDescripsion());
            st.setInt(12, movie.getId_Movie());
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
            String sql = "DELETE FROM movie WHERE Id_Movie = ?";
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
    public ArrayList<Movie> selectAll() {
        ArrayList<Movie> ketQua = new ArrayList<>();
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM movie";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                int id_Movie = rs.getInt("Id_Movie");
                int id_Genre = rs.getInt("Id_Genre");
                String movieName = rs.getString("MovieName");
                Date releasedDate = rs.getDate("ReleasedDate");
                String country = rs.getString("Country");
                Time duration = rs.getTime("Duration");
                String director = rs.getString("Director");
                String language = rs.getString("Language");
                int censorship = rs.getInt("Censorship");
                float version = rs.getFloat("Version");
                float price = rs.getFloat("Price");
                String descripsion = rs.getString("Descripsion");
                String image = rs.getString("Image");

                Movie movie = new Movie(id_Movie, id_Genre, movieName, releasedDate, country, duration, director, language, censorship, version, price, descripsion,image);
                ketQua.add(movie);
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
    public Movie selectById(Integer id) {
        Movie movie = null;
        Connection con = JdbcUtil.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM movie WHERE Id_Movie = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                int id_Genre = rs.getInt("Id_Genre");
                String movieName = rs.getString("MovieName");
                Date releasedDate = rs.getDate("ReleasedDate");
                String country = rs.getString("Country");
                Time duration = rs.getTime("Duration");
                String director = rs.getString("Director");
                String language = rs.getString("Language");
                int censorship = rs.getInt("Censorship");
                float version = rs.getFloat("Version");
                float price = rs.getFloat("Price");
                String descripsion = rs.getString("Descripsion");
                String Image = rs.getString("Image");

                movie = new Movie(id, id_Genre, movieName, releasedDate, country, duration, director, language, censorship, version, price, descripsion,Image);
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
        return movie;
    }

    @Override
    public ArrayList<Movie> selectByCondition(String condition) {
        // Chưa triển khai
        return null;
    }
}

