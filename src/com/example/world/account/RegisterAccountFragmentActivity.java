/*
 * RegisterAccountFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008��10��25��
 */
package com.example.world.account;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;

import com.example.world.JsonStream;
import com.example.world.R;
import com.example.world.SerializationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 *
 */
public class RegisterAccountFragmentActivity extends FragmentActivity implements LocationListener {

	private Handler handler = null;
	private String content=null;  
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ��������

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //ȥ����Ϣ��
		
		setContentView(R.layout.register_fragment_activity);
		
		//�����������̵߳�handler  
        handler=new Handler();  
        
		// ��ȡλ�ù������  
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {  
//            Toast.makeText(this, "�ֻ���λ����(GPS)��������", Toast.LENGTH_SHORT).show();  
//        } else {
//        	Toast.makeText(this, "�뿪���ֻ���λ����(GPS)����", Toast.LENGTH_SHORT).show();  
//            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);  
//            startActivityForResult(intent, 0); //��Ϊ������ɺ󷵻ص���ȡ����
//        }
		
		// ���ҵ�������Ϣ  
        Criteria criteria = new Criteria();  
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // �߾���  
        criteria.setAltitudeRequired(false);  
        criteria.setBearingRequired(false);  
        criteria.setCostAllowed(true);  
        criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���  
  
        String bestProvider = locationManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ  
        Location location = locationManager.getLastKnownLocation(bestProvider); // ͨ��GPS��ȡλ��  
        updateToNewLocation(location);  
        // ���ü��������Զ����µ���Сʱ��Ϊ���N��(1��Ϊ1*1000������д��ҪΪ�˷���)����Сλ�Ʊ仯����N��  
        locationManager.requestLocationUpdates(bestProvider, 100 * 1000, 500, this);
		
		
		
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		
		String NativePhoneNumber=null;
        NativePhoneNumber=telephonyManager.getLine1Number();
        
        String ProvidersName = null;
        // ����Ψһ���û�ID;�������ſ���IMSI���
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI��ǰ��3λ460�ǹ��ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й����š�
        System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "�й��ƶ�";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "�й���ͨ";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "�й�����";
        }
        
        TextView provider = (TextView) findViewById(R.id.provider);
        provider.setText(ProvidersName);
        
        EditText mobile = (EditText) findViewById(R.id.mobile);
        mobile.setText(NativePhoneNumber);
	}
	
    private void updateToNewLocation(Location location) {  
        
    	final TextView tv1 = (TextView) this.findViewById(R.id.location);  
        if (location != null) {
        	// ����
        	final double  latitude = location.getLatitude();
            // γ��
        	final double  longitude = location.getLongitude();
            
            new Thread() {
                @Override
                public void run() {
                	String loc = "\n(" +  latitude+ "," + longitude + ")";
                	HttpClient client = new HttpClient();
                    
                    String url = "http://api.map.baidu.com/geocoder?output=json&location=" + latitude + "," + longitude + "&key=0XzUDah97DzB1jqH7VI0eUgE";
                    GetMethod get = new GetMethod(url);
                    try {
        				client.executeMethod(get);
        				byte[] bytes = get.getResponseBody();
        				String responseText = new String(bytes, "UTF-8");
//        				JsonObject json = new JsonObject();
        				Gson gson = new Gson();
        				Map<String, Object> json = gson.fromJson(responseText, Map.class);
        				
//        				JsonStream jsonStream = new JsonStream(new JettisonMappedXmlDriver());
//        				Object object = jsonStream.deserialize(responseText, "response", Map.class);
//        				Map data = (Map) object;
        				System.out.println(json);
        				if (json != null) {
	        				Map<String, Object> result = (Map<String, Object>) json.get("result");
	        				if (result != null) {
		        				String formattedAddress = (String) result.get("formatted_address");
		        				loc = formattedAddress + loc;
	        				}
        				}
        				content = loc;
        				handler.post(runnableUi);
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
                }
            }.start();
        } else {
            tv1.setText("�޷���ȡλ����Ϣ");
        }  
  
    }
    
    Runnable runnableUi = new  Runnable(){  
        @Override  
        public void run() {  
        	final TextView tv1 = (TextView) RegisterAccountFragmentActivity.this.findViewById(R.id.location);  
            //���½���  
        	tv1.setText(content);  
        }  
          
    };  

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location arg0) {
		
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String arg0) {
		
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String arg0) {
		
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}  

}
