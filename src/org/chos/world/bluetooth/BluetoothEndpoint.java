/*
 * BluetoothEndpoint.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014Äê11ÔÂ30ÈÕ
 */
package org.chos.world.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 
 *
 */
public class BluetoothEndpoint {

	private Context context;
	
	private BluetoothSocket socket;
	
	public BluetoothEndpoint(Context context, BluetoothSocket socket) {
		this.context = context;
		this.socket = socket;
	}
	
	public void connect(BluetoothDevice dev) throws IOException {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		 
	    String tmDevice, tmSerial, tmPhone, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	 
	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String uniqueId = deviceUuid.toString();
	    
//		socket = dev.createRfcommSocketToServiceRecord(UUID.fromString("84D1319C-FBAF-644C-901A-8F091F25AF04"));
		socket = dev.createRfcommSocketToServiceRecord(deviceUuid);
	}
	
	public void connect() {
		
	}
	
	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}
}
