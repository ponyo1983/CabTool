package com.lon.cabtool.core;

import java.util.ArrayList;
import java.util.List;

import com.lon.cabtool.HostInfo;
import com.lon.cabtool.HostVersion;

public class HostMachine {

	static HostMachine gMachine = null;
	Thread feedbackThread;
	Thread versionThread;
	List<HostInfo> infoList = new ArrayList<HostInfo>();

	List<HostVersion> versionList = new ArrayList<HostVersion>();

	byte[][] infoDataList = new byte[][] { new byte[] { (byte) 0xff, 0x05, 0x5d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x06, 0x5d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x05, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x06, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x07, 0x3d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x07, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	private HostMachine() {
		initInfos();
		initVersions();

	}

	public static HostMachine getInstance() {
		if (gMachine == null) {
			gMachine = new HostMachine();
		}
		return gMachine;
	}

	private void initInfos() {
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

	private void initVersions() {
		versionList.add(new HostVersion("GPRS板"));
		versionList.add(new HostVersion("监控板-MSP1"));
		versionList.add(new HostVersion("监控板-MSP2"));
		versionList.add(new HostVersion("解码板1-DSP1"));
		versionList.add(new HostVersion("解码板1-DSP2"));
		versionList.add(new HostVersion("解码板2-DSP1"));
		versionList.add(new HostVersion("解码板2-DSP2"));
		versionList.add(new HostVersion("解码板1-MSP1"));
		versionList.add(new HostVersion("解码板1-MSP2"));
		versionList.add(new HostVersion("解码板2-MSP1"));
		versionList.add(new HostVersion("解码板2-MSP2"));
		versionList.add(new HostVersion("控制板1-MSP主"));
		versionList.add(new HostVersion("控制板1-MSP副"));
		versionList.add(new HostVersion("控制板2-MSP主"));
		versionList.add(new HostVersion("控制板2-MSP副"));
		versionList.add(new HostVersion("记录板-DSP"));
		versionList.add(new HostVersion("记录板-MSP"));
	}

	public List<HostInfo> getInfos() {
		return infoList;
	}

	public List<HostVersion> getVersions() {
		return versionList;
	}

	public void start() {
		if ((feedbackThread == null) || (feedbackThread.isAlive() == false)) {
			feedbackThread = new Thread(new Feedback());
			feedbackThread.start();
		}
	}

	public void stop() {

	}

	public void startQueryVersions() {
		if ((versionThread == null) || (versionThread.isAlive() == false)) {
			versionThread = new Thread(new QueryVersion());
			versionThread.start();
		}
	}

	private void updateData(byte[] data) {
		synchronized (infoDataList) {

			for (byte[] info : infoDataList) {
				if ((info[0] == data[0]) && (info[1] == data[1]) && (info[2] == data[2])) {
					System.arraycopy(data, 3, info, 3, 13);
				}
			}

		}
	}

	public void getData(int index, byte[] data) {
		synchronized (infoDataList) {
			System.arraycopy(infoDataList[index], 0, data, 0, data.length);
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

	private void jkkDebug(byte jkkNum, byte controlType, byte mainXhk, byte checkXhk, byte state) {

		byte[] data = new byte[7];
		data[0] = 0x0a;
		data[1] = 0x3d;
		data[2] = (byte) (jkkNum & 0x03);
		data[3] = controlType;
		data[4] = 0;
		data[5] = 0;
		data[6] = (byte) (mainXhk + ((checkXhk & 0x0ff) << 2) + ((state & 0x0ff) << 6));
		SerialManager.getInstance().sendPackage(data, 0, data.length);
	}

	private void queryVersion(byte boardNum, byte jkkNum) {

		byte[] data = new byte[3];
		data[0] = (byte) 0xfd;
		data[1] = (byte) 0x1d;
		data[2] = boardNum;
		SerialManager.getInstance().sendPackage(data, 0, data.length);
	}

	private class QueryVersion implements Runnable {

		byte[] BoardNum = new byte[] { 1, 2, 3, 4, 6, 8, 10, 5, 7, 9, 11, 12, 14, 13, 15, 16, 17, };
		int[] ABSel = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, +0, +1, +0, +1, -1, -1, };
		int[] JMSel = new int[] { -1, -1, -1, +0, +1, +2, +3, +0, +1, +2, +3, -1, -1, -1, -1, -1, -1, };

		boolean[] NeedYear = new boolean[] { false, false, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, };

		@Override
		public void run() {
			// TODO Auto-generated method stub

			int[][] filters = new int[][] { 
					new int[] { 0xff, 0x05, 0x5d, }, 
					new int[] { 0xff, 0x06, 0x5d, }, 
					new int[] { 0xff, 0xfd, 0x1d, },// 版本信息帧
					new int[] { 0xff, 0x05, 0x7d, }, 
					new int[] { 0xff, 0x06, 0x7d, }, };

			FrameMonitor monitor = new FrameMonitor();
			monitor.addFilter(filters);
			SerialManager.getInstance().getDefaultProtocal().addFrameMonitor(monitor);
			boolean reply = false;
			try {

				for (byte i = 0; i < 17; i++) {
					HostVersion ver = versionList.get(i);
					ver.reset();
				}
				int mainJKK = 0;
				for (byte i = 0; i < 17; i++) {
					HostVersion ver = versionList.get(i);
					if(ver.getSelected()==false) continue;
					if (ABSel[i] != -1) { // 设置AB套
						mainJKK = ABSel[i];
					} else {

					}

					if (JMSel[i] != -1) {
						HostMachine.this.jkkDebug((byte) mainJKK, (byte) 0x55, (byte) JMSel[i], (byte) JMSel[i], (byte) 1);
					}

					// 开始查询
					monitor.disableFilter(0);
					monitor.disableFilter(1);
					monitor.enableFilter(2);
					monitor.disableFilter(3);
					monitor.disableFilter(4);
					
					monitor.clearFrame();
					reply = false;
					for (int j = 0; j < 5; j++) {
						HostMachine.this.queryVersion(BoardNum[i], (byte) mainJKK);

						SerialFrame frame = monitor.getFrame(1000);
						if (frame != null) {
							byte[] data = frame.getData();
							if (data[3] == BoardNum[i]) {
								reply = true;
								ver.setVersion(data[4]);
								int year = data[5] & 0x0ff;
								year = (year >> 4) * 10 + (year & 0x0f);
								if (NeedYear[i] == false) {
									ver.setYear(year);
								}
								int month = data[6] & 0x0ff;
								month = (month >> 4) * 10 + (month & 0x0f);
								ver.setMonth(month);

								int day = data[7] & 0x0ff;
								day = (day >> 4) * 10 + (day & 0x0f);
								ver.setDay(day);
								break;
							}
						}
					}
					if (reply == false)
						continue;

					if (NeedYear[i]) {
						reply = false;
						for (int j = 0; j < 5; j++) {
							HostMachine.this.queryVersion((byte) (BoardNum[i] - 1), (byte) mainJKK);

							SerialFrame frame = monitor.getFrame(1000);
							if (frame != null) {
								byte[] data = frame.getData();
								if (data[3] == BoardNum[i] - 1) {
									reply = true;
									int year = data[5] & 0x0ff;
									year = (year >> 4) * 10 + (year & 0x0f);
									ver.setYear(year);
									break;
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
