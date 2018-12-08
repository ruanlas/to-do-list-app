package com.example.ruan.todolist.entity;

import com.example.ruan.todolist.repository.TaskRepository;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "task", daoClass = TaskRepository.class)
public class Task implements Serializable {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "category_id", foreign = true, foreignAutoRefresh = true)
    private transient Category category;

    @DatabaseField(columnName = "tag_id", foreign = true, foreignAutoRefresh = true)
    private transient Tags tags;

    @DatabaseField(columnName = "status_id", foreign = true, foreignAutoRefresh = true)
    private transient Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
