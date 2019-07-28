package com.facefr.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.facefr.controller.Controller;
import com.facefr.server.in.AuthActCallBack;
import com.facefr.server.in.CollectInfoInstance;
import com.facefr.server.out.BodyCheckBaseInterface;
import com.facefr.server.out.BodyCheckManager;
import com.facefr.server.out.BodyServerOutCallBack;
import com.pertraitbodycheck.activity.R;
import com.x.util.Check;

import java.util.List;

//活体Activity
public class AuthActivity extends BaseActivity implements BodyServerOutCallBack{

	private BodyCheckBaseInterface model;

	private AuthActCallBack mCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mCallBack = Controller.getInstance();

		setContentView(R.layout.activity_bodycheck);
		model=new BodyCheckManager(this);
	}

	//即将显示时调用
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


		model.setActType(CollectInfoInstance.getInstance().getActType());
		model.setActCount(CollectInfoInstance.getInstance().getActCount());//增
		model.setActDifficult(CollectInfoInstance.getInstance().getActDifficult());

		//保存照片张数
		model.setPictureNum(CollectInfoInstance.getInstance(this).getPictureNum());


		//设置是否打开倒计时声音
		model.isOpenTick(false);
		model.setOutCallBack(AuthActivity.this);

		//传入存放控件的父节点
		model.show((LinearLayout)findViewById(R.id.view_bodycheck_parent));

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (model!=null) {
			model.pause();
		}
	}

	@Override
	public void detectionOk(boolean isSuccess) {
		if (isSuccess)
		{

			boolean isgetData=false;
			List<Bitmap> bodyBmps=model.getBmpList();

			String packagedData=model.getPackagedData();
			CollectInfoInstance collectInfoInstance=CollectInfoInstance.getInstance();
			if (collectInfoInstance!=null) {
				collectInfoInstance.setBodyBmps(bodyBmps);
				collectInfoInstance.setBodySuccess(isSuccess);
				if (!Check.IsStringNULL(packagedData)) {
					isgetData=true;
				}
				collectInfoInstance.setIsgetData(isgetData);
				collectInfoInstance.setStrPhotoBase64(packagedData);
			}
			mCallBack.onAllStepCompleteCallback(true,packagedData);//回调
		}else {
			CollectInfoInstance collectInfoInstance=CollectInfoInstance.getInstance();
			if (collectInfoInstance!=null) {
				collectInfoInstance.setBodyBmps(null);
				collectInfoInstance.setBodySuccess(isSuccess);
				collectInfoInstance.setIsgetData(false);
				collectInfoInstance.setStrPhotoBase64(null);
				collectInfoInstance.setSelBuffer(null);
			}
			mCallBack.onAllStepCompleteCallback(false,null);//回调
		}

		Intent in = new Intent(getIntent().getAction());
		in.putExtra("_exit_", "_yes_");
		setResult(Activity.RESULT_OK, in);
		finish();//退出当前avtivity

	}

	@Override
	public void onBack() {
		finish();
	}
}
