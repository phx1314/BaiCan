package com.x.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BmpUtil {
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		byte[] arrOut = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			arrOut = out.toByteArray();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrOut;
	}

	public static Bitmap Bytes2Bitmap(byte[] temp) {
		if (temp != null) {
			return BitmapFactory.decodeByteArray(temp, 0, temp.length);
		} else {
			return null;
		}
	}

	public static byte[] BmpTo4BytesARGBBuf(Bitmap bmp) {
		if (bmp == null)
			return null;

		int w = bmp.getWidth(), h = bmp.getHeight();

		int[] srcPixelsBuf = new int[5000];
		if (srcPixelsBuf.length < w)
			srcPixelsBuf = new int[w];

		byte[] bmpBuf = new byte[w * h * 4];

		int iARGBIndex = 0;
		for (int y = 0; y < h; y++) {
			bmp.getPixels(srcPixelsBuf, 0, w, 0, y, w, 1);
			for (int x = 0; x < w; x++) {
				bmpBuf[iARGBIndex] = (byte) ((srcPixelsBuf[x] & 0xFF) >> 24);
				bmpBuf[iARGBIndex + 1] = (byte) ((srcPixelsBuf[x] & 0xFF) >> 16);
				bmpBuf[iARGBIndex + 2] = (byte) ((srcPixelsBuf[x] & 0xFF) >> 8);
				bmpBuf[iARGBIndex + 3] = (byte) ((srcPixelsBuf[x] & 0xFF) >> 0);
				iARGBIndex += 4;
			}
		}

		return bmpBuf;
	}

	public static boolean saveBmpFile(Bitmap bmp, String file) {
		if (bmp == null || file == null)
			return false;

		FileOutputStream out = null;
		try {
			File f = new File(file);
			out = new FileOutputStream(f);
			bmp.compress(CompressFormat.JPEG, 100, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 *
	 * @param context
	 * @param resId
	 * @return BMP
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 将彩色图转换为灰度图
	 *
	 * @param img
	 *            位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	/**
	 * 根据matrix处理BMP
	 *
	 * @param b
	 * @param matrix
	 * @param bRecycled
	 *            是否回收原图b[原b与return不是同一个BMP需要传true]
	 * @return
	 */
	public static Bitmap getBitmap(Bitmap b, Matrix matrix, boolean bRecycled) {
		if (b == null)
			return null;
		if (matrix == null) {
			return b;
		}
		Bitmap btmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
				b.getHeight(), matrix, true);
		// 如果接收的是原BMP，不能在里面回收
		if (bRecycled && b != null) {
			if (!b.isRecycled())
				b.recycle();// 销毁原图片
			b = null;
		}
		return btmap;
	}

	/**
	 * 旋转Bitmap
	 *
	 * @param b
	 *            要处理的Bitmap
	 * @param rotateDegree
	 *            旋转弧度
	 * @param bRecycled
	 *            是否回收原图b[原b与return不是同一个BMP需要传true]
	 * @return 处理后的Bitmap
	 */
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree,
										 boolean bRecycled) {
		if (b == null)
			return null;
		if (rotateDegree == 0) {
			return b;
		}
		Matrix matrix = new Matrix();
		matrix.postRotate((float) rotateDegree);
		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
				b.getHeight(), matrix, true);
		if (bRecycled && b != null) {
			if (!b.isRecycled())
				b.recycle();// 销毁原图片
			b = null;
		}
		return rotaBitmap;
	}

	/**
	 * 按高度或宽度压缩Bitmap
	 *
	 * @param b
	 *            要处理的Bitmap
	 * @param desMax
	 *            目标最大值
	 * @param bRecycled
	 *            是否回收原图b[原b与return不是同一个BMP需要传true]
	 * @return 处理后的Bitmap
	 */
	public static Bitmap getScaleBitmap(Bitmap b, int desMax, boolean bRecycled) {
		if (b == null)
			return null;
		int srcMax = b.getHeight() > b.getWidth() ? b.getHeight() : b
				.getWidth();
		if (srcMax > desMax) {
			float f = desMax / ((float) srcMax);
			return getScaleBitmap(b, f, f, bRecycled);
		}
		return b;
	}

	/**
	 * 压缩Bitmap
	 *
	 * @param b
	 *            要处理的Bitmap
	 * @param sx
	 * @param sy
	 * @param bRecycled
	 *            是否回收原图b[原b与return不是同一个BMP需要传true]
	 * @return 处理后的Bitmap
	 */
	public static Bitmap getScaleBitmap(Bitmap b, float sx, float sy,
										boolean bRecycled) {
		if (b == null)
			return null;
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);
		Bitmap scaleBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
				b.getHeight(), matrix, true);
		if (bRecycled && b != null) {
			if (!b.isRecycled())
				b.recycle();// 销毁原图片
			b = null;
		}
		return scaleBitmap;
	}

	/**
	 * 将图像裁剪成圆形
	 *
	 * @param bitmap
	 *            要处理的Bitmap
	 * @param bRecycled
	 *            是否回收原图bitmap[原b与return不是同一个BMP需要传true]
	 * @return 处理后的Bitmap
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap, boolean bRecycled) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		if (bRecycled && bitmap != null) {
			if (!bitmap.isRecycled())
				bitmap.recycle();// 销毁原图片
			bitmap = null;
		}
		return output;
	}

	/**
	 * 把图片存到文件夹，图片路径传入图库并通知图库更新
	 *
	 * @param context
	 * @param bmp
	 * @param dir
	 * @param fileName
	 *            照片名[不需要携带文件后缀]
	 */
	public static void saveImgToGallery(Context context, Bitmap bmp,
										String dir, String fileName) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), dir);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		if (fileName == null || ("").equals(fileName)) {
			fileName = System.currentTimeMillis() + "";
		}
		fileName = fileName + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.fromFile(file.getAbsoluteFile())));
		System.out.println("保存到系统相册:" + file.getAbsolutePath());
	}

	/*
	 * 从Assets中读取图片
	 */
	public static Bitmap getImageFromAssetsFile(Context mCtx, String fileName) {
		Bitmap image = null;
		AssetManager am = mCtx.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * @param bitmap
	 * @return
	 *
	 *         将bitmap转换为string
	 */
	public static String bmpToString(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();// outputstream
		bitmap.compress(CompressFormat.PNG, 100, out);
		byte[] appicon = out.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);
	}

	/**
	 * string转成bitmap
	 *
	 * @param st
	 */
	public static Bitmap stringToBmp(String st) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

}
