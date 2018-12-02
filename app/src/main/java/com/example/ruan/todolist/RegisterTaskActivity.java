package com.example.ruan.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.MultiSpinner;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.example.ruan.todolist.adapters.CategoryAdapter;
import com.example.ruan.todolist.adapters.TagAdapter;
import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Category;
import com.example.ruan.todolist.entity.Status;
import com.example.ruan.todolist.entity.Tags;
import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.repository.CategoryRepository;
import com.example.ruan.todolist.repository.StatusRepository;
import com.example.ruan.todolist.repository.TagsRepository;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RegisterTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_title, edt_description;
    private Spinner spn_category, spn_tags;
//    private MultiSpinner spn_tags;
    private ToDoListDBHelper toDoListDBHelper;
    private CategoryRepository categoryRepository;
    private TagsRepository tagsRepository;
    private TaskRepository taskRepository;
    private StatusRepository statusRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btn_save_task = (Button)findViewById(R.id.btn_save_task);
        btn_save_task.setOnClickListener(this);

        edt_title = (EditText)findViewById(R.id.edt_title);
        edt_description = (EditText)findViewById(R.id.edt_description);
        spn_tags = (Spinner)findViewById(R.id.spn_tags);
        spn_category = (Spinner)findViewById(R.id.spn_category);


        try {
            toDoListDBHelper = new ToDoListDBHelper(this);
            categoryRepository = new CategoryRepository(toDoListDBHelper.getConnectionSource());
            tagsRepository = new TagsRepository(toDoListDBHelper.getConnectionSource());
            taskRepository = new TaskRepository(toDoListDBHelper.getConnectionSource());
            statusRepository = new StatusRepository(toDoListDBHelper.getConnectionSource());

            List<Category> categoryList = categoryRepository.queryForAll();
            List<Tags> tagsList = tagsRepository.queryForAll();
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.category_spinner_layout, categoryList);
            spn_category.setAdapter(categoryAdapter);

            TagAdapter tagAdapter = new TagAdapter(this, R.layout.tag_spinner_layout, tagsList);
//            ArrayAdapter<Tags> tagAdapter = new ArrayAdapter<Tags>(this, android.R.layout.simple_spinner_dropdown_item, tagsList);
            spn_tags.setAdapter(tagAdapter);

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

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        switch (elementId){
            case R.id.btn_save_task:
//                Category category = (Category)spn_category.getSelectedItem();
//                Tags tags = (Tags)spn_tags.getSelectedItem();
//                Toast.makeText(v.getContext(), category.getCategoryName(), Toast.LENGTH_SHORT)
//                Toast.makeText(v.getContext(), tags.getTagName(), Toast.LENGTH_SHORT)
//                        .show();
                saveTask();
                break;
            default:
                break;
        }
    }

    private void saveTask(){
        Task task = new Task();
        Status statusPending = null;

        try {
            statusPending = statusRepository.getStatusPending();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        task.setStatus(statusPending);
        Tags tags = (Tags)spn_tags.getSelectedItem();
        Category category = (Category)spn_category.getSelectedItem();
        task.setTitle(edt_title.getText().toString());
        task.setDescription(edt_description.getText().toString());
        task.setTags(tags);
        task.setCategory(category);

        try {
            taskRepository.create(task);
            Toast.makeText(this, "Task salva!", Toast.LENGTH_SHORT)
                .show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
