/*
 * CardFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014年12月2日
 */
package com.example.world.account;

import com.example.world.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 *
 */
public class CardFragmentActivity extends FragmentActivity {

	private static final String TAG = CardFragmentActivity.class.getName();
	
	private static final String TITLE = "title";
	
	private static int getLayoutResource() {
		return R.layout.card_fragment;
	}
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		int layout = getLayoutResource();
		setContentView(layout);
	}
	
	public static Fragment getFragment(String title) {
		Frag newFragment = new Frag();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		newFragment.setArguments(bundle);
		return newFragment;
	}
	
	public static class Frag extends Fragment {
		
		
		private String title;
		
//		public static CardFragment newInstance(String title) {
//			CardFragment newFragment = new CardFragment();
//			Bundle bundle = new Bundle();
//			bundle.putString(TITLE, title);
//			newFragment.setArguments(bundle);
//			return newFragment;
//		}
		
		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate");
			Bundle args = getArguments();
			title = args.getString(TITLE);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			int layout = getLayoutResource();
			View view = inflater.inflate(layout, container, false);
			return view;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onDestroy()
		 */
		@Override
		public void onDestroy() {
			super.onDestroy();
		}
	}
}
