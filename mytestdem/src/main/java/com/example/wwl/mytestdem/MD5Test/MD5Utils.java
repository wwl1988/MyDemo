package com.example.wwl.mytestdem.MD5Test;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5完整性校验
 * Created by wwl on 2017/1/5.
 */

public class MD5Utils {

    /**
     * 默认的密码字符串组合，用来将字节转换成16进制表示的字符，apche校验下载的文件的正确性默认使用的就是这个组合
     */
    protected static char hexDigsts[] = {
            '0','1', '2','3','4','5','6','7', '8', '9','a','b','c','d','e', 'f'};

    protected static MessageDigest messageDigest = null;

    static {

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.d("WWL", "MessageDigest初始化失败");
        }

    }

    /**
     * 生成MD5校验值
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(Context mContext, String fileName) throws IOException {
        InputStream inputStream = mContext.getAssets().open(fileName);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while((numRead = inputStream.read(buffer)) > 0){
            messageDigest.update(buffer, 0, numRead);
        }
        inputStream.close();
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int i, int length) {
        StringBuffer sb = new StringBuffer(2 * length);
        int k = i + length;
        for (int j = i; j < k; j++) {
            appendHexPair(bytes[j], sb);
        }
        return sb.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer sb) {
        char c0 = hexDigsts[(bt & 0xf0) >> 4];//取字节中的高四位进行转化
        char c1 = hexDigsts[bt & 0xf];//取字节中低四位的数字转换
        sb.append(c0);
        sb.append(c1);
    }

    public static boolean checkPassword(String md51, String md52){
        return md51.equals(md52);
    }

}
