package com.example.wwl.myrxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import rx.Observable;
import rx.Subscriber;

public class NormalRxActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private Button mBT;
    private TextView mTV;

    static String str = "一二三四五\n上山打老虎\n老虎一发威\n武松就发发忄";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_rx);
        mET = (EditText) findViewById(R.id.et_normal);
        mBT = (Button) findViewById(R.id.bt_normal);
        mTV = (TextView) findViewById(R.id.tv_normal);
        mET.setText(str);

        mBT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_normal){
            if(mTV.getText().toString() != null || mTV.getText().toString().length() > 0){
                mTV.setText("");
            }
            start();
        }


    }

    private void start() {
        //创建被观察者
        Observable observable = createObservable();
        //创建观察者
        Subscriber subscriber = createSubscriber();
        //文字提示
        mTV.setText("开始订阅，准备观察....\n");
        //订阅
        observable.subscribe(subscriber);
    }

    /**
     * 创建观察者
     * @return
     */
    private Subscriber createSubscriber() {
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mTV.append("执行观察者中的onCompleted()....\n");
                mTV.append("订阅完毕，结束观察。");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mTV.append("执行观察者中的onNext()...\n");
                mTV.append(s + "...\n");
            }
        };
        return subscriber;
    }

    /**
     * 创建被观察者的三种方式
     * @return
     */
    private Observable createObservable() {
        //创建方式一：
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("一二三四五");
                subscriber.onNext("上山打老虎");
                subscriber.onNext("老虎一发威");
                subscriber.onNext("武松就发发忄");
                subscriber.onCompleted();
            }
        });
        //创建方式二：
        String[] array = {"五四三二一", "上山打老虎", "老虎一发威", "武松就发发忄"};
        Observable observable2 = Observable.from(array);
        //创建方式三：
        Observable observable3 = Observable.just("一二三四五六七", "上山打老虎", "老虎一发威", "武松就发发忄");
        return observable3;
    }

}
