package com.app.tomore.data_connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

public class GetJSONFromUrl extends AsyncTask<String,Void,String>{
	@Override
	protected String doInBackground(String...urls){
		Looper.prepare();
		String result ="";
		InputStream isr = null;
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urls[0]);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			isr = entity.getContent();
		}
		catch(Exception e){
			Log.e("log.tag","Error in http connection " +e.toString());
		}
		
		//get the response
		if(isr != null){
			BufferedReader rd = new BufferedReader(new InputStreamReader(isr));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = rd.readLine()) != null)
				{
					sb.append(line);
				}
				isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = sb.toString();
		}
		return result;
	}
}
