package com.facefr.server.in;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;

import com.pertraitbodycheck.activity.R;
import com.x.util.BmpUtil;
import com.x.util.CameraUtil.Degree;
import com.x.view.CameraView;

public class PictureSelfImpl implements PreviewCallback, PictureCallback {

	private CameraView mCameraView;
	private AfterPictureCallBack mAfterCallBack;
	private Bitmap mCurFrame;
	private Matrix mMatrix;
	private boolean bChange = false;// 是否需要每次获取Matrix
	private Context mContext;

	public PictureSelfImpl(AfterPictureCallBack mAfterCallBack,
						   CameraView mCameraView, Context mContext) {
		super();
		this.mCameraView = mCameraView;
		this.mAfterCallBack = mAfterCallBack;
		this.mContext = mContext;
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		if (mCameraView == null)
			return;
		if (data != null) {
			if (PictureSelfCheckInstance.getInstance() != null) {
				PictureSelfCheckInstance.getInstance().PutImgFrame( data,mCameraView);
			}
		}
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		if (mCameraView == null)
			return;
		if (data == null || camera == null)
			return;
		if (camera.getParameters().getPictureFormat() == ImageFormat.JPEG) {
			mCurFrame = BitmapFactory.decodeByteArray(data, 0, data.length);
			// 如果做了前后置摄像头切换，需要每次判断Matrix
			if (bChange || mMatrix == null) {
				mMatrix = new Matrix();
				// 竖屏摄像头需要旋转
				if (mCameraView.getiDisplayOrientation() == Degree.ROTATION_90) {
					if (mCurFrame.getWidth() <= mCurFrame.getHeight()) {
						if (mCameraView.getCurOpenCameraId() == CameraInfo.CAMERA_FACING_FRONT) {
							mMatrix.postRotate(Degree.ROTATION_180);// 16:9
						} else {
							mMatrix.postRotate(Degree.ROTATION_270);
						}
					} else {
						if (mCameraView.getCurOpenCameraId() == CameraInfo.CAMERA_FACING_FRONT)
							mMatrix.postRotate(Degree.ROTATION_270);// 4:3
						else
							mMatrix.postRotate(Degree.ROTATION_0);
					}
					int iMaxPhotoSize = 0;
					iMaxPhotoSize = mCurFrame.getHeight() >= mCurFrame
							.getWidth() ? mCurFrame.getHeight() : mCurFrame
							.getWidth();
					if (iMaxPhotoSize > CameraView.IMG_MAX_HEIGHT) {
						float f = CameraView.IMG_MAX_HEIGHT
								/ ((float) iMaxPhotoSize);
						mMatrix.postScale(f, f);
					}
				}
			}
			mCurFrame = BmpUtil.getBitmap(mCurFrame, mMatrix, false);
			if (mAfterCallBack != null) {
				mAfterCallBack.afterTakePicture();
			}
		}
	}

	//接收so吐出来的结果，进行处理
	public void LogStateChanged(int iReturn) {
		PictureSelfCheckInstance mCallBack = PictureSelfCheckInstance
				.getInstance();
		// 先进行逻辑处理
		if (iReturn >= 0) {
			if (iReturn == 0) {
				PictureSelfCheckInstance.getInstance().SuccessMsgChanged(
						mContext.getResources().getString(
								R.string.hint_envir_success));
			} else {
				if (iReturn == 1) {// 人脸过小
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_size));
				} else if (iReturn == 2) {// 姿态不正确
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_pose));

				} else if (iReturn == 3 || iReturn == 6) {// 人脸位置不正确 or 眼睛不水平
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_position));

				} else if (iReturn == 4) {// 检测到多张人脸
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_more));

				} else if (iReturn == 5) {// 没有人脸
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_none));

				} else if (iReturn == 7) {// 光线昏暗
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_dusky));

				} else if (iReturn == 8) {// 阴阳脸
					mCallBack.FailMsgChanged(mContext.getResources().getString(
							R.string.picture_face_sidelight));
				}
			}
		} else {
			if (mCallBack != null) {
				mCallBack.FailMsgChanged(mContext.getResources().getString(
						R.string.picture_error));
			}
		}

	}

	public Bitmap getCurFrame() {
		return mCurFrame;
	}

	/**
	 * 回收BMP--每次拍照完后必回收
	 */
	public void clearBmp() {
		if (mCurFrame != null && !mCurFrame.isRecycled())
			mCurFrame.recycle();
		mCurFrame = null;
	}

}
