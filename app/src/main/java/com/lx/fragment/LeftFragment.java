package com.lx.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lx.newscoorer.R;

import java.util.List;

import androidx.annotation.NonNull;

public class LeftFragment extends ArrayAdapter {

    public LeftFragment(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LeftList listItem = (LeftList) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_left_menu, null);//实例化一个对象
        TextView leftItem = (TextView) view.findViewById(R.id.ll_list_item);//获取该布局内的文本视图
        leftItem.setText(listItem.getName());//为文本视图设置文本内容
        return view;
    }
}
