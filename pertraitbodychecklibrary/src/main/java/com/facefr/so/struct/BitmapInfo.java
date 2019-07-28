package com.facefr.so.struct;

import android.graphics.Bitmap;

public class BitmapInfo {
	private int score;
	private Bitmap  bitmap;
	public BitmapInfo(int score,Bitmap bitmap){	
		this.score=score;
		this.bitmap=bitmap;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	@Override
	public String toString() {
		return "BitmapUtil [score=" + score + ", bitmap=" + bitmap + "]";
	}
	
	
}
