package com.example.ruan.todolist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ruan.todolist.adapters.TagAdapter;
import com.example.ruan.todolist.adapters.TaskAdapter;
import com.example.ruan.todolist.database.ToDoListDBHelper;
import com.example.ruan.todolist.entity.Tags;
import com.example.ruan.todolist.entity.Task;
import com.example.ruan.todolist.menu.MenuActionsItem;
import com.example.ruan.todolist.repository.TagsRepository;
import com.example.ruan.todolist.repository.TaskRepository;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListTasksByTagFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListTasksByTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListTasksByTagFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ToDoListDBHelper toDoListDBHelper;
    private TaskRepository taskRepository;
    private TagsRepository tagsRepository;
    private ListView lst_view_tasks_by_tag;
    private Spinner spn_tag_filter;
    private TaskAdapter taskAdapter;

    private OnFragmentInteractionListener mListener;

    public ListTasksByTagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListTasksByTagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListTasksByTagFragment newInstance(String param1, String param2) {
        ListTasksByTagFragment fragment = new ListTasksByTagFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_tasks_by_tag, container, false);

        lst_view_tasks_by_tag = (ListView)view.findViewById(R.id.lst_view_tasks_by_tag);
//        lst_view_tasks_by_tag.setOnItemClickListener(this);
        spn_tag_filter = (Spinner)view.findViewById(R.id.spn_tag_filter);
        spn_tag_filter.setOnItemSelectedListener(this);
        registerForContextMenu(lst_view_tasks_by_tag);

        toDoListDBHelper = new ToDoListDBHelper(getContext());
        try {
            tagsRepository = new TagsRepository(toDoListDBHelper.getConnectionSource());
            taskRepository = new TaskRepository(toDoListDBHelper.getConnectionSource());

            List<Tags> tagsList = tagsRepository.queryForAll();
            TagAdapter tagAdapter = new TagAdapter(getContext(), R.layout.tag_spinner_layout, tagsList);
            spn_tag_filter.setAdapter(tagAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        Task task = (Task) taskAdapter.getItem(position);
//
//        Intent intent = new Intent(getContext(), RegisterTaskActivity.class);
//        intent.putExtra("task", task);
////        ((AppCompatActivity)context).startActivityForResult(intent, 0);
//        startActivityForResult(intent, 0);
        Toast.makeText(view.getContext(), task.getTitle(), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewElementId = parent.getId();

        switch (viewElementId){
            case R.id.spn_tag_filter:
                Tags tag = (Tags)spn_tag_filter.getSelectedItem();
                try {
                    List<Task> taskList = taskRepository.getTasksByTag(tag);
                    taskAdapter = new TaskAdapter(getContext(), R.layout.task_list_view_layout, taskList);
                    lst_view_tasks_by_tag.setAdapter(taskAdapter);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(view.getContext(), tag.getTagName(), Toast.LENGTH_SHORT)
//                    .show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        int elementViewId = v.getId();

        switch (elementViewId){
            case R.id.lst_view_tasks_by_tag:

                menu.add(0, MenuActionsItem.ACTION_EDIT_ID, 0, MenuActionsItem.ACTION_EDIT);
                menu.add(0, MenuActionsItem.ACTION_REMOVE_ID, 0, MenuActionsItem.ACTION_REMOVE);
                menu.add(0, MenuActionsItem.ACTION_SET_CONCLUDED_ID, 0, MenuActionsItem.ACTION_SET_CONCLUDED);
                menu.add(0, MenuActionsItem.ACTION_SET_PENDING_ID, 0, MenuActionsItem.ACTION_SET_PENDING);
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
        Task task = taskAdapter.getItem(position);

        switch (menuItemSelectedId){
            case MenuActionsItem.ACTION_EDIT_ID:
                Intent intent = new Intent(getContext(), RegisterTaskActivity.class);
                intent.putExtra("task", task);
                startActivityForResult(intent, 0);
                break;
            case MenuActionsItem.ACTION_REMOVE_ID:

                break;
            case MenuActionsItem.ACTION_SET_CONCLUDED_ID:

                break;
            case MenuActionsItem.ACTION_SET_PENDING_ID:

                break;
            default:
                break;
        }

//        Toast.makeText(getContext(), " Task: " + task.getTitle(), Toast.LENGTH_SHORT)
//            .show();
        return super.onContextItemSelected(item);
    }
}
