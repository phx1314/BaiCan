package com.facefr.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facefr.controller.Controller;
import com.facefr.server.in.BodyViewInnerCallBack;
import com.pertraitbodycheck.activity.R;
import com.x.view.CameraView;
import com.x.view.CustomHeaderLayOut;
import com.x.view.FaceFrameView;

public class BodyView extends RelativeLayout {

	private BodyViewInnerCallBack mViewInnerCallBack;
	private View mView;

	private CameraView mCameraView;//摄像头的view
	private FaceFrameView mFaceFrame;// 人脸框
//	private ImageView mActionTip;// 动作提示图
//	private TextView mProgress;// 进度数字
	private TextView mTxtOpAction;// 动作提示
	private TextView mTxthintMsg;// 失败信息提示


	private CustomHeaderLayOut mHeaderLayOut;//顶部


    private View mBaseMap;//动作完成度的大底图
	private ImageView mBaseMapImgV;
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



	public BodyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_setCustomAttributes(attrs);//从视图获取控件对象

	}

	public BodyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("InflateParams")
	private void _setCustomAttributes(AttributeSet attrs) {
		LayoutInflater mInflater = LayoutInflater.from(this.getContext());
		mView = mInflater.inflate(R.layout.view_bodycheck, null);//获取活体的View
		mView.setBackgroundColor(Controller.getInstance().mStyle.resContentBgColor);
		addView(mView);//添加到当前的view上


		//从视图获取控件
		mCameraView = (CameraView) findViewById(R.id.SurfaceView1);//摄像头的view
		mFaceFrame = (FaceFrameView) findViewById(R.id.face_frame);
//		mProgressBar = (ImageView) findViewById(R.id.prograssbar);
//		mProgress = (TextView) findViewById(R.id.action_range);
//		mActionTip = (ImageView) findViewById(R.id.action_tip);
		mTxtOpAction = (TextView) findViewById(R.id.operation_msg);
		mTxthintMsg = (TextView) findViewById(R.id.hint_msg);
		mHeaderLayOut=(CustomHeaderLayOut) findViewById(R.id.actionbar);


        mBaseMap =(View) findViewById(R.id.base_map);
		mBaseMapImgV = (ImageView) findViewById(R.id.base_map_img);
		mBaseMapImgV.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionBackImg));
        mActionOne =(ImageView) findViewById(R.id.action_one);
		mActionOne.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionOneBackImg));
		mActionTwo  =(ImageView) findViewById(R.id.action_two);
		mActionTwo.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionTwoBackImg));
		mActionThree  =(ImageView) findViewById(R.id.action_three);
		mActionThree.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionThreeBackImg));

		mL1Icon = (ImageView) findViewById(R.id.l1_icon);
		mL1Icon.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resStepOneIcon));
		mL2Icon = (ImageView) findViewById(R.id.l2_icon);
		mL2Icon.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resStepTwoIcon));
		mL3Icon = (ImageView) findViewById(R.id.l3_icon);
		mL3Icon.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resStepThreeIcon));

		mTitleOne = (TextView) findViewById(R.id.title_one);
		mTitleOne.setTextColor(Controller.getInstance().mStyle.resTxtNormalColor);
		mTitleTwo = (TextView) findViewById(R.id.title_two);
		mTitleTwo.setTextColor(Controller.getInstance().mStyle.resTxtNormalColor);
		mTitleThree = (TextView) findViewById(R.id.title_three);
		mTitleThree.setTextColor(Controller.getInstance().mStyle.resTxtNormalColor);


        mPercentageBarOne =(TextView) findViewById(R.id.percentage_bar_one);
		mPercentageBarOne.setTextColor(Controller.getInstance().mStyle.resTxtPercentageColor);
        mPercentageBarTwo =(TextView) findViewById(R.id.percentage_bar_two);
		mPercentageBarTwo.setTextColor(Controller.getInstance().mStyle.resTxtPercentageColor);
        mPercentageBarThree =(TextView) findViewById(R.id.percentage_bar_three);
		mPercentageBarTwo.setTextColor(Controller.getInstance().mStyle.resTxtPercentageColor);

        mLampOne =(ImageView)findViewById(R.id.lamp_one);
		mLampOne.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionErrorImg));
		mLampTwo =(ImageView)findViewById(R.id.lamp_two);
		mLampTwo.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionErrorImg));
		mLampThree =(ImageView)findViewById(R.id.lamp_three);
		mLampThree.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionErrorImg));
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasWindowFocus);

		/*if (!hasWindowFocus) {
			if (mViewInnerCallBack != null) {
				mViewInnerCallBack.onMyPause();
			}
		}else {
			if (mViewInnerCallBack != null) {
				mViewInnerCallBack.onMyResume();
			}
		}*/

		if (mViewInnerCallBack != null) {
			mViewInnerCallBack.onMyWindowFocusChanged(hasWindowFocus);
		}
	}

	public ImageView getL1Icon(){
		return mL1Icon;
	}
	public ImageView getL2Icon(){
		return mL2Icon;
	}
	public ImageView getL3Icon(){
		return mL3Icon;
	}

	public TextView getTitleOne(){
		return mTitleOne;
	}
	public TextView getTitleTwo(){
		return mTitleTwo;
	}
	public TextView getTitleThree(){
		return mTitleThree;
	}



    public TextView getPercentageBarOne(){
        return  mPercentageBarOne;
    }
    public TextView getPercentageBarTwo(){
        return mPercentageBarTwo;
    }
    public TextView getPercentageBarThree(){
        return mPercentageBarThree;
    }
    public ImageView getLampOne(){
        return mLampOne;
    }
    public ImageView getLampTwo(){
        return mLampTwo;
    }
    public ImageView getLampThree(){
        return mLampThree;
    }



    //	public ImageView getScanLine() {
    //		return mScanLine;
    //	}
    public View getBaseMap(){
        return mBaseMap;
    }

    public ImageView getActionOne(){
        return mActionOne;
    }

    public ImageView getActionTwo(){
        return mActionTwo;
    }

    public ImageView getActionThree(){
        return mActionThree;
    }


	public void setInnerCallBack(BodyViewInnerCallBack viewInnerCallBack) {
		this.mViewInnerCallBack = viewInnerCallBack;
	}

	public CameraView getmCameraView() {
		return mCameraView;
	}

	public FaceFrameView getmFaceFrame() {
		return mFaceFrame;
	}

	public void setmFaceFrame(FaceFrameView mFaceFrame) {
		this.mFaceFrame = mFaceFrame;
	}


//	public void setmProgressBar(ImageView mProgressBar) {
//		this.mProgressBar = mProgressBar;
//	}

//	public TextView getmProgress() {
////		return mProgress;
//	}
//
//	public void setmProgress(TextView mProgress) {
////		this.mProgress = mProgress;
//	}

	public TextView getmTxtOpAction() {
		return mTxtOpAction;
	}

	public void setmTxtOpAction(TextView mTxtOpAction) {
		this.mTxtOpAction = mTxtOpAction;
	}

	public TextView getmTxthintMsg() {
		return mTxthintMsg;
	}

	public void setmTxthintMsg(TextView mTxthintMsg) {
		this.mTxthintMsg = mTxthintMsg;
	}

//	public ImageView getmActionTip() {
//		return mActionTip;
//	}

//	public void setmActionTip(ImageView mActionTip) {
//		this.mActionTip = mActionTip;
//	}

	public CustomHeaderLayOut getmHeaderLayOut() {
		return mHeaderLayOut;
	}

	public void setmHeaderLayOut(CustomHeaderLayOut mHeaderLayOut) {
		this.mHeaderLayOut = mHeaderLayOut;
	}

}
