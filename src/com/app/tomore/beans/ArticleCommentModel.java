package com.app.tomore.beans;

import java.io.Serializable;

import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;

public class ArticleCommentModel extends BaseBean implements Serializable, Comparable<ArticleCommentModel>{ 
	
	private String CommentID;
	private String  CommentContent;
	private String  MemberID;
	private String  PostDate;
	private String  UpdateDate;
	private String  AccountName;
	private String  TimeDiff;
	private String  MemberImage;
	
	
	public String getCommentID() {
		return CommentID;
	}

	public void setCommentID(String commentID) {
		CommentID = commentID;
	}

	public String getCommentContent() {
		return CommentContent;
	}

	public void setCommentContent(String commentContent) {
		CommentContent = commentContent;
	}

	public String getMemberID() {
		return MemberID;
	}

	public void setMemberID(String memberID) {
		MemberID = memberID;
	}

	public String getPostDate() {
		return PostDate;
	}

	public void setPostDate(String postDate) {
		PostDate = postDate;
	}
	
	public String getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getTimeDiff() {
		return TimeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		TimeDiff = timeDiff;
	}
	
	public String getMemberImage() {
		return MemberImage;
	}

	public void setMemberImage(String memberImage) {
		MemberImage = memberImage;
	}


	
	@Override
	public int compareTo(ArticleCommentModel another) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object parseJSON(JSONObject jsonObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object cursorToBean(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentValues beanToValues() {
		// TODO Auto-generated method stub
		return null;
	}
}
	