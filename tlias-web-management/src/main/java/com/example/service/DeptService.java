package com.example.service;

import com.example.pojo.Dept;

import java.util.List;

public interface DeptService {
    public List<Dept> list();

    void deleteById(Integer id);

    void add(Dept dept);

    Dept getById(Integer id);

    void modify(Dept dept);
}
