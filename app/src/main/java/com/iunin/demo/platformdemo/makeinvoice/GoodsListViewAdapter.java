package com.iunin.demo.platformdemo.makeinvoice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;

import java.util.List;

/**
 * Created by copo on 17-11-21.
 */

public class GoodsListViewAdapter extends BaseAdapter {
    private List<UserGoodsModel> mGoodsModels;
    public GoodsListViewAdapter(List<UserGoodsModel> mGoodsModels){
        this.mGoodsModels = mGoodsModels;
    }
    @Override
    public int getCount() {
        return this.mGoodsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserGoodsModel model = mGoodsModels.get(position);
        ViewHodel viewHodel ;
        if(convertView != null){
            viewHodel = (ViewHodel) convertView.getTag();
        }else {
            viewHodel = new ViewHodel();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_good,parent,false);
            viewHodel.tv_name = convertView.findViewById(R.id.id_good_name);
            viewHodel.tv_dj = convertView.findViewById(R.id.id_good_price);
            viewHodel.tv_count = convertView.findViewById(R.id.id_good_count);
            viewHodel.tv_sl = convertView.findViewById(R.id.id_good_tax);
            ImageView img_del = convertView.findViewById(R.id.id_delete);
            img_del.setVisibility(View.GONE);
            convertView.setTag(viewHodel);
        }
        viewHodel.tv_name.setText(model.spmc);
        viewHodel.tv_dj.setText(model.hsdj);
        viewHodel.tv_sl.setText(model.sl);
        viewHodel.tv_count.setText(model.spsl);
        return convertView;
    }
    class ViewHodel{
        public TextView tv_name;
        public TextView tv_dj;
        public TextView tv_count;
        public TextView tv_sl;
    }
}
