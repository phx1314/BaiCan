package com.facefr.server.out;

/**
 * 对外回调接口
 *
 */
public interface BodyServerOutCallBack {

	/**
	 * 活体检测完成回调
	 */
	public void detectionOk(boolean issuccess);

	/**
	 * 当点击返回按钮时回调
	 */
	public void onBack();


}