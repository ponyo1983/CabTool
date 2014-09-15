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

		setTitle("主机版本");
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
		timer.schedule(task, 1000, 1000); // 延时1000ms后执行，1000ms执行一次
	
	
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
		switch (item.getItemId()) { // 响应每个菜单项(通过菜单项的ID)
		case R.id.action_get_version:
			HostMachine.getInstance().startQueryVersions();
			break;
		default: // 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		} // 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}


}
