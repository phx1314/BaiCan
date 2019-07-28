package com.x.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class MiniBitmap {
	//根据图片宽度  按原始比例缩小图片
	public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth) {
		// 图片的宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 图片的宽高比例
		float temp = ((float) height) / ((float) width);
		int newHeight = (int) ((newWidth) * temp);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// matrix.postRotate(45);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		bitmap.recycle();
		return resizedBitmap;
	}

	//根据图片高度  按原始比例缩小图片
	public static Bitmap resizeBitmapY(Bitmap bitmap, int newHeight) {
		// 图片的宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 图片的宽高比例
		float temp =((float) width)/((float) height) ;
		int newWidth=(int) (newHeight*temp);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// matrix.postRotate(45);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		bitmap.recycle();
		return resizedBitmap;
	}
}
