package com.example.wwl.myrecyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewActivity extends AppCompatActivity {

    RecyclerView mRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        mRV = (RecyclerView) findViewById(R.id.rv);

        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(new MyRecyclerViewAdapter(initData(), this, mRV));

    }

    private List<TestBean> initData() {
        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new TestBean("满100减99"));
            list.add(new TestBean(i == 0 ? true : false , "满100减98"));
            list.add(new TestBean("满100减97"));
            list.add(new TestBean("满100减96"));
            list.add(new TestBean("满100减95"));
        }
        return list;
    }

}
