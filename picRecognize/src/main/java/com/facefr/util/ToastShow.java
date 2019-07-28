package com.facefr.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示处理类[回到桌面或退出界面Toast取消提示;短促时间内重复Toast不用重复创建]
 *
 * @author lwh
 * @date 2016-05-18
 */
public class ToastShow {

	private Context ctx;
	private Toast toast;

	public ToastShow(Context ctx) {
		super();
		this.ctx = ctx;
	}

	public void show(int res) {
		show(ctx.getString(res), Toast.LENGTH_SHORT);
	}

	public void show(String text) {
		show(text, Toast.LENGTH_SHORT);
	}

	public void showLong(int res) {
		show(ctx.getString(res), Toast.LENGTH_LONG);
	}

	public void showLong(String text) {
		show(text, Toast.LENGTH_LONG);
	}

	public void show(String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(ctx, text, duration);
		} else {
			toast.setText(text);
		}
		toast.show();
	}

	public void cancel() {
		if (toast != null) {
			toast.cancel();
		}
	}
}
