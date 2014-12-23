package com.app.tomore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.app.tomore.net.ToMoreHttpRequest;
import com.app.tomore.net.ToMoreParse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.app.tomore.data_connection.GetJSONFromUrl;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.app.tomore.beans.CategoryModel;

public class MainBLActivity extends Activity {
	private TextView resultView;
	private DialogActivity dialog;
	private ArrayList<CategoryModel> cateList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_bianli_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		// resultView = (TextView) findViewById(R.id.TestData);
		new GetData(MainBLActivity.this, 1).execute("");
		//getData();
	}

	private class GetData extends AsyncTask<String, String, String> {
		// private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
			// this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			ToMoreHttpRequest request = new ToMoreHttpRequest(
					MainBLActivity.this);

			try {
				Log.d("doInBackground", "start request");
				result = request.getAllBLCategories();
				Log.d("doInBackground", "returned");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != dialog) {
				dialog.dismiss();
			}
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				// show empty alert
			} else {
				cateList = new ArrayList<CategoryModel>();
				try {
					// cateList = new Parse().GetMenuByRestId(result);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (cateList != null) {
					Intent intent = new Intent(MainBLActivity.this,
							MyCameraActivity.class); // fake redirect..
					intent.putExtra("menuList", (Serializable) cateList);
					//startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}

	public void getData() {
		String result = "";
		try {
			result = new GetJSONFromUrl().execute(
					"http://54.213.167.5/APIV2/getIcons.php").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// resultView.setText(result);
		GetBL(result);
	}

	// get BianLi data
	public void GetBL(String jsonString) {
		// load data into a list
		List<String> list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray personJSONobject = jsonObject.getJSONArray("data");
			for (int i = 0; i < personJSONobject.length(); i++) {
				list.add(personJSONobject.getJSONObject(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// load data into a hash table, then add the hash tables into a list
		List<Hashtable<String, Object>> hashtableDataList = new ArrayList<Hashtable<String, Object>>();
		// Make a list for arrayAdapter
		List<ImageAndText> ImageAndTextlist = new ArrayList<ImageAndText>();
		for (int i = 0; i < list.size(); i++) {
			try {
				JSONObject DataObject = new JSONObject(list.get(i));
				Hashtable<String, Object> BianLidata = new Hashtable<String, Object>();
				BianLidata.put("IconID", DataObject.getString("IconID"));
				BianLidata.put("Type", DataObject.getString("Type"));
				BianLidata.put("Name", DataObject.getString("Name"));
				BianLidata.put("Image", DataObject.getString("Image"));
				ImageAndTextlist.add(new ImageAndText(DataObject
						.getString("Image"), DataObject.getString("Name")));
				hashtableDataList.add(BianLidata);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAndTextListAdapter(this, ImageAndTextlist,
				gridView));
	}
}
