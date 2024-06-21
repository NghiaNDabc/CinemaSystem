package com.example.hethongdatvexemphim;

import com.example.hethongdatvexemphim.socket.MutilThreadServer;

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
