package com.iunin.demo.platformdemo.makeinvoice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.ArrayWapperAdapter;
import com.iunin.demo.platformdemo.ui.base.ViewHolder;
import com.iunin.service.invoice.baiwang.v1_0.Invoicemodel.TaxGoodCode;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copo on 17-11-16.
 */

public class SelectorGoodsListAdapter extends ArrayWapperAdapter<UserGoodsModel> {
    public SelectorGoodsListAdapter(Context context, List<UserGoodsModel> models) {
        super(context, models);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = getInflater().inflate(R.layout.item_sel_item,null);
        }
        TextView id_good_name = ViewHolder.get(convertView, R.id.id_good_name);
        TextView id_good_price = ViewHolder.get(convertView, R.id.id_good_price); //含税单价
        TextView id_good_discount = ViewHolder.get(convertView, R.id.id_good_discount);
        TextView id_good_tax = ViewHolder.get(convertView, R.id.id_good_tax);

        if (position >= getCount()) {
            position = getCount() - 1;
        }
        UserGoodsModel model = getItem(position);
        id_good_name.setText(model.spmc);
        id_good_price.setText(model.hsdj.toString());
        id_good_discount.setText("0");
        id_good_tax.setText(model.sl.toString()+"%");

        return convertView;
    }
}
