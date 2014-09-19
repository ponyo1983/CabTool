package com.lon.cabtool.core;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.lon.cabtool.util.CRC16;

public class HostProtocal implements SerialProtocal {

	final static int FrameSize = 16;
	byte[] frameBuffer = new byte[FrameSize];
	int frameLength = 0;

	
	byte[] packedData=new byte[FrameSize];
	
	List<FrameMonitor> listMonitors=new ArrayList<FrameMonitor>();
	
	
	@Override
	public void parseFrame(int port,byte[] buffer, int offset, int length) {

		// TODO Auto-generated method stub
		if(length<=0) return;
		if(buffer.length<=offset) return;

		for (int i = 0; i < length; i++) {

			byte data = buffer[offset + i];
			if (data == (byte) 0xff) {
				frameBuffer[frameLength] = data;
				frameLength++;
				
			} else if (frameLength > 0) {

				frameBuffer[frameLength] = data;
				frameLength++;
			}

			if (frameLength == FrameSize) // get a frame
			{
				int crc = CRC16.ComputeCRC16(frameBuffer, 1, 11);
				byte crcLow = (byte) (crc & 0xff);
				byte crcHigh = (byte) (crc >> 8);
				if (crcLow != frameBuffer[12] || crcLow != frameBuffer[13]
						|| crcHigh != frameBuffer[14]
						|| crcHigh != frameBuffer[15]) {
					frameLength = 0;
				
					for(int j=1;j<FrameSize;j++)
					{
						data = frameBuffer[j];
						if (data == (byte) 0xff) {
							frameBuffer[frameLength] = data;
							frameLength++;
							
						} else if (frameLength > 0) {

							frameBuffer[frameLength] = data;
							frameLength++;
						}
					}
					
				}
				else {
					SerialFrame frame=new SerialFrame(port,frameBuffer, FrameSize);
					synchronized (listMonitors) {
						for(FrameMonitor monitor:listMonitors)
						{
							monitor.addFrame(frame);
						}
					}
					frameLength = 0; // start a new frame
				}
				

			}
		}
		

	}

	@Override
	public void addFrameMonitor(FrameMonitor monitor) {
		// TODO Auto-generated method stub
		synchronized (listMonitors) {
			listMonitors.add(monitor);
		}
		
	}

	@Override
	public void removeFrameMonitor(FrameMonitor monitor) {
		// TODO Auto-generated method stub
		synchronized (listMonitors) {
			listMonitors.remove(monitor);
		}
	}

	@Override
	public byte[] getPackedData(byte[] data, int length) {
		// TODO Auto-generated method stub
		
		Arrays.fill(packedData, (byte) 0);
		int len=length>11?11:length;
		
		packedData[0] = (byte) 0xff;
		
		
		System.arraycopy(data, 0, packedData, 1, len);
		int crc = CRC16.ComputeCRC16(packedData, 1, 11);

		byte crcLow = (byte) (crc & 0xff);
		packedData[12] = crcLow;
		packedData[13] = crcLow;

		byte crcHigh = (byte) (crc >> 8);
		packedData[14] = crcHigh;
		packedData[15] = crcHigh;
		
		return packedData;
	}
}
