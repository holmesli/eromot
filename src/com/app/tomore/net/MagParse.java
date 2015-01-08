package com.app.tomore.net;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.tomore.beans.ArticleCatogoryModel;
import com.app.tomore.beans.ArticleCommentModel;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.beans.CategoryModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MagParse {

	public ArrayList<ArticleModel> parseArticleResponse(String result) throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(result);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<ArticleModel> articlelist = new ArrayList<ArticleModel>();
		for (JsonElement obj : jarray) {
			ArticleModel cse = gson.fromJson(obj, ArticleModel.class);
			articlelist.add(cse);
		}
		return articlelist;
	}
	
	public ArrayList<ArticleCommentModel> parseArticleComment(String jsonArticleComment) throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonArticleComment);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<ArticleCommentModel> articleCommentlist = new ArrayList<ArticleCommentModel>();
		for (JsonElement obj : jarray) {
			ArticleCommentModel cse = gson.fromJson(obj, ArticleCommentModel.class);
			articleCommentlist.add(cse);
		}
		return articleCommentlist;
	}
	
	public ArrayList<ArticleCatogoryModel> parseCtegoryResponse(String jsonCategory)  throws JsonSyntaxException 
	{
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonCategory);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		ArrayList<ArticleCatogoryModel> lcs = new ArrayList<ArticleCatogoryModel>();
		for (JsonElement obj : jarray) {
			ArticleCatogoryModel cse = gson.fromJson(obj, ArticleCatogoryModel.class);
			lcs.add(cse);
		}
		return lcs;
	}
	

	public HashMap<String, ArrayList<ArticleModel>> parseIssuse(String articleIssuse, String pre, String next) 
			throws JsonSyntaxException 
	{
		HashMap<String, ArrayList<ArticleModel>> retMap = new HashMap<String, ArrayList<ArticleModel>>();
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(articleIssuse);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
		JsonObject  jobject2 = jelement.getAsJsonObject();
		JsonObject  jobject3 = jelement.getAsJsonObject();
		pre =jobject2.get("pre").getAsString();
		next =jobject3.get("next").getAsString();
		
			if(pre != null)
			{
				for (JsonElement obj : jarray) {
				ArrayList<ArticleModel> article = new ArrayList<ArticleModel>();
	
					ArticleModel cse = gson.fromJson(obj, ArticleModel.class);
					article.add(cse);
				retMap.put(pre,article);
				}
			}
			
			if(next != null)
			{
				for (JsonElement obj1 : jarray) {
				ArrayList<ArticleModel> article = new ArrayList<ArticleModel>();

					ArticleModel cse = gson.fromJson(obj1, ArticleModel.class);
					article.add(cse);
				retMap.put(next,article);
				
			}
		}
		return retMap;
	}
}
