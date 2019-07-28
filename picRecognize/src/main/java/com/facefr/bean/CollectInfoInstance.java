package com.facefr.bean;

import java.io.Serializable;

import android.content.Context;

public class CollectInfoInstance implements Serializable {
	/**
	 * APP采集数据信息
	 */
	private static final long serialVersionUID = 1L;

	private String idcard;// 身份证号
	private byte[] frontId;// 身份证正面照
	private byte[] ocrfrontId;//用于ocr识别的身份证照片

	//初始化属性值
	public void Init(){
		idcard=null;
	}


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
		Init();
	}


	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public byte[] getFrontId() {
		return frontId;
	}

	public void setFrontId(byte[] frontId) {
		this.frontId = frontId;
	}


	public byte[] getOcrfrontId() {
		return ocrfrontId;
	}

	public void setOcrfrontId(byte[] ocrfrontId) {
		this.ocrfrontId = ocrfrontId;
	}




}
