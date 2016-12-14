package com.example.wwl.mytestdem.handlerMN;

/**
 * Created by wwl on 2016/12/13.
 */

public class Looper {

    private static final ThreadLocal<Looper> threadLocal = new ThreadLocal<>();
    /**
     * 存储Message的队列，阻塞式，没有就一直等待
     */
    final MessageQueue messageQueue;

    private Looper() {
        messageQueue = new MessageQueue();
    }

    public static void prepare(){
        if(threadLocal.get() != null){
            throw new RuntimeException("Only one Looper may be create per thread");
        }
        threadLocal.set(new Looper());
    }

    public static void loop(){
        Looper looper = myLooper();
        if(looper == null){
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        MessageQueue messageQueue = looper.messageQueue;
        for (;;){
            Message message = messageQueue.next();
            message.target.handleMessage(message);
        }

    }

    public static Looper myLooper() {
        return threadLocal.get();
    }


}
