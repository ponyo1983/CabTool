package com.lon.cabtool;


public class HostVersion {
	
	String name="";
	boolean selected=true;
	int version=-1;
	int year=-1;
	int month=-1;
	int day=-1;
	
	public HostVersion(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return name;
	}
	public boolean getSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean sel)
	{
		this.selected=sel;
	}
	
	public void setVersion(int version)
	{
		this.version=version;
	}
	
	public void setYear(int year)
	{
		this.year=year;
	}
	public void setMonth(int month)
	{
		this.month=month;
	}
	public void setDay(int day)
	{
		this.day=day;
	}
	
	public void reset()
	{
		version=-1;
		year=-1;
		month=-1;
		day=-1;
	}
	
	public String getDescriptor()
	{
		if(version==-1) return "";
		
		StringBuilder sb=new StringBuilder();
		sb.append("V2.1.3."+version+"  ");
		if(year>0)
		{
			sb.append(year+"Äê");
		}
		if(month>0)
		{
			sb.append(month+"ÔÂ");
		}
		if(day>0)
		{
			sb.append(day+"ÈÕ");
		}
		return sb.toString();
	}
}
