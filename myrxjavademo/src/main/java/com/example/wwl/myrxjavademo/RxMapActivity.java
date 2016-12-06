package com.example.wwl.myrxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxMapActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private TextView mTV;
    private Button mBT;

    private Integer[] number = {1,2,3,4,5,6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_map);
        mET = (EditText) findViewById(R.id.et_rx_map);
        mTV = (TextView) findViewById(R.id.tv_rx_map);
        mBT = (Button) findViewById(R.id.bt_rx_map);

        mBT.setOnClickListener(this);
        mET.setText("输入Integer(int):1,2,3,4,5,6\n输出：type：true/false");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_rx_map){
            if(mTV.getText().toString().length() > 0){
                mTV.setText("");
            }
            start();
        }
    }

    private void start() {
        mTV.setText("\n 输入参数：1，2，3，4，5，6 \n");
        Observable.from(number)
                .map(new Func1<Integer, Boolean>() {

                    @Override
                    public Boolean call(Integer integer) {
                        mTV.append("\nmap() Integer--->Boolean");
                        return (integer < 3);
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mTV.append("\n观察到输出结果：");
                        mTV.append(aBoolean.toString() + "\n");
                    }
                });

    }
}
