package com.example.wwl.myfragmenttest.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by wwl on 2017/1/11.
 */

public class ViewUtil {

    public static void hideSoftKeybord(Activity activity){
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
