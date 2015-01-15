package com.app.tomore.net;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import android.content.Context;
import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;

public class UserCenterRequest {
	private final String url = "http://54.213.167.5/";

	protected Context mContext;

	private BasicHttpClient baseRequest;
	public UserCenterRequest(Context context) {
		mContext = context;
	}
	
	/*
	 * get who followed a memberID list by viewer id
	 */
	//http://54.213.167.5/APIV2/getFollowedList.php?memberID=25&viewerID=34&limit=10&page=1
	public String getFollowedListById(String memberId, String viewerId, int limit, int pageNum)
			throws IOException, TimeoutException {
		return null;
	}
	/*
	 * get who a memberID is following list by viewer id
	 */
	//http://54.213.167.5/getFollowingList.php?memberID=25&viewerID=34&limit=10&page=1
	public String getFollowingListById(String memberId, String viewerId,int limit, int pageNum)
			throws IOException, TimeoutException {
		return null;
	}
	
	/*
	 * get blacklist by memberId
	 */
	//http://54.213.167.5/APIV2/getBlockedList.php?memberID=25&limit=10&page=1
	public String getBlackList(String memberId, int limit, int pageNum)
			throws IOException, TimeoutException {
		return null;
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
	
	/*
	 * send fans info
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
	
	/*
	 * send following info
	 * 
	 */
	//http://54.213.167.5/APIV2/getFollowingList.php
	public String getFollowingRequest(String memberID, String viewerID, String limit, String page)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberID)
                .add("viewerID", viewerID)
                .add("limit", limit)
                .add("page", page);
        HttpResponse httpResponse = baseRequest.post("/getFollowingList.php", params);
        return httpResponse.getBodyAsString();
	}
	
	/*
	 * send blocked info
	 * 
	 */
	//http://54.213.167.5/APIV2/getBlockedList.php
	public String getBlockedRequest(String memberID, String limit, String page)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberID)
                .add("limit", limit)
                .add("page", page);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getBlockedList.php", params);
        return httpResponse.getBodyAsString();
	}
	
	// get user reply list
	//http://54.213.167.5/APIV2/getUpdates.php?memberID=25
	public String getUserUpdate(String memberId)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberId);
        HttpResponse httpResponse = baseRequest.post("/APIV2/getUpdates.php", params);
        return httpResponse.getBodyAsString();
	}
	
	//delete user's thread by Id
	//http://54.213.167.5/deleteThreadByThreadIDAndMemberID.php?&memberID=18&threadID=2
	public String deleteUserThread(String memberId, String threadId)
			throws IOException, TimeoutException {
		
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberId)
                .add("threadID", threadId);
        HttpResponse httpResponse = baseRequest.post("/deleteThreadByThreadIDAndMemberID", params);
        return httpResponse.getBodyAsString();
	}
	
	//get user's post
	//http://54.213.167.5/getThreadListByMemberID.php?memberID=25&limit=20&page=1
	public String getUserPostThreadList(String memberID, String page,String limit)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
        baseRequest.setConnectionTimeout(2000);
        ParameterMap params = baseRequest.newParams()
                .add("memberID", memberID)
                .add("limit", limit)
                .add("page", page);
        HttpResponse httpResponse = baseRequest.post("/getThreadListByMemberID.php", params);
        return httpResponse.getBodyAsString();
	}
}
