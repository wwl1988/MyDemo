package com.example.wwl.mytestdem.mHttpClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * Created by wwl on 2016/12/14.
 */

public class RequestThreadPool {
    //线程池的封装
    private static ThreadPoolExecutor pool;

    /**
     * 根据配置信息初始化线程池
     */
    static void init() {
        PacHttpClientConfig config = PacHttpClient.config;
        pool = new ThreadPoolExecutor(config.corePoolSize,
                config.maxPoolSize, config.keepAliveTime, config.timeUtil, config.blockingQueue);

    }

    /**
     * 执行任务
     * @param r  请求
     */
    public static void execute(final Runnable r) {
        if (r != null) {
            try {
                pool.execute(r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清空阻塞队列
     */
    static void removeAllTask() {
        pool.getQueue().clear();
    }

    /**
     * 从阻塞队列中删除指定任务
     * @param obj
     * @return
     */
    static boolean removeTaskFromQueue(final Object obj) {
        if (!pool.getQueue().contains(obj)) {
            return false;
        }
        pool.getQueue().remove(obj);
        return true;
    }

    /**
     * 获取阻塞队列
     *
     * @return
     */
    static BlockingQueue<Runnable> getQueue() {
        return pool.getQueue();
    }

    /**
     * 关闭，并等待任务执行完毕，不接受新的任务
     */
    static void shutdown() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新的线程
     */
    static void shutdownRightnow() {
        if (pool != null) {
            pool.shutdown();
            try {
                //设置超时时间为1s，等同于关于所有任务
                pool.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
