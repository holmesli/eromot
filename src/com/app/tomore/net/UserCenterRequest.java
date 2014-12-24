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
}
