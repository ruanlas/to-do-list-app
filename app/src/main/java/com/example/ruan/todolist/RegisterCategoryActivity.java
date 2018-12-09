package com.example.ruan.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Category;
import com.example.ruan.todolist.repository.CategoryRepository;

import java.sql.SQLException;

public class RegisterCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private ToDoListDBHelper toDoListDBHelper;
    private CategoryRepository categoryRepository;
    private EditText edt_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toDoListDBHelper = new ToDoListDBHelper(this);
        try {
            categoryRepository = new CategoryRepository(toDoListDBHelper.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        edt_category = (EditText)findViewById(R.id.edt_category);
        Button btn_save_category = (Button)findViewById(R.id.btn_save_category);
        btn_save_category.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Category category = new Category();
        category.setCategoryName(
                edt_category.getText().toString()
        );

        try {
            categoryRepository.create(category);
            Toast.makeText(v.getContext(), "Cadastrado!", Toast.LENGTH_SHORT)
                    .show();
            finish();
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
