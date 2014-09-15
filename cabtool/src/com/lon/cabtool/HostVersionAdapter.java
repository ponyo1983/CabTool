package com.lon.cabtool;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HostVersionAdapter extends BaseAdapter {

	Activity activity;
	List<HostVersion> list;

	public HostVersionAdapter(Activity activity, List<HostVersion> list) {
		this.activity = activity;
		this.list = list;
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
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		HostVersion info = list.get(position);

		view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.list_item_host_version, null);
		TextView text = (TextView) view.findViewById(R.id.addexam_list_item_text);
		String name = info.getName();
		text.setText(name+":");

		
		TextView version = (TextView) view.findViewById(R.id.host_version_content);
		
		version.setText(info.getDescriptor());
		
		ImageView img = (ImageView) view.findViewById(R.id.host_version_check);
		if (info.getSelected()) {
			img.setBackgroundResource(R.drawable.check);
		} else {
			img.setBackground(null);
		}
		return view;
	}

}