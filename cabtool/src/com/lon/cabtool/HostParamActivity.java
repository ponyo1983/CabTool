package com.lon.cabtool;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.lon.cabtool.core.HostMachine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class HostParamActivity extends FragmentActivity {

	private String[] cardName = new String[] { "解码板1-1", "解码板1-2", "解码板2-1", "解码板2-2" };
	private boolean[] cardSel = new boolean[] { true, true, true, true, };
	private ListView areaCheckListView;
	
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		setTitle("主机参数");
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) { // 响应每个菜单项(通过菜单项的ID)
		case R.id.action_settings: {

			AlertDialog ad = new AlertDialog.Builder(HostParamActivity.this).setTitle("选择板卡")
					.setMultiChoiceItems(cardName, cardSel, new DialogInterface.OnMultiChoiceClickListener() {
						public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
							// 点击某个区域
						}
					}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {

							for (int i = 0; i < cardName.length; i++) {
								if (areaCheckListView.getCheckedItemPositions().get(i)) {
										HostMachine.getInstance().setParamSel(i, true);
								} else {
									areaCheckListView.getCheckedItemPositions().get(i, false);
									HostMachine.getInstance().setParamSel(i, false);
								}
							}
							
							dialog.dismiss();
						}
					}).setNegativeButton("取消", null).create();
			areaCheckListView = ad.getListView();
			ad.show();

		}
			break;
		case R.id.action_param_read:
			HostMachine.getInstance().startQueryParams();
			break;
		default: // 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		} // 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment(HostParamActivity.this);
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {

		Activity activity;
		Timer timer;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment(Activity activity) {
			this.activity = activity;
		}

		@Override
		public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_host_version, container, false);
			ListView listView = (ListView) rootView.findViewById(R.id.listView_list);
			int index = getArguments().getInt(ARG_SECTION_NUMBER);

			final HostParamAdapter adapter = new HostParamAdapter(inflater, HostMachine.getInstance().getParams(index));
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub

					final HostParam param = (HostParam) adapter.getItem(arg2);

					final View textEntryView = inflater.inflate(R.layout.param_input, null);

					TextView paramName = (TextView) textEntryView.findViewById(R.id.param_name);
					paramName.setText(param.getName());
					EditText paramValue = (EditText) textEntryView.findViewById(R.id.param_value);
					paramValue.setText(Integer.toString(param.getParam()));
					AlertDialog dlg = new AlertDialog.Builder(activity)

					.setTitle("参数输入").setView(textEntryView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {

							EditText paramValue = (EditText) textEntryView.findViewById(R.id.param_value);
							String valString = paramValue.getText().toString();
							if (valString.isEmpty() == false) {
								try {
									int inputVal = Integer.parseInt(paramValue.getText().toString());

									param.setParam(inputVal);
								} catch (NumberFormatException e) {
									// TODO: handle exception
								}

							}

						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							System.out.println("-------------->2");

						}
					}).create();
					dlg.show();

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
			return rootView;
		}
	}

}
