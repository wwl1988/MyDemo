package com.example.wwl.mytestdem.mHttpClient;

/**
 * Created by wwl on 2016/12/14.
 */

public interface RequestCallback {

    void onSuccess(String content);

    void onFail(String errorMessage);

}
