package com.example.ruan.todolist.interfaces;

import com.example.ruan.todolist.ListAllTasksFragment;
import com.example.ruan.todolist.ListConcludedTasksFragment;
import com.example.ruan.todolist.ListPendingTasksFragment;
import com.example.ruan.todolist.ListTasksByTagFragment;

public interface FragmentsInterface extends
        ListAllTasksFragment.OnFragmentInteractionListener,
        ListTasksByTagFragment.OnFragmentInteractionListener,
        ListPendingTasksFragment.OnFragmentInteractionListener,
        ListConcludedTasksFragment.OnFragmentInteractionListener {
}
