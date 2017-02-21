package com.example.wwl.myfragmenttest.common;

import android.os.Handler;
import android.os.Message;

import com.example.wwl.myfragmenttest.listener.HandlerContainer;

import java.lang.ref.WeakReference;

/**
 * Created by wwl on 2017/1/11.
 */

public class Safehandler<T extends HandlerContainer> extends Handler {

    private WeakReference<T> mRef;

    public Safehandler(WeakReference<T> mRef) {
        this.mRef = mRef;
    }

    public Safehandler(T t) {
        mRef = new WeakReference<T>(t);
    }

    public T getContainer(){
        return  mRef.get();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        HandlerContainer container = getContainer();
        if(container != null){
            container.handleMessage(msg);
        }
    }
}
