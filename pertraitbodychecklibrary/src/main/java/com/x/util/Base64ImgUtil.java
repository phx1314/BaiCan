package com.x.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import BASE64.BASE64Decoder;
import BASE64.BASE64Encoder;

public class Base64ImgUtil {

	/**
	 * 对data进行Base64编码,得到字符串
	 *
	 * @param data
	 * @return string编码后
	 */
	public static String GetImageStr(byte[] data) {
		if (data == null)
			return null;
		return BASE64Encoder.encode(data);
	}

	/**
	 * 读取imgFilePath路径下的文件，并对其进行Base64编码处理
	 *
	 * @param imgFilePath
	 * @return string编码后
	 */
	public static String GetImageStr(String imgFilePath) {
		if (imgFilePath == null || ("").equals(imgFilePath)) {
			return null;
		}
		// 读取图片字节数组
		byte[] data = null;
		InputStream in = null;
		try {
			in = new FileInputStream(imgFilePath);
			int count = in.available();
			if (count <= 0) {// 没有可用字节
				return null;
			}
			data = new byte[count];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 对字节数组Base64编码
		return BASE64Encoder.encode(data);
	}

	/**
	 * 对imgStr进行Base64 解码得到原始数据
	 *
	 * @param imgStr
	 *            编码后的字符串
	 * @return byte[] 原始的二进制数组
	 */
	public static byte[] GenerateImageByte(String imgStr) {
		if (imgStr == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0, j = bytes.length; i < j; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
