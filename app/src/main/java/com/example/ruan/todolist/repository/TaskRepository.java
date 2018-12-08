package com.example.ruan.todolist.repository;

import com.example.ruan.todolist.entity.Category;
import com.example.ruan.todolist.entity.Status;
import com.example.ruan.todolist.entity.Tags;
import com.example.ruan.todolist.entity.Task;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class TaskRepository extends BaseDaoImpl<Task, Integer> {

    public TaskRepository(ConnectionSource connectionSource) throws SQLException {
        super(Task.class);
        setConnectionSource(connectionSource);
        initialize();
    }

    public List<Task> getTasksByStatus(Status status) throws SQLException {
        return this.queryForEq("status_id", status);
    }

    public List<Task> getTasksByTag(Tags tags) throws SQLException {
        return this.queryForEq("tag_id", tags);
    }

    public List<Task> getTasksByCategory(Category category) throws SQLException {
        return this.queryForEq("category_id", category);
    }
}
