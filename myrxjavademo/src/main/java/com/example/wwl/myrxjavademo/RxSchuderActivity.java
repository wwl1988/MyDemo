package com.example.wwl.myrxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxSchuderActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLinearLayout;
    private TextView mTV;
    private Button mBT;
    private static StringBuffer sb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_schuder);

        mLinearLayout = (LinearLayout) findViewById(R.id.ll);
        mTV = (TextView) findViewById(R.id.tv_rx_map);
        mBT = (Button) findViewById(R.id.bt_rx_map);

        if (mBT != null) {
            mBT.setOnClickListener(this);
        }

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
        sb = new StringBuffer();
        sb.append("start()线程：" + Thread.currentThread().getName() + "\n");
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                sb.append("Observe.create()线程：" + Thread.currentThread().getName() + "\n");
                Drawable mDrawable = getResources().getDrawable(R.mipmap.gril);
                subscriber.onNext(mDrawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Drawable, ImageView>() {
                    @Override
                    public ImageView call(Drawable drawable) {
                        sb.append("map(): drawable-->imageview的线程：" + Thread.currentThread().getName() + "\n\n");
                        ImageView img = new ImageView(RxSchuderActivity.this);
                        img.setImageDrawable(drawable);
                        img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        return img;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ImageView>() {
                    @Override
                    public void call(ImageView imageView) {
                        sb.append("call(): 线程：" + Thread.currentThread().getName() + "\n\n");
                        mTV.setText(sb);
                        mLinearLayout.addView(imageView);
                    }
                });

    }

}
