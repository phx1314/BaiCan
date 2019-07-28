package com.facefr.server.out;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.facefr.server.in.BodyCheckServer;
import com.facefr.view.BodyView;
import com.x.util.Check;
import com.x.view.CustomHeaderLayOut;

import java.util.List;

public class BodyCheckManager implements com.facefr.server.out.BodyCheckBaseInterface {

	private Context mContext;//活体activity的界面，可对activity控制
	private BodyView mBodyView;//活体检测界面View
	private CustomHeaderLayOut mHeaderLayOut;

	private BodyCheckServer mBodyServer;
	private BodyServerOutCallBack mOutCallBack;//活体activity继承的接口，可以通过这个，给activity进行回调

	private int mPicNum;
	private int mActType;
	private int mActCount;
	private int mActDiff;

	private boolean isOpenTick;
	private String mTitle="";

	public BodyCheckManager(){

	}

	public BodyCheckManager(Context context){
		mContext=context;
	}

	@Override
	public void setOutCallBack(BodyServerOutCallBack outCallBack) {
		this.mOutCallBack=outCallBack;
		if(mBodyServer!=null){
			mBodyServer.setOutCallBack(this.mOutCallBack);
		}
	}


	@Override
	public boolean show(ViewGroup mView) {
		// TODO Auto-generated method stub
		if (mView==null) {
			return false;
		}
		if (mBodyView==null) {
			mBodyView=new BodyView(mContext, null);
			mView.addView(mBodyView);
			mBodyView.setVisibility(View.VISIBLE);
			mBodyServer=new BodyCheckServer(mContext,mBodyView);
			//设置回调
			mBodyServer.setOutCallBack(this.mOutCallBack);
			//设置配置参数
			mBodyServer.setPictureNum(mPicNum);
			mBodyServer.setActType(mActType);
			mBodyServer.setActCount(mActCount);
			mBodyServer.setActDifficult(mActDiff);
			//设置是否播放倒计时声音
			mBodyServer.setmIsOpenTick(isOpenTick);
			//设置回调
			mBodyView.setInnerCallBack(mBodyServer);
			mBodyServer.onMyResume();//开始运行活体
		}else{
			mBodyView.setVisibility(View.VISIBLE);
			mBodyServer.onMyResume();//开始运行活体
			return true;
		}
		mHeaderLayOut=mBodyView.getmHeaderLayOut();
		if (!Check.IsStringNULL(mTitle)) {
			mHeaderLayOut.setTitle(mTitle);
		}

		this.mHeaderLayOut.setOnleftListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mOutCallBack!=null) {
					mOutCallBack.onBack();
				}
			}
		});



		return true;
	}

	@Override
	public boolean destory() {
		if(mBodyView!=null){
			mBodyView.setVisibility(View.GONE);
			//需要从Activity移除，才能停止view里所有的执行
			ViewGroup parent = ( ViewGroup ) mBodyView.getParent() ;
			parent.removeView(mBodyView);
			mBodyView=null;
		}
		return true;
	}


	@Override
	public boolean pause() {
		if(mBodyServer!=null){
			mBodyServer.onMyPause();
		}
		return true;
	}


	@Override
	public List<Bitmap> getBmpList() {
		if (mBodyServer!=null) {
			Log.e("wsl","getBmpList  size===="+mBodyServer.getBmpList().size());
			return mBodyServer.getBmpList();
		}
		return null;
	}


	@Override
	public String getPackagedData() {
		// TODO Auto-generated method stub
		if (mBodyServer!=null) {
			return mBodyServer.getPagData();
		}
		return null;
	}

	@Override
	public void setTitleTxt(String text) {
		this.mTitle=text;
	}

	@Override
	public void setPictureNum(int picNum) {
		this.mPicNum=picNum;
	}

	@Override
	public void setActType(int actType) {
		this.mActType=actType;
	}

	@Override
	public void setActCount(int actCount) {
		this.mActCount=actCount;
	}

	@Override
	public void setActDifficult(int actDiff) {
		this.mActDiff=actDiff;
	}


	@Override
	public void isOpenTick(boolean isOpenTick) {
		this.isOpenTick=isOpenTick;
	}


}
