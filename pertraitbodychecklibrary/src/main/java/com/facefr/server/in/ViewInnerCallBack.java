package com.facefr.server.in;

import android.graphics.Bitmap;

import com.facefr.server.out.ServeOutCallBack;

public interface ViewInnerCallBack {

	public void setOutCallBack(ServeOutCallBack outCallBack);
	
	public Bitmap getCapture();
	
	public void onMyWindowFocusChanged(boolean hasWindowFocus);
	
	public void onMyResume();
	
	public void onMyPause();
	
	public void onMyDestory();
	
}
