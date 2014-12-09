/*
 * CardFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2014Äê12ÔÂ2ÈÕ
 */
package com.example.world.account;

import com.example.world.DefaultFragment;
import com.example.world.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 *
 */
public class CardFragment extends Fragment {

	private static final String TAG = CardFragment.class.getName();
	
	private static final String TITLE = "title";
	
	private String title;
	
	public static CardFragment newInstance(String title) {
		CardFragment newFragment = new CardFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		newFragment.setArguments(bundle);
		return newFragment;
	}
	
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
		View view = inflater.inflate(R.layout.card_fragment, container, false);
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
