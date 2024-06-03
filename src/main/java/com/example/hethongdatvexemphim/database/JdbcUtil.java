package com.example.hethongdatvexemphim.database;




import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    public static Connection getConnection() {
        Connection con = null;
        try{
            String url = "jdbc:mysql://localhost:3306/Cinema_System";;
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }
    public static  void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void  printInfo(Connection conn) {
        try{
            if(conn != null) {
                System.out.println(conn.getMetaData().toString());
                System.out.println("nghoadz");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
