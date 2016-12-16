package com.example.wwl.mytestdem.mHandler;

import android.util.Log;

/**
 * Created by wwl on 2016/12/13.
 */

public class Main {

    public static void main(String[] args){

         new Main().start();
    }

    private void start(){

        Looper.prepare();

        onCreate();
        //死循环，阻塞式
        Looper.loop();

        System.out.println("exit.....");

        throw new RuntimeException("Main thread looper unexpectedly exited");

    }

    /**
     * 相当于Android中的Ui线程
     */
    private void onCreate() {

        final Thread thread = Thread.currentThread();
        Log.d("WWL", "main thread:"+thread);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("WWL", "current thread is main thread :" + (thread == Thread.currentThread()));
                Log.d("WWL", "msg:" + msg);
            }
        };

        /**
         * 测试一：主线程创建handler，子线程使用该handler发送消息
         */
        new Thread(){
            public void run(){
                //模拟操作耗时
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.obj = "new Thread:" + Thread.currentThread();
                message.what = (int) System.currentTimeMillis();
                //子线程发送消息
                handler.sendMessage(message);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message = new Message();
                message.obj = "message...obj1";
                message.what = 1;
                //子线程发送消息
                handler.sendMessage(message);

                message = new Message();
                message.obj = "message...obj2";
                message.what = 2;
                //子线程发送消息
                handler.sendMessage(message);

                message = new Message();
                message.obj = "message...obj3";
                message.what = 3;
                //子线程发送消息
                handler.sendMessage(message);

            }
        }.start();

        /**
         * 测试二：在thread内部创建handler 会抛出异常
         */
        new Thread(){
            public void run(){

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //此种方式会抛出异常
                Handler h = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d("WWL", "msg:" + msg);
                    }
                };
                //不会抛出异常的方法 Looper.getMainLooper()是将主线程的Looper传入该handler
                /*new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        //TODO
                    }
                };*/


                Message message = new Message();
                message.obj = "handler in new Thread";
                message.what = 66;
                //子线程发送消息
                h.sendMessage(message);

            }
        }.start();

    }


}
