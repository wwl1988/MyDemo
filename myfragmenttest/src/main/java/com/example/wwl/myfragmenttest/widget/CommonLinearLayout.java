package com.example.wwl.myfragmenttest.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wwl on 2017/1/9.
 */

public class CommonLinearLayout extends LinearLayout {

    public CommonLinearLayout(Context context) {
        this(context, null);
    }

    public CommonLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(isKeyboardShown(this)){

        }
    }

    /**
     * 监听软键盘事件
     * @param rootView
     * @return
     */
    private boolean isKeyboardShown(View rootView) {

        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
