package com.x.view;


import com.pertraitbodycheck.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

//参考http://blog.csdn.net/rohsuton/article/details/7518031
public class CustomProgressDialog {
	private Dialog mDialog = null;

	public CustomProgressDialog(Context context,boolean isCanceledOnTouchOutside) {
		createDialog(context, isCanceledOnTouchOutside);
	}

	private boolean createDialog(Context context,
								 boolean isCanceledOnTouchOutside) {
		if (mDialog == null) {
			mDialog = new Dialog(context, R.style.CustomProgressDialog);
			mDialog.setContentView(R.layout.custom_dialog_progress);
			mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
			mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);// 设置点击屏幕Dialog不消失
			mDialog.setCancelable(false);// 设置按返回键是否关闭dialog
			ImageView imageView = (ImageView) mDialog.findViewById(R.id.loadingImageView);
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
			animationDrawable.start();
		}
		return true;
	}

	public void showDialog() {
		if (mDialog == null)
			return;
		mDialog.show();
	}

	public void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}

	}

	/**
	 *
	 * [Summary] setMessage 提示内容
	 *
	 * @param strMessage
	 * @return
	 *
	 */
	public boolean setMessage(String strMessage) {
		TextView tvMsg = (TextView) mDialog.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return true;
	}

	public boolean setMessage(int resId) {
		TextView tvMsg = (TextView) mDialog.findViewById(R.id.id_tv_loadingmsg);
		if (tvMsg != null) {
			tvMsg.setText(resId);
		}

		return true;
	}

}
