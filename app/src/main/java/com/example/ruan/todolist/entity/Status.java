package com.example.ruan.todolist.entity;

import com.example.ruan.todolist.repository.StatusRepository;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "status", daoClass = StatusRepository.class)
public class Status {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "status_name")
    private String statusName;

    public int getId() {
        return id;
    }

    public Status setId(int id) {
        this.id = id;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public Status setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }
}
