package com.x.util;


public class BaseThread extends Thread {

	public boolean ThreadBegin() {
		mExitThread = false;
		start();
		return true;
	}

	public void ThreadEnd() {
		mExitThread = true;
	}

	public void setExitThread(boolean mExitThread) {
		this.mExitThread = mExitThread;
	}

	// =============================================================
	private boolean mExitThread = false;

	public boolean IsExit() {
		return mExitThread;
	}

	protected void Sleep(int iTime) {
		try {
			Thread.sleep(iTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void Sleep(long iTime) {
		try {
			Thread.sleep(iTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
