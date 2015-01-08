package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class FansRequest {
	private final String url = "http://54.213.167.5/";
	protected Context mContext;
	private BasicHttpClient baseRequest;
	public FansRequest(Context context) {
		mContext = context;
	}

	/*
	 * send login info
	 * 
	 */
	//http://54.213.167.5/APIV2/getFollowedList.php
	public String getFansRequest(String memberID, String viewerID, String limit, String page)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberID)
                .add("viewerID", viewerID)
                .add("limit", limit)
                .add("page", page);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getFollowedList.php", params);
        return httpResponse.getBodyAsString();
	}

}
