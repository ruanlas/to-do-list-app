package com.example.ruan.todolist.repository;

import com.example.ruan.todolist.entity.Tags;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class TagsRepository extends BaseDaoImpl<Tags, Integer> {

    public TagsRepository(ConnectionSource connectionSource) throws SQLException {
        super(Tags.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
