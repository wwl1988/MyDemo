package com.example.wwl.mytestdem.mHttpClient;

import android.content.Context;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by wwl on 2016/12/14.
 */

public class PacHttpClientConfig {

    //默认核心池大小
    private static final int DEFAULT_CORE_SIZE = 5;
    //最大线程数
    private static final int DEFAULT_MAX_SIZE = 10;
    //线程池中空余线程存活时间
    private static final long DEFAULT_KEEP_ALIVE_TIME = 15;
    //时间单位
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;
    //线程池阻塞队列（默认长度为50）
    private static final int BLOCK_QUEUE_SIZE = 50;
    private static BlockingQueue<Runnable> defaultQueue = new ArrayBlockingQueue<>(BLOCK_QUEUE_SIZE);

    //默认初始化
    int corePoolSize = DEFAULT_CORE_SIZE;
    int maxPoolSize = DEFAULT_MAX_SIZE;
    TimeUnit timeUtil = DEFAULT_TIME_UNIT;
    long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;
    BlockingQueue<Runnable> blockingQueue = defaultQueue;

    Context context;

    public PacHttpClientConfig(Context context) {
        this.context = context;
    }

    public PacHttpClientConfig  corePoolSize(int corePoolSize){
        this.corePoolSize = corePoolSize;
        return this;
    }

    public PacHttpClientConfig  maxPoolSize(int maxPoolSize){
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public PacHttpClientConfig  keepAliveTime(int keepAliveTime){
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public PacHttpClientConfig  timeUtil(TimeUnit timeUtil){
        this.timeUtil = timeUtil;
        return this;
    }

    public PacHttpClientConfig  blockingQueue(BlockingQueue<Runnable> blockingQueue){
        this.blockingQueue = blockingQueue;
        return this;
    }

}
