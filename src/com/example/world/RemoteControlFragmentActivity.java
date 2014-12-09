/*
 * CardFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014��12��2��
 */
package com.example.world;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 *
 */
public class RemoteControlFragmentActivity extends FragmentActivity {

	private static final String TAG = RemoteControlFragmentActivity.class.getName();
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ��������

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //ȥ����Ϣ��
		
		setContentView(R.layout.capture_fragment_activity);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
