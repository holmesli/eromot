package com.app.tomore;

import com.app.tomore.beans.GeneralBLModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class OrderWebViewActivity extends Activity {

	private GeneralBLModel BLModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bl_web_activity);
		Intent intent = getIntent();
		BLModel = (GeneralBLModel) intent.getSerializableExtra("BLdata");
		WebView view = (WebView) this.findViewById(R.id.webview);
		view.loadUrl(BLModel.getUrl());
	}
}
