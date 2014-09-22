package com.lon.cabtool;

import java.text.NumberFormat;

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
		else if(index>=39 && index<=42) //TAX2箱信息
		{
			HostMachine.getInstance().getData(6, infoData);
			String info="";
			switch(index)
			{
			case 39:
				 int year = ((infoData[7] & 0x001) << 6) | ((infoData[6]&0x0ff) >> 2);
	             int month = ((infoData[6] & 0x003) << 2) + ((infoData[5]&0x0ff) >> 6);
	             int day = ((infoData[5]&0x0ff) >> 1) & 0x01f;
	             NumberFormat nf=NumberFormat.getNumberInstance();
	             {
	             StringBuilder sb=new StringBuilder();
	             if(year<10)
	             {
	            	 sb.append("0");
	             }
	             sb.append(Integer.toString(year)+"年");
	             if(month<10)
	             {
	            	 sb.append("0");
	             }
	             sb.append(Integer.toString(month)+"月");
	             if(day<10)
	             {
	            	 sb.append("0");
	             }
	             sb.append(Integer.toString(day)+"日");
	             info=sb.toString();
	             }
				break;
			case 40:
				 int hour = ((infoData[5] & 0x001) << 4) | ((infoData[4]&0x0ff) >> 4);
                 int minute = ((infoData[4] & 0x00f) << 2) | ((infoData[3]&0x0ff) >> 6);
                 int second = (infoData[3] & 0x03f);
                 {
    	             StringBuilder sb=new StringBuilder();
    	             if(hour<10)
    	             {
    	            	 sb.append("0");
    	             }
    	             sb.append(Integer.toString(hour)+":");
    	             if(minute<10)
    	             {
    	            	 sb.append("0");
    	             }
    	             sb.append(Integer.toString(minute)+":");
    	             if(second<10)
    	             {
    	            	 sb.append("0");
    	             }
    	             sb.append(Integer.toString(second));
    	             info=sb.toString();
    	             }
				break;
			case 41:
				 int cf = ((infoData[7]&0x0ff) >> 1) | ((infoData[8]&0x0ff) << 6);
				 info=Integer.toString(cf)+"MB";
				break;
			case 42:
			     int tempe = infoData[9];
			     info=Integer.toString(tempe)+"℃";
			     break;
			}
			
             return info;
		}
		else if(index>=43 && index<=46) //TAX2箱信息
		{
			HostMachine.getInstance().getData(7, infoData);
			String info="";
			switch(index)
			{
			case 43:
				 int speed = (infoData[3]&0x0ff) + ((infoData[4]&0x0ff) << 8) + ((infoData[5]&0x0ff) << 16);
				 info=Integer.toString(speed)+"m/s";
				break;
			case 44:
				 int xhjType=infoData[6]&0x0ff;
				 info=Integer.toString(xhjType);
				break;
			case 45:
				 int xhjNum = (infoData[7]&0x0ff) + ((infoData[8]&0x0ff) << 8);
				 info=Integer.toString(xhjNum);
				break;
			case 46:
				 int section=infoData[6]&0x0ff;
				 info=Integer.toString(section);
				break;
			}
			return info;
		}
		else if(index>=47&& index<=50)
		{
			HostMachine.getInstance().getData(8, infoData);
			String info="";
			switch(index)
			{
			case 47:
				int miles = ((infoData[3]&0x0ff) | ((infoData[10] & 0x001) << 7)) | (((infoData[4]&0x0ff) | ((infoData[10] & 0x002) << 6)) << 8) | (((infoData[5]&0x0ff) | ((infoData[10] & 0x004) << 5)) << 16);
				 info=Integer.toString(miles);
				break;
			case 48:
				 int bb = ((infoData[6]&0x0ff) | ((infoData[10] & 0x008) << 4));
				 info=Integer.toString(bb);
				break;
			case 49:
				 int stationNum  = ((infoData[7]&0x0ff) | ((infoData[10] & 0x010) << 3));
				 info=Integer.toString(stationNum);
				break;
			case 50:
				 int locoNum = ((infoData[8]&0x0ff) | ((infoData[10] & 0x020) << 2)) + (((infoData[9]&0x0ff) | ((infoData[10] & 0x040) << 1)) << 8);
				 info=Integer.toString(locoNum);
				break;
			}
			return info;
		}
		return "";
	}

}
