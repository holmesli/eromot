package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
public class ThreadsRequest {

	
		private final String url = "http://54.213.167.5/";
		protected Context mContext;
		private BasicHttpClient baseRequest;
		public ThreadsRequest(Context context) {
			mContext = context;
		}
		
		/*
		 * get thread list with filter
		 * http://54.213.167.5/APIV2/getThreadList.php?page=1&limit=2&viewerID=2&filter=0
		 */
		
		public String getThreadList(int pageNum, int number, int viewerId, int filterId)
				throws IOException, TimeoutException
		{
			baseRequest = new BasicHttpClient(url);
	        baseRequest.setConnectionTimeout(2000);
	        ParameterMap params = baseRequest.newParams()
	        		.add("page",Integer.toString(pageNum))
	        		.add("limit",Integer.toString(number))
	        		.add("viewerID",Integer.toString(viewerId))
	        		.add("filter",Integer.toString(filterId));;
	        HttpResponse httpResponse = baseRequest.post("/APIV2/getThreadList.php", params);
	        return httpResponse.getBodyAsString();
		}
	/*
	 * get thread fall list
	 *	http://54.213.167.5/get20RandomThread.php
	 */
		public String getThreadFallList()
				throws IOException, TimeoutException
		{
			baseRequest = new BasicHttpClient(url);
	        baseRequest.setConnectionTimeout(2000);
	        HttpResponse httpResponse = baseRequest.get("get20RandomThread.php",null);
	        return httpResponse.getBodyAsString();
		}
	
	/*
	 * like or unlike a thread
	 */
	
	/*
	 * post comment to a thread
	 */
	
	/*
	 * get thread details
	 */
	
	
}
