package com.example.wwl.mytestdem.retrofitTest.Interface;


import com.example.wwl.mytestdem.retrofitTest.bean.ResponseInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by wwl on 2016/12/19.
 */

public interface DemoService {

    @GET("api/retrofitTesting")
    Call<ResponseInfo>  testHttpGet(@Path("param") String apiAction);

    @GET("api/retrofitTesting")
    Call<ResponseInfo>  testHttpGet2(@QueryMap Map<String, String> params);

}
