/*
 * RegisterAccountFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年10月25日
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
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		setContentView(R.layout.register_fragment_activity);
		
		//创建属于主线程的handler  
        handler=new Handler();  
        
		// 获取位置管理服务  
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {  
//            Toast.makeText(this, "手机定位功能(GPS)功能正常", Toast.LENGTH_SHORT).show();  
//        } else {
//        	Toast.makeText(this, "请开启手机定位功能(GPS)功能", Toast.LENGTH_SHORT).show();  
//            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);  
//            startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
//        }
		
		// 查找到服务信息  
        Criteria criteria = new Criteria();  
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度  
        criteria.setAltitudeRequired(false);  
        criteria.setBearingRequired(false);  
        criteria.setCostAllowed(true);  
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗  
  
        String bestProvider = locationManager.getBestProvider(criteria, true); // 获取GPS信息  
        Location location = locationManager.getLastKnownLocation(bestProvider); // 通过GPS获取位置  
        updateToNewLocation(location);  
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米  
        locationManager.requestLocationUpdates(bestProvider, 100 * 1000, 500, this);
		
		
		
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		
		String NativePhoneNumber=null;
        NativePhoneNumber=telephonyManager.getLine1Number();
        
        String ProvidersName = null;
        // 返回唯一的用户ID;就是这张卡的IMSI编号
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        
        TextView provider = (TextView) findViewById(R.id.provider);
        provider.setText(ProvidersName);
        
        EditText mobile = (EditText) findViewById(R.id.mobile);
        mobile.setText(NativePhoneNumber);
	}
	
    private void updateToNewLocation(Location location) {  
        
    	final TextView tv1 = (TextView) this.findViewById(R.id.location);  
        if (location != null) {
        	// 经度
        	final double  latitude = location.getLatitude();
            // 纬度
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
            tv1.setText("无法获取位置信息");
        }  
  
    }
    
    Runnable runnableUi = new  Runnable(){  
        @Override  
        public void run() {  
        	final TextView tv1 = (TextView) RegisterAccountFragmentActivity.this.findViewById(R.id.location);  
            //更新界面  
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
