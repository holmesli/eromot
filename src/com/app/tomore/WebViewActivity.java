package com.app.tomore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	private String URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bl_web_activity);
		Intent intent = getIntent();
		URL = (String) intent.getStringExtra("URL");
		WebView view = (WebView) this.findViewById(R.id.webview);
		view.loadUrl(URL);
	}
}
