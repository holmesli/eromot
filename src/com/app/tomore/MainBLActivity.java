package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.app.tomore.net.YellowPageParse;
import com.app.tomore.net.YellowPageRequest;

import java.util.concurrent.TimeoutException;

import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.view.View;

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
		final List<ImageAndText> imageAndTextlist = new ArrayList<ImageAndText>();
		int Postion = 0;
		for(CategoryModel c:cateList)
		{
			imageAndTextlist.add(new ImageAndText(c.getImage(),c.getName()));
		}
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAndTextListAdapter(this, imageAndTextlist,
				gridView));
		gridView.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, 
		            int position, long id) {
		    	CategoryModel item = cateList.get(position);
		    	Open_Activity(item.getIconID(),Integer.parseInt(item.getType()));		    	
		    }
		});
	}
	private void Open_Activity(String BLID, int BLType){
        Intent intent;
        if (BLType == 1)
        {
	        intent = new Intent(MainBLActivity.this,
	        		GeneralBLActivity.class);
	        intent.putExtra("BLID",BLID);
	        startActivityForResult(intent, 100);
        }
        else if(BLType == 0){
	        intent = new Intent(MainBLActivity.this,
	        		RestaurantBLActivity.class);
	        startActivityForResult(intent, 100);
        }
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
			YellowPageRequest request = new YellowPageRequest(
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
					cateList = new YellowPageParse().parseCtegoryResponse(result);
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
