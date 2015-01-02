/*
 * CardFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014年12月2日
 */
package com.example.world;

import com.example.world.account.CardFragmentActivity;

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
public class ViewFragmentActivity extends FragmentActivity {

	private static final String TAG = ViewFragmentActivity.class.getName();
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		setContentView(R.layout.view_fragment_activity);
		
		TextView card = (TextView) findViewById(R.id.card);
		card.setOnClickListener(new CardOnClickListener(this));
		
		TextView setting = (TextView) findViewById(R.id.setting);
        setting.setOnClickListener(new SettingOnClickListener(this));
	}
	
	public class CardOnClickListener implements View.OnClickListener {  
        
    	private Context context;
    	
        public CardOnClickListener(Context context) {
        	this.context = context;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
			Intent intent = new Intent(context, CardFragmentActivity.class);
        	context.startActivity(intent);
        }  
    };
	
	public class SettingOnClickListener implements View.OnClickListener {  
        
    	private Context context;
    	
        public SettingOnClickListener(Context context) {
        	this.context = context;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
			Intent intent = new Intent(context, SettingFragmentActivity.class);
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
