package com.x.util;

import com.x.util.CameraUtil.Degree;

/**
 * YUV420SP旋转处理[所有函数已验证可以使用]
 *
 * @author Administrator
 * @date 20170323
 */
public class Yuv240spUtil {
	public static final String TAG="LWH";

	/**
	 * 依据SO在上层对NV21进行旋转[SO里对NV21是进行-90度旋转]
	 * @param nv21Data
	 * @param width
	 * @param height
	 * @param iJpegRotation
	 * @param iCameraID
	 * @return
	 */
	public static byte[] rotateBySo(byte[] nv21Data, int width, int height,
									int iJpegRotation,int iCameraID){
		if(iJpegRotation==Degree.ROTATION_270){
			//前置竖屏->上层应该不处理 -90+90
//			android.util.Log.i(TAG,iJpegRotation+"=="+"0");
			return nv21Data;
		}else if(iJpegRotation==Degree.ROTATION_90){
			//后置竖屏->上层应该处理180度 90+90
//			android.util.Log.i(TAG,iJpegRotation+"=="+"180");
			return rotate180(nv21Data,width,height);
		}else if(iJpegRotation==Degree.ROTATION_180){
			//前后置逆横屏 0+90
//			android.util.Log.i(TAG,iJpegRotation+"=="+"90");
			return rotate90_3(nv21Data,width,height);
		}else if(iJpegRotation==Degree.ROTATION_0) {
			//前后置顺横屏 180+90
//			android.util.Log.i(TAG,iJpegRotation+"=="+"-90");
			return rotateNeg90(nv21Data,width,height);
		}
		return nv21Data;
	}

	/**
	 * 忽略SO的处理,原本上层应做的旋转处理
	 * @param nv21Data
	 * @param width
	 * @param height
	 * @param iJpegRotation
	 * @param iCameraID
	 * @return
	 */
	public static byte[] rotate(byte[] nv21Data, int width, int height,
								int iJpegRotation,int iCameraID){
		if(iJpegRotation==Degree.ROTATION_270){
			return rotateNeg90(nv21Data,width,height);
		}else if(iJpegRotation==Degree.ROTATION_90){
			return rotate90_3(nv21Data,width,height);
		}else if(iJpegRotation==Degree.ROTATION_180){
			return nv21Data;
		}else if(iJpegRotation==Degree.ROTATION_0) {
			return rotate180(nv21Data,width,height);
		}
		return nv21Data;
	}


	/**
	 * 顺时针旋转90度[乘法][左右镜像处理,不推荐处理后置摄像头]
	 *
	 * @param nv21Data
	 *            [NV21]
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] rotate90(byte[] nv21Data, int width, int height) {
		// 小米2s width=1280,height=720
		byte[] destData = new byte[nv21Data.length];
		int wh = width * height;

		// Y转90
		int k = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				destData[k] = nv21Data[width * j + i];
				k++;
			}
		}
		// 取UV
		for (int i = 0; i < width; i += 2) {
			for (int j = 0; j < height / 2; j++) {
				destData[k] = nv21Data[wh + width * j + i];
				destData[k + 1] = nv21Data[wh + width * j + i + 1];
				k += 2;
			}
		}
		return destData;
	}

	/**
	 * 顺时针旋转90度[加法][左右镜像处理,不推荐处理后置摄像头]
	 *
	 * @param nv21Data
	 * @param srcWidth
	 * @param srcHeight
	 * @return
	 */
	public static byte[] rotate90_2(byte[] nv21Data, int srcWidth, int srcHeight) {
		int nWidth = 0, nHeight = 0;
		int wh = 0;
		int uvHeight = 0;
		if (srcWidth != nWidth || srcHeight != nHeight) {
			nWidth = srcWidth;
			nHeight = srcHeight;
			wh = srcWidth * srcHeight;
			uvHeight = srcHeight >> 1;// uvHeight = height / 2
		}
		byte[] destData = new byte[nv21Data.length];

		// 旋转Y
		int k = 0;
		for (int i = 0; i < srcWidth; i++) {
			int nPos = 0;
			for (int j = 0; j < srcHeight; j++) {
				destData[k] = nv21Data[nPos + i];
				k++;
				nPos += srcWidth;
			}
		}

		for (int i = 0; i < srcWidth; i += 2) {
			int nPos = wh;
			for (int j = 0; j < uvHeight; j++) {
				destData[k] = nv21Data[nPos + i];
				destData[k + 1] = nv21Data[nPos + i + 1];
				k += 2;
				nPos += srcWidth;
			}
		}
		return destData;
	}

	/**
	 * 逆时针旋转90度[加法][左右镜像未处理]
	 *
	 * @param nv21Data
	 * @param srcWidth
	 * @param srcHeight
	 * @return
	 */
	public static byte[] rotateNeg90(byte[] nv21Data, int srcWidth,
									 int srcHeight) {
		byte[] destData = new byte[nv21Data.length];
		int nWidth = 0, nHeight = 0;
		int wh = 0;
		int uvHeight = 0;
		if (srcWidth != nWidth || srcHeight != nHeight) {
			nWidth = srcWidth;
			nHeight = srcHeight;
			wh = srcWidth * srcHeight;
			uvHeight = srcHeight >> 1;// uvHeight = height / 2
		}

		// 旋转Y
		int k = 0;
		for (int i = 0; i < srcWidth; i++) {
			int nPos = srcWidth - 1;
			for (int j = 0; j < srcHeight; j++) {
				destData[k] = nv21Data[nPos - i];
				k++;
				nPos += srcWidth;
			}
		}

		for (int i = 0; i < srcWidth; i += 2) {
			int nPos = wh + srcWidth - 1;
			for (int j = 0; j < uvHeight; j++) {
				destData[k] = nv21Data[nPos - i - 1];
				destData[k + 1] = nv21Data[nPos - i];
				k += 2;
				nPos += srcWidth;
			}
		}
		return destData;
	}

	/**
	 * 顺时针旋转90度[左右镜像未处理]
	 *
	 * @param data
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	public static byte[] rotate90_3(byte[] data, int imageWidth, int imageHeight) {
		byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
		// Rotate the Y luma
		int i = 0;
		for (int x = 0; x < imageWidth; x++) {
			for (int y = imageHeight - 1; y >= 0; y--) {
				yuv[i] = data[y * imageWidth + x];
				i++;
			}
		}
		// Rotate the U and V color components
		i = imageWidth * imageHeight * 3 / 2 - 1;
		for (int x = imageWidth - 1; x > 0; x = x - 2) {
			for (int y = 0; y < imageHeight / 2; y++) {
				yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + x];
				i--;
				yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth)
						+ (x - 1)];
				i--;
			}
		}
		return yuv;
	}

	/**
	 * 顺时针旋转180度[左右镜像未处理]
	 *
	 * @param nv21Data
	 * @param srcWidth
	 * @param srcHeight
	 * @return
	 */
	public static byte[] rotate180(byte[] nv21Data, int srcWidth, int srcHeight) {
		byte[] yuv = new byte[srcWidth * srcHeight * 3 / 2];
		int i = 0;
		int count = 0;
		for (i = srcWidth * srcHeight - 1; i >= 0; i--) {
			yuv[count] = nv21Data[i];
			count++;
		}
		i = srcWidth * srcHeight * 3 / 2 - 1;
		for (i = srcWidth * srcHeight * 3 / 2 - 1; i >= srcWidth * srcHeight; i -= 2) {
			yuv[count++] = nv21Data[i - 1];
			yuv[count++] = nv21Data[i];
		}
		return yuv;
	}

	/**
	 * 顺时针旋转270=逆时针旋转90[左右镜像处理,不推荐处理后置摄像头][不推荐因为使用了两次处理]
	 * @param data
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	public static byte[] rotate270(byte[] data, int imageWidth,int imageHeight) {
		byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
		int nWidth = 0, nHeight = 0;
		int wh = 0;
		int uvHeight = 0;
		if (imageWidth != nWidth || imageHeight != nHeight) {
			nWidth = imageWidth;
			nHeight = imageHeight;
			wh = imageWidth * imageHeight;
			uvHeight = imageHeight >> 1;// uvHeight = height / 2
		}
		// Y
		int k = 0;
		for (int i = 0; i < imageWidth; i++) {
			int nPos = 0;
			for (int j = 0; j < imageHeight; j++) {
				yuv[k] = data[nPos + i];
				k++;
				nPos += imageWidth;
			}
		}
		for (int i = 0; i < imageWidth; i += 2) {
			int nPos = wh;
			for (int j = 0; j < uvHeight; j++) {
				yuv[k] = data[nPos + i];
				yuv[k + 1] = data[nPos + i + 1];
				k += 2;
				nPos += imageWidth;
			}
		}
		return rotate180(yuv, imageWidth, imageHeight);
	}
}
