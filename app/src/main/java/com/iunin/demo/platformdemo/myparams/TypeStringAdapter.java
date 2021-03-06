package com.iunin.demo.platformdemo.myparams;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.utils.Constants;

import java.util.List;

/**
 * Created by copo on 17-11-16.
 */

public class TypeStringAdapter implements SpinnerAdapter {
    private String[] datas;

    public TypeStringAdapter(String[] datas) {
        this.datas = datas;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = getView(position, convertView, parent);
        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, null);
        TextView textView = itemView.findViewById(R.id.tv_content);
        textView.setText(datas[position]);
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public static String[] StringListToArray (List<String> stringList){
        int i = 0;
        String[] stringArray = {};
        if(stringList.size() == 0){
            return  stringArray;
        }
        for(String str:stringList){
            stringArray[i] = str;
            i++;
        }
        return stringArray;
    }
}
