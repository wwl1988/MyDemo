package com.example.wwl.mywatchertablecustom;

import android.content.Context;
import android.util.Log;

/**
 * 工具类
 * Created by wwl on 2016/11/19.
 */
public class Utils {

    /**
     * dip 转化为px
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context , int dip){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
