package com.app.tomore.net;

import java.util.ArrayList;

import com.app.tomore.beans.BlockedModel;
import com.app.tomore.beans.FansModel;
import com.app.tomore.beans.FollowingModel;
import com.app.tomore.beans.UserModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
public class UserCenterParse {
	public UserModel parseLoginResponse(String result) {
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
		JsonObject jobject = jelement.getAsJsonObject();
		String loninResult = jobject.get("result").toString();
		JsonArray jarray = jobject.getAsJsonArray("data");

		if (loninResult.equals("\"succ\"")) {
			for (JsonElement obj : jarray) {
				UserModel cse = gson.fromJson(obj, UserModel.class);
				if (cse != null) {
					return cse;
				}
				else
					return null;
			}
		}
		return null;
	}

	public String parseRegisterResponse(String result) {
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
		JsonObject jobject = jelement.getAsJsonObject();
		String registerResult = jobject.get("result").toString();
		return registerResult;
	}

	public ArrayList<FansModel> parseFansResponse(String result)
			throws JsonSyntaxException {
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<FansModel> lcs = new ArrayList<FansModel>();
		for (JsonElement obj : jarray) {
			FansModel cse = gson.fromJson(obj, FansModel.class);
			lcs.add(cse);
		}
		return lcs;
	}

	public ArrayList<FollowingModel> parseFollowingResponse(String result)
			throws JsonSyntaxException {
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<FollowingModel> lcs = new ArrayList<FollowingModel>();
		for (JsonElement obj : jarray) {
			FollowingModel cse = gson.fromJson(obj, FollowingModel.class);
			lcs.add(cse);
		}
		return lcs;
	}
	
	public ArrayList<BlockedModel> parseBlockedResponse(String result)
			throws JsonSyntaxException {
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<BlockedModel> lcs = new ArrayList<BlockedModel>();
		for (JsonElement obj : jarray) {
			BlockedModel cse = gson.fromJson(obj, BlockedModel.class);
			lcs.add(cse);
		}
		return lcs;
	}
}
