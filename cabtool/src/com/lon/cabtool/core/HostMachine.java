package com.lon.cabtool.core;

import java.util.ArrayList;
import java.util.List;

import com.lon.cabtool.HostInfo;

public class HostMachine {

	static HostMachine gMachine = null;
	Thread feedbackThread;

	List<HostInfo> infoList = new ArrayList<HostInfo>();

	byte[][] infoDataList = new byte[][] { new byte[] { (byte) 0xff, 0x05, 0x5d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x06, 0x5d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x05, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x06, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x07, 0x3d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x07, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	private HostMachine() {
		HostInfo grpRun = new HostInfo(infoList.size(), "板卡运行状态");
		infoList.add(grpRun);
		infoList.add(new HostInfo(infoList.size(), grpRun, "控制板1"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "控制板2"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "解码板1"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "解码板2"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "记录板"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "监控板"));

		HostInfo grpTemp = new HostInfo(infoList.size(), "板卡温度(°C)");
		infoList.add(grpTemp);
		infoList.add(new HostInfo(infoList.size(), grpTemp, "记录板"));
		infoList.add(new HostInfo(infoList.size(), grpTemp, "监控板"));
		// 灯显状态
		HostInfo grpLight = new HostInfo(infoList.size(), "灯显状态");
		infoList.add(grpLight);
		infoList.add(new HostInfo(infoList.size(), grpLight, "灯显"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "速度码"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "绝缘节"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "制式"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "低频"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "上下行"));
		// 解码
		HostInfo grpDecode = new HostInfo(infoList.size(), "解码参数");
		infoList.add(grpDecode);
		infoList.add(new HostInfo(infoList.size(), grpDecode, "交流技术50Hz"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "交流技术25Hz"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "移频"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "点码式"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "ZPW2000"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "TVM300"));
		// 控制板1
		HostInfo grpControl1 = new HostInfo(infoList.size(), "控制板1连接");
		infoList.add(grpControl1);
		infoList.add(new HostInfo(infoList.size(), grpControl1, "主用状态"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "主用解码板"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "检查解码板"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "主用线圈"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "检查线圈"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "I/II端"));
		// 控制板2
		HostInfo grpControl2 = new HostInfo(infoList.size(), "控制板2连接");
		infoList.add(grpControl2);
		infoList.add(new HostInfo(infoList.size(), grpControl2, "主用状态"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "主用解码板"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "检查解码板"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "主用线圈"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "检查线圈"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "I/II端"));
	}

	public static HostMachine getInstance() {
		if (gMachine == null) {
			gMachine = new HostMachine();
		}
		return gMachine;
	}

	public List<HostInfo> getInfos() {
		return infoList;
	}

	public void start() {
		if ((feedbackThread == null) || (feedbackThread.isAlive() == false)) {
			feedbackThread = new Thread(new Feedback());
			feedbackThread.start();
		}
	}

	public void stop() {

	}

	private void updateData(byte[] data)
	{
		synchronized (infoDataList) {
			
			for(byte[] info:infoDataList)
			{
				if((info[0]==data[0]) &&(info[1]==data[1]) &&(info[2]==data[2]))
				{
					System.arraycopy(data, 3, info, 3, 13);
				}
			}
			
		}
	}
	
	public void getData(int index ,byte[] data)
	{
		synchronized (infoDataList)
		{
		   System.arraycopy(infoDataList[index], 0, data, 0,data.length);
		}
		
	}
	
	
	private class Feedback implements Runnable {



		@Override
		public void run() {
			// TODO Auto-generated method stub
			byte[] feedback = new byte[7];
			FrameMonitor monitor = new FrameMonitor();

			SerialManager.getInstance().getDefaultProtocal().addFrameMonitor(monitor);
			try {

				while (Thread.currentThread().isInterrupted() == false) {

					SerialFrame frame = monitor.getFrame(1000);
					if (frame != null) {
						byte[] data = frame.getData();

						
						HostMachine.this.updateData(data);
						
						
						if ((data[1] == 0x05 || data[1] == 0x06) && (data[2] == 0x5d)) {
							feedback[0] = 0x09;
							feedback[1] = 0x1d;
							feedback[2] = data[3];
							feedback[3] = data[4];
							feedback[4] = data[9];
							feedback[5] = data[10];
							feedback[6] = 0;

							SerialManager.getInstance().sendPackage(feedback, 0, feedback.length);
						}

					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}
}
