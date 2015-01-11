package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import android.content.Context;
import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

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
	/*
	 * get all BL CommonList
	 * http://54.213.167.5/APIV2/getAdInfoByAdType.php?
	 */
	public String getBlList(int pageNum, int number, String categoryId)
			throws IOException, TimeoutException
	{
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
        		.add("page",Integer.toString(pageNum))
        		.add("limit",Integer.toString(number))
        		.add("adType",categoryId);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getAdInfoByAdType.php", params);
        return httpResponse.getBodyAsString();
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
        		.add("region",region);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getRestInfo.php", params);
        return httpResponse.getBodyAsString();
	}
	//http://54.213.167.5/APIV2/getItemsByRestID.php?restid=48&page=1&limit=10
	public String getRestaurantDetail(String restid)
			throws IOException, TimeoutException {
		String page="1";
	    String limit="1000";
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
        		.add("restid",restid)
        		.add("page",page)
        		.add("limit",limit);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getItemsByRestID.php", params);
        return httpResponse.getBodyAsString();
	}
	//http://54.213.167.5/postFeedback.php?content=content&name=name&phone=6478990689&email=email
	public String postFeedbackToAdmin(String content, String name, String phone, String email)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
        		.add("content",content)
        		.add("name",name)
        		.add("phone",phone)
        		.add("email",email);
        HttpResponse httpResponse = baseRequest.post("/postFeedback.php", params);
        return httpResponse.getBodyAsString();
	}
}
