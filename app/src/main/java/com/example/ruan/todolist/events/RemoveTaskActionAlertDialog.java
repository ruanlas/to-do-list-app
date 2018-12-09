package com.example.ruan.todolist.events;

import android.content.DialogInterface;

import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.interfaces.FragmentRefreshInterface;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;

public class RemoveTaskActionAlertDialog implements DialogInterface.OnClickListener {

    private TaskRepository taskRepository;
    private Task task;
    private FragmentRefreshInterface fragmentRefreshInterface;

    public RemoveTaskActionAlertDialog(TaskRepository taskRepository, Task task, FragmentRefreshInterface fragmentRefreshInterface){
        this.taskRepository = taskRepository;
        this.task = task;
        this.fragmentRefreshInterface = fragmentRefreshInterface;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        try {
            this.taskRepository.delete(task);
            this.fragmentRefreshInterface.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
