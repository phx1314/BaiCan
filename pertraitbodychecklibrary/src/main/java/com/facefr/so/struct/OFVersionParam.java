package com.facefr.so.struct;

public class OFVersionParam {
	public int iVersion1;
	public int iVersion2;
	public int iVersion3;
	public int iVersion4;
	public byte[] strDescription = null;

	public void setVersion(int i1, int i2, int i3, int i4) {
		this.iVersion1 = i1;
		this.iVersion2 = i2;
		this.iVersion3 = i3;
		this.iVersion4 = i4;
	}
	
	public void setDescription(byte[] buf) {
		if (buf == null)
			return;
		strDescription = new byte[buf.length];
		System.arraycopy(buf, 0, strDescription, 0, buf.length);
	}
}
