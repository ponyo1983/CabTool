package com.lon.cabtool;


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 public class HostInfoAdapter extends BaseAdapter{  
	  
	 Activity activity;
	 List<HostInfo> list;
	public HostInfoAdapter(Activity activity,List<HostInfo> list)
	 {
		 this.activity=activity;
		 this.list=list;
	 }
	 

    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return list.size();  
    }  

    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return list.get(position);  
    }  

    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    }  
    @Override  
    public boolean isEnabled(int position) {  
        // TODO Auto-generated method stub  
         if(list.get(position).isGroup()) return false;
         return super.isEnabled(position);  
    }  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        View view=convertView;  
        HostInfo info=list.get(position);
        if(info.isGroup()){  
            view=LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.list_item_host_info_grp, null);  
        }else{  
            view=LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.list_item_host_info, null);  
            ImageView img=(ImageView)view.findViewById(R.id.addexam_list_icon);
            img.setBackgroundResource(info.getImageId());
        }  
        TextView text=(TextView) view.findViewById(R.id.addexam_list_item_text);  
        String name=info.getName();
        if(info.isGroup()==false)
        {
        	name+=(":"+info.getState());
        }
        text.setText(name);  
        return view;  
    }  
      
}  

