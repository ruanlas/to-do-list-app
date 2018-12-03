package com.example.ruan.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ruan.todolist.entity.Category;
import com.example.ruan.todolist.entity.Status;
import com.example.ruan.todolist.entity.Tags;
import com.example.ruan.todolist.entity.Task;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class ToDoListDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "to_do_list_db";
    private static final int DATABASE_VERSION = 3;

    public ToDoListDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Tags.class);
            TableUtils.createTable(connectionSource, Status.class);
            TableUtils.createTable(connectionSource, Task.class);

            PopulateDbHelper.loadStatusTable(database);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Tags.class, true);
            TableUtils.dropTable(connectionSource, Status.class, true);
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
