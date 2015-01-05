package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class MagRequest {
	
	private final String url = "http://54.213.167.5/";

	protected Context mContext;

	private BasicHttpClient baseRequest;
	public MagRequest(Context context) {
		mContext = context;
	}
	
	/*
	 * //get all magazines by id
	 */
	//http://54.213.167.5/APIV2/getArticleByArticleIssue.php?articleIssue=1
	public String getMagById(String articleIssue)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("articleIssue",articleIssue);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getArticleByArticleIssue.php", params);
        return httpResponse.getBodyAsString();
	}
}
