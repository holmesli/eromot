package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;

public class YellowPageRequest {

	private final String url = "http://54.213.167.5/";
	protected Context mContext;
	private BasicHttpClient baseRequest;
	public YellowPageRequest(Context context) {
		mContext = context;
	}
	
	/*
	 * get all BL Categories
	 * 
	 */
	//http://54.213.167.5/APIV2/getIcons.php
	public String getAllBLCategories()
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getIcons.php", null);
        return httpResponse.getBodyAsString();
	}
	
}
