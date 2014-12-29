package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class RegisterRequest {
	private final String url = "http://54.213.167.5/";
	protected Context mContext;
	private BasicHttpClient baseRequest;
	public RegisterRequest(Context context) {
		mContext = context;
	}
	/*
	 * send register info
	 * 
	 */
	//http://54.213.167.5/APIV2/register.php
	public String getLoginResponse(String email, String userName, String password, String school, String major, String gender)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("email", email)
                .add("accountName", userName)
                .add("password", password)
                .add("school", password)
                .add("major", major)
                .add("gender", gender);
        HttpResponse httpResponse = baseRequest.post("/APIV2/register.php", params);
        return httpResponse.getBodyAsString();
	}
}
