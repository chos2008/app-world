/*
 * WorldWebViewClient.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2015Äê3ÔÂ4ÈÕ
 */
package com.example.world;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 *
 */
public class WorldWebViewClient extends WebViewClient {

	public boolean shouldOverrideUrlLoading(WebView view, String url) {  
        view.loadUrl(url);
        return true;
    }
}
