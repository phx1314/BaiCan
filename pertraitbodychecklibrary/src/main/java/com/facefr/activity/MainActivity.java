package com.facefr.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facefr.controller.Controller;
import com.facefr.controller.SampleControllerCallBack;
import com.facefr.controller.StyleModel;
import com.pertraitbodycheck.activity.R;
import com.x.util.CheckPermServer;

public class MainActivity extends BaseActivity {

	private Button mBtnStart;
	private CheckPermServer mCheckPermServer;// android 6.0动态权限

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

		mCheckPermServer = new CheckPermServer(this,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 权限不足
						setResult(CheckPermServer.PERMISSION_DENIEG);
						finish();
					}
				});
	}


	private void initView() {
		mBtnStart = (Button) findViewById(R.id.btn_start);//获取按钮对象
		mBtnStart.setOnClickListener(new OnClickListener() {//设置按钮对象的方法
			@Override
			public void onClick(View view) {
				// android6.0以上动态权限
				if (!mCheckPermServer.permissionSet(MainActivity.this,
						CheckPermServer.PERMISSION_BODY)) {
					onNext();
				}
			}
		});
	}

	//进入活体界面
	private void onNext() {
		StyleModel model = new StyleModel();
//		可以设置各种属性（也可以不设置，按照默认值来），如：
//		model.resContentBgColor = Color.parseColor("#181818");
//		model.resActionBackImg = R.drawable.base_map;
//		model.actCount = 1;
//		model.actType = EnumInstance.EActType.act_shake;
		SampleControllerCallBack sampleControllerCallBack = new SampleControllerCallBack() {
			//重写两个回调函数
			@Override
			public void onBack() {
				System.out.println("回调：点击了返回");
				//如果有需要，可以在这里做点击返回后要做的事情

			}

			@Override
			public void onAllStepCompleteCallback(boolean isSuccess, String dataPage) {
				if (isSuccess) {
					System.out.println("回调：活体检测成功");
					//如果成功，dataPage就是data数据包，可用于上传

				}else {
					System.out.println("回调：活体检测失败");
					//如果失败，则dataPage为空
				}
				//这里做活体完成后要做的事情,比如跳转
				Intent intent=new Intent(getApplicationContext(), ResultActivity.class);
				startActivity(intent);
			}

		};
		//开始
		Controller.getInstance(this).setCallBack(sampleControllerCallBack).show(model);
	}

	@SuppressLint("Override")
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
		if (CheckPermServer.PERMISSION_REQUEST_CODE == requestCode
				&& mCheckPermServer.hasAllPermissionGranted(grantResults)) {
			// 回调中加载下一个Activity
			onNext();
		} else {
			mCheckPermServer.showMissingPermissionDialog();
		}
	}



}
