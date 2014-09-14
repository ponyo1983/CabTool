package com.lon.cabtool;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lon.cabtool.core.HostMachine;
import com.lon.cabtool.core.SerialManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ListView;

public class HostInfoActivity extends Activity {

	private ListView listview;
	HostInfoAdapter adapter;
	Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_host_info);
		this.setTitle("主机信息");
		UsbManager mUsbManager;
		mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		SerialManager.createInstance(mUsbManager);
		SerialManager.getInstance().reset();

		HostMachine.getInstance().start();

		listview = (ListView) findViewById(R.id.listView_list);

		List<HostInfo> list = HostMachine.getInstance().getInfos();
		adapter = new HostInfoAdapter(this, list);
		listview.setAdapter(adapter);

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
		getMenuInflater().inflate(R.menu.host_info, menu);
		return true;
	}

}
