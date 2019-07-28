package com.x.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

public class VibratorUtil {

	/**
	 *
	 * @param ctx
	 *            上下文环境
	 * @param milliseconds
	 *            震动的时长，单位是毫秒
	 */
	public static void Vibrate(Context ctx, long milliseconds) {
		Vibrator vib = (Vibrator) ctx.getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	/**
	 *
	 * @param ctx
	 *            上下文环境
	 * @param pattern
	 *            自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长]时长的单位是毫秒
	 * @param isRepeat
	 *            是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	public static void Vibrate(Context ctx, long[] pattern, boolean isRepeat) {
		Vibrator vib = (Vibrator) ctx.getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
}
