package com.facefr.server.out;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.facefr.server.in.CapturePicServer;
import com.facefr.server.in.ViewInnerCallBack;
import com.facefr.view.PictureView;
import com.x.view.CustomHeaderLayOut;
import com.x.view.FaceFrameView;

public class CapturePicManager implements ManagerBaseInterface{

	private PictureView mPictureView;//环境检测界面的View

	private Context mContext;//环境检测的控制器对象（用于回调或跳转）
	private ServeOutCallBack mOutCallBack;//环境检测的控制器回调接口

	private ViewInnerCallBack mPicServer;

//	private FocusImg mFocusImg;//实时人脸跟踪框
	private FaceFrameView mFaceFrame;//静态人脸对准框
//	private Button mBtnTakePic;//手动拍照按钮
//	private Button mBtnNext;//下一步按钮
	private CustomHeaderLayOut mHeaderLayOut;//自定义Title标题

	public CapturePicManager(Context mContext) {
		super();
		this.mContext = mContext;
	}

	//重写的show对外方法
	@Override
	public boolean show(ViewGroup mView){
		if(mView==null)
			return false;
		try{
			if(this.mPictureView==null){
				//如果环境检测界面为空，就创建环境检测界面
				this.mPictureView=new PictureView(mContext, null);
				mView.addView(this.mPictureView);//直接在传进来的viewGroup上添加创建的环境检测界面
				mPictureView.setVisibility(View.VISIBLE);//设置环境检测View为可见

				mPicServer=new CapturePicServer(mContext,mPictureView);//创建环境检测的server 传：环境检测的控制器对象,环境检测的View
				mPicServer.setOutCallBack(this.mOutCallBack);//设置回调：传，环境检测的控制器对象

				//运行server的函数 里面 初始化了so 开启了视频显示 开启了线程处理帧图 环境检测界面的控件初始化
				mPicServer.onMyResume();
			}else{
				mPictureView.setVisibility(View.VISIBLE);//设置环境检测View为可见

				//运行server的函数 里面 初始化了so 开启了视频显示 开启了线程处理帧图 环境检测界面的控件初始化
				mPicServer.onMyResume();
				return true;
			}

			//获取控件
//			this.mFocusImg=mPictureView.getFocusImg();
			this.mFaceFrame=mPictureView.getFaceFrame();
//			this.mBtnNext=mPictureView.getBtnNext();
//			this.mBtnTakePic=mPictureView.getBtnTakePic();
			this.mHeaderLayOut=mPictureView.getHeaderLayOut();

			//默认值
			setFaceAlign(true);//静态人脸对准框 是否显示 是
//			setFaceTrack(false);//人脸跟踪框 是否显示 否
//			setBtn(true);//拍照按钮 是否显示 无效

			//设置返回按钮的方法  设置自定义标题的左侧点击事件
			this.mHeaderLayOut.setOnleftListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (mOutCallBack!=null) {
						mOutCallBack.onBack();//回调给上层 onback
					}
				}
			});

//			//设置下一步按钮的方法
//			this.mBtnNext.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					if (mOutCallBack != null) {
//						mOutCallBack.BestFaceNoticed(ServeOutCallBack.HAND);//回调给上层 BestFaceNoticed 传 成功
//					}
//				}
//			});
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//环境控制器手动调用
	@Override
	public void setOutCallBack(ServeOutCallBack outCallBack) {
		this.mOutCallBack = outCallBack;
		if(mPicServer!=null){
			mPicServer.setOutCallBack(this.mOutCallBack);
		}
	}

	public boolean hide(){
		if(mPictureView!=null){
			mPictureView.setVisibility(View.INVISIBLE);
		}
		if(mPicServer!=null){
			mPicServer.onMyPause();
		}
		return true;
	}

	//重写的销毁方法
	@Override
	public boolean destory() {
		if(mPictureView!=null){
			mPictureView.setVisibility(View.GONE);
			//需要从Activity移除，才能停止view里所有的执行
			ViewGroup parent = ( ViewGroup ) mPictureView.getParent() ;
			parent.removeView(mPictureView);
			mPictureView=null;
		}
		return true;
	}

	@Override
	public boolean pause(){
		if(mPicServer!=null){
			mPicServer.onMyPause();
		}
		return true;
	}

	@Override
	public Bitmap getFacePhoto(){
		if(mPicServer!=null){
			return mPicServer.getCapture();
		}
		return null;
	}

	//静态人脸对准框 是否显示
	@Override
	public void setFaceAlign(boolean isShow) {
		if(mFaceFrame==null)
			return;
		if(!isShow){
			mFaceFrame.setVisibility(View.INVISIBLE);
		}else{
			mFaceFrame.setVisibility(View.VISIBLE);
		}
	}

//	//人脸跟踪框 是否显示
//	@Override
//	public void setFaceTrack(boolean isShow) {
//		if(mFocusImg==null)
//			return;
//		if(!isShow){
//			mFocusImg.setVisibility(View.INVISIBLE);
//		}else{
//			mFocusImg.setVisibility(View.VISIBLE);
//		}
//	}

//	//拍照按钮 是否显示
//	@Override
//	public void setBtn(boolean isShow) {
//		if(mBtnTakePic==null)
//			return;
//		if(!isShow){
//			mBtnTakePic.setVisibility(View.INVISIBLE);
//		}else{
//			mBtnTakePic.setVisibility(View.VISIBLE);
//		}
//	}

	@Override
	public void setTitleTxt(String text) {
		if (mHeaderLayOut==null) {
			return;
		}
		mHeaderLayOut.setTitle(text);
	}

}
