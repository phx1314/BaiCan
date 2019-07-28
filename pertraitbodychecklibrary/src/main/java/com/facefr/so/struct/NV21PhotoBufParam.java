package com.facefr.so.struct;

public class NV21PhotoBufParam {
	public byte[] FeatureBuf = null;
	public int iWidth;
	public int iHeight;

	public void set(byte[] buf) {
		if (buf == null)
			return;
		FeatureBuf = new byte[buf.length];
		System.arraycopy(buf, 0, FeatureBuf, 0, buf.length);
	}
	public void setSize(int iWidthIn, int iHeightIn) {
		iWidth = iWidthIn;
		iHeight = iHeightIn;
	}
}
