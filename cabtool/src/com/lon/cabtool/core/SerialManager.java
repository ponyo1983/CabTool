package com.lon.cabtool.core;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.lon.cabtool.util.CRC16;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

public class SerialManager {

	private static SerialManager sigletonManager;

	private UsbManager usbManager;

	private UsbSerialPort usbPort;

	Thread rxThread;
	List<SerialProtocal> listProtocals = new ArrayList<SerialProtocal>();

	Thread txThread;
	Queue<byte[]> sndList=new LinkedList<byte[]>();
	
	private SerialManager(UsbManager usbManager) {
		this.usbManager = usbManager;
		listProtocals.add(new HostProtocal());
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

		if (usbPort != null) {
			try {
				usbPort.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rxThread != null && rxThread.isAlive()) {
			rxThread.interrupt();
		}
		if (txThread != null && txThread.isAlive()) {
			txThread.interrupt();
		}
		final List<UsbSerialDriver> drivers = UsbSerialProber
				.getDefaultProber().findAllDrivers(usbManager);

		for (final UsbSerialDriver driver : drivers) {
			final List<UsbSerialPort> ports = driver.getPorts();

			int cnt = ports.size();
			if (cnt > 0) {
				usbPort = ports.get(0);
				UsbDeviceConnection connection = usbManager.openDevice(usbPort
						.getDriver().getDevice());
				if (connection == null)
					return;
				try {

					usbPort.open(connection);
					usbPort.setParameters(9600, 8, UsbSerialPort.STOPBITS_1,
							UsbSerialPort.PARITY_NONE);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						usbPort.close();
					} catch (IOException e2) {
						// Ignore.
					}
					e.printStackTrace();
					return;
				}

				rxThread = new Thread(new RxHandler());
				rxThread.start();

				txThread = new Thread(new TxHandler());
				txThread.start();
				break;
			}

		}
	}

	public SerialProtocal getDefaultProtocal() {
		if (listProtocals.size() > 0) {
			return listProtocals.get(0);
		}
		return null;
	}

	public void sendPackage(byte[] data,int offset,int length)
	{
		if(length<=0) return;
		byte[] sndData=new byte[length];
		System.arraycopy(data, offset, sndData, 0, length);
		synchronized (sndList) {
			
			sndList.add(sndData);
			sndList.notify();
			
		}
	}
	

	private class RxHandler implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			byte[] readBuffer = new byte[1024];
			try {
				while (Thread.currentThread().isInterrupted() == false) {
					int length = usbPort.read(readBuffer, 100000);
					if (length > 0) {
						for (SerialProtocal protocal : listProtocals) {
							protocal.parseFrame(readBuffer,0, length);
						}
					}

				}

			} catch (Exception ex) {
					ex.printStackTrace();
			}

		}

	}

	
	private class TxHandler implements Runnable{

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
						if(listProtocals.size()>0)
						{
							byte[] sndData=listProtocals.get(0).getPackedData(data, data.length);
							if(sndData!=null)
							{
								if (usbPort != null) {
									try {
										usbPort.write(sndData, 1000);
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
