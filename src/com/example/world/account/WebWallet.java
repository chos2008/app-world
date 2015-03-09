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
import com.example.world.WorldWebViewClient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * 
 *
 */
public class WebWallet extends FragmentActivity {

	private static final String TAG = WebWallet.class.getName();
	
	private WebView webview;
	
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		
		webview = new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("http://localhost:9090/as");
		webview.setWebViewClient(new WorldWebViewClient());
		setContentView(webview);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {  
        	webview.goBack();
            return true;
        }
        return false;
    }
}
