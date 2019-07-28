package com.facefr.server.in;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.facefr.so.InvokeSoLib;
import com.pertraitbodycheck.activity.R;
import com.x.util.BaseModuleInterface;
import com.x.util.VibratorUtil;
import com.x.view.CameraView;

import java.io.IOException;
import java.io.InputStream;


public class BodyCheckFlowInstance implements BaseModuleInterface,
		BodyCheckFlowInterface {
	// 代表不同的消息
	public static final int MSG_HINT = 0;// 顶部提示信息需要改变
	public static final int MSG_OPERATION_ACTION = 1;// 动作指令需要更改
	public static final int MSG_OPERATION_COUNT = 2;// 动作次数需要改变
	public static final int MSG_OPERATION_RESULT = 3;// 动作执行成功还是失败需要改变
	public static final int MSG_TOTAL_SUCCESS_COUNT = 4;// 总的成功次数需要改变
	public static final int MSG_TOTAL_FAIL_COUNT = 5;// 总的失败次数需要改变
	public static final int MSG_CLOCK_TIME = 6;// 倒计时需要改变
	public static final int MSG_DONE_OPERATION_COUNT = 7;// 某个动作已完成次数需要改变
	public static final int MSG_DONE_OPERATION_RANGE = 8;// 某个动作已完成幅度需要改变
	public static final int MSG_FINISH_BODY_CHECK = 9;// 是否完成活体检测需要改变
	public static final int MSG_CHANGEWARNINGTEXT = 10;// 改变调试模式下的帧率显示文字

	private int mPicNum;
	private int mActType;
	private int mActCount;
	private int mActDiff;

	public static BodyCheckFlowInstance getInstance() {
		return mInstance;
	}

	private BodyCheckFlowInstance(Context ctx) {
		this.mContext = ctx;
	}

	public static BaseModuleInterface getInstance(Context ctx) {
		if (mInstance == null) {
			synchronized (BodyCheckFlowInstance.class) {
				if (mInstance == null)
					mInstance = new BodyCheckFlowInstance(ctx);
			}
		}
		return mInstance;
	}


	public boolean Init(byte[] inDTModel, byte[] inPointModel) {
		if (InvokeSoLib.getInstance() != null) {
			if (!InvokeSoLib.getInstance().IsInit()) {// 没有初始化SO,则初始化
				try{
					InputStream is = mContext.getResources().openRawResource(R.raw.jy_dt50model);
					byte DTBuffer[]=new byte[is.available()];
					is.read(DTBuffer);

					InputStream is2 = mContext.getResources().openRawResource(R.raw.jy_point50model);
					byte PointBuffer[]=new byte[is2.available()];
					is2.read(PointBuffer);


					InvokeSoLib.getInstance().Init(DTBuffer,PointBuffer);//初始化SO 改，传入dat
					is.close();
					is2.close();
				}
				catch(IOException e)
				{

				}
			}
			// 设置需要保存的照片数量
			InvokeSoLib.getInstance().setOFPhotoNum(mPicNum);
			InvokeSoLib.getInstance().setCfg(mActType, mActCount, mActDiff);
		}
		if (mCheckThread == null) {
			mCheckThread = new BodyCheckThread(mContext, this);
			// 设置优先级
			mCheckThread.setPriority(Thread.MAX_PRIORITY); // 10
			mCheckThread.ThreadBegin();//开启活体线程，往so中丢帧

			System.out.println("mCheckThread.ThreadBegin()");
		}
		mInit = true;
		return true;
	}

	@Override
	public boolean IsInit() {
		// TODO Auto-generated method stub
		return mInit;

	}

	@Override
	public String getLastError() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean Release() {
		// TODO Auto-generated method stubl
//		 mInstance = null;//打开此注释需要在下次使用时为空判断并重新获取
		if (mInit) {
			_exitThread();
		}
		mInit = false;
		return true;
	}

	@Override
	public boolean HintMsgChanged(int enumState) {
		// TODO Auto-generated method stub
		//System.out.println("HintMsgChanged()");
		return _sendMsg(MSG_HINT, enumState);

	}

	@Override
	public boolean TargetOperationCountChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_OPERATION_COUNT, iReturn);
	}

	@Override
	public boolean TargetOperationActionChanged(int enumState) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_OPERATION_ACTION, enumState);
	}

	@Override
	public boolean OperationResultChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_OPERATION_RESULT, iReturn);
	}

	@Override
	public boolean TotalSuccessCountChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_TOTAL_SUCCESS_COUNT, iReturn);
	}

	@Override
	public boolean TotalFailCountChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_TOTAL_FAIL_COUNT, iReturn);
	}

	@Override
	public boolean ClockTimeChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_CLOCK_TIME, iReturn);
	}

	@Override
	public boolean DoneOperationCountChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_DONE_OPERATION_COUNT, iReturn);
	}

	@Override
	public boolean DoneOperationRangeChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_DONE_OPERATION_RANGE, iReturn);
	}

	@Override
	public boolean IsFinishBodyCheckChanged(int iReturn) {
		// TODO Auto-generated method stub
		return _sendMsg(MSG_FINISH_BODY_CHECK, iReturn);
	}


	//接受so吐出来的结果，然后报动作名和成功失败
	@Override
	public boolean PlaySoundChanged(int iMsgType, int iReturn) {
		// TODO Auto-generated method stub
		switch (iMsgType) {
			case PlaySoundInstance.SUCCESS:
			case PlaySoundInstance.FAIL:
			case PlaySoundInstance.CHECK:
				VibratorUtil.Vibrate(mContext, 1000);
				PlaySoundInstance.getInstance().playSoundPoolById(iMsgType);
				break;

			case PlaySoundInstance.ANEAR:
			case PlaySoundInstance.FRONT:
			case PlaySoundInstance.MOUTH:
			case PlaySoundInstance.EYE:
			case PlaySoundInstance.UP:
			case PlaySoundInstance.SHAKE:
				PlaySoundInstance.getInstance().playSoundPoolById(iMsgType);
				break;

			case PlaySoundInstance.TICK:
				// // 循环 PlaySoundInstance.getInstance().playSoundPoolById(3, -1,
				// -1);

				// 0表示暂停,1表示播放
				if (iReturn > 0) {
					PlaySoundInstance.getInstance().startMediaPlay();// 已经在播放则不管，没有播放则开始播放
				} else if (iReturn == 0) {// 倒计时完成,停止播放tick
					PlaySoundInstance.getInstance().pauseMediaPlay();
				}

				break;
		}
		return true;
	}

	/*
	 * 往so中输入摄像头当前帧
	 */
	@Override
	public boolean PutImgFrame(byte[] bm,CameraView mCameraView) {
		if (mCheckThread != null)
			return mCheckThread.PutImgFrame(bm, mCameraView);
		return true;
	}

	public void setHandler(Handler handler) {
		this.mHandler = handler;
	}

	// =========================================
	private static BodyCheckFlowInstance mInstance = null;
	private Context mContext = null;
	private Handler mHandler = null;
	private boolean mInit = false;
	private BodyCheckThread mCheckThread = null;

	// ============================================
	private void _exitThread() {
		if (mCheckThread != null) {
			mCheckThread.ThreadEnd();
			mCheckThread = null;
		}
	}

	private boolean _sendMsg(int iMsgType, int iReturn) {
		if (mHandler != null) {
			Message message = new Message();
			message.what = iMsgType;
			message.obj = iReturn;
			mHandler.sendMessage(message);
			return true;
		}
		return false;
	}

	public int getmPicNum() {
		return mPicNum;
	}

	public void setmPicNum(int mPicNum) {
		this.mPicNum = mPicNum;
	}

	public int getmActType() {
		return mActType;
	}

	public void setmActType(int mActType) {
		this.mActType = mActType;
	}

	public int getmActCount() {
		return mActCount;
	}

	public void setmActCount(int mActCount) {
		this.mActCount = mActCount;
	}

	public int getmActDiff() {
		return mActDiff;
	}

	public void setmActDiff(int mActDiff) {
		this.mActDiff = mActDiff;
	}

}
