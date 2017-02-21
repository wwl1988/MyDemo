package com.example.wwl.myfragmenttest;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by wwl on 2017/1/9.
 */

public class BaseApplication extends Application {

    private static String sCacheDir;
    public static Context sAppContext;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sAppContext = getApplicationContext();

        if(sAppContext.getExternalCacheDir() != null &&
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            sCacheDir = sAppContext.getExternalCacheDir().toString();
        } else {
            sCacheDir = sAppContext.getCacheDir().toString();
        }

    }

    public static Context getsAppContext() {
        return sAppContext;
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }

}
