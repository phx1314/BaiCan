package com.facefr.server.out;

import android.graphics.Bitmap;
import android.view.ViewGroup;

public interface ManagerBaseInterface {

	/**
	 * 设置回调
	 * @param outCallBack
	 */
	public void setOutCallBack(ServeOutCallBack outCallBack);

	/**
	 * 创建并显示控件
	 * @param mView 传入存放控件的父节点布局LayOut
	 * @return
	 */
	public boolean show(ViewGroup mView);

	/**
	 * 销毁控件
	 * @return
	 */
	public boolean destory();

	/**
	 * 获取当前采集人脸照片
	 * @return
	 */
	public Bitmap getFacePhoto();

	/**
	 * 设置是否显示静态人脸对准框
	 * @param isShow
	 */
	public void setFaceAlign(boolean isShow);

//	/**
//	 * 设置是否显示实时人脸追踪框
//	 * @param isShow
//	 */
//	public void setFaceTrack(boolean isShow);

	/**
	 * 设置是否显示拍照按钮
	 * @param isShow
	 */
//	public void setBtn(boolean isShow);

	/**
	 * 设置控件标题栏文字
	 * @param text
	 */
	public void setTitleTxt(String text);

	/**
	 * Activity暂停事件
	 * @return
	 */
	public boolean pause();
}
