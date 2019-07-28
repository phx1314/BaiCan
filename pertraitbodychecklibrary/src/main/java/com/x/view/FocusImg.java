package com.x.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FocusImg extends View {

	private Paint mPaint;
	private float left;
	private float top;
	private float right;
	private float bottom;

	public FocusImg(Context context) {
		this(context, null);
	}

	public FocusImg(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FocusImg(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5.0f);
	}

	public void update(float left, float top, float right, float bottom) {
		this.left =left;
		this.top =top;
		this.right =right;
		this.bottom =bottom;
		postInvalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawRect(left, top, right, bottom, mPaint);
	}

}
