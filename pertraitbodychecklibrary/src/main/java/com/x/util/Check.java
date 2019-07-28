package com.x.util;

import android.annotation.SuppressLint;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("DefaultLocale")
public class Check {

	public static boolean IsStringNULL(String temp) {
		if (temp == null || temp.isEmpty() || ("").equals(temp)
				|| (" ").equals(temp) || temp.equals("undefined")
				|| temp.equals("null"))
			return true;
		return false;
	}

	// /验证身份证格式15位或17位或18位
	public static boolean isPersonNO(String temp) {
		//^(\\d{14}|\\d{17})(X|x|\\d?)$
		Pattern p = Pattern.compile("^(\\d{17})(X|x|\\d)$");
		Matcher m = p.matcher(temp);
		return m.matches();
	}

	// 姓名为中文，字母和数字和下划线,中间不能有空格
	public static boolean isPersonName(String temp) {
		Pattern p = Pattern.compile("^(\\w|[\\u4E00-\\u9FA5]|\\·)*$");
		Matcher m = p.matcher(temp);
		return m.matches();
	}

	public static boolean isNotNullOrEmpty(Object obj) {
		if (obj == null) {
			return false;
		}
		if ("".equals(obj.toString())) {
			return false;
		}
		return true;
	}

	// 保证取到的是数字(不可用返回0)
	public static int IsNum(String temp) {
		try {
			Integer.parseInt(temp);
		} catch (Exception e) {
			return -1;
		}
		return Integer.parseInt(temp);

	}

	/**
	 * 去掉了4个分割线-的32位大写guid
	 *
	 * @return
	 */
	public static String GetGUID32() {
		String s = java.util.UUID.randomUUID().toString().toUpperCase();
		// 去掉"-"符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 判断数字a的第几位是否为1
	 *
	 * @param a
	 *            原始数字
	 * @param bit
	 *            第几位 ,从0开始
	 * @return true/false
	 */
	public static boolean isset(int a, int bit) {
		a = a >> bit;
		if ((a & 1) == 0)
			return false;
		return true;
	}



}
