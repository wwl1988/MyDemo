package com.example.wwl.myrecyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewActivity4 extends AppCompatActivity {

    private RecyclerView mRV;
    private List<String> mData;
    private StraggeredHomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        initData();
        mRV = (RecyclerView) findViewById(R.id.rv);
        mRV.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new StraggeredHomeAdapter(this, mData);
        mAdapter.setOnItemClickListener(new StraggeredHomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("WWL", "presss短按");
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("WWL", "长按删除");
            }
        });
        mRV.setAdapter(mAdapter);

    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 'A'; i < 'Z' ; i++) {
            mData.add("" + (char)i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_straggered, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_add_item :
                mAdapter.addData(1);
                break;
            case  R.id.id_remove_item :
                mAdapter.removeData(1);
                break;
        }
        return true;
    }
}
