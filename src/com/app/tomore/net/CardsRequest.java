package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class CardsRequest {
	
	private final String url = "http://54.213.167.5/";
	protected Context mContext;
	private BasicHttpClient baseRequest;
	public CardsRequest(Context context) {
		mContext = context;
	}
	
	/*
	 * get card by memberID
	 * 
	 */
	//http://54.213.167.5/APIV2/getCardByMemberID.php?memberID=25&limit=5&page=1
	public String getCardByMemberID(String memberID,String limit, String page)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberID)
                .add("limit", limit)
                .add("page",page);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getCardByMemberID.php", params);
        return httpResponse.getBodyAsString();
	}

}
