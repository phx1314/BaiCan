package com.facefr.server.in;

import com.x.view.CameraView;

/**
 * 回调函数
 *
 *
 */
public interface BodyCheckFlowInterface {

	public boolean PutImgFrame(byte[] bm, CameraView mCameraView);
	//public boolean PutImgFrame(byte[] bm, Size size);

	//public boolean PutImgFrame(Bitmap bm);

	public boolean HintMsgChanged(int enumState);

	public boolean TargetOperationCountChanged(int iReturn);

	public boolean TargetOperationActionChanged(int enumState);

	public boolean OperationResultChanged(int iReturn);

	public boolean TotalSuccessCountChanged(int iReturn);

	public boolean TotalFailCountChanged(int iReturn);

	public boolean ClockTimeChanged(int iReturn);

	public boolean DoneOperationCountChanged(int iReturn);

	public boolean DoneOperationRangeChanged(int iReturn);

	public boolean IsFinishBodyCheckChanged(int iReturn);

	public boolean PlaySoundChanged(int iMsgType, int iReturn);

}
