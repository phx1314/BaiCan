package com.facefr.server.in;

import android.content.Context;
import android.hardware.Camera.Size;
import android.util.Log;

import com.facefr.so.InvokeSoLib;
import com.x.util.BaseThread;
import com.x.util.CameraUtil;
import com.x.util.Check;
import com.x.util.Yuv240spUtil;
import com.x.util.gCount;
import com.x.view.CameraView;


public class BodyCheckThread extends BaseThread {
	public static final String TAG = "BodyCheckThread";
	private boolean bFlag = false;

	public BodyCheckThread(Context context, BodyCheckFlowInterface callback) {
		this.mCallback = callback;
		this.setContext(context);
	}

	@Override
	public void ThreadEnd() {
		// TODO Auto-generated method stub
		super.ThreadEnd();
		while (!bFlag) {
			Sleep(2);
		}
	}

	@Override
	public void run() {
		while (!IsExit()) {
			if (mNV21Cache != null) {
				if (gCount.getProcessFrameCount() == 0)
					gCount.clear();
				gCount.IDEProcessFrameCount();
				// 转化成byte数组，并且调用native putFeatureBuf
				// 根据返回值
				mLastTime = System.currentTimeMillis();
				Log.d(TAG, "处理帧");
				if (InvokeSoLib.getInstance() != null) {
					//	LogStateChanged(InvokeSoLib.getInstance().putFeatureBuf(
					//  mNV21Cache, mSize.width, mSize.height));

					CollectInfoInstance.getInstance().lock.lock();// 得到锁
					try {
						mNV21Cache= Yuv240spUtil.rotateBySo(mNV21Cache, mSize.width, mSize.height,
								mCameraView.getJpegRotation(), mCameraView.getCurOpenCameraId());
						if (mCameraView.getiDisplayOrientation()== CameraUtil.Degree.ROTATION_90
								||mCameraView.getiDisplayOrientation()== CameraUtil.Degree.ROTATION_270) {
							//竖屏
							LogStateChanged(InvokeSoLib.getInstance().putFeatureBuf(mNV21Cache,
									mSize.width, mSize.height));
						}else{
							LogStateChanged(InvokeSoLib.getInstance().putFeatureBuf(mNV21Cache,
									mSize.height,mSize.width));
						}
					} finally {
						CollectInfoInstance.getInstance().lock.unlock();// 得到锁
					}





				}
				Log.d(TAG, "耗时" + (System.currentTimeMillis() - mLastTime));
				mNV21Cache = null;
			} else {
				gCount.setNullMark();
				continue;
			}
		}
		bFlag = true;
	}

	public boolean PutImgFrame(byte[] bm,CameraView mCameraView) {
		// 如果当前正忙，则抛弃此帧
		if (mNV21Cache != null) {
			gCount.IDEDropFrameCount();
			return false;
		}
		mNV21Cache = bm;
		this.mCameraView=mCameraView;
		if(	this.mCameraView!=null)
			mSize =mCameraView.getPreviewSize();
		return false;
	}

	// 位运算,得出哪些消息需要改变,每一位代表数据有所更新

	public boolean LogStateChanged(int iMaskReturn) {
		Log.i(TAG, "活体返回值:" + iMaskReturn);
		if (iMaskReturn >= 0) {
			if (Check.isset(iMaskReturn, 0)) {
				// 调用ndk对应底层的函数
				// 得到返回值后通知界面,更新UI
				if (mCallback != null) {
					mCallback.HintMsgChanged(InvokeSoLib.getInstance()
							.getHintMsg());
				}
			}
			if (Check.isset(iMaskReturn, 1)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mLastTargetOperationCount = InvokeSoLib.getInstance()
							.getTargetOperationCount();// 记录此次单个指令需要完成的次数
					mCallback
							.TargetOperationCountChanged(mLastTargetOperationCount);

				}
			}
			if (Check.isset(iMaskReturn, 4)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mReturn = InvokeSoLib.getInstance().getTotalSuccessCount();
					mCallback.TotalSuccessCountChanged(mReturn);

					if (mReturn != 0 && mReturn != mLastTotalSuccessReturn) {
						// 成功，如果与上一次的总成功次数不同
						if (Check.isset(iMaskReturn, 3)) {
							// 调用ndk对应底层的函数
							if (mCallback != null) {
								mCallback.OperationResultChanged(InvokeSoLib
										.getInstance().iSOperationSuccess());
								mCallback.PlaySoundChanged(
										PlaySoundInstance.SUCCESS, 0);
							}
						}
					}

					mLastTotalSuccessReturn = mReturn;
				}

			}
			if (Check.isset(iMaskReturn, 5)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mReturn = InvokeSoLib.getInstance().getTotalFailCount();
					mCallback.TotalFailCountChanged(mReturn);
					if (mReturn != 0 && mReturn != mLastTotalFailReturn) {
						// 失败，如果与上一次的总失败次数不同
						if (Check.isset(iMaskReturn, 3)) {
							// 调用ndk对应底层的函数
							if (mCallback != null) {
								mCallback.OperationResultChanged(InvokeSoLib
										.getInstance().iSOperationSuccess());
								mCallback.PlaySoundChanged(
										PlaySoundInstance.FAIL, 0);
							}
						}
					}
					mLastTotalFailReturn = mReturn;
				}
			}
			if (Check.isset(iMaskReturn, 2)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mReturn = InvokeSoLib.getInstance()
							.getTargetOperationAction();
					mCallback.TargetOperationActionChanged(mReturn);

					mCallback.OperationResultChanged(2);// 既不成功也不失败

					if (mReturn > 0)// 回到桌面时，要不要暂停声音？？
						mCallback.PlaySoundChanged(PlaySoundInstance.TICK, 1);// 有动作则开始倒计时
					else
						mCallback.PlaySoundChanged(PlaySoundInstance.TICK, 0);// 没有动作则终止倒计时
				}
			}

			if (Check.isset(iMaskReturn, 6)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mCallback.ClockTimeChanged(InvokeSoLib.getInstance()
							.getCountClockTime());
				}
			}
			if (Check.isset(iMaskReturn, 7)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mReturn = InvokeSoLib.getInstance().getDoneOperationCount();
					mCallback.DoneOperationCountChanged(mReturn);
					if (0 < mReturn && mReturn < mLastTargetOperationCount) {
						mCallback.PlaySoundChanged(PlaySoundInstance.CHECK, 0);
					}

				}
			}
			if (Check.isset(iMaskReturn, 8)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mCallback.DoneOperationRangeChanged(InvokeSoLib
							.getInstance().getDoneOperationRange());
				}
			}
			if (Check.isset(iMaskReturn, 9)) {
				// 调用ndk对应底层的函数
				if (mCallback != null) {
					mCallback.IsFinishBodyCheckChanged(InvokeSoLib
							.getInstance().iSFinishBodyCheck());
				}
			}
			return true;
		}
		return false;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	// =============================================
	private byte[] mNV21Cache = null;
	// private FeatureParam mOutParam = null;
	private Size mSize = null;
	private BodyCheckFlowInterface mCallback = null;
	private Context mContext = null;
	private long mLastTime = 0;
	private int mLastTotalSuccessReturn = 0;// 上一次总成功次数
	private int mLastTotalFailReturn = 0;// 上一次总失败次数
	private int mLastTargetOperationCount = 0;// 单个指令需要完成的总次数
	private int mReturn;
	private CameraView mCameraView=null;

}
