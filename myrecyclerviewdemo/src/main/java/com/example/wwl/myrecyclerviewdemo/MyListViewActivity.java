package com.example.wwl.myrecyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyListViewActivity extends AppCompatActivity {

    ListView mLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);
        mLV = (ListView) findViewById(R.id.lv);

        mLV.setAdapter(new MyListViewAdapter(this, initData(), mLV));

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
