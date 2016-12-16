package com.example.wwl.mytestdem.mHttpClient;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * Created by wwl on 2016/12/14.
 *
 */
public class HttpRequest implements Runnable {

    /**
     * http请求枚举类型
     */
    enum RequestType {
        GET, POST
    }

    //宿主Manager
    private RequestManager hostManager;
    //URL信息
    private URLEntity urlInfo;
    //请求参数
    private List<RequestParameter> params;
    //请求回调
    private RequestCallback callback;
    //Handler对象，用于在回调时切换回主线程进行相应操作
    private Handler handler;
    //URL及HttpURLConnection对象
    private URL uri;
    private HttpURLConnection connection;
    //请求中断标志位
    private boolean interrupted = false;


    public HttpRequest(RequestManager hostManager, URLEntity urlInfo, List<RequestParameter> params,
                       RequestCallback callback) {
        this.hostManager = hostManager;
        this.urlInfo = urlInfo;
        this.params = params;
        this.callback = callback;
        handler = new Handler();
    }

    @Override
    public void run() {

        switch (urlInfo.getNetType()) {
            case GET:
                //类型为GET时，须将请求参数组装到URL链接字符串上
                String trulyURL;
                if(params != null && !params.isEmpty()){
                    StringBuilder sb = new StringBuilder(urlInfo.getUrl());
                    sb.append("?").append(convertParam2String());
                    trulyURL = sb.toString();
                } else {
                    trulyURL = urlInfo.getUrl();
                }
                //发送GET请求
                sendHttpGetToServer(trulyURL);
                break;
            case POST:
                //发送GET请求
                sendHttpPostToServer(urlInfo.getUrl());
                break;
            default:
                break;
        }


    }

    /**
     * GET请求
     * @param trulyURL
     */
    private void sendHttpGetToServer(String trulyURL) {
        try {
            uri = new URL(trulyURL);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setConnectTimeout(5000);//设置链接服务器超时时间
            connection.setReadTimeout(8000);//设置从服务器读取数据超时时间
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){//200
                //如果没有中断请求，则进行读取数据的请求
                if(!interrupted){
                    final String result = readFromResponse(connection.getInputStream());
                    if(callback != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(result);
                            }
                        });
                    }
                }
            } else {
                handleNetworkError("网络异常");
            }
        } catch (MalformedURLException e) {
            handleNetworkError("网络异常");
        } catch (IOException e) {
            handleNetworkError("网络异常");
        } finally {
            hostManager.requests.remove(this);
        }
    }

    /**
     * POST请求
     * @param url
     */
    private void sendHttpPostToServer(String url) {
        try {
            uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setConnectTimeout(5000);//设置链接服务器超时时间
            connection.setReadTimeout(8000);//设置从服务器读取数据超时时间
            /**
             *  设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
             *  http正文内，因此需要设为true, 默认情况下是false;
             */
            connection.setDoOutput(true);//允许输出
            /**
             * 设置是否从httpUrlConnection读入，默认情况下是true;
             */
            connection.setDoInput(true);//允许输入
            /**
             * getOutputStream会隐含的进行connect(），所以在开发中不调用上述的connect()也可以)。
             */
            //向请求体写参数
            if(params != null && !params.isEmpty()){
                String paramString = convertParam2String();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                bw.write(paramString);
                bw.close();
            }

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){//200
                //如果没有中断请求，则进行读取数据的请求
                if(!interrupted){
                    final String result = readFromResponse(connection.getInputStream());
                    if(callback != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(result);
                            }
                        });
                    }
                }
            } else {
                handleNetworkError("网络异常");
            }
        } catch (MalformedURLException e) {
            handleNetworkError("网络异常");
        } catch (IOException e) {
            handleNetworkError("网络异常");
        } finally {
            hostManager.requests.remove(this);
        }
    }

    /**
     * 将请求参数转化为String
     * @return
     */
    private String convertParam2String() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            RequestParameter parameter = params.get(i);
            sb.append(parameter.getName()).append("=").append(parameter.getValue());
            if(i < params.size() - 1){
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 从http response中读取响应数据
     * @param inputStream
     * @return
     */
    private String readFromResponse(InputStream inputStream) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 异常回调
     * @param errorMsg
     */
    private void handleNetworkError(final String errorMsg) {
        if(callback != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFail(errorMsg);
                }
            });
        }
    }

    void disconnect(){
        //设置标志位
        interrupted = true;
        //如果当前请求正处于与服务器连接状态下，则断开连接
        if(connection != null){
            connection.disconnect();
        }
    }

}
