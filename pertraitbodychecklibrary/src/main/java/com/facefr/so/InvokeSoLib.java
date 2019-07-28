package com.facefr.so;

import com.facefr.so.struct.NV21PhotoBufParam;
import com.facefr.so.struct.OFDataParam;
import com.facefr.so.struct.OFRect;
import com.facefr.so.struct.OFVersionParam;
import com.x.util.BaseModuleInterface;

/**
 * �����λ����SO�ؼ��е������ռ�com.junyufr.szt.util��,����������İ���������
 * 
 * @author Administrator
 * 
 */
public class InvokeSoLib implements BaseModuleInterface {

	/* ��ʵ�� */
	private static InvokeSoLib mInstance = null;
	private final static byte[] _writeLock = new byte[0]; // ��Ϊд��

	public static InvokeSoLib getInstance() {
		if (mInstance == null)
			synchronized (InvokeSoLib.class) {
				if (mInstance == null)
					mInstance = new InvokeSoLib();
			}
		return mInstance;
	}



	private InvokeSoLib() {
	}

	@Override
	public boolean Init(byte[] inDTModel, byte[] inPointModel) {
		// TODO Auto-generated method stub
		synchronized (_writeLock) {
			InvokeSoLib.getInstance().unInit();
			InvokeSoLib.getInstance().init(inDTModel, inPointModel);
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
	public boolean Release() {
		// TODO Auto-generated method stub
		mInit = false;
		return true;
	}

	@Override
	public String getLastError() {
		// TODO Auto-generated method stub
		return null;
	}

	public  int getFacePosSyc(byte[] inBuffer, int iWidth, int iHeight, OFRect outData){
		synchronized (_writeLock) {
			return getFacePos(inBuffer,iWidth,iHeight,outData);
		}
	}
	// ===================================================================

	// ��Ԥ��byte[]֡����ndk,����ֵΪλ�������
	public native int putFeatureBuf(byte[] bmpBuf, int iWidth, int iHeight);

	// ������Ҫִ��ָ�����
	public native int getTargetOperationCount();

	// ������ʾ��,����ֵΪö��
	public native int getHintMsg();

	// ����ִ��ָ��ɹ�����ʧ��
	public native int iSOperationSuccess();

	// �����ܵĳɹ�����
	public native int getTotalSuccessCount();

	// �����ܵ�ʧ�ܴ���
	public native int getTotalFailCount();

	// ������Ҫִ��ָ���,����ֵΪö��
	public native int getTargetOperationAction();

	// ���յ���ʱ
	public native int getCountClockTime();

	// ����һ����������ɴ���
	public native int getDoneOperationCount();

	// ���ն�����ɷ���
	public native int getDoneOperationRange();

	// �Ƿ���ɻ�����,ͬʱ֪�������
	public native int iSFinishBodyCheck();

	// ������Ҫ�����int��Ƭ����
	public native int setOFPhotoNum(int iNum);

	// �õ��ѱ������������������
	public native int getOFPhotoNum();

	// ��ȡÿ����Ƭ��NV21�ֽ�����
	public native int getPhotoNV21Buffer(int iIndex, NV21PhotoBufParam outNV21Data);

	// ��jpg��Ƭ���û�ȥ
	public native int setPhotoJpgBuffer(int iIndex, byte[] inJpgBuffer);

	// ��ȡ���ݰ�data buffer
	public native int getDataBuffer(OFDataParam outData);

	// ����������jpgͼ��buffer
	public native int setSelfPhotoJpgBuffer(byte[] inJpgBuffer);

	// ���������ջҶ�ͼ����buffer
	public native int checkSelfPhotoGrayBuffer(byte[] inBuffer, int iWidth, int iHeight);
	
	// �õ����֤��������������λ��
	private native int getFacePos(byte[] inBuffer, int iWidth, int iHeight, OFRect outData);
	
	// �õ�checkSelfPhotoGrayBuffer������Ƭ��������������
	public native int GetSelfPhotoScore();
	
	// ����¼����Ƶbuffer
	public native int setVideoBuffer(byte[] inBuffer,byte[] strOperator);
	
	// �õ��������жϷ���������Խ�ߣ�˵��Խ������
	public native int getClarityScore(byte[] inGrayBuffer, int iWidth, int iHeight);

	// ��ʼ��
	private native int init(byte[] inDTModel, byte[] inPointModel);

	// �ͷ�
	private native int unInit();
	
	// ����
	public native int setCfg(int iActionType, int iActionNum, int iDifficulty);
	
	// ��ȡ�汾��Ϣ
	public native int getVersion(OFVersionParam outInfo);

	static {
		System.loadLibrary("JY_DTSDK");
		System.loadLibrary("JY_PointSDK");
		System.loadLibrary("JYVIVO");
	}

	// /////////////////////////////////////////////
	private boolean mInit = false;
}
