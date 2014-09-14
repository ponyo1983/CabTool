package com.lon.cabtool;

import com.lon.cabtool.core.HostMachine;

public class HostInfo {

	final static String[] LampName = new String[] { "灭灯", "L", "LU", "U", "U2", "UU", "HU", "H", "B", };
	final static String[] ZsName = new String[] { "未知", "ZPW2000", "移频", "交流计数25HZ", "交流计数50HZ", "点式码", "TVM430", "保留", };
	final static String[][] SignalName = new String[][] {
			new String[] { "无码", "10.3", "11.4", "12.5", "13.6", "14.7", "15.8", "16.9", "18.0", "19.1", "20.2", "21.3", "22.4", "23.5", "24.6", "25.7",
					"26.8", "27.9", "29.0" },
			new String[] { "无码", "7.0", "8.0", "8.5", "9.0", "9.5", "11.0", "12.5", "13.5", "15.0", "16.5", "17.5", "18.5", "20.0", "21.5", "22.5", "23.5",
					"24.5", "26.0", },
			new String[] { "无码", "FDC1_L", "FDC1_U", "FDC1_HU", "A_L", "A_LU", "A_U", "A_UU", "A_HU", "FDC2_L", "FDC2_U", "FDC2_HU", "B_L", "B_LU", "B_U",
					"B_UU", "B_HU", }, };
	HostInfo infoGrp = null;
	String grpName = "";
	int index = -1;
	String key = "";
	byte[] infoData = new byte[16];

	public HostInfo(int index, String grpName) {
		this.index = index;
		this.grpName = grpName;
	}

	public HostInfo(int index, HostInfo grp, String key) {
		this.index = index;
		this.infoGrp = grp;
		this.key = key;
	}

	public boolean isGroup() {
		return (infoGrp == null);
	}

	public String getName() {
		if (infoGrp == null)
			return grpName;
		return key;
	}

	public int getImageId() {
		if (index <= 6)
			return R.drawable.cpu;
		else if (index <= 9)
			return R.drawable.temp;
		else if (index <= 16)
			return R.drawable.light;
		else if (index <= 23)
			return R.drawable.wave;
		return R.drawable.gear;
	}

	public String getState() {
		if (index >= 1 && index <= 6) {
			HostMachine.getInstance().getData(5, infoData);
			int state = (infoData[3] & 0x0ff) & (1 << (index - 1));
			return state > 0 ? "正常" : "故障";
		} else if (index >= 8 && index <= 9) {
			HostMachine.getInstance().getData(5, infoData);
			return "" + (infoData[index] & 0x0ff) + "℃";
		} else if (index >= 11 && index <= 16) {
			String status = "";
			HostMachine.getInstance().getData(0, infoData);
			if ((infoData[11] & 0x040) == 0) {
				HostMachine.getInstance().getData(1, infoData);
			}
			switch (index) {
			case 11:
				byte ledNum = infoData[3];
				status = HostInfo.LampName[ledNum & 0x00f];
				if ((ledNum & 0x10) > 0) {
					status += "S";
				}
				break;
			case 12: // 速度码
				byte sdCode = (byte) (infoData[4] & 0x007);
				status = ((sdCode & 0x01) != 0 ? "1" : "0") + ((sdCode & 0x02) != 0 ? "1" : "0") + ((sdCode & 0x04) != 0 ? "1" : "0");
				break;
			case 13: // 绝缘节
				byte jy = (byte) (infoData[4] & 0x008);
				status = jy != 0 ? "1" : "0";
				break;
			case 14: {
				int zs = (int) (infoData[4] & 0x0f0);
				status = HostInfo.ZsName[zs >> 4];
			}
				break;
			case 15: {
				int zs = (int) (infoData[4] & 0x0f0);
				int signalIndex = 0;
				int signal = infoData[5] & 0x0ff;
				signalIndex = (zs == 0x10) ? 0 : ((zs == 0x20) ? 1 : 2);
				if (signal < HostInfo.SignalName[signalIndex].length) {
					status = HostInfo.SignalName[signalIndex][signal];
				}
			}
				break;
			case 16:
				int sxx = (byte) (infoData[7] & 0x040);
				status = sxx == 0 ? "上行" : "下行";
				break;
			default:
				break;
			}
			return status;
		}
		else if(index>=18 && index<=23)
		{
			HostMachine.getInstance().getData(0, infoData);
			if ((infoData[11] & 0x040) == 0) {
				HostMachine.getInstance().getData(1, infoData);
			}
			int param = (infoData[7]&0x0ff) & (1 << (index-18));
			
			return param>0?"使能":"禁用";
		}
		return "";
	}

}
