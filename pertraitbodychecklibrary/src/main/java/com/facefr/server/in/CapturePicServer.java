package com.facefr.server.in;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera.CameraInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facefr.controller.Controller;
import com.facefr.server.out.ServeOutCallBack;
import com.facefr.so.InvokeSoLib;
import com.facefr.so.struct.OFRect;
import com.facefr.view.PictureView;
import com.pertraitbodycheck.activity.R;
import com.x.util.DisplayUtil;
import com.x.util.GrayBmpUtil;
import com.x.view.CameraView;
import com.x.view.CustomHeaderLayOut;
import com.x.view.FaceFrameView;
import com.x.view.ScanLineAnimation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class CapturePicServer implements ViewInnerCallBack,AfterPictureCallBack{

	public static final String TAG = "CapturePicServer";

	private PictureView mPictureView;
	private Context mContext;//环境检测的控制器对象
	private Bitmap mCurFrame;
	private ServeOutCallBack mOutCallBack;

	private CustomHeaderLayOut mHeaderLayOut;//返回按钮
	private CameraView mCameraView;//摄像头画面的View
//	private ImageView mScanLine;//扫描线
	private FaceFrameView mFaceFrame;//静态人脸对准框
//	private FocusImg mFocusImg;//实时人脸跟踪框
	private TextView mTxtHint;//提示消息
//	private Button mBtnTakePic;//手动拍摄
//	private Button mBtnNext;//下一步

	private ScanLineAnimation mLineAnimation;//扫描线的动画
	private Bitmap mBmpFaceBg;//人脸框图
//	private Bitmap mBmpScanLine;//扫描线图

	private View mBaseMap;//动作完成度的大底图
	private ImageView mActionOne;//三个动作完成度小条形图
	private ImageView mActionTwo;
	private ImageView mActionThree;


	private ImageView mL1Icon,mL2Icon,mL3Icon;//小图标
	private TextView mTitleOne,mTitleTwo,mTitleThree;


	private TextView mPercentageBarOne;//三个百分比字
	private TextView mPercentageBarTwo;
	private TextView mPercentageBarThree;

	private ImageView mLampOne;//三个灯
	private ImageView mLampTwo;
	private ImageView mLampThree;

	private int mScreenHeight;
	private int mScreenWidth;
	private float mRate=0.0f;

	private PictureSelfImpl mPictureSelfImpl;//预览拍照回调



	public CapturePicServer(Context mContext,PictureView mPictureView) {
		super();
		this.mPictureView = mPictureView;
		this.mContext = mContext;

//		WindowManager wm = (WindowManager) mContext
//				.getSystemService(Context.WINDOW_SERVICE);
//		mScreenHeight = wm.getDefaultDisplay().getHeight();//屏幕的高
		//获取屏幕的高度
		mScreenHeight = DisplayUtil.getScreenMetrics(mContext.getApplicationContext()).y;
		mScreenWidth = DisplayUtil.getScreenMetrics(mContext.getApplicationContext()).x;
		onMyCreate();
	}

	@Override
	public void setOutCallBack(ServeOutCallBack outCallBack) {
		this.mOutCallBack = outCallBack;
	}

	private void onMyCreate(){
		if(mPictureView==null){
			return;
		}
		Log.i(TAG," CapturePicServer onMyCreate ");

		this.mPictureView.setInnerCallBack(this);

		//从界面中获取控件


		this.mHeaderLayOut=mPictureView.getHeaderLayOut();
		this.mCameraView=mPictureView.getCameraView();
//		this.mScanLine=mPictureView.getScanLine();
		this.mFaceFrame=mPictureView.getFaceFrame();
//		this.mFocusImg=mPictureView.getFocusImg();
//		this.mBtnNext=mPictureView.getBtnNext();
//		this.mBtnTakePic=mPictureView.getBtnTakePic();
		this.mTxtHint=mPictureView.getTxtHint();

		this.mBaseMap=mPictureView.getBaseMap();
		this.mActionOne=mPictureView.getActionOne();
		this.mActionTwo=mPictureView.getActionTwo();
		this.mActionThree=mPictureView.getActionThree();

		this.mL1Icon=mPictureView.getL1Icon();
		this.mL2Icon=mPictureView.getL2Icon();
		this.mL3Icon=mPictureView.getL3Icon();
		this.mTitleOne=mPictureView.getTitleOne();
		this.mTitleTwo=mPictureView.getTitleTwo();
		this.mTitleThree=mPictureView.getTitleThree();

		this.mPercentageBarOne=mPictureView.getPercentageBarOne();
		this.mPercentageBarTwo=mPictureView.getPercentageBarTwo();
		this.mPercentageBarThree=mPictureView.getPercentageBarThree();
		this.mLampOne=mPictureView.getLampOne();
		this.mLampTwo=mPictureView.getLampTwo();
		this.mLampThree=mPictureView.getLampThree();
//		this.mBtnTakePic.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!mBtnTakePic.isClickable())
//					return;
//				mBtnTakePic.setClickable(false);
//				//当前正在预览
//				if(mCameraView.isPreviewing()){
//					mCameraView.takePicture(mPictureSelfImpl);
//				}else{
////					_setLineViewState(true);
//					//从拍照状态重新回到预览
//					_IsNextOperationAbled(false);
//				}
//			}
//		});
		setParams();//设置人脸框，扫描线，等View的大小
	}

	//运行server的函数
	@Override
	public void onMyResume(){
		if(mPictureView==null){
			return;
		}
		Log.i(PictureView.TAG," CapturePicServer onMyResume ");
		initCameraView();//初始化摄像头显示的画面View（运行摄像头） 和人脸框 扫描线 等控件

		if (InvokeSoLib.getInstance() != null){
			try{

				InputStream is = mContext.getResources().openRawResource(R.raw.jy_dt50model);
				byte DTBuffer[]=new byte[is.available()];
				is.read(DTBuffer);

				InputStream is2 = mContext.getResources().openRawResource(R.raw.jy_point50model);
				byte PointBuffer[]=new byte[is2.available()];
				is2.read(PointBuffer);



				InvokeSoLib.getInstance().Init(DTBuffer,PointBuffer);//初始化SO 改，传入dat
				is.close();
				is2.close();
			}
			catch(Exception e)
			{
			}

		}
		if (PictureSelfCheckInstance.getInstance() == null) {
			PictureSelfCheckInstance.getInstance(mContext);//传 环境检测的控制器对象

		}
		if (PictureSelfCheckInstance.getInstance() != null) {
			if (!PictureSelfCheckInstance.getInstance().IsInit()) {
				//1设置跳转时的声音 ding 2开启线程 线程中不断往so丢图并返回识别结果
				PictureSelfCheckInstance.getInstance().Init(null,null);
				//丢一个内部类进去，可以随时调用里面的方法，用于控制界面 如跳转 扫描线停止扫描 等
				PictureSelfCheckInstance.getInstance().setHandler(mHandler);
			}
		}
	}


	@Override
	public void onMyPause(){
		Log.i(TAG," CapturePicServer onMyPause ");

		CollectInfoInstance.getInstance().lock.lock();// 得到锁
		try {
			//释放so
			if (InvokeSoLib.getInstance().IsInit()) {
				InvokeSoLib.getInstance().Release();
			}
		} finally {
			CollectInfoInstance.getInstance().lock.unlock();// 得到锁
		}


		if (PictureSelfCheckInstance.getInstance() != null) {
			PictureSelfCheckInstance.getInstance().Release();
		}

		if (mCameraView != null)
			mCameraView.releaseCamera();
	}

	@Override
	public void onMyDestory() {
		Log.i(TAG," CapturePicServer onMyDestory ");

	}

	@Override
	public void onMyWindowFocusChanged(boolean hasWindowFocus) {
		if(hasWindowFocus){
			mTxtHint.setText("");
//			_setLineViewState(true);
//			_IsNextOperationAbled(false);
		}
		Log.i(TAG," CapturePicServer onWindowFocusChanged");
	}

	@Override
	public Bitmap getCapture() {
		return mCurFrame;
	}

	@Override
	public void afterTakePicture() {
		PictureSelfCheckInstance.getInstance().exitThread();
//		_IsNextOperationAbled(true);
//		_setLineViewState(false);

		if(mPictureSelfImpl!=null){
			Bitmap bmp=mPictureSelfImpl.getCurFrame();
			GrayBmpUtil mGray=new GrayBmpUtil();
			mGray.PutImg(bmp);
			int iReturn=InvokeSoLib.getInstance().checkSelfPhotoGrayBuffer(mGray.getGrayImg(), mGray.getGrayWidth(), mGray.getGrayHeight());
			_LogStateChanged(iReturn);
			mCurFrame=(bmp);

			// bitmap as callback to outer activity,can not recycled inside
//			mPictureSelfImpl.clearBmp();

			//手动拍摄-回调最优人脸结果
		}
	}

	//内部类 用于接收指令 并做出相应处理
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
				case PictureSelfCheckInstance.PICTURE_STOP_SCAN:
					if(mCameraView!=null)
						mCameraView.stopPreview();
					mTxtHint.setTextColor(Controller.getInstance().mStyle.resTxtSuccessColor);
					mTxtHint.setText(String.valueOf(msg.obj));
//					_setLineViewState(false);
					break;
				case PictureSelfCheckInstance.PICTURE_AUTO_SUCCESS://扫描合格 跳转
					_OnNextOperation();
					break;
				case PictureSelfCheckInstance.PICTURE_AUTO_FAIL:
					mTxtHint.setTextColor(Controller.getInstance().mStyle.resTxtErrorColor);
					mTxtHint.setText(String.valueOf(msg.obj));


					break;
				case PictureSelfCheckInstance.UPDATE_FACE_FRAME:
					OFRect ofRect=PictureSelfCheckInstance.getInstance().getOfRect();
//				System.out.println("ofrect=========="+ofRect);
					if (ofRect!=null) {
						if(mCameraView.getPreviewSize()!=null){
							mRate=(float)(mScreenHeight/2)/mCameraView.getPreviewSize().width;
						}
//						mFocusImg.update(ofRect.iLeft*mRate, ofRect.iTop*mRate, ofRect.iRight*mRate, ofRect.iBottom*mRate);
					}else{
//						mFocusImg.update(0,0,0,0);
					}
					break;
			}
		}

	};

	//初始化摄像头画面的View
	private boolean initCameraView() {
		if (mCameraView == null) {
			return false;
		}

		mCameraView.setFirstCameraId(CameraInfo.CAMERA_FACING_FRONT);//设置摄像头打开的方向 前置摄像头
		mCameraView.setFullScreen(false);//设置是否全屏显示 否

		if (mPictureSelfImpl == null) {//创建预览拍照回调
			mPictureSelfImpl = new PictureSelfImpl(this,mCameraView,mContext);
			mCameraView.setPreviewCallback(mPictureSelfImpl);
		}
		//hide PictureView must add this code
		mCameraView.startPreview();//开始播放

//		mScanLine.setVisibility(View.VISIBLE);//设置扫描线可见
//		mLineAnimation = new ScanLineAnimation(mScanLine, 1200);//让扫描线播放 并设置重复时间1.2秒
//		_setLineViewState(true);//扫描线动画开始播放
		return true;
	}


	//初始化控件的大小
	@SuppressWarnings("ResourceType")
	private void setParams() {
		// 摄像区域高度为屏幕的=4:3
		int mHeight = mScreenHeight / 10 *5;//获取摄像view的高
		int mWidth = (int) (mHeight /4 * 3);//获取摄像view的宽

		//返回按钮
		ViewGroup.LayoutParams lp0 = mHeaderLayOut.getLayoutParams();
		lp0.height = mWidth/8;
		lp0.width = mWidth/8;
		mHeaderLayOut.setLayoutParams(lp0);

		//设置摄像view的宽高
		LayoutParams lp1 = (LayoutParams) mCameraView.getLayoutParams();
		lp1.height = mHeight;
		lp1.width = mWidth;
		mCameraView.setLayoutParams(lp1);
		if (mCameraView.getY() != -(mScreenHeight/6)){
			mCameraView.setY(-(mScreenHeight/6));
		}

		//静态人脸对准框的位于中间
		ViewGroup.LayoutParams lp2 = mFaceFrame.getLayoutParams();
		lp2.width = mWidth+(mScreenHeight/50);
		lp2.height = mHeight+(mScreenHeight/50);
		mFaceFrame.setLayoutParams(lp2);//设置静态人脸对准框的宽
		if (mFaceFrame.getY() != -(mScreenHeight/6)) {
			mFaceFrame.setY(-(mScreenHeight/6));
		}


		//提示语位置
		mTxtHint.setY(mScreenHeight/2);
		mTxtHint.setX(0);


		//三个动作栏
		ViewGroup.LayoutParams lp4 = mBaseMap.getLayoutParams();
		lp4.width = mScreenWidth - 100;
		lp4.height = (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*5;
		mBaseMap.setLayoutParams(lp4);
		mBaseMap.setY(mTxtHint.getY() + mScreenHeight/20);
		mBaseMap.setX(50);

		ViewGroup.LayoutParams lp5 = mActionOne.getLayoutParams();
		lp5.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mActionOne.setLayoutParams(lp5);
		mActionOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));

		ViewGroup.LayoutParams lp6 = mActionTwo.getLayoutParams();
		lp6.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mActionTwo.setLayoutParams(lp6);
		mActionTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));

		ViewGroup.LayoutParams lp7 = mActionThree.getLayoutParams();
		lp7.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mActionThree.setLayoutParams(lp7);
		mActionThree.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*3));

		ViewGroup.LayoutParams lp7_1 = mL1Icon.getLayoutParams();
		lp7_1.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp7_1.width = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mL1Icon.setLayoutParams(lp7_1);
		mL1Icon.setX(0);
		mL1Icon.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));

		ViewGroup.LayoutParams lp7_2 = mTitleOne.getLayoutParams();
		lp7_2.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp7_2.width = (120);
		mTitleOne.setLayoutParams(lp7_2);
		mTitleOne.setX(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));
		mTitleOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));


		ViewGroup.LayoutParams lp7_3 = mL2Icon.getLayoutParams();
		lp7_3.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp7_3.width = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mL2Icon.setLayoutParams(lp7_3);
		mL2Icon.setX(0);
		mL2Icon.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));


		ViewGroup.LayoutParams lp7_4 = mTitleTwo.getLayoutParams();
		lp7_4.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp7_4.width = (120);
		mTitleTwo.setLayoutParams(lp7_4);
		mTitleTwo.setX(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7) );
		mTitleTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));

		ViewGroup.LayoutParams lp7_5 = mL3Icon.getLayoutParams();
		lp7_5.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp7_5.width = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mL3Icon.setLayoutParams(lp7_5);
		mL3Icon.setX(0);
		mL3Icon.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*3));

		ViewGroup.LayoutParams lp7_6 = mTitleThree.getLayoutParams();
		lp7_6.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp7_6.width = (120);
		mTitleThree.setLayoutParams(lp7_6);
		mTitleThree.setX(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7) );
		mTitleThree.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*3));





		ViewGroup.LayoutParams lp8 = mLampOne.getLayoutParams();
		lp8.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp8.width = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mLampOne.setLayoutParams(lp8);
		mLampOne.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));
		mLampOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));

		ViewGroup.LayoutParams lp9 = mLampTwo.getLayoutParams();
		lp9.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp9.width = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mLampTwo.setLayoutParams(lp9);
		mLampTwo.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));
		mLampTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));

		ViewGroup.LayoutParams lp10 = mLampThree.getLayoutParams();
		lp10.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp10.width = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		mLampThree.setLayoutParams(lp10);
		mLampThree.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));
		mLampThree.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*3));

		ViewGroup.LayoutParams lp11 = mPercentageBarOne.getLayoutParams();
		lp11.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp11.width = 150;
		mPercentageBarOne.setLayoutParams(lp11);
		mPercentageBarOne.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7)-150);
		mPercentageBarOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7));

		ViewGroup.LayoutParams lp12 = mPercentageBarTwo.getLayoutParams();
		lp12.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp12.width = 150;
		mPercentageBarTwo.setLayoutParams(lp12);
		mPercentageBarTwo.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7)-150);
		mPercentageBarTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));

		ViewGroup.LayoutParams lp13 = mPercentageBarThree.getLayoutParams();
		lp13.height = ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7);
		lp13.width = 150;
		mPercentageBarThree.setLayoutParams(lp13);
		mPercentageBarThree.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7)-150);
		mPercentageBarThree.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*3));

		float alpha = 0.7f;
		mBaseMap.setAlpha(alpha);


		int count = CollectInfoInstance.getInstance().getActCount();
		if (count == EnumInstance.EActCount.three){
			mActionOne.setAlpha(alpha);
			mL1Icon.setAlpha(alpha);
			mActionTwo.setAlpha(alpha);
			mL2Icon.setAlpha(alpha);
			mActionThree.setAlpha(alpha);
			mL3Icon.setAlpha(alpha);
			mLampOne.setAlpha(alpha);
			mLampTwo.setAlpha(alpha);
			mLampThree.setAlpha(alpha);
			mPercentageBarOne.setAlpha(alpha);
			mPercentageBarTwo.setAlpha(alpha);
			mPercentageBarThree.setAlpha(alpha);
		}else if (count == EnumInstance.EActCount.two){
			mActionOne.setAlpha(alpha);
			mL1Icon.setAlpha(alpha);
			mLampOne.setAlpha(alpha);
			mPercentageBarOne.setAlpha(alpha);

			mActionOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mL1Icon.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mTitleOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mLampOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mPercentageBarOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));

			mActionTwo.setAlpha(alpha);
			mL2Icon.setAlpha(alpha);
			mLampTwo.setAlpha(alpha);
			mPercentageBarTwo.setAlpha(alpha);
			mActionTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mL2Icon.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mTitleTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mLampTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));
			mPercentageBarTwo.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2
					+ (int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7/2));

			mActionThree.setAlpha(alpha);
			mL3Icon.setAlpha(alpha);
			mLampThree.setAlpha(alpha);
			mPercentageBarThree.setAlpha(alpha);

			mActionThree.setVisibility(View.GONE);
			mL3Icon.setVisibility(View.GONE);
			mTitleThree.setVisibility(View.GONE);
			mLampThree.setVisibility(View.GONE);
			mPercentageBarThree.setVisibility(View.GONE);
		}else if (count == EnumInstance.EActCount.one){
			mActionOne.setAlpha(alpha);
			mL1Icon.setAlpha(alpha);
			mLampOne.setAlpha(alpha);
			mPercentageBarOne.setAlpha(alpha);
			mActionOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));
			mL1Icon.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));
			mTitleOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));
			mLampOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));
			mPercentageBarOne.setY(((int)(mScreenHeight - (mTxtHint.getHeight() +  mTxtHint.getY()))/7*2));


			mActionTwo.setAlpha(alpha);
			mL2Icon.setAlpha(alpha);
			mLampTwo.setAlpha(alpha);
			mPercentageBarTwo.setAlpha(alpha);
			mActionTwo.setVisibility(View.GONE);
			mL2Icon.setVisibility(View.GONE);
			mTitleTwo.setVisibility(View.GONE);
			mLampTwo.setVisibility(View.GONE);
			mPercentageBarTwo.setVisibility(View.GONE);

			mActionThree.setAlpha(alpha);
			mL3Icon.setAlpha(alpha);
			mLampThree.setAlpha(alpha);
			mPercentageBarThree.setAlpha(alpha);
			mActionThree.setVisibility(View.GONE);
			mL3Icon.setVisibility(View.GONE);
			mTitleThree.setVisibility(View.GONE);
			mLampThree.setVisibility(View.GONE);
			mPercentageBarThree.setVisibility(View.GONE);
		}

//		mActionOne.setAlpha(alpha);
//		mActionTwo.setAlpha(alpha);
//		mActionThree.setAlpha(alpha);
//		mLampOne.setAlpha(alpha);
//		mLampTwo.setAlpha(alpha);
//		mLampThree.setAlpha(alpha);
//		mPercentageBarOne.setAlpha(alpha);
//		mPercentageBarTwo.setAlpha(alpha);
//		mPercentageBarThree.setAlpha(alpha);

		//获取对准框的图片
//		mBmpFaceBg= BitmapFactory.decodeStream(mContext.getResources().openRawResource(R.drawable.face_frame));
//		mBmpFaceBg = MiniBitmap.resizeBitmap(mBmpFaceBg,mWidth);
//		mFaceFrame.setImageBitmap(mBmpFaceBg);//把图片赋值给 静态人脸对准框
		//获取扫描线的图片
//		mBmpScanLine= BitmapFactory.decodeStream(mContext.getResources().openRawResource(R.drawable.scanline3));
//		mBmpScanLine=MiniBitmap.resizeBitmap(mBmpScanLine,mWidth);
//		mScanLine.setImageBitmap(mBmpScanLine);//把图片赋值给 扫描线View

		//设置实时人脸跟踪框的宽高
//		ViewGroup.LayoutParams lpF = mFocusImg.getLayoutParams();
//		lpF.width =mWidth;
//		lpF.height=mHeight;
//		mFocusImg.setLayoutParams(lpF);


	}

	//点击下一步按钮
	private boolean _OnNextOperation() {
		if (mCameraView != null) {
			Bitmap sCurFrame = PictureSelfCheckInstance.getInstance().getBmpCache();
			if (sCurFrame == null) {
				//在自动扫描即将成功时点击拍照，会出现BMP为空的情形
				if(mCurFrame==null){
					Toast.makeText(mContext.getApplicationContext(),R.string.picture_none, Toast.LENGTH_SHORT).show();
				}
				return false;
			} else {
				//不再做压缩处理
				mCurFrame=sCurFrame;

				//自动扫描-回调最优人脸结果
				if(mOutCallBack!=null){
					mOutCallBack.BestFaceNoticed(ServeOutCallBack.AUTO);
				}
			}
		}
		return true;
	}

	/**
	 * 允许进行下一步状态切换
	 *
	 * @param isAbled
	 *            true/false
	 */
//	@SuppressLint("NewApi")
//	private void _IsNextOperationAbled(boolean isAbled) {
//		if (mCameraView == null)
//			return;
//		//拍完照
//		if (isAbled) {
//			mBtnNext.setVisibility(View.VISIBLE);
//			mBtnTakePic.setText(R.string.btn_preview);
//		} else {
//			//重新预览
//			mCameraView.startPreview();
//			PictureSelfCheckInstance.getInstance().beginCheckThread();
//
//			mBtnNext.setVisibility(View.GONE);
//			mBtnTakePic.setText(R.string.btn_picture);
//		}
//		mBtnTakePic.setClickable(true);
//	}

	//控制扫描线动画播放和停止
//	private void _setLineViewState(boolean isStart) {
//		if (!isStart) {
//			if (mScanLine != null) {
//				mScanLine.setVisibility(View.INVISIBLE);
//				mLineAnimation.stopAnimation();
//			}
//		} else {
//			if (mScanLine != null) {
//				mLineAnimation.startAnimation();
//			}
//		}
//	}

	private void _LogStateChanged(int iReturn) {
		PictureSelfCheckInstance mCallBack = PictureSelfCheckInstance.getInstance();
		// 先进行逻辑处理
		if (iReturn >= 0) {
			if (iReturn == 0) {
				mCallBack.TakeSuccessMsgChanged(mContext.getString(
						R.string.hint_envir_success));
			} else {
				if (iReturn == 1) {// 人脸过小
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_size));
				} else if (iReturn == 2) {// 姿态不正确
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_pose));

				} else if (iReturn == 3 || iReturn == 6) {// 人脸位置不正确or 眼睛不水平?
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_position));

				} else if (iReturn == 4) {// 检测到多张人脸
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_more));

				} else if (iReturn == 5) {// 没有人脸
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_none));

				} else if (iReturn == 7) {// 光线昏暗
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_dusky));

				} else if (iReturn == 8) {// 阴阳脸
					mCallBack.FailMsgChanged(mContext.getString(
							R.string.picture_face_sidelight));
				}
			}
		} else {
			if (mCallBack != null) {
				mCallBack.FailMsgChanged(mContext.getResources().getString(R.string.picture_error));
			}
		}

	}

}
