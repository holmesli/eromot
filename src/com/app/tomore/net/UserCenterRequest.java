package com.app.tomore.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.app.tomore.utils.AndroidMultiPartEntity;
import com.app.tomore.utils.AndroidMultiPartEntity.ProgressListener;

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
	
	/*
	 * upload card by memberID
	 */
	// updateProfile.php, 参数：头像，uploaded，用户id，memberID
    @SuppressWarnings("deprecation")
   	public String updateUserProfile(Bitmap frontImage, String memberID) {
   		String responseString = null;

   		HttpClient httpclient = new DefaultHttpClient();
   		HttpPost httppost = new HttpPost(url + "updateProfile.php");

   		try {
   			AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
   					new ProgressListener() {

   						@Override
   						public void transferred(long num) {
   							// publishProgress((int) ((num / (float) totalSize)
   							// * 100));
   						}
   					});
   			//create temp file for upload
   			String fileName = "temp.jpg";

   			String path = android.os.Environment.getExternalStorageDirectory()
   					.toString();
   			File f = new File(path, fileName);
   			f.createNewFile();

   			// Convert bitmap to byte array
   			ByteArrayOutputStream stream = new ByteArrayOutputStream();
   			frontImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
   			byte[] bitmapdata = stream.toByteArray();

   			// write the bytes in file
   			FileOutputStream fos = new FileOutputStream(f);
   			fos.write(bitmapdata);
   			fos.flush();
   			fos.close();

   			entity.addPart("uploaded1", new FileBody(f));
   			entity.addPart("memberID", new StringBody(memberID));

   			// totalSize = entity.getContentLength();
   			httppost.setEntity(entity);

   			// Making server call
   			org.apache.http.HttpResponse response = httpclient
   					.execute(httppost);
   			HttpEntity r_entity = response.getEntity();
   			// delete temp file after upload
   			f.delete();

   			int statusCode = response.getStatusLine().getStatusCode();
   			if (statusCode == 200) {
   				// Server response
   				responseString = EntityUtils.toString(r_entity);
   			} else {
   				responseString = "Error occurred! Http Status Code: "
   						+ statusCode;
   			}

   		} catch (ClientProtocolException e) {
   			responseString = e.toString();
   		} catch (IOException e) {
   			responseString = e.toString();
   		}
   		
   		return responseString;
   	}
}
