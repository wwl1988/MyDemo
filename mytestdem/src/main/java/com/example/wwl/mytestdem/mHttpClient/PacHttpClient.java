package com.example.wwl.mytestdem.mHttpClient;

import android.app.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by wwl on 2016/12/14.
 */

public class PacHttpClient {
    //配置信息
    static PacHttpClientConfig config;
    //存放每个Activity对应的RequestManager
    static Map<Activity, RequestManager> managerMap;

    /**
     * 初始化
     *
     * @param config 全局配置信息
     */
    public static void init(PacHttpClientConfig config) {
        PacHttpClient.config = config;
        managerMap = new HashMap<>();
        //初始化线程池
        RequestThreadPool.init();
    }

    /**
     * 执行http不含参请求
     *
     * @param activity 调用该方法的Activity
     * @param apiKey   根据该值从xml文件中获取对应的URLEntity
     * @param callback 调用回调
     */
    public static void invokeRequest(Activity activity, String apiKey, RequestCallback callback) {
        invokeRequest(activity, apiKey, null, callback);
    }

    /**
     * 执行http含参请求
     *
     * @param activity 调用该方法的Activity
     * @param apiKey   根据该值从xml文件中获取对应的URLEntity
     * @param params   http请求参数
     * @param callback 回调
     */
    public static HttpRequest invokeRequest(Activity activity, String apiKey, List<RequestParameter> params,
                                            RequestCallback callback) {
        //根据apiKey从XML文件中读取封装的URL实体信息
        URLEntity urlEntity = URLConfigManager.findURLByKey(apiKey);
        RequestManager manager = checkRequestManager(activity, true);
        //创建Request
        HttpRequest request = manager.createRequest(urlEntity, params, callback);
        RequestThreadPool.execute(request);
        return request;
    }

    /**
     * 取消指定Activity中发起的所有Http请求
     * @param activity
     */
    public static void cancelAllrequest(Activity activity){
        checkRequestManager(activity, false).cancelAllRequest();
    }

    /**
     * 取消线程池中整个阻塞队列所有Http请求
     */
    public static void cancelAllTask(){
        RequestThreadPool.removeAllTask();
    }

    /**
     * 取消指定Activity中未执行的请求
     * @param activity
     */
    public static void cancelBlockingRequest(Activity activity){
        checkRequestManager(activity, false).cancelAllRequest();
    }

    /**
     * 取消指定请求
     */
    public static void cancelDesignatedRequest(Activity activity, HttpRequest request){
        checkRequestManager(activity, false).cancelDesignatedRequest(request);
    }

    /**
     * 访问Activity对应的RequestManager对象
     *
     * @param activity  访问的Activity
     * @param createNew 当RequestManager为null时，是否创建新的RequestManager对象
     * @return
     */
    private static RequestManager checkRequestManager(Activity activity, boolean createNew) {
        RequestManager manager;
        if ((manager = managerMap.get(activity)) == null) {
            if (createNew) {
                manager = new RequestManager();
                managerMap.put(activity, manager);
            } else {
                throw new NullPointerException(activity.getClass().getName() + "'s RequestManager is null.");
            }
        }
        return manager;
    }

    /**
     * 关闭线程池，并等待正在执行的任务执行完毕，不接受新的任务
     */
    public static void shutdown(){
        RequestThreadPool.shutdown();
    }

    /**
     * 立即关闭，并挂起所有正在执行的线程，不接受新的任务
     */
    public static void shutdownRightNow(){
        RequestThreadPool.shutdownRightnow();
    }

}
