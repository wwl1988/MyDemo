package com.example.wwl.mytestdem.retrofitTest.Interface;


import com.example.wwl.mytestdem.retrofitTest.bean.ResponseInfo;
import com.example.wwl.mytestdem.retrofitTest.bean.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by wwl on 2016/12/19.
 */

public interface DemoService2 {

    @POST("api/users")
    Call<ResponseInfo>  uploadNewUser(@Body User user);

    //这种方式可以通过request.getParameter的方式直接读取参数信息
    //可以适用多参数@FileMap替代
    //@Header不同于@Headers, @Header是动态添加配置
    @Headers("Content-type:application/x-www-form-unlencoded;charset=UTF-8")
    @FormUrlEncoded
    @POST("api/users")
    Call<ResponseInfo>  uploadNewUser2(@Field("username") String username, @Field("gender") String male, @Field("age") int age);

    //使用@Part上传String数据时，其默认参数类型为application/json
    @Multipart
    @POST("api/multipartTesting")
    Call<ResponseInfo> testMultipart(@Part("part1") String part1, @Part("part2") String part2);

    @Multipart
    @POST("api/files")
    Call<ResponseInfo> uploadFile(@Part("file\";filename=\"text.jpg") RequestBody photo);

}


