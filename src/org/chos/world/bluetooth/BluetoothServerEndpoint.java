/*
 * BluetoothServerEndpoint.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê10ÔÂ25ÈÕ
 */
package org.chos.world.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 
 *
 */
public class BluetoothServerEndpoint {

	/**
	 * the context
	 * <p>
	 * Examples: <pre>
	 * context = getBaseContext();
	 * </pre>
	 */
	private Context context;
	
	private BluetoothAdapter adapter;
	
	private BluetoothServerSocket socket;
	
	public BluetoothServerEndpoint(Context context, BluetoothAdapter adapter) {
		this.context = context;
		this.adapter = adapter;
	}
	
	public void listen() throws IOException {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		 
	    String tmDevice, tmSerial, tmPhone, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	 
	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String uniqueId = deviceUuid.toString();
	    
		// 
		// adapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,  
		// 	   UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
	    socket = adapter.listenUsingRfcommWithServiceRecord("ServerSocketName_1", deviceUuid);
	}
	
	public BluetoothEndpoint accept() throws IOException {
		BluetoothSocket socket = this.socket.accept();
		return new BluetoothEndpoint(context, socket);
	}
}
