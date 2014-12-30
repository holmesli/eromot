package com.app.tomore;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class BLwebview extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bl_web_activity);
		
		String url ="http://m.dianping.com/shop/18766729";
		
		WebView view = (WebView) this.findViewById(R.id.webview);
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl(url);
	}
	
}