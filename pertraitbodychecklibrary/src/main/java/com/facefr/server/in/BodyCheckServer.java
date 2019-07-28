package com.facefr.server.in;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.facefr.controller.Controller;
import com.facefr.server.out.BodyServerOutCallBack;
import com.facefr.so.InvokeSoLib;
import com.facefr.view.BodyView;
import com.pertraitbodycheck.activity.R;
import com.x.util.DisplayUtil;
import com.x.view.CameraView;
import com.x.view.CustomHeaderLayOut;
import com.x.view.FaceFrameView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("HandlerLeak")
public class BodyCheckServer implements BodyViewInnerCallBack {

	private Context mContext;
	private BodyView mBodyView;

	private BodyServerOutCallBack mOutCallBack;

	private int mScreenHeight;// 屏幕高度
	private int mScreenWidth;//屏幕宽度

	private CameraView mCameraView;
	private FaceFrameView mFaceFrame;// 人脸框
//	private ImageView mProgressBar;// 进度条
//	private ImageView mActionTip;// 动作提示图
//	private TextView mProgress;// 进度数字
	private TextView mTxtOpAction;// 动作提示
	private TextView mTxthintMsg;// 失败信息提示

	private PreviewCallback mPreviewCallback;// 预览回调

    private CustomHeaderLayOut mHeaderLayOut;//返回按钮
//	private Bitmap mBmpProgressBg;// 进度条
//	private Bitmap mBmpFace;// 人脸框图
//	private AnimationDrawable mTipAnim;// 动作提示动画
//	private float mProHeight;// 进度条高度

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

	private Integer mSuccessCount;//记录成功总次数


	private boolean bBeforePauseTickPlay = false;// 在pause前倒计时的播放状态

	private int mPicNum;
	private int mActType;
	private int mActCount;
	private int mActDiff;

	private boolean mIsOpenTick;

	private Bitmap mFailBmp;//不通过的灯
	private Bitmap mSuccessBmp;//通过的灯




	// 是否是第一次提示动作
	private boolean isFrist = true;
	private int mCurindexT;

	private List<Bitmap> mBmpList = new ArrayList<Bitmap>();

	private String mPagData;
	private BodyChekDataServer mDataServer;

	public BodyCheckServer(Context context, BodyView bodyView) {
		this.mContext = context;
		this.mBodyView = bodyView;

		mScreenHeight = DisplayUtil.getScreenMetrics(mContext.getApplicationContext()).y;
		mScreenWidth = DisplayUtil.getScreenMetrics(mContext.getApplicationContext()).x;
		onMyCreate();
	}

	@Override
	public void setOutCallBack(BodyServerOutCallBack outCallBack) {
		this.mOutCallBack = outCallBack;
	}

	@Override
	public void onMyWindowFocusChanged(boolean hasWindowFocus) {
//		if (hasWindowFocus) {
//			// 进度条的高度
////			mProHeight = mProgressBar.getHeight();
//		}
	}

	private void onMyCreate() {
		if (mBodyView == null) {
			return;
		}
		mSuccessCount = 0;
        this.mHeaderLayOut=mBodyView.getmHeaderLayOut();
		this.mBodyView.setInnerCallBack(this);
		this.mCameraView = mBodyView.getmCameraView();
		this.mFaceFrame = mBodyView.getmFaceFrame();
//		this.mActionTip = mBodyView.getmActionTip();
//		this.mProgress = mBodyView.getmProgress();
		this.mTxtOpAction = mBodyView.getmTxtOpAction();
		this.mTxthintMsg = mBodyView.getmTxthintMsg();

		this.mBaseMap=mBodyView.getBaseMap();
		this.mActionOne=mBodyView.getActionOne();
		this.mActionTwo=mBodyView.getActionTwo();
		this.mActionThree=mBodyView.getActionThree();

		this.mL1Icon=mBodyView.getL1Icon();
		this.mL2Icon=mBodyView.getL2Icon();
		this.mL3Icon=mBodyView.getL3Icon();
		this.mTitleOne=mBodyView.getTitleOne();
		this.mTitleTwo=mBodyView.getTitleTwo();
		this.mTitleThree=mBodyView.getTitleThree();

		this.mPercentageBarOne=mBodyView.getPercentageBarOne();
		this.mPercentageBarTwo=mBodyView.getPercentageBarTwo();
		this.mPercentageBarThree=mBodyView.getPercentageBarThree();
		this.mLampOne=mBodyView.getLampOne();
		this.mLampTwo=mBodyView.getLampTwo();
		this.mLampThree=mBodyView.getLampThree();



		if (PlaySoundInstance.getInstance() == null)
			PlaySoundInstance.getInstance(mContext);// 单实例初始化


		setParams();

	}


	@Override
	public void onMyResume() {
		if (mBodyView == null) {
			return;
		}
		initCameraView();

		// so初始化
		if (InvokeSoLib.getInstance() != null) {
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
			catch(IOException e)
			{
			}
			finally
			{

			}

		}
		// 初始化
		if (PlaySoundInstance.getInstance() != null
				&& !PlaySoundInstance.getInstance().IsInit()){
			PlaySoundInstance.getInstance().setmIsOpenTick(mIsOpenTick);
			PlaySoundInstance.getInstance().Init(null,null);

		}
		if (BodyCheckFlowInstance.getInstance() == null)
			BodyCheckFlowInstance.getInstance(mContext);
		if (BodyCheckFlowInstance.getInstance() != null) {
			BodyCheckFlowInstance.getInstance().setHandler(mHandler);
			BodyCheckFlowInstance.getInstance().setmPicNum(mPicNum);
			BodyCheckFlowInstance.getInstance().setmActType(mActType);
			BodyCheckFlowInstance.getInstance().setmActCount(mActCount);
			BodyCheckFlowInstance.getInstance().setmActDiff(mActDiff);
			if (!BodyCheckFlowInstance.getInstance().IsInit()) {
				BodyCheckFlowInstance.getInstance().Init(null,null);

			}

		}
		if (bBeforePauseTickPlay) {// 回到桌面时，停止Tick，需要恢复播放
			PlaySoundInstance.getInstance().startMediaPlay();
		}

	}

	private boolean initCameraView() {
		if (mCameraView == null) {
			return false;
		}

		mCameraView.setFirstCameraId(CameraInfo.CAMERA_FACING_FRONT);//摄像头方向
		mCameraView.setFullScreen(false);
		if (mCameraView.getPreviewCallback() == null) {
			if (mPreviewCallback == null) {
				mPreviewCallback = new PictureBodyImpl(mContext,mCameraView);
			}
			mCameraView.setPreviewCallback(mPreviewCallback);
		}

		return true;
	}

	@Override
	public void onMyPause() {
		bBeforePauseTickPlay = PlaySoundInstance.getInstance().IsMediaPlaying();
		if (bBeforePauseTickPlay) {// 回到桌面时，停止Tick
			PlaySoundInstance.getInstance().pauseMediaPlay();
		}



		CollectInfoInstance.getInstance().lock.lock();// 得到锁
		try {
			//释放so
			if (InvokeSoLib.getInstance().IsInit()) {
				InvokeSoLib.getInstance().Release();
			}
		} finally {
			CollectInfoInstance.getInstance().lock.unlock();// 得到锁
		}


		if (BodyCheckFlowInstance.getInstance() != null) {
			BodyCheckFlowInstance.getInstance().Release();
		}

		if (mCameraView != null)
			mCameraView.releaseCamera();

		endCountDown();
	}

	@Override
	public void onMyDestory() {
		if (PlaySoundInstance.getInstance() != null) {
			PlaySoundInstance.getInstance().Release();
		}
	}

	public void setPictureNum(int picNum) {
		this.mPicNum = picNum;
	}

	public void setActType(int actType) {
		this.mActType = actType;
	}

	public void setActCount(int actCount) {
		this.mActCount = actCount;
	}

	public void setActDifficult(int actDiff) {
		this.mActDiff = actDiff;
	}

	public boolean ismIsOpenTick() {
		return mIsOpenTick;
	}

	public void setmIsOpenTick(boolean mIsOpenTick) {
		this.mIsOpenTick = mIsOpenTick;
	}
	@Override
	public List<Bitmap> getBmpList() {
		// TODO Auto-generated method stub
		return mBmpList;
	}

	@Override
	public String getPagData() {
		return mPagData;
	}

	@SuppressWarnings("ResourceType")
	private void setParams() {
		// 摄像区域高度为屏幕的=4:3
		int mHeight = mScreenHeight / 10 *5;
		int mWidth = (int) (mHeight /4 *3);

		//返回按钮
        ViewGroup.LayoutParams lp0 = mHeaderLayOut.getLayoutParams();
        lp0.height = mWidth/8;
        lp0.width = mWidth/8;
        mHeaderLayOut.setLayoutParams(lp0);

		// 不应该控制摄像头区域的高宽，而应该创建遮罩来决定遮挡摄像头哪些区域????
		LayoutParams lp1 = (LayoutParams) mCameraView.getLayoutParams();
		lp1.height = mHeight;
		lp1.width = mWidth;
		mCameraView.setLayoutParams(lp1);
		if (mCameraView.getY() != -(mScreenHeight/6)){
			mCameraView.setY(-(mScreenHeight/6));
		}

		// 将人脸框的宽度改变成和摄像头宽度一样
		ViewGroup.LayoutParams lp2 = mFaceFrame.getLayoutParams();
		lp2.width = mWidth+(mScreenHeight/50);
        lp2.height = mHeight+(mScreenHeight/50);
		mFaceFrame.setLayoutParams(lp2);
		if (mFaceFrame.getY() != -(mScreenHeight/6)) {
			mFaceFrame.setY(-(mScreenHeight/6));
		}

		//提示语位置
		mTxtOpAction.setY(mScreenHeight/2);
		mTxtOpAction.setX(0);
		mTxthintMsg.setY(mScreenHeight/2);
		mTxthintMsg.setX(0);

		//三个动作栏
		ViewGroup.LayoutParams lp4 = mBaseMap.getLayoutParams();
		lp4.width = mScreenWidth - 100;
		lp4.height = (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*5;
		mBaseMap.setLayoutParams(lp4);
		mBaseMap.setY(mTxthintMsg.getY() + mScreenHeight/20);
		mBaseMap.setX(50);

		ViewGroup.LayoutParams lp5 = mActionOne.getLayoutParams();
		lp5.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mActionOne.setLayoutParams(lp5);
		mActionOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));

		ViewGroup.LayoutParams lp6 = mActionTwo.getLayoutParams();
		lp6.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mActionTwo.setLayoutParams(lp6);
		mActionTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));

		ViewGroup.LayoutParams lp7 = mActionThree.getLayoutParams();
		lp7.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mActionThree.setLayoutParams(lp7);
		mActionThree.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*3));

		ViewGroup.LayoutParams lp7_1 = mL1Icon.getLayoutParams();
		lp7_1.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp7_1.width = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mL1Icon.setLayoutParams(lp7_1);
		mL1Icon.setX(0);
		mL1Icon.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));

		ViewGroup.LayoutParams lp7_2 = mTitleOne.getLayoutParams();
		lp7_2.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp7_2.width = (120);
		mTitleOne.setLayoutParams(lp7_2);
		mTitleOne.setX(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));
		mTitleOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));


		ViewGroup.LayoutParams lp7_3 = mL2Icon.getLayoutParams();
		lp7_3.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp7_3.width = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mL2Icon.setLayoutParams(lp7_3);
		mL2Icon.setX(0);
		mL2Icon.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));


		ViewGroup.LayoutParams lp7_4 = mTitleTwo.getLayoutParams();
		lp7_4.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp7_4.width = (120);
		mTitleTwo.setLayoutParams(lp7_4);
		mTitleTwo.setX(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7) );
		mTitleTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));

		ViewGroup.LayoutParams lp7_5 = mL3Icon.getLayoutParams();
		lp7_5.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp7_5.width = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mL3Icon.setLayoutParams(lp7_5);
		mL3Icon.setX(0);
		mL3Icon.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*3));

		ViewGroup.LayoutParams lp7_6 = mTitleThree.getLayoutParams();
		lp7_6.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp7_6.width = (120);
		mTitleThree.setLayoutParams(lp7_6);
		mTitleThree.setX(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7) );
		mTitleThree.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*3));





		ViewGroup.LayoutParams lp8 = mLampOne.getLayoutParams();
		lp8.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp8.width = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mLampOne.setLayoutParams(lp8);
		mLampOne.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));
		mLampOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));

		ViewGroup.LayoutParams lp9 = mLampTwo.getLayoutParams();
		lp9.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp9.width = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mLampTwo.setLayoutParams(lp9);
		mLampTwo.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));
		mLampTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));

		ViewGroup.LayoutParams lp10 = mLampThree.getLayoutParams();
		lp10.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp10.width = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		mLampThree.setLayoutParams(lp10);
		mLampThree.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));
		mLampThree.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*3));

		ViewGroup.LayoutParams lp11 = mPercentageBarOne.getLayoutParams();
		lp11.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp11.width = 150;
		mPercentageBarOne.setLayoutParams(lp11);
		mPercentageBarOne.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7)-150);
		mPercentageBarOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7));

		ViewGroup.LayoutParams lp12 = mPercentageBarTwo.getLayoutParams();
		lp12.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp12.width = 150;
		mPercentageBarTwo.setLayoutParams(lp12);
		mPercentageBarTwo.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7)-150);
		mPercentageBarTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));

		ViewGroup.LayoutParams lp13 = mPercentageBarThree.getLayoutParams();
		lp13.height = ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7);
		lp13.width = 150;
		mPercentageBarThree.setLayoutParams(lp13);
		mPercentageBarThree.setX(mScreenWidth - 100 - ((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7)-150);
		mPercentageBarThree.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*3));


		int count = CollectInfoInstance.getInstance().getActCount();
		if (count == EnumInstance.EActCount.two){

			mActionOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mL1Icon.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mTitleOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mLampOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mPercentageBarOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));

			mActionTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mL2Icon.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mTitleTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mLampTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));
			mPercentageBarTwo.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2
					+ (int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7/2));


			mActionThree.setVisibility(View.GONE);
			mL3Icon.setVisibility(View.GONE);
			mTitleThree.setVisibility(View.GONE);
			mLampThree.setVisibility(View.GONE);
			mPercentageBarThree.setVisibility(View.GONE);
		}else if (count == EnumInstance.EActCount.one){

			mActionOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));
			mL1Icon.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));
			mTitleOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));
			mLampOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));
			mPercentageBarOne.setY(((int)(mScreenHeight - (mTxthintMsg.getHeight() +  mTxthintMsg.getY()))/7*2));



			mActionTwo.setVisibility(View.GONE);
			mL2Icon.setVisibility(View.GONE);
			mTitleTwo.setVisibility(View.GONE);
			mLampTwo.setVisibility(View.GONE);
			mPercentageBarTwo.setVisibility(View.GONE);


			mActionThree.setVisibility(View.GONE);
			mL3Icon.setVisibility(View.GONE);
			mTitleThree.setVisibility(View.GONE);
			mLampThree.setVisibility(View.GONE);
			mPercentageBarThree.setVisibility(View.GONE);
		}


//		//设置图片可以拉伸
//		mFaceFrame.setAdjustViewBounds(true);
//		//设置图片拉伸方式为填充满整个控件
//		mFaceFrame.setScaleType(ImageView.ScaleType.FIT_XY);

		// 将人脸框的宽度改变成和摄像头宽度一样
//		mBmpFace = BitmapFactory.decodeStream(mContext.getResources()
//				.openRawResource(R.drawable.face_frame));
//		mBmpFace = MiniBitmap.resizeBitmap(mBmpFace,
//				(int) mWidth);
//		mFaceFrame.setImageBitmap(mBmpFace);


	}

	private final Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			OnMsg(msg);
		}
	};

	private void OnMsg(Message msg) {
		Integer oText = (Integer) msg.obj;

		switch (msg.what) {
			case BodyCheckFlowInstance.MSG_HINT:
				if (oText != null) {
					parseHintMsg(oText);
				}
				break;
			case BodyCheckFlowInstance.MSG_OPERATION_ACTION:
				if (oText != null) {
					parseTargetOperationAction(oText);
					if (oText > 0) {
						endCountDown();
						startCountDown(oText);
					}
				}
				break;
			case BodyCheckFlowInstance.MSG_OPERATION_COUNT:
				if (oText != null) {
				}
				break;
			case BodyCheckFlowInstance.MSG_OPERATION_RESULT:
				if (oText != null) {
					if (oText == 0 || oText == 1) {
						endCountDown();
					}
				}
				break;
			case BodyCheckFlowInstance.MSG_TOTAL_SUCCESS_COUNT:
				if (oText != null) {
					responseTotalSuccess(oText);
				}
				break;
			case BodyCheckFlowInstance.MSG_TOTAL_FAIL_COUNT:
				if (oText != null) {
				}
				break;
			case BodyCheckFlowInstance.MSG_CLOCK_TIME:
				if (oText != null)
					break;
			case BodyCheckFlowInstance.MSG_DONE_OPERATION_COUNT:
				if (oText != null)
					break;
			case BodyCheckFlowInstance.MSG_DONE_OPERATION_RANGE:
				if (oText != null) {
					psrRangeTxt(oText);
				}
				break;
			case BodyCheckFlowInstance.MSG_FINISH_BODY_CHECK:
				if (oText != null) {
					parseFinishBodyCheck(oText);
					endCountDown();
				}
				break;
			case BodyCheckFlowInstance.MSG_CHANGEWARNINGTEXT:
				break;
		}
	}

	// 对各个信息返回值进行细分解析
	@SuppressLint("NewApi")
	private boolean parseHintMsg(int iReturn) {
		if (iReturn < 0)
			return false;
		if (mTxthintMsg == null)
			return false;
		mTxthintMsg.setTextColor(Controller.getInstance().mStyle.resTxtErrorColor);



		if (iReturn > 1) {
//			mActionTip.setVisibility(View.INVISIBLE);
		}

		if (iReturn == 2 || iReturn == 5 || iReturn == 6 || iReturn == 7) {
			endCountDown();


		}

		if ( iReturn == 4 || iReturn == 6 || iReturn == 7) {
			mTxtOpAction.setText("");



			// 请靠近摄像头
			if (iReturn == 4) {
				mTxthintMsg.setAlpha(1f);
				mTxthintMsg.setText(R.string.hint_msg_close_camera);
				BodyCheckFlowInstance.getInstance().PlaySoundChanged(
						PlaySoundInstance.ANEAR, 0);
			}
			// 无人
			if (iReturn == 6) {
				mTxthintMsg.setText(R.string.hint_msg_nobody);
				ObjectAnimator alpha = ObjectAnimator.ofFloat(mTxthintMsg,
						"alpha", 1f, 0f);
				alpha.setDuration(400);
				alpha.start();
			}
			// 超时
			if (iReturn == 7) {
				mTxthintMsg.setAlpha(1f);
				mTxthintMsg.setText(R.string.hint_msg_timeout);
			}
		} else {
			mTxthintMsg.setText("");
		}
		// 请按提示指令操作
		if (iReturn == 1) {
			mTxtOpAction.setText(R.string.hint_msg_nodo_warn);
		}

		// 未按提示操作
		if (iReturn == 2) {



			BodyCheckFlowInstance.getInstance().PlaySoundChanged(
					PlaySoundInstance.FAIL, 0);
		}
		if (iReturn == 3) {
			BodyCheckFlowInstance.getInstance().PlaySoundChanged(
					PlaySoundInstance.FRONT, 0);
		}
		return true;
	}

	// 解析动作
	private boolean parseTargetOperationAction(int iReturn) {
		if (iReturn < 0)
			return false;
		if (mTxtOpAction == null)
			return false;
		mTxtOpAction.setTextColor(Controller.getInstance().mStyle.resTxtSuccessColor);
		if (iReturn == 0) {
			mTxtOpAction.setText("");// 黑底
		} else if (iReturn == 1) {// 以下都是白体
			mTxtOpAction.setText(R.string.operation_action_lefthead);
		} else if (iReturn == 2) {
			mTxtOpAction.setText(R.string.operation_action_righthead);
		} else if (iReturn == 3) {
			BodyCheckFlowInstance.getInstance().PlaySoundChanged(
					PlaySoundInstance.UP, 0);
			mTxtOpAction.setText(R.string.operation_action_uphead);
			// 请点头
//			mActionTip.setVisibility(View.VISIBLE);
//			runFrame(mActionTip, 1);
		} else if (iReturn == 4) {
			mTxtOpAction.setText(R.string.operation_action_downhead);
		} else if (iReturn == 5) {
			BodyCheckFlowInstance.getInstance().PlaySoundChanged(
					PlaySoundInstance.MOUTH, 0);
			mTxtOpAction.setText(R.string.operation_action_openmouth);
			// 张嘴
//			mActionTip.setVisibility(View.VISIBLE);
//			runFrame(mActionTip, 0);
		} else if (iReturn == 6) {
			BodyCheckFlowInstance.getInstance().PlaySoundChanged(
					PlaySoundInstance.EYE, 0);
			mTxtOpAction.setText(R.string.operation_action_closeeye);
			// 请眨眼
//			mActionTip.setVisibility(View.VISIBLE);
//			runFrame(mActionTip, 2);
		} else if (iReturn == 7) {
			BodyCheckFlowInstance.getInstance().PlaySoundChanged(
					PlaySoundInstance.SHAKE, 0);
			mTxtOpAction.setText(R.string.operation_action_shakehead);
		}
		return true;
	}

	//动作完成幅度@@
	private void psrRangeTxt(Integer oText) {
		if (mFailBmp == null){
			InputStream is = mContext.getResources().openRawResource(Controller.getInstance().mStyle.resActionErrorImg);
			mFailBmp = BitmapFactory.decodeStream(is);
		}
		if (mSuccessBmp == null){
			InputStream is2 = mContext.getResources().openRawResource(Controller.getInstance().mStyle.resActionSuccessImg);
			mSuccessBmp = BitmapFactory.decodeStream(is2);
		}


		if(mSuccessCount.intValue() == 0){

			mPercentageBarTwo.setText("0%");
			mPercentageBarThree.setText("0%");
			mLampTwo.setImageBitmap(mFailBmp);
			mLampThree.setImageBitmap(mFailBmp);
			if (oText.intValue() >= 100)
			{
				mLampOne.setImageBitmap(mSuccessBmp);
				mPercentageBarOne.setText("100%");
			}else {
				mLampOne.setImageBitmap(mFailBmp);
				mPercentageBarOne.setText(oText.toString()+"%");
			}
		}else if (mSuccessCount.intValue() == 1){
			if ("100%".compareTo(mPercentageBarOne.getText().toString())==0){
				mPercentageBarThree.setText("0%");
				mLampThree.setImageBitmap(mFailBmp);
				if (oText.intValue() >= 100){
					mLampTwo.setImageBitmap(mSuccessBmp);
					mPercentageBarTwo.setText("100%");
				}else {
					mLampTwo.setImageBitmap(mFailBmp);
					mPercentageBarTwo.setText(oText.toString()+"%");
				}
			}else {

				mPercentageBarTwo.setText("0%");
				mPercentageBarThree.setText("0%");
				mLampTwo.setImageBitmap(mFailBmp);
				mLampThree.setImageBitmap(mFailBmp);
				if (oText.intValue() >= 100)
				{
					mLampOne.setImageBitmap(mSuccessBmp);
					mPercentageBarOne.setText("100%");
				}else {
					mLampOne.setImageBitmap(mFailBmp);
					mPercentageBarOne.setText(oText.toString()+"%");
				}
			}
		}else if (mSuccessCount.intValue() == 2){
			if ("100%".compareTo(mPercentageBarTwo.getText().toString())==0){

				if (oText.intValue() >= 100){
					mLampThree.setImageBitmap(mSuccessBmp);
					mPercentageBarThree.setText("100%");
				}else {
					mLampThree.setImageBitmap(mFailBmp);
					mPercentageBarThree.setText(oText.toString()+"%");
				}
			}else {
				mPercentageBarThree.setText("0%");
				mLampThree.setImageBitmap(mFailBmp);
				if (oText.intValue() >= 100){
					mPercentageBarTwo.setText("100%");
					mLampTwo.setImageBitmap(mSuccessBmp);
				}else {
					mLampTwo.setImageBitmap(mFailBmp);
					mPercentageBarTwo.setText(oText.toString()+"%");
				}
			}
		}
	}
	//接收成功总次数
	void responseTotalSuccess(Integer oText){
		mSuccessCount = oText;//记录
	}



	// 活体完成
	private boolean parseFinishBodyCheck(int iReturn) {

		if (iReturn < 0)
			return false;
		// 关掉活体检测进程,释放资源

		// 调用NDK的UnInit释放So
		if (BodyCheckFlowInstance.getInstance() != null)
			BodyCheckFlowInstance.getInstance().Release();
		// 结束动画要置空
//		mTipAnim = null;
		boolean isSuccess = false;
		if (iReturn == 0) {
			isSuccess = true;
		}

		if (iReturn == 0){
			if (mSuccessCount.intValue() >= 2){
				mPercentageBarThree.setText("100%");
				mLampThree.setImageBitmap(mSuccessBmp);
			}else if (mSuccessCount.intValue() == 1){
				mPercentageBarTwo.setText("100%");
				mLampTwo.setImageBitmap(mSuccessBmp);
			}else {
				mPercentageBarOne.setText("100%");
				mLampOne.setImageBitmap(mSuccessBmp);
			}
		}


		// 获取照片
		mDataServer = new BodyChekDataServer();
		mBmpList = mDataServer.getBestBmps();
		byte[] selfBuf=CollectInfoInstance.getInstance().getSelBuffer();
		mPagData=mDataServer.getStrDataPhoto(selfBuf);
		// 回调活体检测完成
		mOutCallBack.detectionOk(isSuccess);
		return true;
	}



	// 动作计时器（超过5秒未按语音动作 重复语音提示该动作）
	/**
	 * @param oText
	 *            开始计时
	 */
	private void startCountDown(int oText) {
		Message message = new Message();
		message.what = BodyCheckFlowInstance.MSG_OPERATION_ACTION;
		message.obj = oText;
		mHandler.sendMessageDelayed(message, 5000);
	}

	/**
	 * 关闭计时
	 */
	private void endCountDown() {
		mHandler.removeMessages(BodyCheckFlowInstance.MSG_OPERATION_ACTION);
	}

}
