package com.facefr.server.in;


import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CollectInfoInstance implements Serializable {
	/**
	 * APP采集数据信息
	 */
	private static final long serialVersionUID = 1L;

	private byte[] selBuffer;// 人脸正面照
	private boolean bodySuccess;// 活体检测结果
	private boolean isgetData;//是否或取data数据包
	private String strPhotoBase64;
	private List<Bitmap> bodyBmps;

	private int mActCount;     //执行次数
	private int mActDifficult; //动作难度
	private int mActType;      //动作类型
	private int mPictureNum;   //照片张数

	public Lock lock = new ReentrantLock();// 锁对象


	private static CollectInfoInstance mInstance = null;

	public static CollectInfoInstance getInstance() {
		return mInstance;
	}

	// 单实例,仅初始化一次
	public static CollectInfoInstance getInstance(Context ctx) {
		if (mInstance == null) {
			synchronized (CollectInfoInstance.class) {
				if (mInstance == null)
					mInstance = new CollectInfoInstance();
			}
		}
		return mInstance;
	}

	private CollectInfoInstance() {
	}

	public void init(){
		selBuffer=null;
		bodySuccess=false;
		strPhotoBase64="";
	}

	public void setPictureNum(int pictureNum){
		mPictureNum = pictureNum;
	}

	public int getPictureNum(){
		return mPictureNum;
	}


	public int getActDifficult(){
		return mActDifficult;
	}

	public void setActDifficult(int actDifficult){
		mActDifficult = actDifficult;
	}

	public int getActType(){
		return mActType;
	}

	public void setActType(int type){
		mActType = type;
	}

	public int getActCount(){
		return mActCount;
	}

	public void setActCount(int actCount){
		mActCount = actCount;
	}

	public byte[] getSelBuffer() {
		return selBuffer;
	}

	public void setSelBuffer(byte[] selBuffer) {
		this.selBuffer = selBuffer;
	}

	public boolean isBodySuccess() {
		return bodySuccess;
	}

	public void setBodySuccess(boolean bodySuccess) {
		this.bodySuccess = bodySuccess;
	}

	public String getStrPhotoBase64() {
		return strPhotoBase64;
	}

	public void setStrPhotoBase64(String strPhotoBase64) {
		this.strPhotoBase64 = strPhotoBase64;
	}

	public List<Bitmap> getBodyBmps() {
		return bodyBmps;
	}

	public void setBodyBmps(List<Bitmap> bodyBmps) {
		this.bodyBmps = bodyBmps;
	}

	public boolean isIsgetData() {
		return isgetData;
	}

	public void setIsgetData(boolean isgetData) {
		this.isgetData = isgetData;
	}



}
