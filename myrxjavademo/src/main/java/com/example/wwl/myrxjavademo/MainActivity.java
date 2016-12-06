package com.example.wwl.myrxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBT1, mBT2, mBT3, mBT4, mBT5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBT1 = (Button) findViewById(R.id.bt1);
        mBT2 = (Button) findViewById(R.id.bt2);
        mBT3 = (Button) findViewById(R.id.bt3);
        mBT4 = (Button) findViewById(R.id.bt4);
        mBT5 = (Button) findViewById(R.id.bt5);

        mBT1.setOnClickListener(this);
        mBT2.setOnClickListener(this);
        mBT3.setOnClickListener(this);
        mBT4.setOnClickListener(this);
        mBT5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt1){
            startActivity(new Intent(this, NormalRxActivity.class));
        } else if(id == R.id.bt2){
            startActivity(new Intent(this, RxMapActivity.class));
        } else if(id == R.id.bt3){
            startActivity(new Intent(this, RxSchuderActivity.class));
        } else if(id == R.id.bt4){
            startActivity(new Intent(this, RxFlatMapActivity.class));
        } else if(id == R.id.bt5){
            startActivity(new Intent(this, RxMergeActivity.class));
        }

    }
}
