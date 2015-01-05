package com.app.tomore.net;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.BLRestaurantModel;
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
	
//	public HashMap<String, ArrayList<ArticleModel>> parseIssusePre(String jsonPre) 
//			throws JsonSyntaxException 
//	{
//		HashMap<String, ArrayList<ArticleModel>> retMap = new HashMap<String, ArrayList<ArticleModel>>();
//		Gson gson = new Gson();
//		JsonElement jelement = new JsonParser().parse(jsonPre);
//	    JsonObject  jobject = jelement.getAsJsonObject();
//	    JsonArray jarray = jobject.getAsJsonArray("data");
//		for (JsonElement obj : jarray) {
//			JsonObject  jobject2 = obj.getAsJsonObject();
//			String jarray2 = jobject2.getAsString();
//			if(jarray2 != null)
//			{
//				ArrayList<ArticleModel> article = new ArrayList<ArticleModel>();
//				for (JsonElement obj2 : jarray2)
//				{
//					ArticleModel cse = gson.fromJson(obj2, ArticleModel.class);
//					article.add(cse);
//				}
//				retMap.put("pre",article);
//			}
//		}
//		return retMap;
//	}
//	
//	public HashMap<String, ArrayList<ArticleModel>> parseIssuseNext(String jsonNext) 
//			throws JsonSyntaxException 
//	{
//		HashMap<String, ArrayList<ArticleModel>> retMap = new HashMap<String, ArrayList<ArticleModel>>();
//		Gson gson = new Gson();
//		JsonElement jelement = new JsonParser().parse(jsonNext);
//	    JsonObject  jobject = jelement.getAsJsonObject();
//	    JsonArray jarray = jobject.getAsJsonArray("data");
//		for (JsonElement obj : jarray) {
//			JsonObject  jobject2 = obj.getAsJsonObject();
//			JsonArray jarray2 = jobject2.getAsJsonArray("next");
//			if(jarray2 != null)
//			{
//				ArrayList<ArticleModel> article = new ArrayList<ArticleModel>();
//				for (JsonElement obj2 : jarray2)
//				{
//					ArticleModel cse = gson.fromJson(obj2, ArticleModel.class);
//					article.add(cse);
//				}
//				retMap.put("next",article);
//			}
//		}
//		return retMap;
//	}
}
