package com.app.tomore.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import android.content.Context;

import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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
	
	public String getCommentByMemberId(String articleID, String memberID, String commentContent)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("articleID",articleID)
        		.add("memberID",memberID)
        		.add("commentContent",commentContent);
        HttpResponse httpResponse = baseRequest.post("/APIV2/postCommentToArticle.php", params);
        return httpResponse.getBodyAsString();
	}
	
	public String getCommentByArticleId(String articleID, String page, String limit)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("articleID",articleID)
        		.add("page",page)
        		.add("limit",limit);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getCommentsByArticleID.php", params);
        return httpResponse.getBodyAsString();
	}
	
}
