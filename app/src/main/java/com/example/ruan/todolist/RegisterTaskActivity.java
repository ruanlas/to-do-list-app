package com.example.ruan.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_title, edt_description;
    private Spinner spn_tags, spn_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btn_save_task = (Button)findViewById(R.id.btn_save_task);
        btn_save_task.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        switch (elementId){
            case R.id.btn_save_task:

                break;
            default:
                break;
        }
    }
}
