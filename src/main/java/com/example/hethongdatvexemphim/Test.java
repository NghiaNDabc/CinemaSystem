package com.example.hethongdatvexemphim;

import com.example.hethongdatvexemphim.dao.MovieDAO;
import com.example.hethongdatvexemphim.dao.UserDAO;
import com.example.hethongdatvexemphim.models.Movie;
import com.example.hethongdatvexemphim.models.User;
import com.example.hethongdatvexemphim.socket.GoiTin;
import com.example.hethongdatvexemphim.socket.YeuCau;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<User> movies = new ArrayList<>();
        User user = new User();
        user.setUserName("1111");
        movies.add(user);
        GoiTin<User>  goiTin = new GoiTin<>();
        goiTin.setListT(movies);
        goiTin.setYeuCau(YeuCau.DangNhap);
        Gson gson = new Gson();
        String json = gson.toJson(goiTin);
        System.out.println(json);
        System.out.println(1);
        GoiTin hihi = new Gson().fromJson(json, GoiTin.class);
        hihi.getListT().forEach(System.out::println);
    }
}
