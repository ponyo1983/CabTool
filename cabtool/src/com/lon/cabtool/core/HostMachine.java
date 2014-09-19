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

	}

	public static HostMachine getInstance() {
		if (gMachine == null) {
			gMachine = new HostMachine();
		}
		return gMachine;
	}

	private void initInfos() {
		HostInfo grpRun = new HostInfo(infoList.size(), "�忨����״̬");
		infoList.add(grpRun);
		infoList.add(new HostInfo(infoList.size(), grpRun, "���ư�1"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "���ư�2"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "�����1"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "�����2"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "��¼��"));
		infoList.add(new HostInfo(infoList.size(), grpRun, "��ذ�"));

		HostInfo grpTemp = new HostInfo(infoList.size(), "�忨�¶�(��C)");
		infoList.add(grpTemp);
		infoList.add(new HostInfo(infoList.size(), grpTemp, "��¼��"));
		infoList.add(new HostInfo(infoList.size(), grpTemp, "��ذ�"));
		// ����״̬
		HostInfo grpLight = new HostInfo(infoList.size(), "����״̬");
		infoList.add(grpLight);
		infoList.add(new HostInfo(infoList.size(), grpLight, "����"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "�ٶ���"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "��Ե��"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "��ʽ"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "��Ƶ"));
		infoList.add(new HostInfo(infoList.size(), grpLight, "������"));
		// ����
		HostInfo grpDecode = new HostInfo(infoList.size(), "�������");
		infoList.add(grpDecode);
		infoList.add(new HostInfo(infoList.size(), grpDecode, "��������50Hz"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "��������25Hz"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "��Ƶ"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "����ʽ"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "ZPW2000"));
		infoList.add(new HostInfo(infoList.size(), grpDecode, "TVM300"));
		// ���ư�1
		HostInfo grpControl1 = new HostInfo(infoList.size(), "���ư�1����");
		infoList.add(grpControl1);
		infoList.add(new HostInfo(infoList.size(), grpControl1, "����״̬"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "���ý����"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "�������"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "������Ȧ"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "�����Ȧ"));
		infoList.add(new HostInfo(infoList.size(), grpControl1, "I/II��"));
		// ���ư�2
		HostInfo grpControl2 = new HostInfo(infoList.size(), "���ư�2����");
		infoList.add(grpControl2);
		infoList.add(new HostInfo(infoList.size(), grpControl2, "����״̬"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "���ý����"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "�������"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "������Ȧ"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "�����Ȧ"));
		infoList.add(new HostInfo(infoList.size(), grpControl2, "I/II��"));
	}

	private void initVersions() {
		versionList.add(new HostVersion("GPRS��"));
		versionList.add(new HostVersion("��ذ�-MSP1"));
		versionList.add(new HostVersion("��ذ�-MSP2"));
		versionList.add(new HostVersion("�����1-DSP1"));
		versionList.add(new HostVersion("�����1-DSP2"));
		versionList.add(new HostVersion("�����2-DSP1"));
		versionList.add(new HostVersion("�����2-DSP2"));
		versionList.add(new HostVersion("�����1-MSP1"));
		versionList.add(new HostVersion("�����1-MSP2"));
		versionList.add(new HostVersion("�����2-MSP1"));
		versionList.add(new HostVersion("�����2-MSP2"));
		versionList.add(new HostVersion("���ư�1-MSP��"));
		versionList.add(new HostVersion("���ư�1-MSP��"));
		versionList.add(new HostVersion("���ư�2-MSP��"));
		versionList.add(new HostVersion("���ư�2-MSP��"));
		versionList.add(new HostVersion("��¼��-DSP"));
		versionList.add(new HostVersion("��¼��-MSP"));
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

	// 0-A�� 1:-B��
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
		
		if(versionThread!=null && (versionThread.isAlive()))
		{
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

			int[][] filters = new int[][] { new int[] { 0xff, 0x05, 0x5d, }, new int[] { 0xff, 0x06, 0x5d, }, new int[] { 0xff, 0xfd, 0x1d, },// �汾��Ϣ֡
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
					if (ABSel[i] != -1) { // ����AB��
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
								int mainUse = ((data[5] &0x0ff)& 0x03);
								int checkUse = ((data[5] &0x0ff)& 0x0f) >> 2;
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
					// ��ʼ��ѯ
					monitor.disableFilter(0);
					monitor.disableFilter(1);
					monitor.enableFilter(2);
					monitor.disableFilter(3);
					monitor.disableFilter(4);

					monitor.clearFrame();
					reply = false;
					long startTime=System.currentTimeMillis();
					while(true)
					{
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
						long endTime=System.currentTimeMillis();
						if(endTime-startTime>10000)
						{
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
}
