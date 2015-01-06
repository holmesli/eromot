package com.app.tomore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.net.YellowPageParse;
import com.app.tomore.net.YellowPageRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class BLwebview extends Activity{
	
	int BLID;
	private int limit;
	private int pageNumber;
	private DialogActivity dialog;
	private ArrayList<GeneralBLModel> dataList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bl_web_activity);
		
		//String url ="http://m.dianping.com/shop/18766729";
		Intent i = getIntent();
		BLID = Integer.parseInt(i.getStringExtra("BLID"));
		//WebView view = (WebView) this.findViewById(R.id.webview);
		//view.getSettings().setJavaScriptEnabled(true);
		limit = 20;
		pageNumber = 1;
		new GetData(BLwebview.this, 1).execute("");
	}
	

	private void BindDataToView() {
		WebView view = (WebView) this.findViewById(R.id.webview);
		view.loadUrl(dataList.get(0).getUrl());
	}
	

	private class GetData extends AsyncTask<String, String, String> {
		// private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
			// this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			YellowPageRequest request = new YellowPageRequest(BLwebview.this); // BLRequest
			try {
				Log.d("doInBackground", "start request");
				result = request.getBlList(pageNumber, limit,Integer.toString(BLID));
				Log.d("doInBackground", "returned");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != dialog) {
				dialog.dismiss();
			}
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")){
				
			}
			else{
				dataList = new ArrayList<GeneralBLModel>();
				dataList = new YellowPageParse().parseGeneralBLResponse(result);
				BindDataToView();
			}
		}
	}
	
}