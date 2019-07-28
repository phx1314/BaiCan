package com.facefr.server.out;



/**
 * 对外的回调接口
 *
 */
public interface ServeOutCallBack {

	public static final int AUTO=0;
	public static final int HAND=1;

	/**
	 * 采集最优人脸照片结果
	 * @param action 结果类型(手动\自动)
	 */
	public void BestFaceNoticed(int action);

	/**
	 * 返回回调
	 */
	public void onBack();

}

