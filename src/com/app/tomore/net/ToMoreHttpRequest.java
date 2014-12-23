package com.app.tomore.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.text.TextUtils;

import com.app.tomore.httpclient.*;

public class ToMoreHttpRequest {

	private final String url = "http://54.213.167.5/";

	protected Context mContext;

	private BasicHttpClient baseRequest;
	public ToMoreHttpRequest(Context context) {
		mContext = context;
	}
	
	private String getUrl(String u, String a) {
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(u).append("/").append(a);
		return sb.toString();
	}
	private String returnString = null;
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
	
	/*
	 * //get all magazines by id
	 */
	//http://54.213.167.5/APIV2/getArticleByArticleIssue.php?articleIssue=1
	public String getMagById(String magId)
			throws IOException, TimeoutException {
//		baseRequest = new AndroidHttpClient(url);
//		baseRequest.setMaxRetries(5);
//		ParameterMap params = baseRequest.newParams()
//                .add("articleIssue", magId);
//		baseRequest.post("APIV2/getArticleByArticleIssue.php", params, new AsyncCallback() {
//            
//            @Override
//            public void onComplete(HttpResponse httpResponse)
//            {
//            	returnString =  httpResponse.getBodyAsString();
//            }
//            @Override
//            public void onError(Exception e) {
//            	returnString =  e.toString();
//            }
//        });
		
		return returnString;
	}
}
