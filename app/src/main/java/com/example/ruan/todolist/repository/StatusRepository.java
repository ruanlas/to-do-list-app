package com.example.ruan.todolist.repository;

import com.example.ruan.todolist.entity.Status;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class StatusRepository extends BaseDaoImpl<Status, Integer> {

    public StatusRepository(ConnectionSource connectionSource) throws SQLException {
        super(Status.class);
        setConnectionSource(connectionSource);
        initialize();
    }

    public Status getStatusPending() throws SQLException{
        Integer integer = 2;
        return this.queryForId(integer);
    }
}
