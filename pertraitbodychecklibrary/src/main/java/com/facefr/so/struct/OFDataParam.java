package com.facefr.so.struct;

public class OFDataParam {
	public byte[] DataBuf = null;

	public void set(byte[] buf) {
		if (buf == null)
			return;
		DataBuf = new byte[buf.length];
		System.arraycopy(buf, 0, DataBuf, 0, buf.length);
	}
}
