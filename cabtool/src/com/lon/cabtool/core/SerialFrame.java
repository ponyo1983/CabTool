package com.lon.cabtool.core;


public class SerialFrame {

	
	final static int FRAME_SIZE=16;
	byte[] data=new byte[FRAME_SIZE];
	
	public SerialFrame(byte[] buffer,int length)
	{
		System.arraycopy(buffer, 0, data, 0, length);
	}
	
	public byte[] getData()
	{
		return data;
	}
	
}
