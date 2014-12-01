/*
 * RegisterAccountFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年06月25日
 */
package com.example.world;

import com.example.world.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 *
 */
public class SetFragmentActivity extends FragmentActivity {

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
		
		setContentView(R.layout.login_fragment_activity);
	}

}
