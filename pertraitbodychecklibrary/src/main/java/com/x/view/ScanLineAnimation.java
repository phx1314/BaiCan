package com.x.view;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

//扫描线动画类
public class ScanLineAnimation {
	ImageView imageView;
	long time;

	public ScanLineAnimation(ImageView imageView, long time) {
		this.imageView = imageView;
		this.time = time;
	}
	public void startAnimation() {
		ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(time);
		if (imageView != null) {
			imageView.startAnimation(animation);
		}
	}
	public void stopAnimation() {
		if (imageView != null) {
			imageView.clearAnimation();
		}
	}

}
