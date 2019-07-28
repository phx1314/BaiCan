package com.facefr.server.in;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera.Size;
import android.util.Log;

import com.facefr.so.InvokeSoLib;
import com.facefr.so.struct.BitmapInfo;
import com.facefr.so.struct.OFRect;
import com.pertraitbodycheck.activity.R;
import com.x.util.BaseThread;
import com.x.util.BmpUtil;
import com.x.util.CameraUtil.Degree;
import com.x.util.YuvUtil;
import com.x.util.gCount;
import com.x.view.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动检测BMP预览照片
 *
 *
 */
public class PictureAutoCheckThread extends BaseThread {
	public static final String TAG = "LWH3";

	// 持续多少帧返回通用的结果才认为结果有效
	public static final int CX_INDEX_COUNT =5;
	public static int  contiCount=2;

	public PictureAutoCheckThread(Context mContext,PictureSelfCheckInstance mCallBack) {
		super();
		this.mContext = mContext;
		this.mCallBack = mCallBack;
	}


	//线程  不断传照片到so
	@Override
	public void run() {
		super.run();
		mBitmapList = new ArrayList<BitmapInfo>();
		Sleep(500);//延迟0.5秒 环境检测

		long detecStartTime=System.currentTimeMillis();
		while (!IsExit()) {
			if (mPreviewSize != null && mNvData != null && mBmpCache == null) {
				long startTime=System.currentTimeMillis();

				mGrayData = YuvUtil.convertYUV420toGray(mNvData,
						mPreviewSize.width, mPreviewSize.height,
						mCameraView.getJpegRotation(),
						mCameraView.getCurOpenCameraId());//获取灰度图

				int iReturn = 0;

				CollectInfoInstance.getInstance().lock.lock();// 得到锁
				try {
					if (mCameraView.getiDisplayOrientation() == Degree.ROTATION_90
							||mCameraView.getiDisplayOrientation() == Degree.ROTATION_270) {
						iReturn = InvokeSoLib.getInstance()
								.checkSelfPhotoGrayBuffer(YuvUtil.getZoom50ByGray(mGrayData,480,640),
										mPreviewSize.height/2, mPreviewSize.width/2);
					} else {
						iReturn = InvokeSoLib.getInstance()
								.checkSelfPhotoGrayBuffer(YuvUtil.getZoom50ByGray(mGrayData,640,480),
										mPreviewSize.width/2, mPreviewSize.height/2);
					}
				} finally {
					CollectInfoInstance.getInstance().lock.unlock();// 解锁
				}


				Log.i(TAG,iReturn+"==iReturn");
				OFRect ofRect=new OFRect();
				if(InvokeSoLib.getInstance().getFacePosSyc(mGrayData,
						mPreviewSize.height, mPreviewSize.width,ofRect)==0){
					mCallBack.setOfRect(ofRect);
				}else{
					mCallBack.setOfRect(null);
				}
				long detectTime=System.currentTimeMillis()-detecStartTime;
				if (detectTime>=2000) {
					contiCount=2;
				}

				//把so吐出来的结果 回调
				if (LogStateChanged(iReturn)) {
					//取最后一帧(非最优)返回
					convertYuv2Bmp(CameraView.IMG_SCALE_HEIGHT);
					ThreadEnd();
				} else {
					setBmpNull();
				}
				long dealTime=System.currentTimeMillis()-startTime;
				if (dealTime<100) {
					Sleep(100-dealTime);
				}
				mNvData = null;
			}
		}
	}


	public List<BitmapInfo> getmBitmapList() {
		return mBitmapList;
	}

	public Bitmap getBmpCache() {
		return mBmpCache;
	}

	/**
	 * 清除回收BMP
	 */
	public void setBmpNull() {
		// 0409
		if (mBmpCache != null) {
			if (!mBmpCache.isRecycled())
				mBmpCache.recycle();
			mBmpCache = null;
		}
	}

	/*
	 * 输入摄像头当前帧
	 */
	public boolean PutImgFrame(byte[] nvData,CameraView mCameraView) {
		// 如果当前正忙，则抛弃此帧
		if (mBmpCache != null || mNvData != null) {
			gCount.IDEDropFrameCount();
			return false;
		}
		if(mCameraView==null){
			return false;
		}
		this.mNvData = nvData;
		this.mPreviewSize = mCameraView.getPreviewSize();
		this.mCameraView=mCameraView;
		return true;
	}


	public boolean LogStateChanged(int iReturn) {
		// 先进行逻辑处理
		if (iReturn >= 0) {
			if (iReturn == 0) {
				index_0++;

				int tmpScore = InvokeSoLib.getInstance().GetSelfPhotoScore();
				if (tmpScore > selfPhotoScore) {
					selfPhotoScore = tmpScore;
					mNvData2 = mNvData;
				}

				if (index_0>=contiCount) {
					if (mCallBack != null)
						mCallBack.SuccessMsgChanged(mContext.getResources().getString(R.string.hint_envir_success));
					index_0 = 0;
					selfPhotoScore = -100001;
					return true;
				}
				//单帧成功则清除上次的错误提示
				if (mCallBack != null)
					mCallBack.FailMsgChanged("");
			}else{
				index_0=0;
				if (iReturn == 1) {// 人脸过小
					index_1++;
					if (index_1 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_size));
						index_1 = 0;
					}

				} else if (iReturn == 2) {// 姿态不正确
					index_2++;
					if (index_2 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_pose));
						index_2 = 0;
					}

				} else if (iReturn == 3 || iReturn == 6) {// 人脸位置不正确 or 眼睛不水平
					index_3++;
					if (index_3 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_position));
						index_3 = 0;
					}

				} else if (iReturn == 4) {// 检测到多张人脸
					index_4++;
					if (index_4 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_more));
						index_4 = 0;
					}

				} else if (iReturn == 5) {// 没有人脸
					index_5++;
					if (index_5 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_none));
						index_5 = 0;
					}

				} else if (iReturn == 7) {// 光线昏暗
					index_7++;
					if (index_7 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_dusky));
						index_7 = 0;
					}

				} else if (iReturn == 8) {// 阴阳脸
					index_8++;
					if (index_8 == CX_INDEX_COUNT) {
						if (mCallBack != null)
							mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_face_sidelight));
						index_8 = 0;
					}

				}

			}
		} else {
			if (mCallBack != null)
				mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_error));
		}
		// 通知UI更新界面
		return false;
	}

	public void convertYuv2Bmp(int max) {
		try {
			mOutStream = new ByteArrayOutputStream();
			mYuvImg = new YuvImage(mNvData2, ImageFormat.NV21,
					mPreviewSize.width, mPreviewSize.height, null);
			mNvData = null;
			mNvData2 = null;
			mYuvImg.compressToJpeg(new Rect(0, 0, mPreviewSize.width,
					mPreviewSize.height), 100, mOutStream);
			// ////崩溃在此
			Bitmap bmp = BitmapFactory.decodeByteArray(
					mOutStream.toByteArray(), 0, mOutStream.size());
			if (bmp == null)
				return;
			if (mMatrix == null) {
				mMatrix = new Matrix();
				// 先压缩后旋转
				// 自拍照不能太大,需适当压缩图片
				int iSrcMax = bmp.getHeight() >= bmp.getWidth() ? bmp
						.getHeight() : bmp.getWidth();
				if (iSrcMax > max) {
					float f = max / ((float) iSrcMax);
					mMatrix.postScale(f, f);
				}
				mCameraView.dealMatrix(bmp, mMatrix);
			}
			// 里面已回收bmp
			mBmpCache = BmpUtil.getBitmap(bmp, mMatrix, true);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (mOutStream != null)
					mOutStream.close();
				mYuvImg = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public int getDealFrameCount() {
		return mDealFrameCount;
	}

	// ///////////////////////////////////////////////////
	private Bitmap mBmpCache = null;
	private Context mContext;

	private PictureSelfCheckInstance mCallBack = null;

	private Size mPreviewSize = null;
	private CameraView mCameraView;
	private YuvImage mYuvImg = null;
	private ByteArrayOutputStream mOutStream;
	private byte[] mNvData;
	private byte[] mNvData2;
	private Matrix mMatrix;

	private List<BitmapInfo> mBitmapList;
	private int mDealFrameCount=0;
	private byte[] mGrayData;

	// 记录最高分
	private int selfPhotoScore = -100001;

	// 记录返回值的次数
	private int index_0 = 0;
	private int index_1 = 0;
	private int index_2 = 0;
	private int index_3 = 0;
	private int index_4 = 0;
	private int index_5 = 0;
	private int index_7 = 0;
	private int index_8 = 0;

}
