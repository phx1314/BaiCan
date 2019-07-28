package com.x.util;

import android.graphics.Bitmap;

public class GrayBmpUtil {

	public GrayBmpUtil() {
	}

	public GrayBmpUtil(Bitmap bm_in) {
		PutImg(bm_in);
	}

	public boolean PutImg(Bitmap bm_in) {
		if (bm_in == null)
			return false;

		mImg = bm_in;
		mIsSetGrayBuf = false;

		return true;
	}

	public boolean IsNull() {
		return (mImg == null);
	}

	public boolean Clear() {
		mImg = null;
		return true;
	}

	public int getGrayWidth() {
		if (mImg != null) {
			if (mIsSetGrayBuf)
				return mGrayWidth;

			int iWidth = mImg.getWidth();
			if (iWidth % 4 != 0) {
				iWidth += (4 - iWidth % 4);
			}
			return iWidth;
		}

		return 0;
	}

	public int getGrayHeight() {
		if (mImg != null) {
			if (mIsSetGrayBuf)
				return mGrayHeight;
			return mImg.getHeight();
		}
		return 0;
	}

	// 原始图像深度
	public int getDepth() {
		if (mImg != null)
			return 4;
		return 0;
	}

	public byte[] getGrayImg() {
		if (!mIsSetGrayBuf)
			ToGray();
		if (!mIsSetGrayBuf)
			return null;

		return mGrayBuf;
	}

	public String getLastError() {
		return "";
	}

	// ========================================================
	private Bitmap mImg = null;
	private int mGrayWidth = 0;
	private int mGrayHeight = 0;
	private byte[] mGrayBuf = null;
	private boolean mIsSetGrayBuf = false;
	// private int[] mSrcPixelsBuf = new int[5000 * 5000];
	private int[] mSrcPixelsBuf = new int[5000];

	@SuppressWarnings("unused")
	private int _Compress(byte[] arrGray, byte[] arrCompress) {
		if (arrGray.length <= 0)
			return 0;
		byte ch = arrGray[0];
		byte iNum = 1;
		int iCompressIndex = 0;
		for (int i = 1; i < arrGray.length; i++) {
			if (ch == arrGray[i] && iNum < 127)
				iNum++;
			else {
				arrCompress[iCompressIndex] = ch;
				arrCompress[iCompressIndex + 1] = iNum;
				iCompressIndex += 2;

				ch = arrGray[i];
				iNum = 1;
			}
		}
		if (iNum >= 1) {
			arrCompress[iCompressIndex] = ch;
			arrCompress[iCompressIndex + 1] = iNum;
			iCompressIndex += 2;
		}

		return iCompressIndex;

	}

	// 解压缩
	@SuppressWarnings("unused")
	private int _DecodeCompress(byte[] arrCompress, byte[] arrGray) {
		if (arrCompress.length <= 0)
			return 0;
		byte ch;
		byte count;
		int iIndex = 0;
		for (int i = 0; i < arrCompress.length; i++) {

			ch = arrCompress[i];
			i = i + 1;
			count = arrCompress[i];

			if (count < 1 || count > 127) {
				continue;
			}

			for (int j = iIndex; j < (iIndex + count); j++) {
				arrGray[j] = ch;
			}
			iIndex = iIndex + count;

		}
		return iIndex;

	}

	private boolean ToGray(Bitmap bmp) {
		if (bmp == null)
			return false;

		int w = bmp.getWidth(), h = bmp.getHeight();

		if (mSrcPixelsBuf.length < w)
			mSrcPixelsBuf = new int[w + 100];

		if (mGrayBuf == null || mGrayWidth != w || mGrayHeight != h) {
			mGrayWidth = w;
			mGrayHeight = h;
			mGrayBuf = null;
		}
		byte[] arrRowBuf = new byte[mGrayWidth];
		// System.arraycopy(Object src, int srcPos, Object dest, int destPos,
		// int length)
		if (mGrayBuf == null)
			mGrayBuf = new byte[mGrayWidth * mGrayHeight];

		int iGrayIndex = 0;
		// int red, green, blue = 0;
		// int alpha = 0xFF << 24, gray = 0;
		for (int y = 0; y < h; y++) {
			bmp.getPixels(mSrcPixelsBuf, 0, w, 0, y, w, 1);
			for (int x = 0; x < w; x++) {
				// red = (mSrcPixelsBuf[x] & 0x00FF0000) >> 16;
				// green = (mSrcPixelsBuf[x] & 0x0000FF00) >> 8;
				// blue = (mSrcPixelsBuf[x] & 0x000000FF);
				// X = 0.3×R+0.59×G+0.11×B// 网上提供的
				// gray = (int) ((float) red * 0.3 + (float) green * 0.59 +
				// (float) blue * 0.11);
				// arrRowBuf[x] = (byte)alpha | (gray << 16) | (gray << 8) |
				// gray;
				// ////////////////////////////////////////
				// arrRowBuf[x] = (byte) (((blue) * 117 + (green) * 601 + (red)
				// * 306) >> 10);// 跟C++宏相同

				arrRowBuf[x] = (byte) (mSrcPixelsBuf[x] & 0xFF);// 只转B
			}
			System.arraycopy(arrRowBuf, 0, mGrayBuf, iGrayIndex, w);
			iGrayIndex += w;
		}

		// bmp.getPixels(mSrcPixelsBuf, 0, w, 0, 0, w, h);
		// int iOffsetSrc = 0;
		// int iOffsetDest = 0;
		// for (int row = 0; row < h; row++) {
		// iOffsetSrc = row * w;
		// iOffsetDest = row * w;
		// for (int col = 0; col < w; col++) {
		// pixel = mSrcPixelsBuf[iOffsetSrc + col + cValidRect.iLeft];
		// int A = (pixel >> 24) & 0xFF;
		// int R = (pixel >> 16) & 0xFF;
		// int G = (pixel >> 8) & 0xFF;
		// int B = pixel & 0xFF;
		// int value = ((B) * 117 + (G) * 601 + (R) * 306) >> 10;
		// mGrayBuf[iOffsetDest + col] = (byte) value;
		//
		// // bxs // 因为当前显示的就是灰度图，所以不必进行上面的转换
		// mGrayBuf[iOffsetDest + col] = (byte) (mSrcPixelsBuf[iOffsetSrc
		// + col] & 0xFF);
		// }
		// }

		return true;
	}

	private boolean ToGray() {
		if (mImg == null)
			return false;
		if (mIsSetGrayBuf)
			return true;

		if (!ToGray(mImg))
			return false;

		mIsSetGrayBuf = true;
		return true;
	}
}
