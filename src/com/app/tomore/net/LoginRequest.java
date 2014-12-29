package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class LoginRequest {
	private final String url = "http://54.213.167.5/";
	protected Context mContext;
	private BasicHttpClient baseRequest;
	public LoginRequest(Context context) {
		mContext = context;
	}

	/*
	 * send login info
	 * 
	 */
	//http://54.213.167.5/APIV2/Login.php
	public String getLoginResponse(String email, String password)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("email", email)
                .add("password", password);
        HttpResponse httpResponse = baseRequest.post("/APIV2/Login.php", params);
        return httpResponse.getBodyAsString();
	}
}
