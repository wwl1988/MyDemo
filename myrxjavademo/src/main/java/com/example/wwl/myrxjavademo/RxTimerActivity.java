package com.example.wwl.myrxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxTimerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private Button mBT1, mBT2;
    private TextView mTV;

    private Subscription mSubscription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_timer);

        mET = (EditText) findViewById(R.id.et_timer);
        mBT1 = (Button) findViewById(R.id.bt_timer_start);
        mBT2 = (Button) findViewById(R.id.bt_timer_cancal);
        mTV = (TextView) findViewById(R.id.tv_timer);


        mBT1.setOnClickListener(this);
        mBT2.setOnClickListener(this);

        mET.setText("定时器，每一秒发送打印一个数字   \n\ninterval(1, TimeUnit.SECONDS)  创建一个每隔一秒发送一次事件的对象");
        mTV.setText("开始：");


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_timer_start) {
            start();
        } else if (id == R.id.bt_timer_cancal) {
            //取消订阅
            if(mSubscription != null && !mSubscription.isUnsubscribed()){
                mSubscription.unsubscribe();
            }

        }

    }

    private void start() {
        //interval 是运行中哎computation Scheduler线程中的，因此需要转到主线程
        mSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mTV.append(aLong + " ");
                    }
                });


    }
}
