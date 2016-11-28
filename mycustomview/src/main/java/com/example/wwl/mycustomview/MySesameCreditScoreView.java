package com.example.wwl.mycustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 芝麻信用分自定义控件
 * Created by wwl on 2016/11/26.
 */
public class MySesameCreditScoreView extends View {

    private Context context;
    /**
     * 雷达的角个数
     */
    private int mRadarCount = 5;
    /**
     * 角的弧度，计算坐标用
     */
    private float mRadian = (float) ((2 * Math.PI) / mRadarCount);
    /**
     * 雷达各个角的标题
     */
    private String[] mTitle = new String[]{"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质"};
    /**
     * 雷达各个角的图标
     */
    private int[] mTitleDrawable = new int[]{R.mipmap.ic_performance, R.mipmap.ic_history, R.mipmap.ic_contacts,
            R.mipmap.ic_performance, R.mipmap.ic_identity};
    /**
     * 假定每个角最大值为200
     */
    private float mMaxValue = 200;
    /**
     * 仿造的假数据：
     */
    private int[] mActualData = new int[]{100, 150, 70, 90, 180};
    /**
     * 雷达线的画笔
     */
    private Paint mLinePaint;
    /**
     * 雷达覆盖区画笔
     */
    private Paint mCoverPaint;
    /**
     * 中心分数的画笔
     */
    private Paint mCenterScorePaint;

    private Paint mAngleTitlePaint;
    /**
     * 中心分数的大小
     */
    private float mCenterScoreTextSize;
    /**
     * 中心分数文字的颜色
     */
    private int mCenterScoreTextColor;
    /**
     * 雷达区半径
     */
    private float mRadarRadius;
    /**
     * 画线笔的颜色
     */
    private int mLineColor;
    /**
     * 覆盖区笔的颜色
     */
    private int mCoverColor;
    /**
     * 角标题的文字大小
     */
    private float mTitleSize;
    /**
     * 角标题的文字颜色
     */
    private int mTitleColor;

    /**
     * 中心点的坐标
     */
    private int mXCenter, mYCenter;


    public MySesameCreditScoreView(Context context) {
        this(context, null);
    }

    public MySesameCreditScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySesameCreditScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //读取XML配置参数
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MySesameCreditScoreView, defStyleAttr, 0);
        mCenterScoreTextSize = ta.getDimension(R.styleable.MySesameCreditScoreView_center_score_text_size,
                Utils.dip2px(context, 25));
        mCenterScoreTextColor = ta.getColor(R.styleable.MySesameCreditScoreView_center_score_text_color, Color.WHITE);
        mRadarRadius = ta.getDimension(R.styleable.MySesameCreditScoreView_radar_radius,
                Utils.dip2px(context, 200));
        mLineColor = ta.getColor(R.styleable.MySesameCreditScoreView_line_color, Color.WHITE);
        mCoverColor = ta.getColor(R.styleable.MySesameCreditScoreView_cover_color, Color.WHITE);
        mTitleSize = ta.getDimensionPixelSize(R.styleable.MySesameCreditScoreView_title_size,
                Utils.dip2px(context, 16));
        mTitleColor = ta.getColor(R.styleable.MySesameCreditScoreView_title_color, Color.WHITE);
        ta.recycle();

        //初始化划线的画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(1.2f);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        //初始化覆盖区域的画笔
        mCoverPaint = new Paint();
        mCoverPaint.setAntiAlias(true);
        mCoverPaint.setColor(mCoverColor);
        mCoverPaint.setAlpha(160);
        mCoverPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //初始化中心分数的画笔
        mCenterScorePaint = new TextPaint();
        mCenterScorePaint.setAntiAlias(true);
        mCenterScorePaint.setColor(mCenterScoreTextColor);
        mCenterScorePaint.setTextSize(mCenterScoreTextSize);
        //初始化角title的画笔
        mAngleTitlePaint = new Paint();
        mAngleTitlePaint.setAntiAlias(true);
        mAngleTitlePaint.setColor(mTitleColor);
        mAngleTitlePaint.setTextSize(mTitleSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadarRadius = Math.min(w, h) / 4;
        mXCenter = w / 2;
        mYCenter = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mMeasureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int mMeasureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mMeasureHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int mMeasureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;
        if (mMeasureWidthMode == MeasureSpec.EXACTLY) {
            width = mMeasureWidthSize;
        } else {
            width = Utils.dip2px(context, 300);
        }

        if (mMeasureHeightMode == MeasureSpec.EXACTLY) {
            height = mMeasureHeightSize;
        } else {
            height = Utils.dip2px(context, 300);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制外围五边形
        drawPentagon(canvas);
        //绘制中心点到五边形顶点的连线
        drawCenterToAngle(canvas);
        //绘制覆盖区
        drawCoverArear(canvas);
        //绘制中心区文字
        drawCenterText(canvas);
        //绘制五角处的文字
        drawAngleTitle(canvas);
        //绘制五角文字上图片
        drawAngleTitleDrawable(canvas);
    }

    /**
     * 绘制五角处的图片
     *
     * @param canvas
     */
    private void drawAngleTitleDrawable(Canvas canvas) {

        for (int i = 0; i < mRadarCount; i++) {
            String text = mTitle[i];
            Rect bound = new Rect();
            mAngleTitlePaint.getTextBounds(text, 0, text.length(), bound);

            Bitmap mDrawable = BitmapFactory.decodeResource(getResources(), mTitleDrawable[i]);
            int mDrawableWidth = mDrawable.getWidth();
            int mDrawableHeight = mDrawable.getHeight();

            int x = getPoint(i, 1.1f).x;
            int y = getPoint(i, 1.1f).y;

            int dx = (bound.width() - mDrawableWidth) / 2;
            int dy = -bound.height() - mDrawableHeight;

            if (i == 0) {
                x += dx;
                y += dy;
            } else if (i == 1) {
                x += dx;
                y += mDrawableHeight / 2 + dy;
            } else if (i == 2) {
                x -= bound.width() - dx;
                y += mDrawableHeight / 2 + dy;
            } else if (i == 3) {
                x -= bound.width() - dx;
                y += dy;
            } else if (i == 4) {
                x -= bound.width() / 2 - dx;
                y += dy;
            }
            canvas.drawBitmap(mDrawable, x, y, mAngleTitlePaint);
        }

    }

    /**
     * 绘制五角处的文字
     *
     * @param canvas
     */
    private void drawAngleTitle(Canvas canvas) {

        for (int i = 0; i < mRadarCount; i++) {
            String text = mTitle[i];
            Rect bound = new Rect();
            mAngleTitlePaint.getTextBounds(text, 0, text.length(), bound);

            Bitmap mDrawable = BitmapFactory.decodeResource(getResources(), mTitleDrawable[i]);
            int mDrawableHeight = mDrawable.getHeight();

            int x = getPoint(i, 1.1f).x;
            int y = getPoint(i, 1.1f).y;

            if (i == 1) {
                y += mDrawableHeight / 2;
            } else if (i == 2) {
                x -= bound.width();
                y += mDrawableHeight / 2;
            } else if (i == 3) {
                x -= bound.width();
            } else if (i == 4) {
                x -= bound.width() / 2;
            }
            canvas.drawText(text, x, y, mAngleTitlePaint);
        }

    }

    /**
     * 绘制中心区
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        int centerScore = 0;
        for (int i = 0; i < mActualData.length; i++) {
            centerScore += mActualData[i];
        }
        Rect bound = new Rect();
        String text = String.valueOf(centerScore);
        mCenterScorePaint.getTextBounds(text, 0, text.length(), bound);
        canvas.drawText(text, mXCenter - bound.width() / 2, mYCenter + bound.height() / 2, mCenterScorePaint);
    }

    /**
     * 绘制覆盖区
     *
     * @param canvas
     */
    private void drawCoverArear(Canvas canvas) {
        Path path = new Path();
        float precentage;
        for (int i = 0; i < mRadarCount; i++) {
            precentage = mActualData[i] / mMaxValue;
            if (i == 0) {
                path.moveTo(getPoint(i, precentage).x, getPoint(i, precentage).y);
            } else {
                path.lineTo(getPoint(i, precentage).x, getPoint(i, precentage).y);
            }
        }
        //闭合曲线
        path.close();
        //绘制边线
        canvas.drawPath(path, mLinePaint);
        //绘制实心区
        canvas.drawPath(path, mCoverPaint);
    }

    /**
     * 绘制中心点到五边形顶点的连线
     *
     * @param canvas
     */
    private void drawCenterToAngle(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mRadarCount; i++) {
            path.moveTo(mXCenter, mYCenter);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, mLinePaint);
        }
    }

    /**
     * 绘制最外层五边形
     *
     * @param canvas
     */
    private void drawPentagon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mRadarCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {
                path.lineTo(getPoint(i).x, getPoint(i).y);
            }
        }
        //闭合曲线
        path.close();
        canvas.drawPath(path, mLinePaint);
    }

    /**
     * 根据五边形的角的位置返回该角的坐标点
     *
     * @param position
     * @return
     */
    private Point getPoint(int position) {
        return getPoint(position, 1);
    }

    /**
     * 根据五边形的角的位置返回该角的坐标点
     *
     * @param position
     * @param percentage
     * @return
     */
    private Point getPoint(int position, float percentage) {
        int x = 0;
        int y = 0;
        if (position == 0) {
            x = (int) (mXCenter + Math.sin(mRadian) * mRadarRadius * percentage);
            y = (int) (mYCenter - Math.cos(mRadian) * mRadarRadius * percentage);
        } else if (position == 1) {
            x = (int) (mXCenter + Math.sin(mRadian / 2) * mRadarRadius * percentage);
            y = (int) (mYCenter + Math.cos(mRadian / 2) * mRadarRadius * percentage);
        } else if (position == 2) {
            x = (int) (mXCenter - Math.sin(mRadian / 2) * mRadarRadius * percentage);
            y = (int) (mYCenter + Math.cos(mRadian / 2) * mRadarRadius * percentage);
        } else if (position == 3) {
            x = (int) (mXCenter - Math.sin(mRadian) * mRadarRadius * percentage);
            y = (int) (mYCenter - Math.cos(mRadian) * mRadarRadius * percentage);
        } else if (position == 4) {
            x = mXCenter;
            y = (int) (mYCenter - mRadarRadius * percentage);
        }
        return new Point(x, y);
    }

}

