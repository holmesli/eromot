package com.app.tomore.beans;

import java.io.Serializable;

import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;

public class ArticleCatogoryModel extends BaseBean implements Serializable, Comparable<ArticleCatogoryModel>{ 
	
	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public String getCategoryImage() {
		return CategoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		CategoryImage = categoryImage;
	}

	
	private String CategoryID;
	private String  CategoryName;
	private String  CategoryImage;
	
	@Override
	public int compareTo(ArticleCatogoryModel another) {
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
	