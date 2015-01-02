/*
 * CardFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014年12月2日
 */
package com.example.world;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 
 *
 */
public class StartFragmentActivity extends FragmentActivity {

	private static final String TAG = StartFragmentActivity.class.getName();
	
	private Handler handler;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		setContentView(R.layout.start_fragment_activity);
		
		ImageView bg = (ImageView) findViewById(R.id.background);
		bg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
			}
		}, 5000); // 3s
	}
	


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
