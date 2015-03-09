/*
 * MainFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê11ÔÂ25ÈÕ
 */
package com.example.world;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 *
 */
public class TheStore extends FragmentActivity {

	private static final String TAG = TheStore.class.getName();
	
	private WebView webView;
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.eb_higo_index);
		
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.loadUrl("http://m.yhd.com");
		webView.setWebViewClient(new WorldWebViewClient());
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {  
        	webView.goBack();
            return true;
        }
        return false;
    }
}
