package com.app.tomore.net;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.app.tomore.beans.ThreadCmtModel;
import com.app.tomore.beans.ThreadImageModel;
import com.app.tomore.beans.ThreadLikeModel;
import com.app.tomore.beans.ThreadModel;

public class ThreadsParse {
	public ArrayList<ThreadModel> parseThreadModel(String jsonThreads) 
			throws JsonSyntaxException 
	{
		
//		 "data": [
//		          {
//		              "ThreadID": "733",
//		              "ThreadTitle": "",
//		              "ThreadPostDate": "2015-01-06 19:59:54",
//		              "ThreadUpdateDate": null,
//		              "ThreadContent": "5元在Omar Deserre 淘到的巴黎地图",
//		              "ThreadType": "1",
//		              "MemberID": "135",
//		              "LastCommentDate": null,
//		              "ThreadImages": [
//		                  {
//		                      "ImageID": "308",
//		                      "ImageUrl": "http://54.213.167.5/image/thread/20150106-195954-342image.jpg",
//		                      "ImageWidth": "480",
//		                      "ImageHeight": "640"
//		                  }
//		              ],
//		              "LikeList": [],
//		              "Comments": [],
//		              "AccountName": "Xxhottie88xx",
//		              "MemberImage": "http://54.213.167.5/image/member/DefaultMemberImage/default_userProfileF.jpg",
//		              "TimeDiff": "1天前"
//		          }
		
		
		ArrayList<ThreadModel> retList = new ArrayList<ThreadModel>();
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(jsonThreads);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("data");
	    for (JsonElement obj : jarray)
	    {
	    	ThreadModel aThread = gson.fromJson(obj, ThreadModel.class);
	    	
	    	JsonObject  jobject2 = obj.getAsJsonObject();
	    	JsonArray threadImageArray = jobject2.getAsJsonArray("ThreadImages");
	    	JsonArray threadLikeArray = jobject2.getAsJsonArray("LikeList");
	    	JsonArray threadCommentArray = jobject2.getAsJsonArray("Comments");
	    	if(threadImageArray != null)
	    	{
	    		ArrayList<ThreadImageModel> imageModelList = new ArrayList<ThreadImageModel>();
	    		for (JsonElement objThreadImage : threadImageArray)
				{
	    			ThreadImageModel imageModel = gson.fromJson(objThreadImage, ThreadImageModel.class);
	    			imageModelList.add(imageModel);
				}
	    		aThread.setThreadImageList(imageModelList);
	    	}
	    	
	    	if(threadLikeArray != null)
	    	{
	    		ArrayList<ThreadLikeModel> likeModelList = new ArrayList<ThreadLikeModel>();
	    		for (JsonElement objLike : threadLikeArray)
	    		{
	    			ThreadLikeModel likeModel = gson.fromJson(objLike, ThreadLikeModel.class);
	    			likeModelList.add(likeModel);
	    		}

	    		aThread.setThreadLikeList(likeModelList);
	    	}
	    	
	    	if(threadCommentArray != null)
	    	{
	    		ArrayList<ThreadCmtModel> listComment = new ArrayList<ThreadCmtModel>();
	    		for (JsonElement objComment : threadCommentArray)
	    		{
	    			ThreadCmtModel commentModel = gson.fromJson(objComment, ThreadCmtModel.class);
	    			listComment.add(commentModel);
	    		}
	    		
	    		aThread.setThreadCmtList(listComment);
	    	}

			retList.add(aThread);
	    }
	    
		return retList;
	}
}
