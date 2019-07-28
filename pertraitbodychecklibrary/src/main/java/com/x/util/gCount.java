package com.x.util;

public class gCount {
	public static int getProcessFrameCount() {
		return mProcessFrameCount;
	}


	//处理的帧数
	public static void IDEProcessFrameCount() {
		if (mProcessFrameCount > 0 && mLastNullTime > 0) {
			mNullCountTime += (System.currentTimeMillis() - mLastNullTime);
			mLastNullTime = 0;
		}
		mProcessFrameCount++;
	}

	public static Integer getCameraFrameCount() {
		return mCameraFrameCount;
	}

	//相机帧数
	public static void IDECameraFrameCount() {
		if (mCameraFrameCount > 500)
			clear();
		mCameraFrameCount++;
	}

	//丢弃的帧
	public static void IDEDropFrameCount() {
		mDropFrameCount++;
	}

	public static void clear() {
		mProcessFrameCount = 0;
		mCameraFrameCount = 0;
		mNullCountTime = 0;
		mDropFrameCount = 0;
		mBeginTime = System.currentTimeMillis();

		System.gc();
	}

	public static long getTimeCount() {
		return System.currentTimeMillis() - mBeginTime;
	}

	public static void setNullMark() {
		mLastNullTime = System.currentTimeMillis();
	}

	public static long getNullCountTime() {
		return mNullCountTime;
	}

	public static int getDropFrameCount() {
		return mDropFrameCount;
	}

	// ========================================
	private static int mProcessFrameCount = 0;
	private static int mCameraFrameCount = 0;
	private static long mBeginTime = System.currentTimeMillis();
	private static long mLastNullTime = 0;
	private static long mNullCountTime = 0;
	private static int mDropFrameCount = 0;
}
