package com.app.tomore.net;

import java.util.ArrayList;

import com.app.tomore.beans.ArticleModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MagParse {

	public ArrayList<ArticleModel> parseArticleResponse(String jsonArticle) throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonArticle);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<ArticleModel> articlelist = new ArrayList<ArticleModel>();
		for (JsonElement obj : jarray) {
			ArticleModel cse = gson.fromJson(obj, ArticleModel.class);
			articlelist.add(cse);
		}
		return articlelist;
	}
}
