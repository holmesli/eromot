package com.app.tomore.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;

public class BLRequest {

	int ID;
	private int limit = 10;
	protected Context mContext;
	private BasicHttpClient baseRequest;
	private final String url ="http://54.213.167.5/APIV2/getAdInfoByAdType.php?";
	
	public BLRequest(Context context,int id) {
		// TODO Auto-generated constructor stub
		mContext = context;
		ID = id;
	}
	
	/*
	 * get all BL data of the BL Type
	 * 
	 */
	public List<String> getAllData()
	    throws IOException, TimeoutException {
		List<String> reponseList= new ArrayList<String>();
		int page = 1;
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		boolean morePage = true;
		HttpResponse httpResponse = null;
		while (morePage == true)
		{
			String suburl =  "adType=" +Integer.toString(ID) + "&page=" + Integer.toString(page)+ "&limit=" + Integer.toString(limit);
			baseRequest = new BasicHttpClient(url);
	        baseRequest.setConnectionTimeout(2000);
	        httpResponse = baseRequest.post(suburl, null);
	        try {
				jsonObject = new JSONObject(httpResponse.getBodyAsString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				jsonArray =  jsonObject.getJSONArray("data");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(jsonArray == null || jsonArray.length() == 0)
	        {
	        	morePage = false;
	        }
	        else{
	        	page += 1;
	        	reponseList.add(httpResponse.getBodyAsString());
	        }
		}
        return reponseList;
	}

}
