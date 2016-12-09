package com.example.wwl.myrxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxSortActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private TextView mTV;
    private Button mBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_sort);

        mET = (EditText) findViewById(R.id.et_rx_map);
        mTV = (TextView) findViewById(R.id.tv_rx_map);
        mBT = (Button) findViewById(R.id.bt_rx_map);

        mBT.setOnClickListener(this);
        mBT.setText("点击排序");
        mET.setText("为给定数据列表排序，1,3,5,2,34,7,5,56,23,43  \n\ntoSortedList():为时间中标的数据排序");

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
        Integer[] numbers = {1,3,5,2,34,7,9,86,23,43};
        Observable.from(numbers)
                .toSortedList()
                .flatMap(new Func1<List<Integer>, Observable<Integer>>() {

                    @Override
                    public Observable<Integer> call(List<Integer> integers) {
                        return Observable.from(integers);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTV.append(integer + " ,");
                    }
                });


    }

}
