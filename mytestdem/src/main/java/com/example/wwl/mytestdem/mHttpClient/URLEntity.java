package com.example.wwl.mytestdem.mHttpClient;

/**
 *
 * Created by wwl on 2016/12/14.
 */

public class URLEntity {
    /**
     * apiKey
     */
    private String key;
    /**
     * 缓存时间
     */
    private long expires;
    /**
     * 请求方式  GET or POST
     */
    private HttpRequest.RequestType netType;
    /**
     * url
     */
    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public HttpRequest.RequestType getNetType() {
        return netType;
    }

    public void setNetType(HttpRequest.RequestType netType) {
        this.netType = netType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
