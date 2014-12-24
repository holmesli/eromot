package com.app.tomore.net;

import java.util.ArrayList;

import com.app.tomore.beans.CategoryModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class YellowPageParse {


	public ArrayList<CategoryModel> parseCtegoryResponse(String jsonCategory)  throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonCategory);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<CategoryModel> lcs = new ArrayList<CategoryModel>();
		for (JsonElement obj : jarray) {
			CategoryModel cse = gson.fromJson(obj, CategoryModel.class);
			lcs.add(cse);
		}
		return lcs;
	}
}
