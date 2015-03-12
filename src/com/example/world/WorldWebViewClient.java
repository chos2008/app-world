/*
 * WorldWebViewClient.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2015Äê3ÔÂ4ÈÕ
 */
package com.example.world;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 *
 */
public class WorldWebViewClient extends WebViewClient {

	private final String errorHtml = "<html><body><h1>Page not find!</h1></body></html>";
	
	public boolean shouldOverrideUrlLoading(WebView view, String url) {  
        view.loadUrl(url);
        return true;
    }

	/* (non-Javadoc)
	 * @see android.webkit.WebViewClient#onReceivedError(android.webkit.WebView, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
		view.loadData(errorHtml, "text/html", "UTF-8");
		//view.loadUrl("file:///android_asset/index.html");  
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.webkit.WebViewClient#onReceivedSslError(android.webkit.WebView, android.webkit.SslErrorHandler, android.net.http.SslError)
	 */
	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		//super.onReceivedSslError(view, handler, error);
//		handler.cancel();
		handler.proceed();
//		handlem
	}
}
