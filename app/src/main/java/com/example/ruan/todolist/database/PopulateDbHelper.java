package com.example.ruan.todolist.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.ruan.todolist.entity.Status;
import com.example.ruan.todolist.repository.StatusRepository;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class PopulateDbHelper {

    public static void loadStatusTable(SQLiteDatabase sqLiteDatabase)
    {
        ContentValues statusConcluded = new ContentValues();
        statusConcluded.put("status_name", "Concluído");
        ContentValues statusPending = new ContentValues();
        statusPending.put("status_name", "Pendente");

        sqLiteDatabase.insertOrThrow("status", null, statusConcluded);
        sqLiteDatabase.insertOrThrow("status", null, statusPending);
    }

    public static void loadStatusTable(ConnectionSource connectionSource)
    {
        Status statusConcluded = new Status();
        statusConcluded.setStatusName("Concluído");
        Status statusPending = new Status();
        statusPending.setStatusName("Pendente");
        try {
            StatusRepository statusRepository = new StatusRepository(connectionSource);
            statusRepository.create(statusConcluded);
            statusRepository.create(statusPending);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
