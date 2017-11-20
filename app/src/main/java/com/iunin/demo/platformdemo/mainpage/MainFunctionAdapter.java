package com.iunin.demo.platformdemo.mainpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;

import java.util.List;

/**
 * Created by copo on 17-11-13.
 */

public class MainFunctionAdapter extends RecyclerView.Adapter<MainFunctionAdapter.FuctionHolder> {
    private List<FunctionItem> itemList ;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    public MainFunctionAdapter(List<FunctionItem> itemList, Context context) {
        this.itemList = itemList;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public FuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FuctionHolder(LayoutInflater.from(mContext).inflate(
                R.layout.item_main,parent,false));
    }

    @Override
    public void onBindViewHolder(FuctionHolder holder, int position) {
        FunctionItem item = itemList.get(position);
        holder.textView.setText(item.getTitle());
        holder.img.setBackgroundResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class FuctionHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView textView;
        public FuctionHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_left);
            textView = itemView.findViewById(R.id.text_center);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
        }

    }

    interface OnItemClickListener{
        void onClick(int position);
    }
}
