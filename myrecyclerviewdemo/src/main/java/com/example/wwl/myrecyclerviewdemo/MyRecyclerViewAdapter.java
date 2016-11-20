package com.example.wwl.myrecyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wwl on 2016/11/20.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CouponVH> {

    private List<TestBean> mListData;
    private Context context;
    private LayoutInflater inflater;
    private RecyclerView mRV;

    private int mSelectPosition = -1;

    public MyRecyclerViewAdapter(List<TestBean> mListData, Context context, RecyclerView mRV) {
        this.mListData = mListData;
        this.context = context;
        this.mRV = mRV;
        inflater = LayoutInflater.from(context);

        for (int i = 0; i < mListData.size(); i++) {
            if (mListData.get(i).isSelected()) {
                mSelectPosition = i;
            }
        }

    }

    @Override
    public CouponVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("WWL", "onCreateViewHolder:");
        return new CouponVH(inflater.inflate(R.layout.item_coupon, parent, false));
    }

    @Override
    public void onBindViewHolder(final CouponVH holder, final int position) {
        Log.d("WWL", "position:"+ position);
        holder.iv_select.setSelected(mListData.get(position).isSelected());
        holder.tv_coupon_info.setText(mListData.get(position).getName());

        holder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectPosition != position) {
//                    //方案一：
//                    if (mSelectPosition >= 0) {
//                        mListData.get(mSelectPosition).setSelected(false);
//                        Bundle payloadOld = new Bundle();
//                        payloadOld.putBoolean("KEY_SELECT", false);
//                        notifyItemChanged(mSelectPosition, payloadOld);
//                    }
//                    mSelectPosition = position;
//                    mListData.get(mSelectPosition).setSelected(false);
//                    Bundle payloadNew = new Bundle();
//                    payloadNew.putBoolean("KEY_SELECT", true);


                    //方案二： RV默认会多缓存两级
                    if(mSelectPosition >= 0){

                        CouponVH mCouponVH = (CouponVH) mRV.findViewHolderForLayoutPosition(mSelectPosition);
                        if(mCouponVH != null){
                            mCouponVH.iv_select.setSelected(false);
                        } else {
                            notifyItemChanged(mSelectPosition, false);//强制清除缓存
                        }
                        mListData.get(mSelectPosition).setSelected(false);
                    }

                    holder.iv_select.setSelected(true);
                    mSelectPosition = position;
                    mListData.get(position).setSelected(true);

                }
            }
        });

    }

    @Override
    public void onBindViewHolder(CouponVH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Log.d("WWL", "onBindViewHolder333333:");
    }

    //    @Override
//    public void onBindViewHolder(CouponVH holder, int position, List<Object> payloads) {
//        if(payloads.isEmpty()){
//            onBindViewHolder(holder, position);
//        } else {
//            Bundle payload = (Bundle) payloads.get(0);
//            Boolean key_select = payload.getBoolean("KEY_SELECT");
//            holder.iv_select.setSelected(key_select);
//        }
//
//    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public static class CouponVH extends RecyclerView.ViewHolder {

        private ImageView iv_select;
        private TextView tv_coupon_info;

        public CouponVH(View itemView) {
            super(itemView);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
            tv_coupon_info = (TextView) itemView.findViewById(R.id.tv_coupon_info);
        }
    }

}


