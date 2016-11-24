package com.example.wwl.myrecyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwl on 2016/11/24.
 */

public class StraggeredHomeAdapter extends RecyclerView.Adapter<StraggeredHomeAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> mData;
    private List<Integer> mHeight;
    private onItemClickListener listener;

    public StraggeredHomeAdapter(Context context, List<String> mData) {
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
        mHeight = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            mHeight.add((int) (100 + Math.random() * 300));
        }
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_stragered_home, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("WWL", "onBindViewHolder:" + position);
        ViewGroup.LayoutParams lp = holder.tv_num.getLayoutParams();
        lp.height = mHeight.get(position);
        holder.tv_num.setLayoutParams(lp);
        holder.tv_num.setText(mData.get(position));
        if(listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemLongClick(holder.itemView, pos);
                    //设置长按删除数据
                    removeData(position);
                    return true;
                }
            });

        }
    }



    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


    public void removeData(int position){
        mData.remove(position);
        mHeight.remove(position);
        notifyItemRemoved(position);
    }

    public void addData(int position){
        mData.add(position, "Insert one");
        mHeight.add((int) (100 + Math.random() * 300));
        notifyItemInserted(position);
    }

}
