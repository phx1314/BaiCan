package com.x.util;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

@SuppressLint("Wakelock")
public class App {
	public static final boolean IsDebug = true;

	static public void AddModule(BaseModuleInterface Module) {
		if (Module != null && mListModule != null)
			mListModule.add(0, Module);
	}

	static public void AddActivity(Activity add) {
		if (add != null && mListActivity != null) {
			// add.setPersistent(true);
			mListActivity.add(add);
		}
	}

	static public void FinishAllActivity() {
		for (int i = 0, j = mListActivity.size(); i < j; i++) {
			Activity s = mListActivity.get(0);
			if (s != null)
				s.finish();
		}
		mListActivity.clear();
	}

	/*
	 * 退出程序前，需要释放各个模块 因为初始化各个模块，是在这个线程中，所以释放也放在这里
	 */
	static public void ExitApp() {
		for (int i = 0, j = mListModule.size(); i < j; i++) {
			BaseModuleInterface Module = mListModule.get(i);
			if (Module != null)
				Module.Release();
		}
		mListModule.clear();
		FinishAllActivity();
		System.exit(0);
	}

	public static boolean onKeyDown(Activity activity, int keyCode,
									KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
			case KeyEvent.KEYCODE_DPAD_DOWN:
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_DPAD_RIGHT:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				ShutScreen(null, activity);
				// Toast.makeText(activity, "截获键盘消息:" + event.getKeyCode(),
				// Toast.LENGTH_SHORT).show();
				return true;
		}

		return false;
	}

	public static boolean dispatchKeyEvent(Activity activity, KeyEvent event) {
		return false;
	}

	public static boolean VolumeUpper(Activity activity) {
		if (activity == null)
			return false;

		AudioManager audioManager = (AudioManager) activity
				.getSystemService(Service.AUDIO_SERVICE);
		if (audioManager == null)
			return false;

		// 第一个参数：声音类型
		// 第二个参数：调整音量的方向
		// 第三个参数：可选的标志位
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
		return true;
	}

	public static boolean VolumeLower(Activity activity) {
		if (activity == null)
			return false;

		AudioManager audioManager = (AudioManager) activity
				.getSystemService(Service.AUDIO_SERVICE);
		if (audioManager == null)
			return false;
		// 第一个参数：声音类型
		// 第二个参数：调整音量的方向
		// 第三个参数：可选的标志位
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
		return true;
	}

	public static void ShutScreen(Context packageContext, Activity activity) {

		WindowManager.LayoutParams params = activity.getWindow()
				.getAttributes();

		if (params.screenBrightness == WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
				|| params.screenBrightness == WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
				|| params.screenBrightness > 0.5F)
			params.screenBrightness = 0.01F;
		else
			params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;

		activity.getWindow().setAttributes(params);

		// Android判断屏幕关闭状态
		// PowerManager pm = (PowerManager) packageContext
		// .getSystemService(Context.POWER_SERVICE);
		// pm.isScreenOn();
		// pm.goToSleep(5000);

		// 灭屏幕常亮
		// PowerManager.WakeLock wakeLock = ((PowerManager) packageContext
		// .getSystemService(Context.POWER_SERVICE)).newWakeLock(
		// PowerManager.ACQUIRE_CAUSES_WAKEUP
		// | PowerManager.SCREEN_DIM_WAKE_LOCK, "MyActivity");
		// // 关闭屏幕常亮
		// if (wakeLock != null) {
		// wakeLock.acquire();
		// //wakeLock.release();
		// }

		// // 启用屏幕常亮
		// PowerManager.WakeLock wakeLock = ((PowerManager) packageContext
		// .getSystemService(Context.POWER_SERVICE)).newWakeLock(
		// PowerManager.SCREEN_BRIGHT_WAKE_LOCK
		// | PowerManager.ON_AFTER_RELEASE, "MyActivity");
		// wakeLock.acquire();
		// // 关闭屏幕常亮
		// if (wakeLock != null) {
		// wakeLock.release();
		// }

		// DevicePolicyManager localDevicePolicyManager = (DevicePolicyManager)
		// packageContext
		// .getSystemService(Context.DEVICE_POLICY_SERVICE);
		// localDevicePolicyManager.lockNow();

		// Intent intent = new Intent();
		// intent.setAction("android.intent.action.ACTION_SHUTDOWN");
		// packageContext.sendBroadcast(intent);

		// String cmd = "su -c reboot";
		// String cmd = "shutdown -h";
		// String cmd = "adb shell reboot -p";
		// try {
		// Runtime.getRuntime().exec(cmd);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Intent intent = new Intent(
		// "android.intent.action.ACTION_REQUEST_SHUTDOWN");
		// intent.putExtra("android.intent.extra.KEY_CONFIRM", false);//
		// 是否需要用户点击确认
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// packageContext.startActivity(intent);

		// Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
		// packageContext.sendBroadcast(intent);
		// intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);// 是否需要用户点击确认
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// packageContext.startActivity(intent);
		// packageContext.ServiceManagerProxy();
	}

	public static boolean setLockScreenAndPattern(Context context, boolean bLock) {
		boolean b1 = setLockScreenLight(context, bLock);
		boolean b2 = setLockPatternEnabled(context, bLock);
		return b1 & b2;
	}

	// ==============================================================
	private static List<BaseModuleInterface> mListModule = new ArrayList<BaseModuleInterface>();
	private static List<Activity> mListActivity = new ArrayList<Activity>();
	private static PowerManager.WakeLock mWakeLock = null;

	private static boolean setLockScreenLight(Context context, boolean bLock) {
		if (!bLock) {
			if (mWakeLock != null) {
				mWakeLock.release();
				mWakeLock = null;
				Log.w("test", context.toString() + " release");
			}
		} else {
			if (mWakeLock != null) {
				mWakeLock.release();
				Log.w("test", context.toString() + " release2");
				mWakeLock = null;
			}
			Log.w("test", context.toString() + " acquire");
			// PARTIAL_WAKE_LOCK：保持CPU 运转，屏幕和键盘灯允许熄灭；
			// SCREEN_DIM_WAKE_LOCK:保持CPU 运转，屏幕保持点亮（屏幕将进入DIM状态），键盘灯允许熄灭；
			// SCREEN_BRIGHT_WAKE_LOCK：保持CPU 运转，屏幕保持点亮，键盘灯允许熄灭；
			// FULL_WAKE_LOCK：保持CPU 运转，屏幕和键盘灯保持高亮显示；
			// ACQUIRE_CAUSES_WAKEUP：正常的WakeLocks是不能点亮屏幕的，然和，
			// 他们可以在屏幕点亮后保持点亮状态。ACQUIRE_CAUSES_WAKEUP可以强制点亮屏幕。
			// 比较经典的用法是，当重要通知到来时，可以立刻点亮屏幕来通知用户。（比如短信）
			// ON_AFTER_RELEASE：WakeLocks被释放时，时钟将被重置，使得屏幕点亮的时间稍微增长。

			PowerManager powerManager = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
					"My Lock");
			if (mWakeLock == null)
				return false;
			// mWakeLock.setReferenceCounted(false);
			mWakeLock.acquire();// 永久锁与超时锁的区别

		}
		return true;
	}

	private static boolean setLockPatternEnabled(Context context, boolean bLock) {
		if (context == null)
			return false;

		ContentResolver contentResolver = context.getContentResolver();
		if (contentResolver == null)
			return false;

		android.provider.Settings.System.putInt(contentResolver,
				android.provider.Settings.System.LOCK_PATTERN_ENABLED,
				bLock ? 0 : 1);
		return true;
	}
}
