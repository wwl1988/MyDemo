package com.example.wwl.mytestdem.mHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by wwl on 2016/12/14.
 */

public class RequestManager {

    ArrayList<HttpRequest> requests;

    public RequestManager() {
        requests = new ArrayList<>();
    }

    /**
     * 无参数调用
     */
    public HttpRequest createRequest(URLEntity url, RequestCallback callback){
        return createRequest(url,null, callback);
    }

    /**
     * 有参数调用
     */
    public HttpRequest createRequest(URLEntity urlInfo, List<RequestParameter> params, RequestCallback callback){
        HttpRequest httpRequest = new HttpRequest(this, urlInfo, params, callback);
        addRequest(httpRequest);
        return httpRequest;
    }

    /**
     * 添加到Request列表中
     * @param httpRequest
     */
    public void addRequest(HttpRequest httpRequest){
        requests.add(httpRequest);
    }

    public void deleteRequest(HttpRequest httpRequest){
        if(requests.contains(requests))
             requests.remove(httpRequest);
    }


    /**
     * 取消所有的网络请求，包括正在执行的
     */
    public void cancelAllRequest(){
        BlockingQueue<Runnable> queue = RequestThreadPool.getQueue();
        for (int i = requests.size() - 1; i >= 0; i++) {
            HttpRequest request = requests.get(i);
            if(queue.contains(request)){
                queue.remove(request);
            } else {//如果队列中不存在，说明正在执行
                request.disconnect();
            }
            deleteRequest(request);
        }
        requests.clear();
    }

    /**
     * 取消未执行的网络请求   //有疑问  为何不在cancelAllRequest()的基础上修改呢？？？？？  把else去掉
     */
    public void cancelBlockingRequest(){
        //clone复制
        List<HttpRequest> intersaction = (List<HttpRequest>) requests.clone();
        //retainAll方式意思是取两个list的交集
        intersaction.retainAll(RequestThreadPool.getQueue());
        //清除线程池中的筛选出来的list交集（请求）
        RequestThreadPool.getQueue().removeAll(intersaction);
        //删除RequestManager中存储的请求
        requests.removeAll(intersaction);
    }

    /**
     * 取消指定的网络请求
     */
    public void cancelDesignatedRequest(HttpRequest request){
        if(!RequestThreadPool.removeTaskFromQueue(request)){
            request.disconnect();
        }
        deleteRequest(request);
    }
}
