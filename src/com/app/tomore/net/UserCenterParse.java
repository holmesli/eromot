package com.app.tomore.net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserCenterParse {
	public String parseLoginResponse(String result){
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    String loninResult = jobject.get("result").toString();
		return loninResult;		
	}
	
	public String parseRegisterResponse(String result){
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    String registerResult = jobject.get("result").toString();
	    return registerResult;
	}
}
