package com.example.ruan.todolist.components;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ruan.todolist.RegisterTaskActivity;

import java.util.Date;

public class DatePicker implements DatePickerDialog.OnDateSetListener {
    private Context context;
    private EditText edt_date;

    public DatePicker(Context context, EditText edt_date){
        this.context = context;
        this.edt_date = edt_date;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

//        Date date = new Date((year - 1900), month, dayOfMonth);
//        Toast.makeText(context, date.toString(), Toast.LENGTH_LONG).show();

        edt_date.getText().clear();
        month++;
        String stringDay, stringMonth, stringYear;
        stringDay = (dayOfMonth < 10)?  0 + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
        stringMonth = (month < 10)?  0 + String.valueOf(month) : String.valueOf(month);
        stringYear = String.valueOf(year);
        edt_date.setText(stringDay + stringMonth + stringYear);

//        String data = String.valueOf(dayOfMonth) + " /"
//                + String.valueOf(month+1) + " /" + String.valueOf(year);
//        Toast.makeText(context,
//                "DATA = " + data, Toast.LENGTH_SHORT)
//                .show();
    }
}
