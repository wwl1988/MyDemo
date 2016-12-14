package com.example.wwl.myrxjavademo;

import android.app.Application;

/**
 * Created by wwl on 2016/12/13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.wanjian.sak.LayoutManager.init(this);
    }
}
