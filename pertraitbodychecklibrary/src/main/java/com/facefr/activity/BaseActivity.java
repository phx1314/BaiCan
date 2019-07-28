package com.facefr.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.facefr.server.in.CollectInfoInstance;


public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 应用运行时，保持屏幕高亮，不锁屏
		if (CollectInfoInstance.getInstance()==null) {
			CollectInfoInstance.getInstance(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		//竖屏
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	@Override
	protected void onPause() {
		// 在Activity销毁的时候释放wakeLock
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// 实现淡入淡出的效果
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		// // 由左向右滑入的效果
		// overridePendingTransition(android.R.anim.slide_in_left,
		// android.R.anim.slide_out_right);
	}

	public static boolean sendMsg(Handler mHandler, int iMsgType, String strInfo) {
		if (mHandler != null) {
			Message message = mHandler.obtainMessage();
			message.what = iMsgType;
			message.obj = strInfo;
			mHandler.sendMessage(message);
		}
		return true;
	}

	public static boolean sendMsgDelayed(Handler mHandler, int iMsgType, String strInfo,long delayMillis) {
		if (mHandler != null) {
			Message message = mHandler.obtainMessage();
			message.what = iMsgType;
			message.obj = strInfo;
			mHandler.sendMessageDelayed(message, delayMillis);
		}
		return true;
	}

}
