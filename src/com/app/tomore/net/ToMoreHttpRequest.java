package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import android.content.Context;
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
	
}
