package com.example.dachui.cuttomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.dachui.cuttomview.utils.DensityUtils;

/**
 * <p>描述：半圆形进度条度view</p>
 * 作者： liujiyi<br>
 * 日期： 2018/6/25<br>
 */
public class ProgressView extends View {
    private static final String TAG = ProgressView.class.getSimpleName();
    private int mHeight;
    private int mWidth;
    /**
     * 半圆方向
     * 0 左
     * 1 右
     */
    private int mDirection;
    /**
     * 圆角度
     */
    private float mRound;
    /**
     * 半圆形宽度
     */
    private int mFitWidth;
    private Paint mBgPaint;
    private Paint mUsedBgPaint;
    private Rect mFitRect;
    private Rect mBgRect;
    private RectF mArcBgRect;
    private Rect textWidthRect = new Rect();
    private float mAnimStartAngle;

    public ProgressView(Context context) {
        super(context);
        initPaint(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        Log.d(TAG, "initPaint");
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0);
        int noUsedColor = Color.BLACK;
        int usedColor = Color.BLUE;
        try {
            mDirection = a.getInteger(R.styleable.ProgressView_half_progress_direction, 0);
            mRound = a.getDimension(R.styleable.ProgressView_half_progress_Round, 40f);
            noUsedColor = a.getColor(R.styleable.ProgressView_half_progress_no_used_bg, Color.BLACK);
            usedColor = a.getColor(R.styleable.ProgressView_half_progress_used_bg, Color.BLUE);
        } finally {
            a.recycle();
        }
        Log.d(TAG, "mDirection==="+mDirection);
        Log.d(TAG, "mRound==="+mRound);
        mUsedBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUsedBgPaint.setAntiAlias(true);
        mUsedBgPaint.setColor(usedColor);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(noUsedColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //只想match-parent或者精确值处理
        //wrap-content需加判断处理
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        mWidth = specSize;
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        mHeight = specSize;
        setMeasuredDimension(mWidth, mHeight);
        Log.d(TAG, mHeight + "===" + mWidth);
        if (mDirection == 0) {
            //左边
            mArcBgRect = new RectF(0, 0, getRound(), mHeight);
            mBgRect = new Rect(getRound() / 2, 0, mWidth, mHeight);
            mFitRect = new Rect(getRound() / 2, 0, mFitWidth, mHeight);
        } else {
            mArcBgRect = new RectF(mWidth - getRound(), 0, mWidth, mHeight);
            mBgRect = new Rect(0, 0, mWidth - getRound() / 2, mHeight);
            mFitRect = new Rect(0, 0, mWidth - getRound() / 2, mHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        canvas.drawArc(mArcBgRect, 0, 360, false, mBgPaint);//绘制圆弧，不含圆心
        canvas.drawRect(mBgRect, mBgPaint);

        if (mDirection == 0) {
            //左边
            drawProgressLeft(canvas);
        } else {
            drawProgressRight(canvas);
        }
    }

    private void drawProgressRight(Canvas canvas) {
        mFitRect.right = mFitWidth;
        if (mFitRect.right > mWidth - getRound() / 2) {
            mFitRect.right = mWidth - getRound() / 2;
        }
        canvas.drawRect(mFitRect, mUsedBgPaint);

        if (mFitWidth > (mWidth - getRound() / 2)) {
            Log.d(TAG, "===mWidth - getRound() / 2===" + (mWidth - getRound() / 2));
            float angle = (mFitWidth - (mWidth - getRound() / 2)) * (90f / (float) (getRound() / 2));
            Log.d(TAG, "===angle===" + angle);
            if (angle > 90) {
                angle = 90;
            }
            canvas.drawArc(mArcBgRect, 90 - angle, 180 + 2 * angle, false, mUsedBgPaint);//绘制圆弧，不含圆心
        }
    }

    private void drawProgressLeft(Canvas canvas) {
        float angle = mFitWidth * (90f / (float) (getRound() / 2));
        Log.d(TAG, "angle" + angle);
        Log.d(TAG, "mFitWidth" + mFitWidth);
        if (angle > 90) {
            angle = 90;
        }
        canvas.drawArc(mArcBgRect, 180 - angle, 2 * angle, false, mUsedBgPaint);//绘制圆弧，不含圆心
        if (mFitWidth > getRound() / 2) {
            mFitRect.right = mFitWidth;
            mFitRect.left = getRound() / 2;
            canvas.drawRect(mFitRect, mUsedBgPaint);
        }
    }

    public void setFitWidth(int width) {
        mFitWidth = width;
        invalidate();
    }


    private int getRound() {
        return DensityUtils.dp2px(getContext(), mRound);
    }
}
