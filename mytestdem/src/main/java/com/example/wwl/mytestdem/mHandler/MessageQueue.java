package com.example.wwl.mytestdem.mHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by wwl on 2016/12/13.
 */

public class MessageQueue {

    private BlockingQueue<Message> blockingQueue = new LinkedBlockingDeque<>();

    /**
     * 阻塞式，没有消息就一直等待
     * @return
     */
    public Message next(){
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 插入到消息队列尾部
     * @param message
     */
    void enqueueMessage(Message message){
        try {
            blockingQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
