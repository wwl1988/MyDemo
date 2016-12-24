package com.example.wwl.mytestdem.OkHttpTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.framed.Header;

/**
 * okHttp测试
 * Created by wwl on 2016/12/16.
 */

public class Test {

    static String url = "http://localhost:8080/LocalServer/hello.api";

    public static void main(String[] args){
        try {
            testHttpGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testHttpGet() throws IOException {
        //创建一个默认配置的okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建一个request请求
        Request request = new Request.Builder().url(url).build();
        //响应
        Response response = client.newCall(request).execute();
        //判断是否响应成功
        if(response.isSuccessful()){
            String content = response.body().string();
        }

    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String testHttpPost(String url, String json) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .header("headerKey1", "headerValue1")//添加请求头
                .addHeader("headerKey2", "headerValue2")//追加请求头2
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        //下载网络图片到本地
        if(response.isSuccessful()){
            InputStream in = response.body().byteStream();
            FileOutputStream fos = new FileOutputStream(new File("Android.jpg"));
            int length;
            byte[] buffer = new byte[1024];
            while((length = in.read(buffer))> 0){
                fos.write(buffer, 0, length);
            }

        }

        //获取响应头
        Headers header = response.headers();
        for (int i = 0; i < header.size(); i++) {
            String name = header.name(i);
        }

        //以其他形式提交数据
        RequestBody formBody = new FormBody.Builder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "XXXXXXXXXXXXXX")
                .build();

        Request request1 = new Request.Builder().url(url).post(formBody).build();

        //响应缓存
        Cache cache = new Cache(new File("D:\\cache"), 1024*1000);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        OkHttpClient client1 = builder.build();

        //取消请求
        Call call = client1.newCall(request1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                call.cancel();//可以在这里取消请求
            }
        });
        call.cancel();//可以在这里取消请求
        client.dispatcher().cancelAll();//取消所有请求
        //如果取消一个正在连接的请求，会抛出IO异常

        //异步请求
        //通过execute方法执行的请求是同步的请求，异步的话需要使用enqueue方法
        //这里所谓的同步与异步，并不是指多个请求的串行或者并行执行的区别，而是指线程是否会因此次请求而阻塞

        return response.body().string();

    }


}
