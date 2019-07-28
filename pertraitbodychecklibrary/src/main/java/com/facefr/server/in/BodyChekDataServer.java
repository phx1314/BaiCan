package com.facefr.server.in;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;

import com.facefr.so.InvokeSoLib;
import com.facefr.so.struct.NV21PhotoBufParam;
import com.facefr.so.struct.OFDataParam;
import com.x.util.Base64ImgUtil;
import com.x.util.BmpUtil;
import com.x.view.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BodyChekDataServer {

	private String strDataPhoto = null;//data数据包

	public List<Bitmap> getmBestBmps() {
		return getBestBmps();
	}

	public List<Bitmap> getBestBmps() {
		List<Bitmap> bestBmps = new ArrayList<Bitmap>();
		if (InvokeSoLib.getInstance()==null) {
			return null;
		}
		int iTotalCount = InvokeSoLib.getInstance().getOFPhotoNum();
		NV21PhotoBufParam outImgParam = null;
		Bitmap bmp = null;
		for (int i = 0; i < iTotalCount; i++) {
			outImgParam = new NV21PhotoBufParam();
			bmp = _getBestPhotoFromBottom(outImgParam, i);
			bestBmps.add(bmp);
		}
		return bestBmps;
	}

	public String getStrDataPhoto(byte[] selfBuf) {
		if (InvokeSoLib.getInstance()==null) {
			return null;
		}
		//将环境检测照片给so
		InvokeSoLib.getInstance().setSelfPhotoJpgBuffer(selfBuf);
		//将活体最优照片丢给so
		List<Bitmap> bestBmps=getBestBmps();
		if (bestBmps==null) {
			return null;
		}
		for (int i = 0; i < bestBmps.size(); i++) {
			Bitmap bmp=bestBmps.get(i);
			byte[] JpgBuffer = BmpUtil.Bitmap2Bytes(bmp);
			InvokeSoLib.getInstance().setPhotoJpgBuffer(i, JpgBuffer);
			JpgBuffer = null;
			if (!bmp.isRecycled()) {
				bmp.recycle();
				bmp=null;
			}
		}
		// 得到最后的数据包
		OFDataParam outParam = new OFDataParam();
		strDataPhoto = _getBestPhotoFromBottom(outParam);


        System.out.println(String.format("datapage = %d",strDataPhoto.length()));

		return strDataPhoto;
	}

	/**
	 * 从底层拿取数据并转换
	 *
	 * @param outParam
	 * @return
	 */
	private Bitmap _getBestPhotoFromBottom(NV21PhotoBufParam outParam,
										   int iIndex) {
		Bitmap mBMPTemp = null;
		YuvImage mYuvImg = null;
		ByteArrayOutputStream mOutStream = null;
		try {
			if (InvokeSoLib.getInstance() != null) {
				// 调用NDK,得到iIndex对应的最优人脸照片, 将照片byte
				InvokeSoLib.getInstance().getPhotoNV21Buffer(iIndex, outParam);
			}
			if (outParam.FeatureBuf != null) {
				mOutStream = new ByteArrayOutputStream();
				mYuvImg = new YuvImage(outParam.FeatureBuf, ImageFormat.NV21,
						outParam.iWidth, outParam.iHeight, null);
				mOutStream.reset();
				mYuvImg.compressToJpeg(new Rect(0, 0, outParam.iWidth,
						outParam.iHeight), 100, mOutStream);
				mBMPTemp = BitmapFactory.decodeByteArray(
						mOutStream.toByteArray(), 0, mOutStream.size());
				// 压缩旋转
				Matrix matrix = new Matrix();
				matrix.postRotate(-90);
				// 宽度小于240的不压缩(没旋转前高宽是反着的)
				if (mBMPTemp.getHeight() > CameraView.IMG_SCALE_WIDTH) {
					float f = CameraView.IMG_SCALE
							/ ((float) mBMPTemp.getHeight());
					matrix.postScale(f, f);
				}
				mBMPTemp = Bitmap.createBitmap(mBMPTemp, 0, 0, mBMPTemp.getWidth(),
						mBMPTemp.getHeight(), matrix, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (mOutStream != null)
					mOutStream.close();
				mYuvImg = null;
				outParam = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mBMPTemp;
	}

	/**
	 * 从底层拿取数据并转换
	 *
	 * @param outParam
	 * @return
	 */
	private String _getBestPhotoFromBottom(OFDataParam outParam) {
		String temp = null;
		try {
			if (InvokeSoLib.getInstance() != null) {
				// 调用NDK,得到最终的数据包字节数组,转码Base64
				InvokeSoLib.getInstance().getDataBuffer(outParam);
			}
			if (outParam.DataBuf != null) {
				temp = Base64ImgUtil.GetImageStr(outParam.DataBuf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

}
