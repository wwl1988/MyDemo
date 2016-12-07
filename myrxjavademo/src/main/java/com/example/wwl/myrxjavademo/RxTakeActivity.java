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

public class RxTakeActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mET;
    private TextView mTV;
    private Button mBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_take);
        mET = (EditText) findViewById(R.id.et_rx_map);
        mTV = (TextView) findViewById(R.id.tv_rx_map);
        mBT = (Button) findViewById(R.id.bt_rx_map);
        mBT.setOnClickListener(this);
        mET.setText("输出[1,2,3,4,5,6,7,8,9,10]中第三个和第四个奇数，\n\ntake(i) 取前i个事件 \ntakeLast(i) 取后i个事件 \ndoOnNext(Action1) 每次观察者中的onNext调用之前调用");
        mBT.setText("take测试");

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
        Integer[] integer = {1,2,3,4,5,6,7,8,9,10};
        Observable.from(integer)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 != 0;
                    }
                })
                //取前4个
                .take(4)
                //取前四个中的后两个
                .takeLast(2)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTV.append("before onNext() \n");
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTV.append("onNext()---->" + integer + "\n");
                    }
                });

    }
}
