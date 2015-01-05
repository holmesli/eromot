package com.app.tomore.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.beans.CategoryModel;
import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.beans.BLMenuModel;
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

	public ArrayList<GeneralBLModel> parseGeneralBLResponse(String jsonCategory)  throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonCategory);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<GeneralBLModel> lcs = new ArrayList<GeneralBLModel>();
		for (JsonElement obj : jarray) {
			GeneralBLModel cse = gson.fromJson(obj, GeneralBLModel.class);
			lcs.add(cse);
		}
		return lcs;
	}
	//http://54.213.167.5/APIV2/getRestInfo.php?region=-1&page=1&limit=1000
	//key = region name, value = region rest list
	public HashMap<String, ArrayList<BLRestaurantModel>> parseRestaurantResponse(String jsonRestaurant) 
			throws JsonSyntaxException 
	{
		HashMap<String, ArrayList<BLRestaurantModel>> retMap = new HashMap<String, ArrayList<BLRestaurantModel>>();
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonRestaurant);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		for (JsonElement obj : jarray) {
			JsonObject  jobject2 = obj.getAsJsonObject();
			JsonArray jarray2 = jobject2.getAsJsonArray("scarborough");
			if(jarray2 != null)
			{
				ArrayList<BLRestaurantModel> restaurantlist = new ArrayList<BLRestaurantModel>();
				for (JsonElement obj2 : jarray2)
				{
				BLRestaurantModel cse = gson.fromJson(obj2, BLRestaurantModel.class);
				restaurantlist.add(cse);
				}
				retMap.put("scarborough",restaurantlist);
			}
		}
		return retMap;
	}
	public  ArrayList<BLMenuModel> parseRestaurantDetailResponse(String jsonRestaurantDetail) 
			throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonRestaurantDetail);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<BLMenuModel> Menulist = new ArrayList<BLMenuModel>();
		for (JsonElement obj : jarray) {
		BLMenuModel cse= gson.fromJson(obj, BLMenuModel.class);
		    Menulist.add(cse);
		}

		return Menulist;
	}
}