package com.app.tomore.beans;

import java.io.Serializable;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;

public abstract class BaseBean<T> implements Serializable {
	private static final long serialVersionUID = -804757173578073135L;

	public abstract T parseJSON(JSONObject jsonObj);
	public abstract JSONObject toJSON();
	public abstract T cursorToBean(Cursor cursor);
	public abstract ContentValues beanToValues();

	@SuppressWarnings("unchecked")
	public T  parseJSON(Gson gson,String json){
		return (T)gson.fromJson(json, this.getClass());
	}
	
}