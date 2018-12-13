package com.example.ruan.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ruan.todolist.adapters.CategoryAdapter;
import com.example.ruan.todolist.adapters.TagAdapter;
import com.example.ruan.todolist.components.DateHelper;
import com.example.ruan.todolist.components.DatePicker;
import com.example.ruan.todolist.components.Mask;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterTaskActivity extends AppCompatActivity implements View.OnClickListener {
    static final int DATE_DIALOG_ID = 0;

    private EditText edt_title, edt_description, edt_date;
    private Spinner spn_category, spn_tags;
//    private MultiSpinner spn_tags;
    private ToDoListDBHelper toDoListDBHelper;
    private CategoryRepository categoryRepository;
    private TagsRepository tagsRepository;
    private TaskRepository taskRepository;
    private StatusRepository statusRepository;
    private boolean newTask;
    private Task task;


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


        edt_date = (EditText)findViewById(R.id.edt_date);
        edt_date.addTextChangedListener(Mask.insert("##/##/####", edt_date));
        edt_date.setOnClickListener(this);
        ImageButton btn_date = (ImageButton)findViewById(R.id.btn_date);
        btn_date.setOnClickListener(this);



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

        newTask = this.verifyIfIsNewTask();
        if (!newTask){
            this.loadFieldsToEdited();
        }

    }

    private boolean verifyIfIsNewTask(){
        Bundle bundle = getIntent().getExtras();
        return !( (bundle != null) && (bundle.containsKey("task")) );
    }

    private void loadFieldsToEdited(){
        Bundle bundle = getIntent().getExtras();
        task = (Task)bundle.getSerializable("task");
        try {
            task = taskRepository.queryForSameId(task);
//            Toast.makeText(this, task.getTitle() + "| status: " + task.getStatus().getStatusName(), Toast.LENGTH_SHORT)
//                    .show();
            edt_title.setText(task.getTitle());
            edt_description.setText(task.getDescription());

            CategoryAdapter categoryAdapter = (CategoryAdapter) spn_category.getAdapter();
            int positionCategory = categoryAdapter.getPosition(task.getCategory());
            spn_category.setSelection(positionCategory);

            TagAdapter tagAdapter = (TagAdapter)spn_tags.getAdapter();
            int positionTag = tagAdapter.getPosition(task.getTags());
            spn_tags.setSelection(positionTag);

            edt_date.setText(
                    DateHelper.convertDateToStringPTBR(task.getEndDate())
            );

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
                if ( fieldsIsValid() ){
                    saveTask();
                }
                break;
            case R.id.edt_date:
            case R.id.btn_date:
                showDialog(DATE_DIALOG_ID);
                break;
            default:
                break;
        }
    }

    private boolean fieldsIsValid()
    {
        if (
                edt_title.getText().toString().isEmpty() ||
                        edt_title.getText().toString().trim().isEmpty()
                ){
            Toast.makeText(this, "O campo título não pode estar vazio", Toast.LENGTH_LONG)
                    .show();
            edt_title.requestFocus();
            return false;
        }
        if (
                edt_description.getText().toString().isEmpty() ||
                        edt_description.getText().toString().trim().isEmpty()
                ){
            Toast.makeText(this, "O campo descrição não pode estar vazio", Toast.LENGTH_LONG)
                    .show();
            edt_description.requestFocus();
            return false;
        }
        if (
                edt_date.getText().toString().isEmpty() ||
                        edt_date.getText().toString().trim().isEmpty()
                ){
            Toast.makeText(this, "O campo data não pode estar vazio", Toast.LENGTH_LONG)
                    .show();
            edt_date.requestFocus();
            return false;
        }
        if ( !DateHelper.dateIsValid(edt_date.getText().toString()) ){
            Toast.makeText(this, "A data está com formato inválido", Toast.LENGTH_LONG)
                    .show();
            edt_date.requestFocus();
            return false;
        }

        return true;
    }

    private void saveTask(){
//        // 01/12/2018
//        Date date = new Date(2018 - 1900, 11, 1);
//            Toast.makeText(this, date.toString(), Toast.LENGTH_LONG)
//                    .show();

        if (newTask){
            task = new Task();
            task.setCreatedAt(new Date());
            Status statusPending = null;

            try {
                statusPending = statusRepository.getStatusPending();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            task.setStatus(statusPending);
        }
        Tags tags = (Tags)spn_tags.getSelectedItem();
        Category category = (Category)spn_category.getSelectedItem();
        task.setEndDate(
                DateHelper.convertStringPTBRToData(edt_date.getText().toString())
        );
        task.setTitle(edt_title.getText().toString());
        task.setDescription(edt_description.getText().toString());
        task.setTags(tags);
        task.setCategory(category);


        try {
            if (newTask){
                taskRepository.create(task);
            }else {
                taskRepository.update(task);
            }
            Toast.makeText(this, "Task salva!", Toast.LENGTH_SHORT)
                .show();
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
//        return super.onCreateDialog(id);
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                if (newTask){
                    return new DatePickerDialog(this, new DatePicker(this, edt_date), ano, mes,
                            dia);
                }else {
                    return new DatePickerDialog(this, new DatePicker(this, edt_date),
                            (task.getEndDate().getYear() + 1900),
                            task.getEndDate().getMonth(),
                            task.getEndDate().getDate()
                    );
                }
        }
        return null;
    }

}
