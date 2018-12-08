package com.example.ruan.todolist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ruan.todolist.adapters.TaskAdapter;
import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.interfaces.FragmentsInterface;
import com.example.ruan.todolist.repository.StatusRepository;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentsInterface,
        View.OnClickListener, AdapterView.OnItemClickListener{

    private ToDoListDBHelper toDoListDBHelper;
    private StatusRepository statusRepository;
    private TaskRepository taskRepository;
    private ListView lst_view_all_tasks;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_task);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lst_view_all_tasks = (ListView)findViewById(R.id.lst_view_all_tasks);
        lst_view_all_tasks.setOnItemClickListener(this);

        toDoListDBHelper = new ToDoListDBHelper(this);
        try {
            taskRepository = new TaskRepository(toDoListDBHelper.getConnectionSource());
//            Toast.makeText(view.getContext(), "Conexão criada!", Toast.LENGTH_SHORT)
//                .show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Task> taskList = null;
        try {
            taskList = taskRepository.queryForAll();
            if (taskList != null){
//                TaskAdapter taskAdapter = new TaskAdapter(view.getContext(), R.layout.task_list_view_layout, taskList);
                taskAdapter = new TaskAdapter(this, R.layout.task_list_view_layout, taskList);
                lst_view_all_tasks.setAdapter(taskAdapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list_all_tasks) {

        } else if (id == R.id.nav_list_concluded_tasks) {

        } else if (id == R.id.nav_list_pending_tasks) {

        } else if (id == R.id.nav_list_task_by_category) {

        } else if (id == R.id.nav_list_task_by_tag) {

        } else if (id == R.id.nav_add_category) {
            Intent intent = new Intent(this, RegisterCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_tag) {
            Intent intent = new Intent(this, RegisterTagActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();

        switch (buttonId){
            case R.id.fab_add_task:
                Intent intent = new Intent(MainActivity.this, RegisterTaskActivity.class);
                startActivityForResult(intent, 0);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = taskAdapter.getItem(position);

        Intent intent = new Intent(this, RegisterTaskActivity.class);
        intent.putExtra("task", task);
//        ((AppCompatActivity)context).startActivityForResult(intent, 0);
        startActivityForResult(intent, 0);
//        Toast.makeText(view.getContext(), task.getTitle(), Toast.LENGTH_SHORT)
//                .show();
    }
}
