package com.example.ruan.todolist.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.ruan.todolist.R;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getRow(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getRow(position, convertView, parent);
    }

    private View getRow(int position, View convertView, ViewGroup parent){
        ItensHolder itensHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(this.layoutResourceId, parent, false);
            itensHolder = new ItensHolder();
            itensHolder.textView = (CheckedTextView) convertView.findViewById(R.id.txt_category_name);
            convertView.setTag(itensHolder);
        }else {
            itensHolder = (ItensHolder)convertView.getTag();
        }
        itensHolder.textView.setText(categoryList.get(position).getCategoryName());
        return convertView;
    }

    private static class ItensHolder{
        public CheckedTextView textView;
    }
}
