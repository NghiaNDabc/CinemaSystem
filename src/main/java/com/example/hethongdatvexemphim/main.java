package com.example.hethongdatvexemphim;

import com.example.hethongdatvexemphim.dao.CinemaDAO;
import com.example.hethongdatvexemphim.dao.MovieDAO;
import com.example.hethongdatvexemphim.database.JdbcUtil;
import com.example.hethongdatvexemphim.models.Cinema;
import com.example.hethongdatvexemphim.models.Movie;
import com.example.hethongdatvexemphim.socket.MutilThreadServer;

import java.sql.Connection;

public class main {
    public static void main(String[] args) {
        MutilThreadServer server = new MutilThreadServer();
        Thread t = new Thread(server);
        t.start();
//        Movie x = MovieDAO.getInstance().selectById(1);
//        System.out.println(x.getDescripsion());
//        System.out.println(x.getImage());
    }
}
