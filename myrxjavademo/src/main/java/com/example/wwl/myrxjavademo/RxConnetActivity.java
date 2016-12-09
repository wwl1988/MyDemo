package com.example.wwl.myrxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Timestamped;

public class RxConnetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private TextView mTV;
    private Button mBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_connet);

        mET = (EditText) findViewById(R.id.et_rx_map);
        mTV = (TextView) findViewById(R.id.tv_rx_map);
        mBT = (Button) findViewById(R.id.bt_rx_map);


        mBT.setOnClickListener(this);
        mBT.setText("点击排序并加时间戳");
        mET.setText("为给定数据列表：1,3,5,2,34,7,5,86,23,43中每一个数据加上一个时间戳");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_rx_map) {
            mTV.setText("");
            start();
        }
    }

    private void start() {
        Integer[] words = {1, 3, 5, 2, 34, 7, 5, 86, 23, 43};
        Observable.from(words)
                //排序
                .toSortedList()
                //排序后，转化
                .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(List<Integer> integers) {
                        return Observable.from(integers);
                    }
                })
                //加时间戳
                .timestamp()
                //订阅处理
                .subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                        mTV.append("value:" + integerTimestamped.getValue() + "    time:  ");
                        mTV.append(sdf.format(new Date(integerTimestamped.getTimestampMillis())) + "\n");
                    }
                });

    }

}
