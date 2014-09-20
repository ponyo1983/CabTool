package com.lon.cabtool;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HostParamAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<HostParam> list;

	public HostParamAdapter(LayoutInflater inflater, List<HostParam> list) {
		this.inflater = inflater;
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
		HostParam param = list.get(position);

		view = inflater.inflate(R.layout.list_item_host_param, null);
		TextView text = (TextView) view.findViewById(R.id.addexam_list_item_text);
		String name = param.getName();
		text.setText(name + ":");

		TextView version = (TextView) view.findViewById(R.id.host_version_content);

		if (param.getParam() > 0) {
			version.setText(Integer.toString(param.getParam()));
		} else {
			version.setText("");
		}
		ImageView img = (ImageView) view.findViewById(R.id.addexam_list_icon);
		img.setBackgroundResource(R.drawable.params);

		TextView stateView = (TextView) view.findViewById(R.id.host_version_state);

		int state = param.getState();
		switch (state) {
		case 1:
			stateView.setText("读取中...");
			stateView.setTextColor(Color.BLACK);
			break;
		case 2:
			stateView.setText("读取成功");
			stateView.setTextColor(Color.GREEN);
			break;
		case 3:
			stateView.setText("读取失败");
			stateView.setTextColor(Color.RED);
			break;
		case -1:
			stateView.setText("写入中...");
			stateView.setTextColor(Color.BLACK);
			break;
		case -2:
			stateView.setText("写入成功");
			stateView.setTextColor(Color.GREEN);
			break;
		case -3:
			stateView.setText("写入失败");
			stateView.setTextColor(Color.RED);
			break;
		default:
			stateView.setText("");
			stateView.setTextColor(Color.BLACK);
			break;
		}

		return view;
	}

}