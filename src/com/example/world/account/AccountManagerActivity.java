/*
 * AccountManagerActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2015年1月2日
 */
package com.example.world.account;

import com.example.world.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 
 *
 */
public class AccountManagerActivity extends Activity {

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		setContentView(R.layout.account_manager_fragment_activity);
		
		TextView logout = (TextView) findViewById(R.id.logout);
		logout.setOnClickListener(new LogoutOnClickListener(this));
	}

	public class LogoutOnClickListener implements View.OnClickListener {  
        
    	private Context context;
    	
        public LogoutOnClickListener(Context context) {
        	this.context = context;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
			Intent intent = new Intent(context, LoginFragmentActivity.class);
        	context.startActivity(intent);
        }  
    }
}
