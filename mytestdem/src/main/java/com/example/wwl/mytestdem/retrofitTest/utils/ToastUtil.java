package com.example.wwl.mytestdem.retrofitTest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wwl on 2016/12/21.
 */

public class ToastUtil {

    private static Toast toast;

    public static void showTooast(Context context, String content){
        if(toast == null){
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
