package com.lon.cabtool;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lon.cabtool.core.HostMachine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HostVersionActivity extends Activity {

	private ListView listview;
	HostVersionAdapter adapter;
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_version);

		setTitle("�����汾");
		listview = (ListView) findViewById(R.id.listView_list);

		List<HostVersion> list = HostMachine.getInstance().getVersions();
		adapter = new HostVersionAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				HostVersion version=(HostVersion) adapter.getItem(arg2);
				version.setSelected(!version.getSelected());
				adapter.notifyDataSetChanged();
			}
		});
		
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		TimerTask task = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};

		timer = new Timer(true);
		timer.schedule(task, 1000, 1000); // ��ʱ1000ms��ִ�У�1000msִ��һ��
	
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host_version, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) { // ��Ӧÿ���˵���(ͨ���˵����ID)
		case R.id.action_get_version:
			HostMachine.getInstance().startQueryVersions();
			break;
		default: // ��û�д�����¼�����������������
			return super.onOptionsItemSelected(item);
		} // ����true��ʾ������˵�����¼�������Ҫ�����¼�����������ȥ��
		return true;
	}


}
