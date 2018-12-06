package com.example.ruan.todolist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruan.todolist.adapters.TaskAdapter;
import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListAllTasksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListAllTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAllTasksFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ToDoListDBHelper toDoListDBHelper;
    private TaskRepository taskRepository;
    private ListView lst_view_all_tasks;
    private TaskAdapter taskAdapter;

    private Context context;

    private OnFragmentInteractionListener mListener;

    public ListAllTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAllTasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListAllTasksFragment newInstance(String param1, String param2) {
        ListAllTasksFragment fragment = new ListAllTasksFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_all_tasks, container, false);
        lst_view_all_tasks = (ListView)view.findViewById(R.id.lst_view_all_tasks);
//        context = view.getContext();
        context = container.getContext();
        toDoListDBHelper = new ToDoListDBHelper(context);
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
                taskAdapter = new TaskAdapter(context, R.layout.task_list_view_layout, taskList);
                lst_view_all_tasks.setAdapter(taskAdapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lst_view_all_tasks.setOnItemClickListener(this);

        return view;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = taskAdapter.getItem(position);

//        Intent intent = new Intent(context, RegisterTaskActivity.class);
//        intent.putExtra("task", task);
//        ((AppCompatActivity)context).startActivityForResult(intent, 0);

        Toast.makeText(view.getContext(), task.getTitle(), Toast.LENGTH_SHORT)
                .show();
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
}
