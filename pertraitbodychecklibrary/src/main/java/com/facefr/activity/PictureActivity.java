package com.facefr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.facefr.controller.Controller;
import com.facefr.server.in.CollectInfoInstance;
import com.facefr.server.in.PictureActCallBack;
import com.facefr.server.out.CapturePicManager;
import com.facefr.server.out.ManagerBaseInterface;
import com.facefr.server.out.ServeOutCallBack;
import com.facefr.view.PictureView;
import com.pertraitbodycheck.activity.R;
import com.x.util.BmpUtil;

//环境Activity
public class PictureActivity extends BaseActivity implements ServeOutCallBack {

	private ManagerBaseInterface model;//model
	private Bitmap mBestBmp;

	private PictureActCallBack mCallBack;

	//初始化时调用
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCallBack = Controller.getInstance();

		setContentView(R.layout.activity_picture);


		//初始化 CapturePicManager 继承于 ManagerBaseInterface接口，里面重写了接口函数
		model=new CapturePicManager(this);

		model.setOutCallBack(PictureActivity.this);
		Log.i(PictureView.TAG,"=PictureActivity onCreate=");


	}

	//即将显示时调用
	@Override
	protected void onResume() {
		super.onResume();
		Log.i(PictureView.TAG,"=PictureActivity onResume=" );

		//传入存放控件的父节点
		model.show((LinearLayout)findViewById(R.id.view_picture_parent));
//		model.setBtn(false);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(PictureView.TAG,"=PictureActivity onPause=" );
		model.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(PictureView.TAG,"=PictureActivity onStop=" );
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(PictureView.TAG,"=PictureActivity onDestroy=" );
	}

	@Override
	public void BestFaceNoticed(int action) {
		mBestBmp=model.getFacePhoto();//获取自拍照

		//外部收到回调后的处理事件
		if(mBestBmp!=null){
			byte[] selBuffer=BmpUtil.Bitmap2Bytes(mBestBmp);
			if (CollectInfoInstance.getInstance()!=null) {
				CollectInfoInstance.getInstance().setSelBuffer(selBuffer);//设置到专门保存数据的类中
			}
			if (!mBestBmp.isRecycled()) {//回收图片，避免内存泄漏
				mBestBmp.recycle();
				mBestBmp = null;
			}
		}



		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), AuthActivity.class);
		startActivityForResult(intent, 0);
//		startActivity(intent);
	}

	@Override
	public void onBack() {
		// TODO Auto-generated method stub
		this.finish();
		mCallBack.onBack();
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			String sMessage = data.getExtras().getString("_exit_");
			if ( "_yes_".equals(sMessage) )
			{
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





}
