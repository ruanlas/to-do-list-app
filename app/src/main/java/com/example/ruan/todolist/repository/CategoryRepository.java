package com.example.ruan.todolist.repository;

import com.example.ruan.todolist.entity.Category;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class CategoryRepository extends BaseDaoImpl<Category, Integer> {

    public CategoryRepository(ConnectionSource  connectionSource) throws SQLException {
        super(Category.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
