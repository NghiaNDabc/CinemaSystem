package com.example.hethongdatvexemphim.dao;

import java.util.ArrayList;

public interface IDao <T,M> {
    public int insert(T t);

    public int update(T t);

    public int delete(M id);

    public ArrayList<T> selectAll();

    public T selectById(M id);

    public ArrayList<T> selectByCondition(String condition);
}
