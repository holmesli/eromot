package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.app.tomore.net.ToMoreHttpRequest;
import com.app.tomore.net.ToMoreParse;

import java.util.concurrent.TimeoutException;

import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.app.tomore.adapters.ImageAndTextListAdapter;
import com.app.tomore.beans.CategoryModel;
import com.app.tomore.beans.ImageAndText;

public class MainBLActivity extends Activity {
	private DialogActivity dialog;
	private ArrayList<CategoryModel> cateList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_bianli_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainBLActivity.this, 1).execute("");
	}
	
	private void BindDataToGridView()
	{
		List<ImageAndText> imageAndTextlist = new ArrayList<ImageAndText>();
		for(CategoryModel c:cateList)
		{
			imageAndTextlist.add(new ImageAndText(c.getImage(),c.getName()));
		}
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAndTextListAdapter(this, imageAndTextlist,
				gridView));
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
					cateList = new ToMoreParse().parseCtegoryResponse(result);
					BindDataToGridView();
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
}
