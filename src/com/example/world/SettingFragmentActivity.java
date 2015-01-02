/*
 * CardFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014年12月2日
 */
package com.example.world;

import com.example.world.account.AccountManagerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 
 *
 */
public class SettingFragmentActivity extends FragmentActivity {

	private static final String TAG = SettingFragmentActivity.class.getName();
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		setContentView(R.layout.setting_fragment_activity);
		
		TextView accountMgr = (TextView) findViewById(R.id.accountMgr);
		accountMgr.setOnClickListener(new AccountManagerOnClickListener(this));
        
		TextView update = (TextView) findViewById(R.id.update);
        update.setOnClickListener(new UpdateOnClickListener(this));
        
        TextView about = (TextView) findViewById(R.id.about);
        about.setOnClickListener(new AboutOnClickListener(this));
	}
	
	public class AccountManagerOnClickListener implements View.OnClickListener {  
        
    	private Context context;
    	
        public AccountManagerOnClickListener(Context context) {
        	this.context = context;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
			Intent intent = new Intent(context, AccountManagerActivity.class);
        	context.startActivity(intent);
        }  
    }

    public class UpdateOnClickListener implements View.OnClickListener {  
        
    	private Context context;
    	
        public UpdateOnClickListener(Context context) {
        	this.context = context;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
			Intent intent = new Intent(context, CaptureFragmentActivity.class);
        	context.startActivity(intent);
        }  
    }
    
    public class AboutOnClickListener implements View.OnClickListener {  
        
    	private Context context;
    	
        public AboutOnClickListener(Context context) {
        	this.context = context;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
			Intent intent = new Intent(context, CaptureFragmentActivity.class);
        	context.startActivity(intent);
        }  
    }

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
