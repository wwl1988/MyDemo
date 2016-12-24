package com.example.wwl.mytestdem.retrofitTest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.wwl.mytestdem.retrofitTest.Interface.DemoService;
import com.example.wwl.mytestdem.retrofitTest.Interface.DemoService2;
import com.example.wwl.mytestdem.retrofitTest.bean.ResponseInfo;
import com.example.wwl.mytestdem.retrofitTest.bean.User;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wwl on 2016/12/19.
 */

public class RetrofitTest1 extends Activity {

    public static final String TAG = "WWL";
    public static final String url = "http://192.168.199.210:8080/Test/index.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testRetrofitHttpGet();

        testRetrofitHttpPost();

    }


    private void testRetrofitHttpGet() {
        //step1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.199.210:8080/Test/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //step2
        DemoService service = retrofit.create(DemoService.class);
        //step3
        Call<ResponseInfo> call = service.testHttpGet("value");

        Map<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "value2");
        Call<ResponseInfo> call2 = service.testHttpGet2(params);
        call.enqueue(new Callback<ResponseInfo>() {

            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                String describe = response.body().describe;
                Log.d(TAG, "成功："+describe);

            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                //失败时调用
                Log.d(TAG, "失败");
            }
        });

    }

    private void testRetrofitHttpPost() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

        DemoService2 service = retrofit.create(DemoService2.class);

        Call<ResponseInfo> call = service.uploadNewUser(new User("wwl", "male", 28));

        //这种方式可以通过request.getParameter的方式直接读取参数信息
        //如果传入的字段有中文，需要设置转义，否则无法识别 或者在DemoService2中添加@Header
        try {
            String name = URLEncoder.encode("汪汪", "utf-8");
            Call<ResponseInfo> call2 = service.uploadNewUser2(name, "male", 28);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Call<ResponseInfo> call3 = service.testMultipart("this is part1", "this is part2");

        //通过@Part上传一个文件
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "text.jpg");
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        Call<ResponseInfo> call4 = service.uploadFile(image);

    }


}
