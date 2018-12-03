package com.example.ruan.todolist.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ruan.todolist.R;
import com.example.ruan.todolist.entity.Task;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int layoutResourceId;
    private List<Task> taskList;

    public TaskAdapter(Context context, int layoutResourceId, List<Task> taskList) {
        super(context, layoutResourceId, taskList);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getRow(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getRow(position, convertView, parent);
    }

    private View getRow(int position, View convertView, ViewGroup parent){
        ItensHolder itensHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(this.layoutResourceId, parent, false);
            itensHolder = new ItensHolder();
            itensHolder.txt_task_description = (TextView)convertView.findViewById(R.id.txt_task_description);
            itensHolder.txt_task_title = (TextView)convertView.findViewById(R.id.txt_task_title);
            itensHolder.txt_task_status = (TextView)convertView.findViewById(R.id.txt_task_status);
            convertView.setTag(itensHolder);
        }else {
            itensHolder = (ItensHolder)convertView.getTag();
        }

        itensHolder.txt_task_title.setText(taskList.get(position).getTitle());
        itensHolder.txt_task_description.setText(taskList.get(position).getDescription());
        itensHolder.txt_task_status.setText("[ " + taskList.get(position).getStatus().getStatusName() + " ]");
        return convertView;
    }

    private static class ItensHolder{
        public TextView txt_task_title, txt_task_description, txt_task_status;
    }
}
