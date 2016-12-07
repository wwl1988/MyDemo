package com.example.wwl.myrxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBT1, mBT2, mBT3, mBT4, mBT5;
    private Button mBT6, mBT7, mBT8, mBT9, mBT10, mBT11, mBT12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBT1 = (Button) findViewById(R.id.bt1);
        mBT2 = (Button) findViewById(R.id.bt2);
        mBT3 = (Button) findViewById(R.id.bt3);
        mBT4 = (Button) findViewById(R.id.bt4);
        mBT5 = (Button) findViewById(R.id.bt5);
        mBT6 = (Button) findViewById(R.id.bt6);
        mBT7 = (Button) findViewById(R.id.bt7);
        mBT8 = (Button) findViewById(R.id.bt8);
        mBT9 = (Button) findViewById(R.id.bt9);
        mBT10 = (Button) findViewById(R.id.bt10);
        mBT11 = (Button) findViewById(R.id.bt11);
        mBT12 = (Button) findViewById(R.id.bt12);

        mBT1.setOnClickListener(this);
        mBT2.setOnClickListener(this);
        mBT3.setOnClickListener(this);
        mBT4.setOnClickListener(this);
        mBT5.setOnClickListener(this);
        mBT6.setOnClickListener(this);
        mBT7.setOnClickListener(this);
        mBT8.setOnClickListener(this);
        mBT9.setOnClickListener(this);
        mBT10.setOnClickListener(this);
        mBT11.setOnClickListener(this);
        mBT12.setOnClickListener(this);

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
        } else if(id == R.id.bt6){
            startActivity(new Intent(this, RxBindingActivity.class));
        } else if(id == R.id.bt7){
            startActivity(new Intent(this, RxFilterActivity.class));
        } else if(id == R.id.bt8){
            startActivity(new Intent(this, RxTakeActivity.class));
        } else if(id == R.id.bt9){
            startActivity(new Intent(this, RxTimerActivity.class));
        } else if(id == R.id.bt10){
//            startActivity(new Intent(this, RxMergeActivity.class));
        } else if(id == R.id.bt11){
//            startActivity(new Intent(this, RxMergeActivity.class));
        } else if(id == R.id.bt12){
//            startActivity(new Intent(this, RxMergeActivity.class));
        }

    }
}
