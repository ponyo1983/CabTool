package com.lon.cabtool.core;

public interface SerialProtocal {

	public void parseFrame(int port,byte[] data,int offset,int length);
	
	public void addFrameMonitor(FrameMonitor monitor);
	public void removeFrameMonitor(FrameMonitor monitor);
	
	public byte[] getPackedData(byte[] data,int length);
}
