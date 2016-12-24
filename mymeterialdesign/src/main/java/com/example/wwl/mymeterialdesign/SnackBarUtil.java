package com.example.wwl.mymeterialdesign;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wwl on 2016/12/21.
 */

public class SnackBarUtil {

    public static final int Info = 1;
    public static final int Comfirm = 2;
    public static final int Warning = 3;
    public static final int Alert = 4;

    public static int red = 0xfff44336;
    public static int green = 0xff4caf50;
    public static int blue = 0xff295f3;
    public static int orange = 0xffffc107;

    /**
     * Snackbar短时间显示，自定义背景颜色和字体颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar ShortSnackbar(View view, String message, int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * Snackbar长时间显示，自定义背景颜色和字体颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar LongSnackbar(View view, String message, int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * Snackbar自定义时长显示，自定义背景颜色和字体颜色
     * @param view
     * @param message
     * @param duration
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, String message, int duration, int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * Snackbar短时间显示，可预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar ShortSnackbar(View view, String message, int type){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * Snackbar长时间显示，可预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar LongSnackbar(View view, String message, int type){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * Snackbar自定义时长显示，可预设类型
     * @param view
     * @param message
     * @param duration
     * @param type
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, String message, int duration, int type){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     *
     * @param snackbar
     * @param layoutId
     * @param index
     * @return
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index){
        //首先获取Snackbar的跟布局
        View snackbarView = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        //初始化要添加的View
        View addView = LayoutInflater.from(snackbarView.getContext()).inflate(layoutId, null);
        //设置要添加View的参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        //添加
        snackbarLayout.addView(addView, index, params);
    }

    /**
     * 选择预设类型
     * @param snackbar
     * @param type
     */
    private static void switchType(Snackbar snackbar, int type) {
        switch (type){
            case Info:
                setSnackbarColor(snackbar, blue);
                break;
            case Comfirm:
                setSnackbarColor(snackbar, green);
                break;
            case Warning:
                setSnackbarColor(snackbar, orange);
                break;
            case Alert:
                setSnackbarColor(snackbar, Color.YELLOW, red);
                break;
        }

    }

    /**
     * 设置snackbar的背景颜色
     * @param snackbar
     * @param blue
     */
    private static void setSnackbarColor(Snackbar snackbar, int blue) {
        View view = snackbar.getView();
        if(view !=null){
            view.setBackgroundColor(blue);
        }
    }

    /**
     * 设置snackbar的背景颜色和字体颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    private static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view !=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView)view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }


}
