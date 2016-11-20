package com.example.wwl.mywatchertablecustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义View之心跳记录表
 * Created by wwl on 2016/11/19.
 */
public class MyWatcherTableView extends View {
    /**
     * Activity引用的上下文
     */
    private Context context;
    /**
     * xy轴的画笔及文字的宽度
     */
    private float mCoordinateWitdh;
    /**
     * xy轴及文字的颜色
     */
    private int mCoordinateColor;
    /**
     * xy轴坐标文字的大小
     */
    private float mCoordinateTextSize;
    /**
     * 画笔
     */
    private Paint mXYPaint;

    /**
     * 圆的宽度
     */
    private float mCircleRadius;
    /**
     * 圆的颜色
     */
    private int mCircleColor;
    /**
     * 圆的画笔
     */
    private Paint mCirclePaint;

    private String[] xCoordinateTextStringArray = new String[]{"02", "03", "04", "05", "06", "07", "08"};
    private String[] yCoordinateTextStringArray = new String[]{"66", "67", "68", "74", "77", "128", "98"};
    private int[] yCoordinateTextIntArray = new int[yCoordinateTextStringArray.length];
    private int yMin;//假定用户数据都大于0
    private int yUserMax;//用户的最大动态数值

    public MyWatcherTableView(Context context) {
        this(context, null);
    }

    public MyWatcherTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWatcherTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取资源文件
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyWatcherTableView, defStyleAttr, 0);
        mCoordinateWitdh = typedArray.getDimension(R.styleable.MyWatcherTableView_coordinate_width, Utils.dip2px(context, 2));
        mCoordinateTextSize = typedArray.getDimension(R.styleable.MyWatcherTableView_coordinate_text_size, Utils.dip2px(context, 14));
        mCoordinateColor = typedArray.getColor(R.styleable.MyWatcherTableView_coordinate_color, Color.GRAY);

        mCircleRadius = typedArray.getDimension(R.styleable.MyWatcherTableView_circle_radius, Utils.dip2px(context, 4));
        mCircleColor = typedArray.getColor(R.styleable.MyWatcherTableView_circle_color, Color.BLUE);

        //注意回收
        typedArray.recycle();
        //设置画笔
        mXYPaint = new Paint();
        mXYPaint.setStrokeWidth(mCoordinateWitdh);
        mXYPaint.setColor(mCoordinateColor);
        mXYPaint.setAntiAlias(true);
        mXYPaint.setTextSize(mCoordinateTextSize);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        //将动态的String数据转化为int型数据
        for (int i = 0; i < yCoordinateTextStringArray.length; i++) {
            yCoordinateTextIntArray[i] = Integer.parseInt(yCoordinateTextStringArray[i]);
            //获取动态数据的最小值
            if( i == 0){
                yMin = yCoordinateTextIntArray[0];
                yUserMax = yCoordinateTextIntArray[0];
            } else {
                yMin = Math.min(yMin, yCoordinateTextIntArray[i]);
                yUserMax = Math.max(yUserMax, yCoordinateTextIntArray[i]);
            }
        }
        //使得y轴坐标轴首位为10的倍数
        yMin = yMin - yMin % 10;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        if(measureWidthMode == MeasureSpec.EXACTLY){
            width = measureWidthSize;
        } else {
            width = Utils.dip2px(context, 300);
        }

        if(measureHeightMode == MeasureSpec.EXACTLY){
            height = measureHeightSize;
        } else {
            height = Utils.dip2px(context, 240);
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制xy轴坐标和箭头
        drawXYCoordinate(canvas);
        //绘制xy坐标轴上的刻度文字
        drawXYCoordinateText(canvas);

    }

    /**
     * 绘制xy轴坐标
     * @param canvas
     */
    private void drawXYCoordinate(Canvas canvas) {
        //绘制y轴
        canvas.drawLine(getPaddingLeft(),
                getPaddingTop(),
                getPaddingLeft(),
                getHeight() - getPaddingTop() - getPaddingBottom(),
                mXYPaint);

        //绘制y轴箭头
        canvas.drawLine(getPaddingLeft() - 15,
                getPaddingTop() + 30,
                getPaddingLeft(),
                getPaddingTop(),mXYPaint);
        canvas.drawLine(getPaddingLeft() + 15,
                getPaddingTop() + 30,
                getPaddingLeft(),
                getPaddingTop(),mXYPaint);
        //绘制x轴
        canvas.drawLine(getPaddingLeft(),
                getHeight() - getPaddingTop() - getPaddingBottom(),
                getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom(),
                mXYPaint);
        //绘制x轴箭头
        canvas.drawLine(getWidth() - getPaddingLeft() - getPaddingRight() - 30,
                getHeight() - getPaddingTop() - getPaddingBottom() - 15,
                getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom(),
                mXYPaint);
        canvas.drawLine(getWidth() - getPaddingLeft() - getPaddingRight() - 30,
                getHeight() - getPaddingTop() - getPaddingBottom() + 15,
                getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom(),
                mXYPaint);

    }

    /**
     * x轴等分
     */
    private int xScale;
    /**
     * y轴等分
     */
    private double yScale;
    /**
     *  绘制xy轴坐标刻度文字
     * @param canvas
     */
    private void drawXYCoordinateText(Canvas canvas) {
        //x轴
        // x轴可用长度
        int xLength = getWidth() - getPaddingRight() - getPaddingLeft() - 50;
        Rect bound = new Rect();
        //x轴单位刻度对应长度
        xScale = (int) Math.ceil(xLength / xCoordinateTextStringArray.length);
        for (int i = 0; i < xCoordinateTextStringArray.length; i++) {
            //绘制刻度
            canvas.drawLine(getPaddingLeft() + i * xScale,
                    getHeight() - getPaddingTop() - getPaddingBottom() - 10,
                    getPaddingLeft() + i * xScale,
                    getHeight() - getPaddingTop() - getPaddingBottom(),
                    mXYPaint);
            //绘制x轴文字
            mXYPaint.getTextBounds(xCoordinateTextStringArray[i], 0, xCoordinateTextStringArray[i].length(), bound);
            canvas.drawText(xCoordinateTextStringArray[i],
                    getPaddingLeft() + i * xScale - bound.width() / 2,
                    getHeight() - getPaddingTop() - getPaddingBottom() + 50,
                    mXYPaint);

        }

        //y轴
        //y轴长度  分四段
        int yLength = getHeight() - getPaddingTop() * 2 - getPaddingBottom() - 50;
        //y轴达内长度对应长度
        yScale = yLength  * 1.0 / (yUserMax - yMin);
        //绘制4等分线
        for (int i = 0; i < 5 ; i++) {
            //绘制y轴刻度
            canvas.drawLine(getPaddingLeft(),
                    getHeight() - getPaddingTop() - getPaddingBottom() - i * yLength / 4,
                    getWidth() - getPaddingLeft() - getPaddingRight() - 20,
                    getHeight() - getPaddingTop() - getPaddingBottom() - i * yLength / 4,
                    mXYPaint);
            //绘制y轴等分文字
            String dividerIntLength = String.valueOf(i * (yUserMax - yMin)/ 4 + yMin);
            mXYPaint.getTextBounds(dividerIntLength, 0, dividerIntLength.length(), bound);
            canvas.drawText(dividerIntLength,
                    getPaddingLeft() - bound.width() - 30,
                    getHeight() - getPaddingTop() - getPaddingBottom() - i * yLength / 4  + bound.height() / 2,
                    mXYPaint);
        }

        //绘制坐标轴中的曲线
        for (int i = 0; i < yCoordinateTextIntArray.length - 1; i++) {
            canvas.drawLine(getPaddingLeft() + i * xScale,
                    (int)(getHeight() - getPaddingTop() - getPaddingBottom() - yScale * (yCoordinateTextIntArray[i] - yMin)),
                    getPaddingLeft() + (i + 1) * xScale,
                    (int)(getHeight() - getPaddingTop() - getPaddingBottom() - yScale * (yCoordinateTextIntArray[i + 1] - yMin)),
                    mXYPaint);
        }
        //绘制圆点
        for (int i = 0; i < yCoordinateTextIntArray.length; i++) {
            mCirclePaint.setColor(mCircleColor);
            canvas.drawCircle(getPaddingLeft() + i * xScale,
                    (int)(getHeight() - getPaddingTop() - getPaddingBottom() - yScale * (yCoordinateTextIntArray[i] - yMin)), mCircleRadius, mCirclePaint);
            mCirclePaint.setColor(Color.WHITE);
            canvas.drawCircle(getPaddingLeft() + i * xScale,
                    (int)(getHeight() - getPaddingTop() - getPaddingBottom() - yScale * (yCoordinateTextIntArray[i] - yMin)), mCircleRadius - 5, mCirclePaint);
            //绘制圆点上的数值
            mXYPaint.getTextBounds(yCoordinateTextStringArray[i], 0, yCoordinateTextStringArray[i].length(), bound);
            canvas.drawText(yCoordinateTextStringArray[i],
                    getPaddingLeft() + i * xScale - bound.width() / 2 ,
                    (int)(getHeight() - getPaddingTop() - getPaddingBottom() - yScale * (yCoordinateTextIntArray[i] - yMin) - mCircleRadius / 2 - 15),
                    mXYPaint);
        }

    }


}
