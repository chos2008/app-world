/*
 * MainFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年11月25日
 */
package com.example.world;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.chos.world.ContactModel;

import com.example.world.MoreFragment.MyOnClickListener;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 *
 */
public class BluetoothFragmentActivity extends FragmentActivity {

	private static final String TAG = BluetoothFragmentActivity.class.getName();
	
	private static final int REQUEST_ENABLE = 1;
	
	private BluetoothAdapter adapter;
	
	private BroadcastReceiver mReceiver;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bluetooth_fragment_activity);
		
		Context context = this;
		Resources resources = getResources();
		
		final List<Map<String, Object>> listData = new LinkedList<Map<String,Object>>();
		//生成适配器的Item和动态数组对应的元素  
		final SimpleAdapter listItemAdapter = new SimpleAdapter(context, listData,//数据源   
				R.layout.bluetooth,//ListItem的XML实现  
				//动态数组与ImageItem对应的子项          
				new String[] {"ItemImage","ItemTitle", "ItemText", "ItemBond", "ItemConnect"},   
				//ImageItem的XML文件里面的一个ImageView,两个TextView ID  
				new int[] {R.id.ItemImage, R.id.ItemTitle, R.id.ItemText, R.id.ItemBond, R.id.ItemConnect}  
				); 
		final ListView listView = (ListView) BluetoothFragmentActivity.this.findViewById(R.id.bluetooth);
		//添加并且显示  
		listView.setAdapter(listItemAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Map<String, Object> map = (Map<String, Object>) listView.getAdapter().getItem(position);
				String mac = (String) map.get("ItemText");
				BluetoothDevice device = adapter.getRemoteDevice(mac);
				
				String name = (String) map.get("ItemTitle");
				int bondState = device.getBondState();
				if (bondState == BluetoothDevice.BOND_BONDED) {
					Method method;
					Boolean unBond = false;
					try {
						method = BluetoothDevice.class.getMethod("removeBond");
						unBond = (Boolean) method.invoke(device);  
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if (unBond) {
						map.put("ItemBond", R.drawable.bond);
						listItemAdapter.notifyDataSetChanged();
					}
					
					Toast toast = Toast.makeText(getApplicationContext(), "蓝牙设备 " + name + " 配对已取消", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else if (bondState == BluetoothDevice.BOND_BONDING) {
					map.put("ItemBond", R.drawable.bonding);
					listItemAdapter.notifyDataSetChanged();
					
					Toast toast = Toast.makeText(getApplicationContext(), "蓝牙设备 " + name + " 正在配对...", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ImageView imageCodeProject = new ImageView(getApplicationContext());
					imageCodeProject.setImageResource(R.drawable.logo_t);
					toastView.addView(imageCodeProject, 0);
					toast.show();
					return;
				}
				
				Toast toast = Toast.makeText(getApplicationContext(), "配对蓝牙设备 " + name, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView imageCodeProject = new ImageView(getApplicationContext());
				imageCodeProject.setImageResource(R.drawable.logo_t);
				toastView.addView(imageCodeProject, 0);
				toast.show();
				boolean bond = device.createBond();
				if (bond) {
					map.put("ItemBond", R.drawable.bonded);
					listItemAdapter.notifyDataSetChanged();
					
				}
			}
			
		});
		
		mReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_FOUND.equals(action)) { // 找到设备
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					Log.v(TAG, "find device:" + device.getName() + device.getAddress());
					setTitle("搜索蓝牙设备 " + device.getName());
					Map<String, Object> map = new HashMap<String, Object>();  
					map.put("ItemImage", R.drawable.ic_launcher); //图像资源的ID
					map.put("ItemTitle", device.getName());
					map.put("ItemText", device.getAddress());
					
					int bondState = device.getBondState();
					switch (bondState) {
						case BluetoothDevice.BOND_NONE: 
							map.put("ItemBond", R.drawable.bond);
							break;
						case BluetoothDevice.BOND_BONDED: 
							map.put("ItemBond", R.drawable.bonded);
							break;
						case BluetoothDevice.BOND_BONDING: 
							map.put("ItemBond", R.drawable.bonding);
							break;
						default:
							map.put("ItemBond", R.drawable.bond);
					}
					
					map.put("ItemConnect", R.drawable.connect);
					
					listData.add(map);
					listItemAdapter.notifyDataSetChanged();
					// listItemAdapter.notifyDataSetInvalidated();
				} else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) { // 状态改变
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            int bondState = device.getBondState();
		            String name = device.getName();
		            if (bondState == BluetoothDevice.BOND_BONDED) {
		            	Toast toast = Toast.makeText(getApplicationContext(), "状态变化: 蓝牙设备 " + device.getName() + " 已配对", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = (LinearLayout) toast.getView();
						ImageView imageCodeProject = new ImageView(getApplicationContext());
						imageCodeProject.setImageResource(R.drawable.logo_t);
						toastView.addView(imageCodeProject, 0);
						toast.show();
		            } else if(bondState == BluetoothDevice.BOND_BONDING) {
		            	Toast toast = Toast.makeText(getApplicationContext(), "状态变化: 蓝牙设备 " + name + " 正在配对", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = (LinearLayout) toast.getView();
						ImageView imageCodeProject = new ImageView(getApplicationContext());
						imageCodeProject.setImageResource(R.drawable.logo_t);
						toastView.addView(imageCodeProject, 0);
						toast.show();
		            } else if (bondState == BluetoothDevice.BOND_NONE) {
		            	Toast toast = Toast.makeText(getApplicationContext(), "状态变化: 蓝牙设备 " + device.getName() + " 未配对", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = (LinearLayout) toast.getView();
						ImageView imageCodeProject = new ImageView(getApplicationContext());
						imageCodeProject.setImageResource(R.drawable.logo_t);
						toastView.addView(imageCodeProject, 0);
						toast.show();
		            } else {
		            	Toast toast = Toast.makeText(getApplicationContext(), "状态变化: 蓝牙设备 " + device.getName() + " 未知配对状态", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = (LinearLayout) toast.getView();
						ImageView imageCodeProject = new ImageView(getApplicationContext());
						imageCodeProject.setImageResource(R.drawable.logo_t);
						toastView.addView(imageCodeProject, 0);
						toast.show();
		            }
				} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { // 搜索完成
					setTitle("搜索完成");
//					if (mNewDevicesAdapter.getCount() == 0) {
//						Log.v(TAG,"find over");
//					}
				}
			}
		};
		
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
		

		TextView enableBluetooth = (TextView) findViewById(R.id.enableBluetooth);
		TextView disableBluetooth = (TextView) findViewById(R.id.disableBluetooth);
		TextView searchBluetooth = (TextView) findViewById(R.id.searchBluetooth);
		
		adapter = BluetoothAdapter.getDefaultAdapter();
		
		if (adapter.isEnabled()) {
			enableBluetooth.setTextColor(resources.getColor(R.color.coral));
		} else {
			disableBluetooth.setTextColor(resources.getColor(R.color.coral));
		}
		
		disableBluetooth.setOnClickListener(new MyOnClickListener(this, 0, adapter));
		enableBluetooth.setOnClickListener(new MyOnClickListener(this, 1, adapter));
		searchBluetooth.setOnClickListener(new MyOnClickListener(this, 2, adapter));
	}

	public class MyOnClickListener implements View.OnClickListener {  
        
		private Context context;
		
		private int action;
		
    	private BluetoothAdapter adapter;
    	
        public MyOnClickListener(Context context, int action, BluetoothAdapter adapter) {
        	this.context = context;
        	this.action = action;
        	this.adapter = adapter;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	if (action == 0) {
        		if (adapter.isEnabled()) {
	        		adapter.disable();
        		}
        		TextView enableBluetooth = (TextView) findViewById(R.id.enableBluetooth);
        		enableBluetooth.setTextColor(context.getResources().getColor(R.color.black));
        		TextView disableBluetooth = (TextView) findViewById(R.id.disableBluetooth);
        		disableBluetooth.setTextColor(context.getResources().getColor(R.color.coral));
        	} else if (action == 1) {
        		//弹出对话框提示用户是后打开
        		Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        		startActivityForResult(enabler, REQUEST_ENABLE);
        		//不做提示，强行打开
        		// mAdapter.enable();
        		
//        		TextView enableBluetooth = (TextView) findViewById(R.id.enableBluetooth);
//        		enableBluetooth.setTextColor(context.getResources().getColor(R.color.coral));
//        		TextView disableBluetooth = (TextView) findViewById(R.id.disableBluetooth);
//        		disableBluetooth.setTextColor(context.getResources().getColor(R.color.black));
        	} else if (action == 2) {
        		adapter.startDiscovery();
        		setTitle("正在搜索...");
        	}
        }  
    }

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_ENABLE) {
//            String bookname=data.getExtras().getString("bookname");  
//            String booksale=data.getExtras().getString("booksale");  
//            TextView_result.setText("书籍名称:"+bookname+"书籍价钱"+booksale+"元");
			
			Resources resources = getResources();
			TextView enableBluetooth = (TextView) findViewById(R.id.enableBluetooth);
    		enableBluetooth.setTextColor(resources.getColor(R.color.coral));
    		TextView disableBluetooth = (TextView) findViewById(R.id.disableBluetooth);
    		disableBluetooth.setTextColor(resources.getColor(R.color.black));
        }
		super.onActivityResult(requestCode, resultCode, data);
	};
}
