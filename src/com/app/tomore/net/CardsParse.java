package com.app.tomore.net;

import java.util.ArrayList;

import com.app.tomore.beans.CardModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CardsParse {
	
	public ArrayList<CardModel> parseCardResponse(String jsonCard)  throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonCard);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<CardModel> lcs = new ArrayList<CardModel>();
		for (JsonElement obj : jarray) {
			CardModel cse = gson.fromJson(obj, CardModel.class);
			lcs.add(cse);
		}
		return lcs;
	}

}
