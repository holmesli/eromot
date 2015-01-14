package com.app.tomore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

	private String URL;
	private WebView browser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bl_web_activity);
		Intent intent = getIntent();
		URL = (String) intent.getStringExtra("URL");
		browser = (WebView) this.findViewById(R.id.webview);
		browser.setWebViewClient(new MyBrowser());
		browser.getSettings().setLoadsImagesAutomatically(true);
		browser.getSettings().setJavaScriptEnabled(true);
		browser.loadUrl(URL);

	}
	
	
	   private class MyBrowser extends WebViewClient {
		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		         view.loadUrl(url);
		         return true;
		      }
		   }
}
