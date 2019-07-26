package com.ntdlg.bc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MViewOne extends View {
    private Paint mPaint1, mPaint2, mPaint3;
    private float length;
    private RectF mRectF;
    private float percent1 = 0, percent2 = 0, percent3 = 0;

    public MViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MViewOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MViewOne(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mPaint1 = new Paint();
        mPaint1.setStrokeWidth(10);
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(Color.parseColor("#FFA831"));
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint2 = new Paint();
        mPaint2.setStrokeWidth(10);
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.parseColor("#36C4B7"));
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint3 = new Paint();
        mPaint3.setStrokeWidth(10);
        mPaint3.setAntiAlias(true);
        mPaint3.setColor(Color.parseColor("#FF3167"));
        mPaint3.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        length = w;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画圆
        mRectF = new RectF((float) (length * 0.1), (float) (length * 0.1),
                (float) (length * 0.9), (float) (length * 0.9));
        float percent = percent1 + percent2 + percent3;
        canvas.drawArc(mRectF, 180, percent1 * 180 / percent, false, mPaint1);
        canvas.drawArc(mRectF, 180 + percent1 * 180 / percent, percent2 * 180 / percent, false, mPaint2);
        canvas.drawArc(mRectF, 180 + percent1 * 180 / percent + percent2 * 180 / percent, percent3 * 180 / percent, false, mPaint3);
    }

    public void setProgress(float percent1, float percent2, float percent3) {
        this.percent1 = percent1;
        this.percent2 = percent2;
        this.percent3 = percent3;
        invalidate();
    }

    public void setColor1(String color) {
        mPaint1.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setColor2(String color) {
        mPaint2.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setColor3(String color) {
        mPaint3.setColor(Color.parseColor(color));
        invalidate();
    }

}