package com.example.ruan.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ruan.todolist.adapters.TaskAdapter;
import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Status;
import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.events.RemoveTaskActionAlertDialog;
import com.example.ruan.todolist.interfaces.FragmentRefreshInterface;
import com.example.ruan.todolist.menu.MenuActionsItem;
import com.example.ruan.todolist.repository.StatusRepository;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListPendingTasksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListPendingTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPendingTasksFragment extends Fragment implements AdapterView.OnItemClickListener, FragmentRefreshInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ToDoListDBHelper toDoListDBHelper;
    private TaskRepository taskRepository;
    private StatusRepository statusRepository;
    private ListView lst_view_pending_tasks;
    private TaskAdapter taskAdapter;

    private Status statusConcluded = null;
    private Status statusPending = null;

    private OnFragmentInteractionListener mListener;

    public ListPendingTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListPendingTasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListPendingTasksFragment newInstance(String param1, String param2) {
        ListPendingTasksFragment fragment = new ListPendingTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_pending_tasks, container, false);

        lst_view_pending_tasks = (ListView)view.findViewById(R.id.lst_view_pending_tasks);
//        lst_view_pending_tasks.setOnItemClickListener(this);
        registerForContextMenu(lst_view_pending_tasks);

        toDoListDBHelper = new ToDoListDBHelper(getContext());
        try {
            taskRepository = new TaskRepository(toDoListDBHelper.getConnectionSource());
            statusRepository = new StatusRepository(toDoListDBHelper.getConnectionSource());

            statusConcluded = statusRepository.getStatusConcluded();
            statusPending = statusRepository.getStatusPending();
//            Toast.makeText(view.getContext(), "Conexão criada!", Toast.LENGTH_SHORT)
//                .show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.loadListView();

        return view;
    }

    private void loadListView(){
        List<Task> taskList = null;
        try {
            Status status = statusRepository.getStatusPending();
            taskList = taskRepository.getTasksByStatus(status);
            if (taskList != null){
//                TaskAdapter taskAdapter = new TaskAdapter(view.getContext(), R.layout.task_list_view_layout, taskList);
                taskAdapter = new TaskAdapter(getContext(), R.layout.task_list_view_layout, taskList);
                lst_view_pending_tasks.setAdapter(taskAdapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void refresh() {
        this.loadListView();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = taskAdapter.getItem(position);

        Intent intent = new Intent(getContext(), RegisterTaskActivity.class);
        intent.putExtra("task", task);
//        ((AppCompatActivity)context).startActivityForResult(intent, 0);
        startActivityForResult(intent, 0);
//        Toast.makeText(view.getContext(), task.getTitle(), Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        int elementViewId = v.getId();

        switch (elementViewId){
            case R.id.lst_view_pending_tasks:
//                ListView listView = (ListView)v;
                AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Task task = (Task) lst_view_pending_tasks.getItemAtPosition(adapterContextMenuInfo.position);

                menu.add(0, MenuActionsItem.ACTION_EDIT_ID, 0, MenuActionsItem.ACTION_EDIT);
                menu.add(0, MenuActionsItem.ACTION_REMOVE_ID, 0, MenuActionsItem.ACTION_REMOVE);
                if ( statusPending.equals(task.getStatus()) ){
                    menu.add(0, MenuActionsItem.ACTION_SET_STATUS_CONCLUDED_ID, 0, MenuActionsItem.ACTION_SET_STATUS_CONCLUDED);
                }else {
                    menu.add(0, MenuActionsItem.ACTION_SET_STATUS_PENDING_ID, 0, MenuActionsItem.ACTION_SET_STATUS_PENDING);
                }
//                Toast.makeText(getContext(), "Fui pressionado por um logo tempo", Toast.LENGTH_SHORT)
//                    .show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuItemSelectedId = item.getItemId();

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = adapterContextMenuInfo.position;
        final Task task = taskAdapter.getItem(position);

        switch (menuItemSelectedId){
            case MenuActionsItem.ACTION_EDIT_ID:
                Intent intent = new Intent(getContext(), RegisterTaskActivity.class);
                intent.putExtra("task", task);
                startActivityForResult(intent, 0);
                break;
            case MenuActionsItem.ACTION_REMOVE_ID:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Atenção");
                alertDialog.setMessage("Você deseja realmente remover esta task?");
                alertDialog.setPositiveButton("OK", new RemoveTaskActionAlertDialog(
                        taskRepository, task, this
                ));
                alertDialog.setNegativeButton("Cancelar", null);
                alertDialog.show();
                break;
            case MenuActionsItem.ACTION_SET_STATUS_CONCLUDED_ID:
                task.setStatus(statusConcluded);
                try {
                    taskRepository.update(task);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case MenuActionsItem.ACTION_SET_STATUS_PENDING_ID:
                task.setStatus(statusPending);
                try {
                    taskRepository.update(task);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        this.loadListView();

//        Toast.makeText(getContext(), " Task: " + task.getTitle(), Toast.LENGTH_SHORT)
//            .show();
        return super.onContextItemSelected(item);
    }
}
