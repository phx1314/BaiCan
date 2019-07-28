package com.x.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class DisplayUtil {
	private static final String TAG = "DisplayUtil";

	/**
	 * dp与px相互转化 dp和屏幕的密度有关,而px与屏幕密度无关 dip转px
	 *
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转dip
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度和高度，单位为px
	 *
	 * @param context
	 * @return
	 */
	public static Point getScreenMetrics(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		Log.i(TAG, "Screen---Width = " + w_screen + " Height = " + h_screen
				+ " densityDpi = " + dm.densityDpi);
		return new Point(w_screen, h_screen);

	}

	/**
	 * 获取屏幕长宽比
	 *
	 * @param context
	 * @return
	 */
	public static float getScreenRate(Context context) {
		Point P = getScreenMetrics(context);
		float H = P.y;
		float W = P.x;
		return (H / W);
	}

	/**
	 * 强制隐藏虚拟键盘
	 * @param v
	 */
	public static void hideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	/**
	 *
	 * 获取状态栏高度[onWindowFocusChanged中执行]
	 *
	 * @param activity
	 * @return
	 */
	public static int getStateHeight(Activity activity) {
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	/**
	 * 获取标题栏的高度[onWindowFocusChanged中执行]
	 *
	 * @param activity
	 * @return
	 */
	public static int getTitleHeight(Activity activity) {
		int statusBarHeight = getStateHeight(activity);
		int contentViewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		int titleBarHeight = contentViewTop - statusBarHeight;
		return titleBarHeight;
	}

	/**
	 * 模拟home键
	 *
	 * @param context
	 */
	public static void goToDestop(Context context) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);
	}

	/**
	 * 获得当前界面所用的xml文件的根view
	 * [最外层的可见LayOut节点]
	 * [窗口绘制完成才能获取到有效值]
	 * @param activity
	 * @return
	 */
	public static View getRootView(Activity activity){
		return ((ViewGroup) (activity.getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
	}

	/**
	 * 获得当前界面所在xml文件的最根view
	 * [不可见的FrameLayout]
	 * [窗口绘制完成才能获取到有效值]
	 * @param activity
	 * @return
	 */
	public static View getContentView(Activity activity){
		return activity.getWindow().getDecorView().findViewById(android.R.id.content);
	}
}
