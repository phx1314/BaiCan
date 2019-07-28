package com.facefr.server.in;

import java.util.List;

import android.graphics.Bitmap;

import com.facefr.server.out.BodyServerOutCallBack;


public interface BodyViewInnerCallBack {

	public void setOutCallBack(BodyServerOutCallBack outCallBack);
	
	public void onMyWindowFocusChanged(boolean hasWindowFocus);

	public void onMyResume();

	public void onMyPause();

	public void onMyDestory();
	
	public List<Bitmap> getBmpList();
	
	public String getPagData();
	
	

}
