package com.lon.cabtool.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

public class SerialManager {

	private static SerialManager sigletonManager;

	private UsbManager usbManager;


	private List<UsbSerialPort> usbList=new ArrayList<UsbSerialPort>();
	private List<Thread> rxThreads=new ArrayList<Thread>();
	private List<RxHandler> rxHandlers=new ArrayList<SerialManager.RxHandler>();
	private List<Thread> txThreads=new ArrayList<Thread>();
	private List<TxHandler> txHandlers=new ArrayList<SerialManager.TxHandler>();
	

	List<SerialProtocal> protocalList = new ArrayList<SerialProtocal>();

	
	
	
	private SerialManager(UsbManager usbManager) {
		this.usbManager = usbManager;
		protocalList.add(new HostProtocal());
	}

	public static void createInstance(UsbManager usbManager) {
		if (sigletonManager == null) {
			sigletonManager = new SerialManager(usbManager);
		}
	}

	public static SerialManager getInstance() {

		return sigletonManager;
	}

	public void reset() {

		for (UsbSerialPort port : usbList) {
			if (port != null) {
				try {
					port.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		usbList.clear();
		
		for(Thread rxThread:rxThreads)
		{
			if (rxThread != null && rxThread.isAlive()) {
				rxThread.interrupt();
			}
		}
		rxThreads.clear();
		rxHandlers.clear();
		for(Thread txThread:txThreads)
		{
			if (txThread != null && txThread.isAlive()) {
				txThread.interrupt();
			}
		}
		txThreads.clear();
		txHandlers.clear();
		
		final List<UsbSerialDriver> drivers = UsbSerialProber
				.getDefaultProber().findAllDrivers(usbManager);

		for (final UsbSerialDriver driver : drivers) {
			final List<UsbSerialPort> ports = driver.getPorts();

			for(UsbSerialPort port:ports)
			{
				UsbDeviceConnection connection = usbManager.openDevice(port.getDriver().getDevice());
				if (connection == null) continue;
				try {

					port.open(connection);
					port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1,UsbSerialPort.PARITY_NONE);
				
					RxHandler rxHandler=new RxHandler(port,usbList.size());
					Thread rxThread=new Thread(rxHandler);
					rxThread.start();
					rxThreads.add(rxThread);
					rxHandlers.add(rxHandler);
					
					TxHandler txHandler=new TxHandler(port,usbList.size());
					Thread txThread=new Thread(txHandler);
					txThread.start();
					txThreads.add(txThread);
					txHandlers.add(txHandler);
					
					usbList.add(port);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						port.close();
					} catch (IOException e2) {
						// Ignore.
					}
					e.printStackTrace();
				}
			}
		}
	}

	public SerialProtocal getDefaultProtocal() {
		if (protocalList.size() > 0) {
			return protocalList.get(0);
		}
		return null;
	}

	public void sendPackage(int port ,byte[] data,int offset,int length)
	{
		if(length<=0) return;
		byte[] sndData=new byte[length];
		System.arraycopy(data, offset, sndData, 0, length);
		for(TxHandler txHandler:txHandlers)
		{
			if(txHandler.getPort()==port)
			{
				txHandler.sendPackage(sndData);
				break;
			}
		}
	
	}
	

	private class RxHandler implements Runnable {

		UsbSerialPort port=null;
		int portIndex=0;
		public RxHandler(UsbSerialPort port,int portIndex)
		{
			this.port=port;
			this.portIndex=portIndex;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub

			byte[] readBuffer = new byte[1024];
			try {
				while (Thread.currentThread().isInterrupted() == false) {
					int length = port.read(readBuffer, 100000);
					if (length > 0) {
						synchronized (protocalList) {
							for (SerialProtocal protocal : protocalList) {
								protocal.parseFrame(portIndex,readBuffer,0, length);
							}
						}
						
					}

				}

			} catch (Exception ex) {
					ex.printStackTrace();
			}

		}

	}

	
	private class TxHandler implements Runnable{

		UsbSerialPort port=null;
		Queue<byte[]> sndList=new LinkedList<byte[]>();
		int portIndex=0;
		public TxHandler(UsbSerialPort port,int portIndex)
		{
			this.port=port;
			this.portIndex=portIndex;
		}
		
		public void sendPackage(byte[] sndData)
		{
			synchronized (sndList) {
				
				sndList.add(sndData);
				sndList.notify();
				
			}
		}
		public int getPort()
		{
			return portIndex;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(Thread.currentThread().isInterrupted()==false)
				{
					
					byte[] data=null;
					synchronized (sndList) {
						if(sndList.size()<=0)
						{
							sndList.wait();
						}
						if(sndList.size()>0)
						{
							data=sndList.remove();
						}
					}
					if(data!=null)
					{
						if(protocalList.size()>0)
						{
							byte[] sndData=protocalList.get(0).getPackedData(data, data.length);
							if(sndData!=null)
							{
								if (port != null) {
									try {
										port.write(sndData, 1000);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
	}
}
