package com.example.ruan.todolist.interfaces;

import com.example.ruan.todolist.ListAllTasksFragment;
import com.example.ruan.todolist.ListConcludedTasksFragment;
import com.example.ruan.todolist.ListPendingTasksFragment;
import com.example.ruan.todolist.ListTaskByCategoryFragment;
import com.example.ruan.todolist.ListTaskByTagFragment;
import com.example.ruan.todolist.RegisterCategoryFragment;
import com.example.ruan.todolist.RegisterTagFragment;

public interface FragmentsInterface extends
        RegisterCategoryFragment.OnFragmentInteractionListener,
        RegisterTagFragment.OnFragmentInteractionListener,
        ListAllTasksFragment.OnFragmentInteractionListener,
        ListConcludedTasksFragment.OnFragmentInteractionListener,
        ListPendingTasksFragment.OnFragmentInteractionListener,
        ListTaskByCategoryFragment.OnFragmentInteractionListener,
        ListTaskByTagFragment.OnFragmentInteractionListener {
}
