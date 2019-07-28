package com.x.util;

import android.graphics.Bitmap;
import android.hardware.Camera.CameraInfo;

import com.x.util.CameraUtil.Degree;

import java.util.Arrays;

/**
 * 灰度图相关操作类
 *
 *
 */
public class YuvUtil {

	/**
	 * 提取YUV420中的Y分量即灰度图并旋转处理
	 *
	 * @param data
	 *            YUV420[YUV420sp或YUV420p]
	 * @param width
	 *            摄像头真实宽度
	 * @param height
	 *            摄像头真实高度
	 * @param iDegree[getiDisplayOrientation]
	 *            旋转角度
	 * @param bFlip
	 *            是否需要左右翻转[前置摄像头需要]
	 * @return 灰度图二进制数组
	 */
	@Deprecated
	public static byte[] convertYUV420toGray(byte[] data, int width,
											 int height, int iDegree, boolean bFlip) {
		if (width <= 0 || height <= 0 || data == null) {
			return null;
		}
		// 小米2s width=1280,height=720
		byte[] mGrayBuf = new byte[width * height];
		if(iDegree==Degree.ROTATION_0){
			// 不旋转直接提取原始灰度图
			System.arraycopy(data, 0, mGrayBuf, 0, width * height);
		}else if(iDegree==Degree.ROTATION_90){
			// 二维逆时针旋转90度
			// dest[width - j- 1][i]=src[i][j];
			// 二维左右翻转
			// dest[i][width-j-1]=src[i][j];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (!bFlip) {
						// 一维逆时针旋转90度
						mGrayBuf[((width - j - 1) * height + i)] = data[i * width+ j];
					} else {
						// 一维逆时针旋转90度并左右翻转
						mGrayBuf[((width - j - 1) * height + (height - i - 1))] = data[i* width + j];
					}
				}
			}
		}
		return mGrayBuf;
	}

	/**
	 * 提取YUV420中的Y分量即灰度图并旋转处理
	 *
	 * @param data
	 *            YUV420[YUV420sp或YUV420p]
	 * @param width
	 *            摄像头真实宽度
	 * @param height
	 *            摄像头真实高度
	 * @param iJpegRotation[getJpegRotation]
	 *            旋转角度
	 * @param iCameraID
	 * 				摄像头类型(前置|后置)
	 * @return 灰度图二进制数组
	 */
	public static byte[] convertYUV420toGray(byte[] data, int width,
											 int height, int iJpegRotation,int iCameraID) {
		if (width <= 0 || height <= 0 || data == null) {
			return null;
		}
		// 小米2s width=1280,height=720
		byte[] mGrayBuf = new byte[width * height];
		if(iJpegRotation==Degree.ROTATION_270){
			// Log.i(CameraView.TAG, "YUV270");
			// 二维逆时针旋转90度
			// dest[width - j- 1][i]=src[i][j];
			// 二维左右翻转
			// dest[i][width-j-1]=src[i][j];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (iCameraID==CameraInfo.CAMERA_FACING_BACK) {
						// 一维逆时针旋转90度
						mGrayBuf[((width - j - 1) * height + i)] = data[i * width+ j];
					} else {
						// 前置一维逆时针旋转90度并左右翻转
						mGrayBuf[((width - j - 1) * height + (height - i - 1))] = data[i* width + j];
					}
				}
			}
		}else if(iJpegRotation==Degree.ROTATION_90){
			//Log.i(CameraView.TAG, "YUV90");
			//顺时针旋转90度
			//二维数组dest[j][height -i- 1]=src[i][j];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					mGrayBuf[j*height+(height-i-1)] = data[i * width+ j];
				}
			}
		}else if(iJpegRotation==Degree.ROTATION_180){
			//Log.i(CameraView.TAG, "YUV180");
			// 不旋转直接提取原始灰度图
			System.arraycopy(data, 0, mGrayBuf, 0, width * height);
			//前置需左右翻转?
		}else if(iJpegRotation==Degree.ROTATION_0){
			//Log.i(CameraView.TAG, "YUV0");
			if (iCameraID==CameraInfo.CAMERA_FACING_FRONT) {
				//前置上下倒置(<=>顺时针180加左右翻转)
				//二维数组dest[h-i-1][j]=src[i][j];
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						mGrayBuf[(height-i-1)*width+j] = data[i * width+ j];
					}
				}
			}else{
				//顺时针旋转180度
				//二维数组dest[h-i-1][w-j-1]=src[i][j];
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						mGrayBuf[(height-i-1)*width+(width-j-1)] = data[i * width+ j];
					}
				}
			}
		}
		return mGrayBuf;
	}



	public static byte[] getZoom50ByGray(byte[] grayData,int width,int height){
		byte[] result = new byte[width*height/4];
		int z = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result[z++] = grayData[i*width+j];
				j++;
			}
			i++;
		}
		return result;
	}

	/**
	 * YUV420SP旋转处理
	 * @param nv21Data [NV21]
	 * @param width
	 * @param height
	 * @param iJpegRotation
	 * @param iCameraID
	 */
	public static byte[] rotate90YUV240SP(byte[] nv21Data, int width,
										  int height, int iJpegRotation,int iCameraID){
		// 小米2s width=1280,height=720
		byte[] destData=new byte[nv21Data.length];

		int wh = width * height;
		//顺时针旋转Y90度
		int k = 0;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++)
			{
				destData[k] = nv21Data[width * j + i];
				k++;
			}
		}
		//取UV
		for(int i = 0; i < width; i += 2) {
			for(int j = 0; j < height / 2; j++)
			{
				destData[k] = nv21Data[wh+ width * j + i];
				destData[k+1] = nv21Data[wh + width * j + i + 1];
				k+=2;
			}
		}
		return destData;
	}

	/**
	 * YUV转RGB rgb长度即width×heght
	 *
	 * @param rgb
	 * @param yuv420sp
	 * @param width
	 * @param height
	 */
	public static void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width,
									  int height) {
		final int frameSize = width * height;

		for (int j = 0, yp = 0; j < height; j++) {
			int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000)
						| ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
		}
	}

	/**
	 * 取值某点的RGB
	 *
	 * @param yuv420sp
	 * @param width
	 * @param height
	 * @param mWidth
	 * @param mHeight
	 * @return
	 */
	public static int decodeYUV420SP(byte[] yuv420sp, int width, int height,
									 int mWidth, int mHeight) {
		// Y矩阵长度frameSize , V和U矩阵第一位即frameSize
		final int frameSize = width * height;
		// yp为Y在矩阵中的位置，yph为所需要点的高mHeight-1，ypw为所需要点的宽mWidth
		int yp, yph = mHeight - 1, ypw = mWidth;
		yp = width * yph + ypw;
		// uvp为
		// uv在数组中的位置，V和U矩阵第一位即frameSize，yph>>1取值范围（0，0，1，1，2，2...）yph从0开始，即UV数组为Y数组的1/2.
		int uvp = frameSize + (yph >> 1) * width, u = 0, v = 0;
		// 获取Y的数值
		int y = (0xff & ((int) yuv420sp[yp])) - 16;
		if (y < 0)
			y = 0;
		if ((ypw & 1) == 0) {
			v = (0xff & yuv420sp[uvp++]) - 128;
			u = (0xff & yuv420sp[uvp]) - 128;
		} else {
			u = (0xff & yuv420sp[uvp--]) - 128;
			v = (0xff & yuv420sp[uvp]) - 128;
		}

		int y1192 = 1192 * y;
		int r = (y1192 + 1634 * v);
		int g = (y1192 - 833 * v - 400 * u);
		int b = (y1192 + 2066 * u);

		if (r < 0)
			r = 0;
		else if (r > 262143)
			r = 262143;
		if (g < 0)
			g = 0;
		else if (g > 262143)
			g = 262143;
		if (b < 0)
			b = 0;
		else if (b > 262143)
			b = 262143;

		return (0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff));
	}

	public static byte[] getPicturePixel(Bitmap bitmap){
		int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];//保存所有的像素的数组，图片宽×高
		bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
		byte[] buf=new byte[pixels.length*3];

		System.out.println("w-----"+bitmap.getWidth()+"h----"+bitmap.getHeight());

		for(int i = 0; i < pixels.length; i++){
			int clr = pixels[i];
			int  red   = (clr & 0x00ff0000) >> 16;  //取高两位
			int  green = (clr & 0x0000ff00) >> 8; //取中两位
			int  blue  =  clr & 0x000000ff; //取低两位

			//  buf[i] = (byte) (((blue) * 117 + (green) * 601 + (red) 306) >> 10);

			buf[i*3]=(byte) red;
			buf[i*3+1]=(byte)green;
			buf[i*3+2]=(byte)blue;

			// System.out.println("r="+red+",g="+green+",b="+blue);
		}
		return buf;
	}

}