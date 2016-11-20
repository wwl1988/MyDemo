package com.example.wwl.myrecyclerviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * ListView adapter
 * Created by wwl on 2016/11/21.
 */
public class MyListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<TestBean> mListData;
    private ListView mLV;
    private int mSelectPosition = -1;

    public MyListViewAdapter(Context context, List<TestBean> mListData, ListView mLV) {
        inflater = LayoutInflater.from(context);
        this.mListData = mListData;
        this.mLV = mLV;

        for (int i = 0; i < mListData.size(); i++) {
            if(mListData.get(i).isSelected()){
                mSelectPosition = i;
            }
        }
    }

    @Override
    public int getCount() {
        return mListData != null ? mListData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //为什么会初始化4次   联想RelativeLayout会绘制4次，Linearlayout绘制2次 ??????????
        Log.d("WWL", "position:" + position);
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            //parent是干嘛的
            convertView = inflater.inflate(R.layout.item_coupon, null);
            holder.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
            holder.tv_counpon_info = (TextView) convertView.findViewById(R.id.tv_coupon_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_select.setSelected(mListData.get(position).isSelected());
        holder.tv_counpon_info.setText(mListData.get(position).getName());
        holder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != mSelectPosition){
                    //由于list机制，不可见到可见会重新刷新数据，所以如果之前选中的不可见了，只需要将mListData中设置为false
                    //如果为可见，则需要定位到改item
                    if(mSelectPosition >= 0){//如果一开始没选中，不用判断
                        int firstVisiblePosition = mLV.getFirstVisiblePosition() - mLV.getHeaderViewsCount();
                        int lastVisiblePosition = mLV.getLastVisiblePosition() - mLV.getHeaderViewsCount();
                        if(mSelectPosition >= firstVisiblePosition &&
                                mSelectPosition <= lastVisiblePosition){
                            View childView = mLV.getChildAt(mSelectPosition - firstVisiblePosition);
                            ViewHolder lastHolder = (ViewHolder)childView.getTag();
                            lastHolder.iv_select.setSelected(false);
                        }
                        mListData.get(mSelectPosition).setSelected(false);
                        //改变点击的item为选中状态
                        holder.iv_select.setSelected(true);
                        mSelectPosition = position;
                        mListData.get(mSelectPosition).setSelected(true);
                    }


                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private ImageView iv_select;
        private TextView tv_counpon_info;
    }

}
