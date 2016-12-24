package com.example.wwl.mytestdem.retrofitTest.Interface;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wwl on 2016/12/22.
 */

public interface RequestServes {
    //相当于xxx.do
    @POST("mobileLogin/submit.html")
    Call<String> getString(@Query("loginname") String loginname,
                           @Query("loginpwd") String loginpwd);

}
