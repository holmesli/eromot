package com.app.tomore.net;

import java.util.ArrayList;

import com.app.tomore.beans.BLRestaurantModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class RestaurantParse {
	
	public ArrayList<BLRestaurantModel> parseRestaurantResponse(String jsonRestaurant)  throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonRestaurant);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<BLRestaurantModel> restaurantlist = new ArrayList<BLRestaurantModel>();
		for (JsonElement obj : jarray) {
			BLRestaurantModel cse = gson.fromJson(obj, BLRestaurantModel.class);
			restaurantlist.add(cse);
		}
		return restaurantlist;
	}

}
