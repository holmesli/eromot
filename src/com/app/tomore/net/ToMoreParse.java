package com.app.tomore.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.CategoryModel;
import com.app.tomore.beans.CommonModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class ToMoreParse {

	public CommonModel CommonPares(String str) {
		Gson gson = new Gson();
		Type type = new TypeToken<CommonModel>() {
		}.getType();
		CommonModel model = new CommonModel();
		model = gson.fromJson(str, type);
		return model;
	}
}
