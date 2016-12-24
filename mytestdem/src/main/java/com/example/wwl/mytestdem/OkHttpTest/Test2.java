package com.example.wwl.mytestdem.OkHttpTest;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wwl on 2016/12/17.
 */

public class Test2 extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpGet();
        HttpPost();

    }

    private void HttpGet() {
        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url("xxxxxxxxxx")
                .build();
        //new call
        Call call = client.newCall(request);
        //enqueue异步的方式执行请求  execute为同步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ////该线程不是IO线程，如果需要更新UI，需要使用Handler
                //返回的是String
                response.body().string();
                //返回的是二进制字节数组
                response.body().bytes();
                //InputStream
                response.body().byteStream();
                //Reader
                response.body().charStream();
                //更新UI用
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
        });

    }

    private void HttpPost() {


    }



}
