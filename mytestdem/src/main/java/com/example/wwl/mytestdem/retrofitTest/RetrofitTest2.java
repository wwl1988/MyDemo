package com.example.wwl.mytestdem.retrofitTest;

import android.app.Activity;
import android.os.Bundle;

import com.example.wwl.mytestdem.retrofitTest.Interface.RequestServes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 假定url全地址为：http://106.3.227.33/pulamsi/mobileLogin/submit.html
 * Created by wwl on 2016/12/22.
 */

public class RetrofitTest2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testPost();
    }

    private void testPost() {
        Retrofit retrofit = new Retrofit.Builder()
                //基本地址头
                .baseUrl("http://106.3.227.33/pulamsi/")
                //设置返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //设置返回值为Gson的支持(以实体类反回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Java的动态代理模式
        RequestServes requestServes = retrofit.create(RequestServes.class);
        //传入参数
        Call<String> call = requestServes.getString("userName", "123");
        //发出网络请求
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //TODO
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //TODO
            }
        });

    }

}
