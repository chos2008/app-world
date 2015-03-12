/*
 * MainFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年11月25日
 */
package com.example.world;

import com.example.world.RefreshableWebView.PullToRefreshListener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 *
 */
public class TianGou extends FragmentActivity {

	private static final String TAG = TianGou.class.getName();
	
	private RefreshableWebView refreshableView;
	
	private WebView webView;
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏
		
		setContentView(R.layout.eb_higo_index);
		
		refreshableView = (RefreshableWebView) findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);
		
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.loadUrl("http://localhost:9090/as/tgou/index.htm");
		webView.setWebViewClient(new WorldWebViewClient());
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {  
        	if (webView.canGoBack()) {
	        	webView.goBack();
	            return true;
        	} else {
        		this.finish();
        		return true;
        	}
        }
        return false;
    }
}
