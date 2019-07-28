package com.facefr.server.out;

import java.util.List;

import android.graphics.Bitmap;
import android.view.ViewGroup;

public interface BodyCheckBaseInterface {


	/**
	 * 设置回调接口
	 * @param outCallBack
	 */
	public void setOutCallBack(BodyServerOutCallBack outCallBack);

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
	 * Activity暂停事件
	 * @return
	 */
	public boolean pause();

	/**
	 * 获取活体检测图片
	 * @return
	 */
	public List<Bitmap> getBmpList();

	/**
	 * 获取活体检测数据包
	 * @return
	 */
	public String getPackagedData();

	/**
	 * 设置控件标题栏文字
	 *
	 * @param text
	 */
	public void setTitleTxt(String text);

	/**
	 * @param picNum
	 * 设置活体照片数量
	 */
	public void setPictureNum(int picNum);


	/**
	 * @param actType
	 * 设置动作类型
	 */
	public void setActType(int actType);


	/**
	 * @param actCount
	 * 设置动作次数
	 */
	public void setActCount(int actCount);

	/**
	 * @param actDiff
	 * 设置动作难易程度
	 */
	public void setActDifficult(int actDiff);

	/**
	 * @param isOpenTick
	 * 是否播放倒计时声音
	 */
	public void isOpenTick(boolean isOpenTick);


}

