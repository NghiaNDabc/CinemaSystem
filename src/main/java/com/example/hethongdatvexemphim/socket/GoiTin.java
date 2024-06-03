package com.example.hethongdatvexemphim.socket;

import com.example.hethongdatvexemphim.models.Movie;

import java.io.Serializable;
import java.util.ArrayList;

public class GoiTin <T> implements  Serializable {
    private ArrayList<T> listT = new ArrayList<>();
    private YeuCau yeuCau;

    public GoiTin() {
    }

    public GoiTin(ArrayList<T> listT, YeuCau yeuCau) {
        this.listT = listT;
        this.yeuCau = yeuCau;
    }

    public YeuCau getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(YeuCau yeuCau) {
        this.yeuCau = yeuCau;
    }

    public ArrayList<T> getListT() {
        return listT;
    }

    public void setListT(ArrayList<T> listT) {
        this.listT = listT;
    }
}
