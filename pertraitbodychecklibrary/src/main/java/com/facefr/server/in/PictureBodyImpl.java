package com.facefr.server.in;

import com.x.view.CameraView;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;

public class PictureBodyImpl implements PreviewCallback{
	
private CameraView mCameraView;
	
	public PictureBodyImpl(Context mContext,CameraView mCameraView) {
		this.mCameraView=mCameraView;
	}
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		if (data != null) {
			if (BodyCheckFlowInstance.getInstance() != null) {
				BodyCheckFlowInstance.getInstance().PutImgFrame(data,mCameraView);
			}
		}
	}
}
