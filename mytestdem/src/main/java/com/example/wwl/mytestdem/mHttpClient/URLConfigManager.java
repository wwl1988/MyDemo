package com.example.wwl.mytestdem.mHttpClient;

import android.content.res.XmlResourceParser;


import com.example.wwl.mytestdem.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by wwl on 2016/12/14.
 */

public class URLConfigManager {

    private static HashMap<String, URLEntity> sUrlMap;

    public static URLEntity findURLByKey(String key){
        if(sUrlMap == null || sUrlMap.isEmpty()){
            findURLFromXML();
        }
        return sUrlMap.get(key);
    }

    private static void findURLFromXML() {
        sUrlMap = new HashMap<>();
        //获取XML解析器
        XmlResourceParser parser = PacHttpClient.config.context.getResources().getXml(R.xml.request_urls);
        int eventCode;
        try {
            eventCode = parser.getEventType();
            while(eventCode != XmlResourceParser.END_DOCUMENT){
               switch (eventCode) {
                   case XmlResourceParser.START_DOCUMENT:
                       break;
                   case XmlResourceParser.END_TAG:
                       break;
                   case XmlResourceParser.START_TAG:
                       if(parser.getName().equals("Node")){
                           URLEntity urlEntity = new URLEntity();
                           String key = parser.getAttributeValue(null, "key");
                           urlEntity.setKey(key);
                           String expires = parser.getAttributeValue(null, "expires");
                           urlEntity.setExpires(Long.parseLong(expires));
                           String netType = parser.getAttributeValue(null, "netType");
                           urlEntity.setNetType("GET".equals(netType) ? HttpRequest.RequestType.GET : HttpRequest.RequestType.POST);
                           String url = parser.getAttributeValue(null, "url");
                           urlEntity.setUrl(url);
                           sUrlMap.put(key, urlEntity);
                       }
                    default:
                        break;
               }
                try {
                    eventCode = parser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }

    }


}
