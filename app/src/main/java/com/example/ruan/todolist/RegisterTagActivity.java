package com.example.ruan.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Tags;
import com.example.ruan.todolist.repository.TagsRepository;

import java.sql.SQLException;

public class RegisterTagActivity extends AppCompatActivity implements View.OnClickListener{

    private ToDoListDBHelper toDoListDBHelper;
    private TagsRepository tagsRepository;
    private EditText edt_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toDoListDBHelper = new ToDoListDBHelper(this);
        try {
            tagsRepository = new TagsRepository(toDoListDBHelper.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        edt_tag = (EditText)findViewById(R.id.edt_tag);
        Button btn_save_tag = (Button)findViewById(R.id.btn_save_tag);
        btn_save_tag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Tags tags = new Tags();
        tags.setTagName(
                edt_tag.getText().toString()
        );
        try {
            tagsRepository.create(tags);
            Toast.makeText(v.getContext(), "Cadastrado!", Toast.LENGTH_SHORT)
                    .show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
