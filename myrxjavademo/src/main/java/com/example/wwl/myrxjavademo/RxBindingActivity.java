package com.example.wwl.myrxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxBindingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private Button mBT;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);

        mET = (EditText) findViewById(R.id.et_binding);
        mBT = (Button) findViewById(R.id.bt_binding);
        mTV = (TextView) findViewById(R.id.et_binding);

        mBT.setOnClickListener(this);

        mET.setText("输入含有1的数字，下方才会出现提示");
        mTV.setText("提示数据：");

        initData();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_binding){
            mTV.append("lalala \n\n");
        }
    }

    private void initData() {
        //用来监听mET，同时匹配输入数据来提示
        RxTextView.textChanges(mET)
                //在一次事件发生后的一段时间内，如果没有新的事件发生，则发出该事件
                .debounce(500, TimeUnit.MILLISECONDS)
                //运行在新的线程中
                .observeOn(Schedulers.newThread())
                //通过输入的数据，来匹配数据库中的数据从而提示
                .map(new Func1<CharSequence, List<String>>() {

                    List<String> list = new ArrayList<>();

                    @Override
                    public List<String> call(CharSequence charSequence) {
                        if(charSequence.toString().contains("1")){
                            for (int i = 0; i < 5; i++) {
                                list.add("11"+i);
                            }
                        }
                        return list;
                    }
                })
                //将list列表转换成想要的String数据
                .flatMap(new Func1<List<String>, Observable<String>>() {

                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                //转换主线程 以便操作组件
                .observeOn(AndroidSchedulers.mainThread())
                //过滤
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !mTV.getText().toString().contains(s);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //展示数据
                        mTV.append(s + "\n");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("WWL", "异常啦啦啦啦啦啦啦啦啦啦啦啦啦啦");
                    }
                });

        mBT.setText("连续点击防止误触");
        RxView.clicks(mBT)
                //防止误触，间隔500ms
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    //相当于onClickListener回调
                    @Override
                    public void call(Void aVoid) {
                        mTV.append("\n*****防误触测试******\n");
                    }
                });

    }
}
