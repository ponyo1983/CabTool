package com.lon.cabtool.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lon.cabtool.HostInfo;
import com.lon.cabtool.HostParam;
import com.lon.cabtool.HostVersion;

public class HostMachine {

	static HostMachine gMachine = null;
	Thread feedbackThread;
	Thread versionThread;

	Thread paramReadThread;

	List<HostInfo> infoList = new ArrayList<HostInfo>();

	List<HostVersion> versionList = new ArrayList<HostVersion>();

	boolean[] paramSel=new boolean[]{true,true,true,true,};
	HashMap<String, List<HostParam>> paramHash = new HashMap<String, List<HostParam>>();

	int abMode = -1;
	int mainPort = -1;

	byte[][] infoDataList = new byte[][] { new byte[] { (byte) 0xff, 0x05, 0x5d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x06, 0x5d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x05, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x06, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x07, 0x3d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { (byte) 0xff, 0x07, 0x7d, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	private HostMachine() {
		initInfos();
		initVersions();
		initParams();
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

	private void initParams() {
		for (int i = 0; i < 4; i++) {
			List<HostParam> list = new ArrayList<HostParam>();
			list.add(new HostParam("温度系数", 0));
			list.add(new HostParam("25Hz", 0));
			list.add(new HostParam("50Hz", 0));
			list.add(new HostParam("550Hz", 0));
			list.add(new HostParam("650Hz", 0));
			list.add(new HostParam("750Hz", 0));
			list.add(new HostParam("850Hz", 0));
			list.add(new HostParam("1700Hz", 0));
			list.add(new HostParam("2000Hz", 0));
			list.add(new HostParam("2300Hz", 0));
			list.add(new HostParam("2600Hz", 0));
			paramHash.put(Integer.toString(i), list);
		}
	}

	public List<HostParam> getParams(int index) {
		String key = Integer.toString(index);
		return paramHash.get(key);
	}
	
	public void clearParam(int index)
	{
		if(index>=0 && index<4)
		{
			String key = Integer.toString(index);
			List<HostParam> params=paramHash.get(key);
			
			for(HostParam param:params)
			{
				param.setParam(0);
			}
		}
	}
	
	public void setParamSel(int index,boolean sel)
	{
		if(index>=0&&index<4)
		{
			paramSel[index]=sel;
		}
	}
	public boolean getParamSel(int index)
	{
		if(index>=0&&index<4)
		{
			return paramSel[index];
		}
		return false;
	}
	

	public void set06Standard() {
		for (int i = 0; i < 4; i++) {
			List<HostParam> params = paramHash.get(Integer.toString(i));
			params.get(1).setParam(207);
			params.get(2).setParam(220);
			params.get(3).setParam(299);
			params.get(4).setParam(308);
			params.get(5).setParam(317);
			params.get(6).setParam(317);
			params.get(7).setParam(309);
			params.get(8).setParam(307);
			params.get(9).setParam(297);
			params.get(10).setParam(272);
		}

	}

	public List<HostInfo> getInfos() {
		return infoList;
	}

	public List<HostVersion> getVersions() {
		return versionList;
	}

	public int getABMode() {
		return abMode;
	}

	// 0-A套 1:-B套
	public void setABMode(int mode) {
		this.abMode = mode;
		this.mainPort = -1;
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

		if (versionThread != null && (versionThread.isAlive())) {
			versionThread.interrupt();
			try {
				versionThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		versionThread = new Thread(new QueryVersion());
		versionThread.start();
	}

	public void startQueryParams() {
		if (paramReadThread != null && (paramReadThread.isAlive())) {
			paramReadThread.interrupt();
			try {
				paramReadThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		paramReadThread = new Thread(new ParamReadHandler());
		paramReadThread.start();
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

						if (data[2] == 0x5d) {
							if (abMode < 0) {
								if (((data[11] & 0x040) == 0x040)) {
									abMode = (data[1] == 0x05) ? 0 : ((data[1] == 0x06) ? 1 : -1);
								}
							}
							if (((abMode == 0) && (data[1] == 0x05)) || ((abMode == 1) && (data[1] == 0x06))) {
								feedback[0] = 0x09;
								feedback[1] = 0x1d;
								feedback[2] = data[3];
								feedback[3] = data[4];
								feedback[4] = data[9];
								feedback[5] = data[10];
								feedback[6] = 0;
								mainPort = frame.getPort();
								SerialManager.getInstance().sendPackage(frame.getPort(), feedback, 0, feedback.length);
							}
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
		SerialManager.getInstance().sendPackage(mainPort, data, 0, data.length);
	}

	private void xhkReadE2PROM(byte jkkNum, byte xhkNum, byte addr) {

		byte[] data = new byte[10];
		data[0] = 0x0a;
		data[1] = 0x1d;
		data[2] = (byte) (xhkNum | 0x038);
		data[3] = (byte) 0x55;
		data[4] = addr;
		data[5] = 0;
		data[6] = 0;
		data[7] = 0;
		data[8] = 0;
		data[9] = (byte) (((data[2] & 0x0ff) + (data[3] & 0x0ff) + (data[4] & 0x0ff)) & 0x07f);

		SerialManager.getInstance().sendPackage(mainPort, data, 0, data.length);

	}

	private void queryVersion(byte boardNum, byte jkkNum) {

		byte[] data = new byte[3];
		data[0] = (byte) 0xfd;
		data[1] = (byte) 0x1d;
		data[2] = boardNum;
		SerialManager.getInstance().sendPackage(mainPort, data, 0, data.length);
	}

	private class QueryVersion implements Runnable {

		byte[] BoardNum = new byte[] { 1, 2, 3, 4, 6, 8, 10, 5, 7, 9, 11, 12, 14, 13, 15, 16, 17, };
		int[] ABSel = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, +0, +1, +0, +1, -1, -1, };
		int[] JMSel = new int[] { -1, -1, -1, +0, +1, +2, +3, +0, +1, +2, +3, -1, -1, -1, -1, -1, -1, };

		boolean[] NeedYear = new boolean[] { false, false, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, };

		@Override
		public void run() {
			// TODO Auto-generated method stub

			int[][] filters = new int[][] { new int[] { 0xff, 0x05, 0x5d, }, new int[] { 0xff, 0x06, 0x5d, }, new int[] { 0xff, 0xfd, 0x1d, },// 版本信息帧
					new int[] { 0xff, 0x05, 0x7d, }, new int[] { 0xff, 0x06, 0x7d, }, };

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
					if (ver.getSelected() == false)
						continue;
					mainJKK = HostMachine.this.getABMode();
					if (ABSel[i] != -1) { // 设置AB套
						mainJKK = ABSel[i];
						monitor.enableFilter(mainJKK);
						monitor.disableFilter(1 - mainJKK);
						monitor.disableFilter(2);
						monitor.disableFilter(3);
						monitor.disableFilter(4);
						monitor.clearFrame();
						HostMachine.this.setABMode(mainJKK);
						long startTime = System.currentTimeMillis();
						while (true) {
							SerialFrame frame = monitor.getFrame(1000);
							if (frame != null) {
								byte[] data = frame.getData();
								if ((data[11] & 0x040) == 0x040) {
									break;
								}
							}
							long endTime = System.currentTimeMillis();
							if (endTime - startTime > 6000) {
								break;
							}
						}

					} else {
						monitor.enableFilter(0);
						monitor.enableFilter(1);
						monitor.disableFilter(2);
						monitor.disableFilter(3);
						monitor.disableFilter(4);
						monitor.clearFrame();
						long startTime = System.currentTimeMillis();
						while (true) {
							SerialFrame frame = monitor.getFrame(1000);
							if (frame != null) {
								byte[] data = frame.getData();
								if ((data[11] & 0x040) == 0x040) {
									mainJKK = (data[1] == 0x05) ? 0 : ((data[1] == 0x06) ? 1 : -1);
									break;
								}
							}
							long endTime = System.currentTimeMillis();
							if (endTime - startTime > 3000) {
								break;
							}
						}

					}

					if (JMSel[i] != -1) {
						monitor.disableFilter(0);
						monitor.disableFilter(1);
						monitor.disableFilter(2);
						int index = (mainJKK == 0) ? 0 : 1;
						monitor.enableFilter(3 + index);
						monitor.disableFilter(3 + (1 - index));
						monitor.clearFrame();
						long startTime = System.currentTimeMillis();
						while (true) {
							HostMachine.this.jkkDebug((byte) index, (byte) 0x55, (byte) JMSel[i], (byte) JMSel[i], (byte) 1);
							SerialFrame frame = monitor.getFrame(1000);

							if (frame != null) {
								byte[] data = frame.getData();
								int mainUse = ((data[5] & 0x0ff) & 0x03);
								int checkUse = ((data[5] & 0x0ff) & 0x0f) >> 2;
								if ((mainUse == JMSel[i]) && (checkUse == JMSel[i])) {

									break;
								}
							}
							long endTime = System.currentTimeMillis();
							if (endTime - startTime > 5000) {
								break;
							}
						}

					}

					Thread.currentThread().sleep(2000);
					// 开始查询
					monitor.disableFilter(0);
					monitor.disableFilter(1);
					monitor.enableFilter(2);
					monitor.disableFilter(3);
					monitor.disableFilter(4);

					monitor.clearFrame();
					reply = false;
					long startTime = System.currentTimeMillis();
					while (true) {
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
						long endTime = System.currentTimeMillis();
						if (endTime - startTime > 10000) {
							break;
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

	private class ParamReadHandler implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			FrameMonitor monitor = new FrameMonitor();
			monitor.addFilter(new int[] { 0xff, 0x0a, 0x1d, });

			SerialManager.getInstance().getDefaultProtocal().addFrameMonitor(monitor);

			for (byte i = 0; i < 4; i++)
			{
				boolean sel=HostMachine.this.getParamSel(i);
				if(sel)
				{
					HostMachine.this.clearParam(i);
				}
			}
			
			try {

				int ab=HostMachine.this.getABMode();
				if(ab<0)
				{
					ab=0;
					HostMachine.this.setABMode(0);
				}
				byte jkk=(byte)ab;
				jkk=(jkk==0)?(byte)0:(byte)1;
				for (byte i = 0; i < 4; i++) {
					List<HostParam> params=HostMachine.this.getParams(i);
					for (byte j = 1; j < 12; j++) {
						long startTime = System.currentTimeMillis();
						while (true) {
							HostMachine.this.jkkDebug(jkk, (byte) 0x55, i, i, (byte) 1);
							Thread.currentThread().sleep(200);

							HostMachine.this.xhkReadE2PROM(jkk, i, j);
							
							SerialFrame frame=monitor.getFrame(500);
							if(frame!=null)
							{
								byte[] data=frame.getData();
								 if (((data[3] & 0x0fc) == 0x038) && ((data[4]&0x0ff) == 0x055))
	                                {
	                                    byte addr = data[5];
	                                    int paramVal = ((data[6]&0x0ff) + ((data[7]&0x0ff) << 8));
	                                    if (addr == j)
	                                    {
	                                    	params.get(j-1).setParam(paramVal);
	                                        break;
	                                    }
	                                }
							}
							
							long endTime = System.currentTimeMillis();
							if (endTime - startTime > 15000) {
								break;
							}
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				SerialManager.getInstance().getDefaultProtocal().removeFrameMonitor(monitor);
			}

		}

	}

}
