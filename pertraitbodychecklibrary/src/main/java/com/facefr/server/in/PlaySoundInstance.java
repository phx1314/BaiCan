package com.facefr.server.in;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pertraitbodycheck.activity.R;
import com.x.util.BaseModuleInterface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

public class PlaySoundInstance implements BaseModuleInterface, OnErrorListener,
		OnPreparedListener, OnCompletionListener, OnLoadCompleteListener {
	public static final int TICK = -1;// 倒计时声音

	// 一次只有一个声音,与下标对应,从0开始
	public static final int SUCCESS = 0;// 操作成功声音
	public static final int FAIL = 1;// 操作失败声音
	public static final int CHECK = 2;// 单次成功声音

	public static final int ANEAR = 3;// 请靠近摄像头声音
	public static final int FRONT = 4;// 请正对摄像头声音

	public static final int MOUTH = 5;// 张嘴声音
	public static final int EYE = 6;// 闭眼声音
	public static final int UP = 7;// 向上抬头声音
	public static final int SHAKE = 8;// 左右摇头声音

	public static final int SHUTTER = 9;// 快门声

	public static PlaySoundInstance getInstance() {
		return mInstance;
	}

	private PlaySoundInstance(Context ctx) {
		this.mContext = ctx;
	}

	public static PlaySoundInstance getInstance(Context ctx) {
		if (mInstance == null) {
			synchronized (PlaySoundInstance.class) {
				if (mInstance == null)
					mInstance = new PlaySoundInstance(ctx);
			}
		}
		return mInstance;
	}

	@SuppressLint("UseSparseArrays")

	public boolean Init(byte[] inDTModel, byte[] inPointModel) {
		// TODO Auto-generated method stub
		if (mSoundPoolMap == null) {
			mSoundPoolMap = new HashMap<Integer, PoolInfo>();
		}
		mSoundPoolMap.put(SUCCESS, new PoolInfo(R.raw.succeed));
		mSoundPoolMap.put(FAIL, new PoolInfo(R.raw.failed));
		mSoundPoolMap.put(CHECK, new PoolInfo(R.raw.check));
		mSoundPoolMap.put(ANEAR, new PoolInfo(R.raw.anear));
		mSoundPoolMap.put(FRONT, new PoolInfo(R.raw.front));
		mSoundPoolMap.put(MOUTH, new PoolInfo(R.raw.mouth));
		mSoundPoolMap.put(EYE, new PoolInfo(R.raw.eye));
		mSoundPoolMap.put(UP, new PoolInfo(R.raw.up));
		mSoundPoolMap.put(SHAKE, new PoolInfo(R.raw.shake));
		// /system/media/audio/ui/camera_click.ogg
		// 音频文件只要初始一次
		initSoundPool(2);
		// 倒计时是单独用MediaPlayer
		if (mIsOpenTick) {
			initMediaPlay(R.raw.tick, true);
			mInitTick=true;
		}
		mInit = true;
		return true;
	}

	public boolean ismInitTick() {
		return mInitTick;
	}

	public void setmInitTick(boolean mInitTick) {
		this.mInitTick = mInitTick;
	}

	public boolean ismIsOpenTick() {
		return mIsOpenTick;
	}

	public void setmIsOpenTick(boolean mIsOpenTick) {
		this.mIsOpenTick = mIsOpenTick;
	}

	@Override
	public boolean IsInit() {
		// TODO Auto-generated method stub
		return mInit;
	}

	@Override
	public boolean Release() {
		// TODO Auto-generated method stub
		if (mInit) {
			releaseSoundPool();
		}
		if (mInitTick) {
			releaseMediaPlay();
		}
		mInit = false;
		mInitTick=false;
		return true;
	}

	@Override
	public String getLastError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e("==", "Media出错" + what + "==" + extra);
		try {
			if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			}
			mp.reset();
			_setMPState(MPSTATE.Error);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		_setMPState(MPSTATE.Prepared);
		if (isContinue)
			mp.start();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (isContinue) {// 控制播放完重播
			mp.start();
		}
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		// 标记音频文件加载完毕
		if (status == 0 && null != mSoundPoolMap) {
			for (PoolInfo pool : mSoundPoolMap.values()) {
				if (pool != null && pool.getSoundID() == sampleId) {
					pool.setPrepared(true);
					break;
				}
			}
		}
	}

	// ================================================
	// .3gp文件支持的最好,参考http://developer.android.com/guide/appendix/media-formats.html
	/**
	 * MediaPlayer初始化音频文件(.wav文件android 4.1后才支持),一般处理比较大的音频文件
	 *
	 * @param mediaPlayer
	 *            一般处理比较大的音频文件
	 * @param resid
	 *            音频文件来源(如R.raw.music)
	 * @param isLoop
	 *            是否循环播放,true为循环
	 * @return boolean true/false
	 */
	public boolean initMediaPlay(int resid, boolean isLoop) {
		_setMPState(MPSTATE.Idle);
		mMediaPlayer = MediaPlayer.create(mContext, resid);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		return true;
	}

	/**
	 * 开始播放
	 *
	 * @return boolean true/false
	 */
	public boolean startMediaPlay() {
		if (mMediaPlayer != null) {
			try {
				if (isStoped) {
					_setMPState(MPSTATE.Inited);

					//确保在prepare之前播放器已经stop了
					mMediaPlayer.stop();

					mMediaPlayer.prepare();
					mMediaPlayer.setOnPreparedListener(this);
				}
				if (isPrepared && !mMediaPlayer.isPlaying()) {
					mMediaPlayer.start();
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		_setMPState(MPSTATE.Started);
		return true;
	}

	/**
	 * 暂停播放
	 *
	 * @return boolean true/false
	 */
	public boolean pauseMediaPlay() {
		// if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
		// mMediaPlayer.pause();//使用此方法魅族MX5会media server died 100
		// }
		// 解决方法1:使用延迟暂停的方式即播完本次后不播
		_setMPState(MPSTATE.Paused);

		stopMediaPlay();// 解决方法2：直接使用停止方式,之后再prepare&start
		return true;
	}

	/**
	 * 当前是否在播放
	 *
	 * @return boolean true/false
	 */
	public boolean IsMediaPlaying() {
		if (mMediaPlayer != null) {
			return mMediaPlayer.isPlaying();
		}
		return false;
	}

	/**
	 * 停止播放
	 *
	 * @return boolean true/false
	 */
	public boolean stopMediaPlay() {
		if (mMediaPlayer != null && isPrepared) {
			mMediaPlayer.stop();
		}
		_setMPState(MPSTATE.Stoped);
		return true;
	}

	/**
	 * onDestory中记得释放资源
	 *
	 * @return boolean true/false
	 */
	public boolean releaseMediaPlay() {
		if (mMediaPlayer != null) {
			mMediaPlayer.setOnErrorListener(null);
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		_setMPState(MPSTATE.End);
		return true;
	}

	/**
	 * SoundPool声音池类,一般处理比较短促的反应速度要求高的文件,音频格式建议使用OGG格式,Mp3文件最好不要超过100KB
	 * 最大只能申请1M的内存空间,提供了pause和stop方法，建议最好不要轻易使用,可能会使你的程序莫名其妙的终止
	 *
	 * @param ctx
	 *            上下文环境
	 * @param iMaxStreams
	 *            设置同时能够播放多少音效,默认为1,切记不能写的太大,最多容纳10个音频
	 * @param resIds
	 *            装载音频资源文件的int数组，存放如 R.raw.sound2的值
	 * @return
	 */
	public boolean initSoundPool(int iMaxStreams) {
		if (mSoundPool == null)
			mSoundPool = new SoundPool(iMaxStreams, AudioManager.STREAM_MUSIC,
					0);// 初始化

		// load得到soundID,从1开始,soundID为0表示加载失败,异步加载的方式
		// load个数不要超过256这个临界点
		if (mSoundPoolMap != null) {
			for (PoolInfo pool : mSoundPoolMap.values()) {
				if (pool != null) {
					pool.setSoundID(mSoundPool.load(mContext, pool.getResID(),
							1));
					mSoundPool.setOnLoadCompleteListener(this);
				}
			}
		}

		return true;
	}

	/**
	 *
	 * @param ctx
	 *            上下文环境
	 * @return float类型的音量大小
	 */
	public float setVolume(Context ctx) {
		// 实例化声音管理器AudioManager对象，控制声音
		AudioManager mgr = (AudioManager) ctx
				.getSystemService(Context.AUDIO_SERVICE);
		// 当前音量
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		// 最大音量
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		return volume;
	}

	/**
	 * SoundPool播放key指定的音效
	 *
	 * @param key
	 *            Map标记
	 * @return boolean
	 */
	public boolean playSoundPoolById(int key) {
		if (mSoundPool != null) {
			PoolInfo pool = mSoundPoolMap.get(key);
			if (pool != null) {
				if (!pool.isPrepared()) {// 声音文件没加载完
					// Toast.makeText(mContext, "正在加载声音文件，请稍后...",
					// Toast.LENGTH_SHORT).show();
					return false;
				}
				// 0为不循环,-1为永远循环(loop=-1时,MP3音频文件会报AudioFlinger could not create
				// track, status: -12错误,一个设备最多允许32AudioTrack)
				// 此音量是指多媒体音量;可得到streamID,从1开始,0表示失败
				pool.setStreamID(mSoundPool.play(pool.getSoundID(), 1.0f, 1.0f,
						pool.getPriority(), pool.getLoop(), pool.getRate()));
				if (pool.getStreamID() != 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * SoundPool暂停播放
	 *
	 * @param key
	 *            Map标记
	 * @return boolean true/false
	 */
	public boolean pauseSP(int key) {
		if (mSoundPool != null) {
			PoolInfo pool = mSoundPoolMap.get(key);
			if (pool != null) {
				// streamID通过play()返回，同时只允许一次播放则没关系
				mSoundPool.pause(pool.getStreamID());
			}
		}
		return true;
	}

	/**
	 * SoundPool停止播放
	 *
	 * @param key
	 * @return boolean true/false
	 */
	public boolean stopSP(int key) {
		if (mSoundPool != null) {
			PoolInfo pool = mSoundPoolMap.get(key);
			if (pool != null) {
				// streamID通过play()返回
				mSoundPool.stop(pool.getStreamID());
			}
		}
		return true;
	}

	/**
	 * onDestory中记得释放资源
	 *
	 * @return boolean true/false
	 */
	public boolean releaseSoundPool() {
		if (mSoundPool != null) {
			mSoundPool.release();
			mSoundPool = null;
		}
		if (mSoundPoolMap != null) {
			mSoundPoolMap.clear();
			mSoundPoolMap = null;
		}
		return true;
	}

	// ====================================================================
	private static PlaySoundInstance mInstance = null;
	private Context mContext;
	private boolean mInit = false;
	private boolean mInitTick=false;
	//是否打开倒计时声音
	private boolean mIsOpenTick;
	// 针对多个音频并使用不同的设置
	private Map<Integer, PoolInfo> mSoundPoolMap;
	private SoundPool mSoundPool;
	private MediaPlayer mMediaPlayer;
	private boolean isPrepared = false;// 是否准备好
	private boolean isContinue = false;// 是否继续播放|暂停
	private boolean isStoped = false;// 仅标记是否停止播放

	// ////////////////////////////////////////////////////////
	enum MPSTATE {
		Idle, Inited, Prepared, Started, Paused, Stoped, Error, End
	}
	/**
	 * 程序控制MediaPlayer状态
	 * @param state 枚举
	 */
	private void _setMPState(MPSTATE state) {
		if (state != null) {
			if (state == MPSTATE.Idle || state == MPSTATE.Inited) {
				isPrepared = false;
				isContinue = false;
				isStoped = false;
			} else if (state == MPSTATE.Prepared) {
				isPrepared = true;
			} else if (state == MPSTATE.Started) {
				isContinue = true;
				isStoped = false;
			} else if (state == MPSTATE.Paused) {
				isContinue = false;
				isStoped = false;
			} else if (state == MPSTATE.Stoped) {
				isContinue = false;
				isStoped = true;
			} else if (state == MPSTATE.Error || state == MPSTATE.End) {
				isPrepared = false;
				isContinue = false;
				isStoped = false;
			}
		}
	}

	private class PoolInfo {
		private int resID;
		private int soundID;
		private int streamID;// 默认全部为0表示失败
		private int priority = 0;
		private int loop = 0;
		private float rate = 1f;
		private boolean isPrepared = false;

		public PoolInfo(int resID) {
			super();
			this.resID = resID;
		}

		public int getResID() {
			return resID;
		}

		public int getSoundID() {
			return soundID;
		}

		public void setSoundID(int soundID) {
			this.soundID = soundID;
		}

		public int getStreamID() {
			return streamID;
		}

		public void setStreamID(int streamID) {
			this.streamID = streamID;
		}

		public int getPriority() {
			return priority;
		}

		@SuppressWarnings("unused")
		public void setPriority(int priority) {
			this.priority = priority;
		}

		public int getLoop() {
			return loop;
		}

		@SuppressWarnings("unused")
		public void setLoop(int loop) {
			this.loop = loop;
		}

		public float getRate() {
			return rate;
		}

		@SuppressWarnings("unused")
		public void setRate(float rate) {
			this.rate = rate;
		}

		public boolean isPrepared() {
			return isPrepared;
		}

		public void setPrepared(boolean isPrepared) {
			this.isPrepared = isPrepared;
		}
	}
}
