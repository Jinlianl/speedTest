package com.example.speedtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawAnimation extends View {
	private Paint mPaint, qPaint, gPaint; // 声明两只画笔

	private Path mPath; // 声明路径
	// 分别 代表贝塞尔曲线的开始坐标,结束坐标,控制点坐标
	private int qStartX, qStartY, qEndX, qEndY;

	private int screenW, screenH; // 用于屏幕的宽高

	public int speed = 0;

	public DrawAnimation(Context context) {
		super(context);

		init();
	}

	private void init() {
		mPaint = new Paint();// 创建一个画笔对象
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize((float) 28.0);
		mPaint.setAntiAlias(true); // 消除锯齿
		mPaint.setStrokeWidth(3);// 设置画笔宽度
		qPaint = new Paint();
		qPaint.setColor(Color.RED);
		qPaint.setStrokeWidth(6);
		qPaint.setAntiAlias(true); // 消除锯齿
		gPaint = new Paint();
		gPaint.setColor(Color.YELLOW);
		gPaint.setStrokeWidth(2);
		gPaint.setAntiAlias(true);
		gPaint.setTextSize((float) 20.0);
	}

	public DrawAnimation(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DrawAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		qStartX = w / 2;
		qStartY = h - 100;
	}

	void passValue(long spd) {
		speed = (int) spd;
	}

	@Override
	protected void onDraw(Canvas mCanvas) {
		// TODO Auto-generated method stub
		super.onDraw(mCanvas);
		// 指针指向逻辑
		int degree = (int) Math.round(speed / 3.3);
		if (speed < 120) {
			double trans = (45 - degree) * 2 * Math.PI / 360;
			qEndY = (int) Math.round(qStartY - 200 * Math.cos(trans));
			qEndX = (int) Math.round(qStartX - 200 * Math.sin(trans));
		} else {
			double trans = (degree - 45) * 2 * Math.PI / 360;
			qEndY = (int) Math.round(qStartY - 200 * Math.cos(trans));
			qEndX = (int) Math.round(qStartX + 200 * Math.sin(trans));
		}

		//画指针
		mCanvas.drawLine(qStartX, qStartY, qEndX, qEndY, mPaint);
		mCanvas.drawPoint(qStartX, qStartY, qPaint);
		float temp = (float) (200 / 1.414 * 1.1);
		mCanvas.drawText("0", (float) (qStartX - temp - 15),
				(float) (qStartY - temp), gPaint);
		mCanvas.drawText("150", (float) (qStartX - 15),
				(float) (qStartY - 200 * 1.1), gPaint);
		mCanvas.drawText("KB", (float) (qStartX - 15), (float) (qStartY - 130),
				mPaint);
		mCanvas.drawText("300", (float) (qStartX + temp - 15),
				(float) (qStartY - temp), gPaint);
		invalidate();
	}
}
