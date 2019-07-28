package com.facefr.server.in;

public class EnumInstance {

	/**
	 *活体照片数量
	 */
	public static final class EPicNum{
		public static final int one =1;
		public static final int two=2;
		public static final int three=3;
	}

	/**
	 *动作类型
	 */
	public static final class EActType{

		public static final int act_mouth = 1<<1;  //张嘴
		public static final int act_head  = 1<<2;   //点头
		public static final int act_shake = 1<<3; //摇头
	}

	/**
	 *动作次数
	 */
	public static final class EActCount{
		public static final int one =1;
		public static final int two=2;
		public static final int three=3;
	}

	/**
	 *动作难度
	 */
	public static final class EActDiffi{
		public static final int easy=0;
		public static final int normal=1;
		public static final int difficult=2;
	}


	// java返回码
	public final static int RT_Timeout = -5;// android本地连接服务器超时

	public final static int THREAD_TRY_COUNT = 3;// 线程超时尝试次数

	public final static int COMPARE_FINISHE=1;//比对结束


}
