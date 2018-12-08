package com.example.ruan.todolist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ruan.todolist.adapters.TaskAdapter;
import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Status;
import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.interfaces.FragmentRefreshInterface;
import com.example.ruan.todolist.repository.StatusRepository;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListConcludedTasksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListConcludedTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListConcludedTasksFragment extends Fragment implements AdapterView.OnItemClickListener, FragmentRefreshInterface {
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
    private ListView lst_view_concluded_tasks;
    private TaskAdapter taskAdapter;

    private OnFragmentInteractionListener mListener;

    public ListConcludedTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListConcludedTasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListConcludedTasksFragment newInstance(String param1, String param2) {
        ListConcludedTasksFragment fragment = new ListConcludedTasksFragment();
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

        View view = inflater.inflate(R.layout.fragment_list_concluded_tasks, container, false);

        lst_view_concluded_tasks = (ListView)view.findViewById(R.id.lst_view_concluded_tasks);
        lst_view_concluded_tasks.setOnItemClickListener(this);

        toDoListDBHelper = new ToDoListDBHelper(getContext());
        try {
            taskRepository = new TaskRepository(toDoListDBHelper.getConnectionSource());
            statusRepository = new StatusRepository(toDoListDBHelper.getConnectionSource());
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
            Status status = statusRepository.getStatusConcluded();
            taskList = taskRepository.getTasksByStatus(status);
            if (taskList != null){
//                TaskAdapter taskAdapter = new TaskAdapter(view.getContext(), R.layout.task_list_view_layout, taskList);
                taskAdapter = new TaskAdapter(getContext(), R.layout.task_list_view_layout, taskList);
                lst_view_concluded_tasks.setAdapter(taskAdapter);
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
}
