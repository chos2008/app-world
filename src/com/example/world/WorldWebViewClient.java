/*
 * WorldWebViewClient.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2015��3��4��
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
