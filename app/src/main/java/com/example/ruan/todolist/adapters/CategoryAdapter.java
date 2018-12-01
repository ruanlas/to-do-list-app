package com.example.ruan.todolist.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.ruan.todolist.entity.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int layoutResourceId;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, int layoutResourceId, List<Category> categoryList){
        super(context, layoutResourceId, categoryList);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.categoryList = categoryList;
    }
}
