package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.DialogActivity;
import com.app.tomore.MainBLActivity;
import com.app.tomore.MyCameraActivity;
import com.app.tomore.R;
import com.app.tomore.adapters.GeneralBLAdpater;
import com.app.tomore.beans.CategoryModel;
import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.beans.GeneralBLText;
import com.app.tomore.net.BLRequest;
import com.app.tomore.net.GeneralBLParse;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class GeneralBLActivity extends Activity  {

	private DialogActivity dialog;
	private ArrayList<GeneralBLModel> dataList;
	int BLID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general_bl_layout);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		Intent i = getIntent();
		BLID = Integer.parseInt(i.getStringExtra("BLID"));
		new GetData(GeneralBLActivity.this, 1).execute("");
	}
	private void BindDataToListView()
	{
		final List<GeneralBLText> GeneralBLTextlist = new ArrayList<GeneralBLText>();
		int Postion = 0;
		for(GeneralBLModel c:dataList)
		{
			GeneralBLTextlist.add(new GeneralBLText(c.getTitle(),c.getContact(),c.getLanguage_M(),c.getLanguage_C(),c.getLanguage_E()));
		}
		ListView listview = (ListView) findViewById(R.id.ListView);
		listview.setAdapter(new GeneralBLAdpater(this, GeneralBLTextlist,
				listview));
		listview.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, 
		            int position, long id) {
		    	//GeneralBLModel item = dataList.get(BLID);
		    	Open_Activity(position);		    	
		    }
		});
	}
	private void Open_Activity(int position){
        Intent intent;
	    intent= new Intent(GeneralBLActivity.this,
	    		GeneralBLDetailActivity.class);
	    intent.putExtra("BLdata",dataList.get(position));
	    startActivityForResult(intent, 100);
	}
	
	private class GetData extends AsyncTask<String, String, List<String> > {
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
		protected List<String> doInBackground(String... params) {
			List<String> result = null;
			BLRequest request = new BLRequest(
					GeneralBLActivity.this,BLID); //BLRequest 
			try {
				Log.d("doInBackground", "start request");
				result = request.getAllData();
				Log.d("doInBackground", "returned");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(List<String>  result) {
			if (null != dialog) {
				dialog.dismiss();
			}
			Log.d("onPostExecute", "postExec state");
			List<GeneralBLModel> addtoDataList = new ArrayList<GeneralBLModel>();
			if (result == null || result.equals("")) {
				// show empty alert
			} else {
				dataList = new ArrayList<GeneralBLModel>();
				try {
					for(int i =0; i < result.size();i++){
						addtoDataList = new GeneralBLParse().parseGeneralBLResponse(result.get(i));
						dataList.addAll(addtoDataList);
						addtoDataList = null;
					}
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (dataList != null) {
					Intent intent = new Intent(GeneralBLActivity.this,
							MyCameraActivity.class); // fake redirect..
					intent.putExtra("menuList", (Serializable) dataList);
					//startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}

}
