package com.example.ruan.todolist.repository;

import com.example.ruan.todolist.entity.Task;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class TaskRepository extends BaseDaoImpl<Task, Integer> {

    public TaskRepository(ConnectionSource connectionSource) throws SQLException {
        super(Task.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
