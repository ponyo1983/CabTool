package com.lon.cabtool;


public class HostParam {

	private String name="";
	boolean selected=true;
	int param=-1;
	
	int state=0; //1-读参数,2-读参数OK，3-读参数Fail ，-1-写参数 ,-2,写参数OK,-3写参数失败
	
	public HostParam(String name,int param)
	{
		this.name=name;
		this.param=param;
	}
	
	public String getName()
	{
		return name;
	}
	public int getParam()
	{
		return param;
	}
	public void setParam(int param)
	{
		this.param=param;
		
	}
	public boolean getSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean sel)
	{
		this.selected=sel;
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
	{
		this.state=state;
	}
}
