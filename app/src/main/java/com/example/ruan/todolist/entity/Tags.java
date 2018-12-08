package com.example.ruan.todolist.entity;

import com.example.ruan.todolist.repository.TagsRepository;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tags", daoClass = TagsRepository.class)
public class Tags {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "tag_name")
    private String tagName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object obj) {
//        return super.equals(obj);
        Tags tags = (Tags)obj;
        return (this.getId() == tags.getId());
    }
}
