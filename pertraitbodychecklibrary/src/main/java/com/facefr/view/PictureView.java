package com.facefr.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facefr.controller.Controller;
import com.facefr.server.in.ViewInnerCallBack;
import com.pertraitbodycheck.activity.R;
import com.x.view.CameraView;
import com.x.view.CustomHeaderLayOut;
import com.x.view.FaceFrameView;

//环境检测界面
public class PictureView extends RelativeLayout {

	public static final String TAG = "PictureView";

	private View mView;

	private CustomHeaderLayOut mHeaderLayOut;//自定义Title
//	private ImageView mScanLine;//扫描线
	private FaceFrameView mFaceFrame;//静态人脸对准框
	private TextView mTxtHint;//提示消息

//	private FocusImg mFocusImg;//实时人脸跟踪框

//	private Button mBtnTakePic;//手动拍摄
//	private Button mBtnNext;//下一步
	private CameraView mCameraView;//视频view

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

	private ViewInnerCallBack mInterface;

	public PictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i(TAG,TAG+"=PictureView new=" );
		_setCustomAttributes(attrs, 0);

	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		Log.i(TAG,TAG+"=onWindowFocusChanged=" + hasWindowFocus);

		//替换 在Activity里onPause 调用pause方法

		/*if(!hasWindowFocus){
			if(mInterface!=null)
				mInterface.onMyPause();
		}else{
			mTxtHint.setText("");
			if (mInterface!=null) {
				mInterface.onMyResume();
			}
		}*/

		if(mInterface!=null){
			mInterface.onMyWindowFocusChanged(hasWindowFocus);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 对应onResume()
		super.onLayout(changed, l, t, r, b);
		Log.i(TAG,TAG+"=onLayout=" );
	}

	@Override
	protected void onDetachedFromWindow() {
		// 对应onDestroy
		super.onDetachedFromWindow();
		Log.i(TAG,TAG+"=onDetachedFromWindow=" );
		if(mInterface!=null){
			mInterface.onMyDestory();
		}
	}

	@SuppressLint("InflateParams")
	private void _setCustomAttributes(AttributeSet attrs, int defStyleAttr) {
		// 还可以实现自定义样式

		// 实现初始化，加载布局文件
		LayoutInflater mInflater = LayoutInflater.from(this.getContext());
		mView = mInflater.inflate(R.layout.view_picture, null);//获取view_picture 布局文件
		mView.setBackgroundColor(Controller.getInstance().mStyle.resContentBgColor);
		addView(mView);//添加到当前的view上

		//可以自定义属性和事件
		//mTitle = (TextView) findViewById(R.id.titleTV);
		mFaceFrame = (FaceFrameView) findViewById(R.id.face_frame);
		mTxtHint = (TextView) findViewById(R.id.hint_msg);
//		mFocusImg=(FocusImg) findViewById(R.id.focus);

		mCameraView = (CameraView) findViewById(R.id.SurfaceView1);
//		mScanLine = (ImageView) findViewById(R.id.scanline);

//		mBtnTakePic = (Button) findViewById(R.id.btn_take_photo);
//		mBtnNext=(Button) findViewById(R.id.btn_next);
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
		mPercentageBarThree.setTextColor(Controller.getInstance().mStyle.resTxtPercentageColor);

		mLampOne =(ImageView)findViewById(R.id.lamp_one);
		mLampOne.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionErrorImg));
		mLampTwo =(ImageView)findViewById(R.id.lamp_two);
		mLampTwo.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionErrorImg));
		mLampThree =(ImageView)findViewById(R.id.lamp_three);
		mLampThree.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resActionErrorImg));

	}

	public void setInnerCallBack(ViewInnerCallBack mInterface) {
		this.mInterface = mInterface;
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

	public FaceFrameView getFaceFrame() {
		return mFaceFrame;
	}

	public TextView getTxtHint() {
		return mTxtHint;
	}

//	public FocusImg getFocusImg() {
//		return mFocusImg;
//	}

//	public Button getBtnTakePic() {
//		return mBtnTakePic;
//	}
//
//	public Button getBtnNext() {
//		return mBtnNext;
//	}

	public CameraView getCameraView() {
		return mCameraView;
	}

/*	public TextView getTitle() {
		return mTitle;
	}*/

	public CustomHeaderLayOut getHeaderLayOut() {
		return mHeaderLayOut;
	}

}
