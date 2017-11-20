package com.iunin.demo.platformdemo.myparams;

import android.database.DataSetObserver;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.utils.Constants;

/**
 * Created by copo on 17-11-16.
 */

public class FppyAdapter implements SpinnerAdapter {
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
        return Constants.FPPY.length;
    }

    @Override
    public Object getItem(int position) {
        return Constants.FPPY[position];
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fppy, null);
        TextView textView = itemView.findViewById(R.id.tv_fppy);
        textView.setText(Constants.FPPY[position]);
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
}
