package com.example.wwl.myrecyclerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.debug.hv.ViewServer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_rv, bt_lv;
    Button bt_rv2, bt_rv3, bt_rv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rv = (Button) findViewById(R.id.bt_rv);
        bt_lv = (Button) findViewById(R.id.bt_lv);
        bt_rv2 = (Button) findViewById(R.id.bt_rv2);
        bt_rv3 = (Button) findViewById(R.id.bt_rv3);
        bt_rv4 = (Button) findViewById(R.id.bt_rv4);

        bt_rv.setOnClickListener(this);
        bt_rv2.setOnClickListener(this);
        bt_rv3.setOnClickListener(this);
        bt_rv4.setOnClickListener(this);
        bt_lv.setOnClickListener(this);
        ViewServer.get(this).addWindow(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_rv){
            startActivity(new Intent(this, MyRecyclerViewActivity.class));
        } else if(id == R.id.bt_lv){
            startActivity(new Intent(this, MyListViewActivity.class));
        } else if(id == R.id.bt_rv2){
            startActivity(new Intent(this, MyRecyclerViewActivity2.class));
        } else if(id == R.id.bt_rv3){
            startActivity(new Intent(this, MyRecyclerViewActivity3.class));
        } else if(id == R.id.bt_rv4){
            startActivity(new Intent(this, MyRecyclerViewActivity4.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }
}
