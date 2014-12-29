package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class RestaurantRequest {
	
	private final String url = "http://54.213.167.5/";

	protected Context mContext;

	private BasicHttpClient baseRequest;
	public RestaurantRequest(Context context) {
		mContext = context;
	}
	
	/*
	 * //get all Restaurant by id
	 */ 
	//http://54.213.167.5/APIV2/getRestInfo.php?page=1&limit=5&region=4
	public String getRestaurantId(String page,String limit,String region)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
        		.add("page",page)
        		.add("limit",limit)
        		.add("page",page);
 
        
        HttpResponse httpResponse = baseRequest.post("http://54.213.167.5/APIV2/getRestInfo.php", params);
        return httpResponse.getBodyAsString();
	}
}
