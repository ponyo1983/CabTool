package com.lon.cabtool;


public class HostParam {

	private String name="";
	boolean selected=true;
	int param=-1;
	
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
}
