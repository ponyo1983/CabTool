package com.lon.cabtool.core;


public class SerialFrame {

	
	final static int FRAME_SIZE=16;
	byte[] data=new byte[FRAME_SIZE];
	int port=-1;
	public SerialFrame(int port,byte[] buffer,int length)
	{
		this.port=port;
		System.arraycopy(buffer, 0, data, 0, length);
	}
	
	public byte[] getData()
	{
		return data;
	}
	
	public int getPort()
	{
		return port;
	}
	
}
