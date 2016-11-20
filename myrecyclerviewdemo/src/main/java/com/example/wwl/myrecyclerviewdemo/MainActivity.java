package com.example.wwl.myrecyclerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_rv, bt_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rv = (Button) findViewById(R.id.bt_rv);
        bt_lv = (Button) findViewById(R.id.bt_lv);

        bt_rv.setOnClickListener(this);
        bt_lv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_rv){
            startActivity(new Intent(this, MyRecyclerViewActivity.class));
        } else if(id == R.id.bt_lv){
            startActivity(new Intent(this, MyListViewActivity.class));
        }

    }


}
