package com.facefr.server.in;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.facefr.activity.BaseActivity;
import com.facefr.so.InvokeSoLib;
import com.facefr.so.struct.BitmapInfo;
import com.facefr.so.struct.OFRect;
import com.pertraitbodycheck.activity.R;
import com.x.util.BaseModuleInterface;
import com.x.view.CameraView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PictureSelfCheckInstance implements BaseModuleInterface,
		OnCompletionListener {

	public final static int PICTURE_AUTO_SUCCESS = 113;// 自动扫描合格，自动跳转到下一步
	public final static int PICTURE_AUTO_FAIL = 114;// 自动扫描失败
	public final static int PICTURE_STOP_SCAN = 115;// 停止扫描线
	public final static int UPDATE_FACE_FRAME= 116;// 更新人脸区域

	private OFRect mOfRect;

	private static PictureSelfCheckInstance mInstance = null;
	private PictureSelfCheckInstance(Context ctx) {
		this.mContext = ctx;
	}

	//初始化自己，并返回自己（静态变量）
	public static BaseModuleInterface getInstance(Context ctx) {
		if (mInstance == null) {
			synchronized (PictureSelfCheckInstance.class) {
				if (mInstance == null)
					mInstance = new PictureSelfCheckInstance(ctx);
			}
		}
		return mInstance;
	}
	public static PictureSelfCheckInstance getInstance() {
		return mInstance;
	}


	public boolean Init(byte[] inDTModel, byte[] inPointModel) {
		if (InvokeSoLib.getInstance() != null) {
			if (!InvokeSoLib.getInstance().IsInit())// 没有初始化SO,则初始化
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
				finally
				{

				}
		}
		_initMediaPlay();//设置跳转时的声音 ding
		beginCheckThread();//开启线程 线程中不断往so丢图并返回识别结果
		mInit = true;//记录 已经初始化
		return true;
	}

	@Override
	public boolean IsInit() {
		return mInit;
	}

	@Override
	public String getLastError() {
		return "";
	}

	@Override
	public boolean Release() {
		if (mInit) {
			_releaseMediaPlay();
			exitThread();
		}
		mInit = false;
		return true;
	}

	public boolean SuccessMsgChanged(String strInfo) {
		Log.i(PictureAutoCheckThread.TAG, "自动拍照成功");
		BaseActivity.sendMsg(mHandler,PICTURE_STOP_SCAN,strInfo);
		_startMediaPlay();//播放叮声音
		return true;
	}

	public boolean TakeSuccessMsgChanged(String strInfo) {
		Log.i(PictureAutoCheckThread.TAG, "手动拍照成功");
		BaseActivity.sendMsg(mHandler,PICTURE_STOP_SCAN,strInfo);
		return true;
	}

	public boolean FailMsgChanged(String strInfo) {
		Log.i(PictureAutoCheckThread.TAG, "自动拍照结果" + strInfo);
		BaseActivity.sendMsg(mHandler,PICTURE_AUTO_FAIL, strInfo);
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		//播放完成再跳转
		BaseActivity.sendMsg(mHandler,PICTURE_AUTO_SUCCESS,null);
	}

	//开启线程 线程中不断往so丢图并返回识别结果
	public void beginCheckThread(){
		if (mCheckThread == null) {
			mCheckThread = new PictureAutoCheckThread(mContext, this);
			mCheckThread.setPriority(Thread.MAX_PRIORITY);
			mCheckThread.ThreadBegin();//开启线程
		}
	}

	//关闭线程
	public void exitThread() {
		if (mCheckThread != null) {
			mCheckThread.setBmpNull();
			mCheckThread.ThreadEnd();
			mCheckThread = null;
		}
	}

	/**
	 * 线程检测每一预览帧[不能阻塞UI]
	 * @param data
	 * @param mCameraView
	 * @return
	 */
	public boolean PutImgFrame(byte[] data,CameraView mCameraView) {
		if (mCheckThread != null)
			return mCheckThread.PutImgFrame(data,mCameraView);
		return true;
	}

	public Bitmap getBmpCache() {
		if (mCheckThread != null)
			return mCheckThread.getBmpCache();
		return null;
	}

	public List<BitmapInfo> getBitmapList() {
		if (mCheckThread != null)
			return mCheckThread.getmBitmapList();
		return null;
	}

	public int getDealFrameCount() {
		if (mCheckThread != null)
			return mCheckThread.getDealFrameCount();
		return 0;
	}

	public void setHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}


	// /////////////////////////////////////////////////////////////
	private Context mContext = null;//环境检测的控制器对象
	private boolean mInit = false; //是否初始化
	private PictureAutoCheckThread mCheckThread = null;
	protected Handler mHandler;
	private MediaPlayer mMediaPlayer;//跳转时的声音 ding

	//设置跳转时的声音 ding
	private boolean _initMediaPlay() {
		if (mMediaPlayer == null) {
			mMediaPlayer = MediaPlayer.create(mContext, R.raw.ding);//设置跳转时的声音 ding
		}
		return true;
	}

	/**
	 * 开始播放
	 *
	 * @return boolean true/false
	 */
	private boolean _startMediaPlay() {
		if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(this);
		}
		return true;
	}

	/**
	 * onDestory中记得释放资源
	 *
	 * @return boolean true/false
	 */
	private boolean _releaseMediaPlay() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		return true;
	}

	public OFRect getOfRect() {
		return mOfRect;
	}

	public void setOfRect(OFRect mOfRect) {
		this.mOfRect = mOfRect;
		if (mHandler != null) {
			Message message = new Message();
			message.what =UPDATE_FACE_FRAME;
			mHandler.sendMessage(message);
		}
	}

}
