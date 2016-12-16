package com.example.wwl.mytestdem.mHandler;

/**
 * Created by wwl on 2016/12/13.
 */

public class Handler {

    private MessageQueue messageQueue;

    public Handler() {
        Looper looper = Looper.myLooper();
        if(looper == null){
            throw new RuntimeException(
                    "Can't create handler inside thread that has not called Looper.prepare().");
        }
        this.messageQueue = looper.messageQueue;
    }

    public void sendMessage(Message msg){
        msg.target = this;
        messageQueue.enqueueMessage(msg);
    }

    public void handleMessage(Message msg){

    }

}
