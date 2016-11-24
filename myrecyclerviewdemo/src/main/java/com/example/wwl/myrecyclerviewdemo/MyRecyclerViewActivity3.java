package com.example.wwl.myrecyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewActivity3 extends AppCompatActivity {

    private RecyclerView mRV;
    private List<String> mData;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);

        initData();
        mRV = (RecyclerView) findViewById(R.id.rv);
        mRV.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        mRV.addItemDecoration(new DividerGridItemDecoration(this));
        mRV.setAdapter(mAdapter = new HomeAdapter(this));

    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 'A'; i < 'Z' ; i++) {
            mData.add("" + (char)i);
        }
    }


    /**
     *
     * RecyclerView的Adapter
     */
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

        private Context context;
        private LayoutInflater inflater;

        public HomeAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.item_home, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_num.setText(mData.get(position));

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_num;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            }
        }

    }


}
