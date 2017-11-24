package com.iunin.demo.platformdemo.makeinvoice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;

import java.util.List;

/**
 * Created by copo on 17-11-14.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADD = 0;
    private static final int TYPE_CONTENT = 1;
    private List<UserGoodsModel> goods;
    private Context mContext;

    private OnItemClickGoodsListener onItemClickGoodsListener;

    public GoodsListAdapter(List<UserGoodsModel> goods, Context context){
        this.goods = goods;
        mContext = context;
    }

    public void setOnItemClickGoodsListener(OnItemClickGoodsListener onItemClickGoodsListener){
        this.onItemClickGoodsListener = onItemClickGoodsListener;
    }
    class GoodsListHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView count;
        private TextView taxRate;
        private ImageView btn_delete;

        public GoodsListHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_good_name);
            price = itemView.findViewById(R.id.id_good_price);
            count = itemView.findViewById(R.id.id_good_count);
            taxRate = itemView.findViewById(R.id.id_good_tax);
            btn_delete = itemView.findViewById(R.id.id_delete);
        }
    }

    class AddGoodsHolder extends RecyclerView.ViewHolder {
        public AddGoodsHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ADD){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_goods,parent,false);
            AddGoodsHolder addGoodsHolder = new AddGoodsHolder(itemView);
            return addGoodsHolder;
        }else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_good,parent,false);
            GoodsListHolder goodsListHolder = new GoodsListHolder(itemView);
            return goodsListHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int i = position;
        if(holder instanceof AddGoodsHolder){
            if(onItemClickGoodsListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //添加商品的回调
                        onItemClickGoodsListener.addGoods();
                    }
                });
            }
        }else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickGoodsListener.modify(goods.get(i));
                }
            });
            final UserGoodsModel goodsModel = goods.get(position);
            ((GoodsListHolder)holder).name.setText(goodsModel.spmc);
            ((GoodsListHolder)holder).price.setText(goodsModel.hsdj);
            ((GoodsListHolder)holder).count.setText(goodsModel.spsl);
            ((GoodsListHolder)holder).taxRate.setText(goodsModel.sl);
            ((GoodsListHolder)holder).btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除商品的点击事件
                    goods.remove(goodsModel);
                    notifyDataSetChanged();
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position == goods.size()){
            return TYPE_ADD;
        }else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return goods.size() + 1;
    }

    interface OnItemClickGoodsListener{
        //添加
        void addGoods();
        //修改
        void modify(UserGoodsModel model);
    }
}
