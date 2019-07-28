package com.x.util;

public interface BaseModuleInterface {
	public boolean Init(byte[] inDTModel, byte[] inPointModel);

	public boolean IsInit();

	public boolean Release();

	public String getLastError();


}
