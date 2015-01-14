package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.net.YellowPageParse;
import com.app.tomore.net.YellowPageRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class OrderActivity extends Activity{

	private DialogActivity dialog;
	int BLID;
	private ListView mListView;
	private int limit;
	private int pageNumber;
	private Activity mContext;
	private ArrayList<GeneralBLModel> dataList;
	OrderBLAdpater newsListAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_listview);
		mListView = (ListView) findViewById(R.id.Orderlistview);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		Intent i = getIntent();
		mContext = this;
		BLID = Integer.parseInt(i.getStringExtra("BLID"));
		limit = 20;
		pageNumber = 1;
		new GetData(OrderActivity.this, 1).execute("");
		mListView.setOnItemClickListener(itemClickListener);
		LinearLayout whole_layout = (LinearLayout)findViewById(R.id.OrderLayout);
		TextView header_Text = (TextView) whole_layout.findViewById(R.id.btMeg);
		header_Text.setText(getString(R.string.CustomMade));
		final Button btnBack = (Button) whole_layout.findViewById(R.id.bar_title_bl_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

	
	private void BindDataToListView() {
		newsListAdapter = new OrderBLAdpater();
		mListView.setAdapter(newsListAdapter);

	}

	private void Open_Activity(int position) {
		Intent intent;
		intent = new Intent(OrderActivity.this,
				WebViewActivity.class);
		intent.putExtra("URL", dataList.get(position).getUrl());
		startActivityForResult(intent, 100);
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Open_Activity(position);
		}
	};




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
			YellowPageRequest request = new YellowPageRequest(OrderActivity.this); // BLRequest
			try {
				Log.d("doInBackground", "start request");
				result = request.getBlList(pageNumber, limit,Integer.toString(BLID));
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
				dataList = new ArrayList<GeneralBLModel>();
				dataList = new YellowPageParse().parseGeneralBLResponse(result);
				BindDataToListView();
			}
		}
	}
	
	private void showPopup(final GeneralBLModel generalbltext){

		String Cancel = getString(R.string.Cancel);
		String Phone1 = generalbltext.getPhone1();
		List<CharSequence>  cs = new ArrayList<CharSequence>();
		cs.add(Phone1);
    	if(generalbltext.getPhone2() != null){
    		if(generalbltext.getPhone2().length() > 7 || !generalbltext.getPhone2().equals(""))
    		{
    			cs.add(generalbltext.getPhone2());
    		}
    	}
		cs.add(Cancel);
    	CharSequence [] options = cs.toArray(new CharSequence[cs.size()]);
    	final int length = options.length;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.sentMessage));
		builder.setItems(options, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface Optiondialog, int which) {
		        if (which == 0){
		        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
		                    + generalbltext.getPhone1())));
		        }
		        else if (which == 1 && length == 3)
		        {
		        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
		                    + generalbltext.getPhone2())));
		        }

		    }
		});
		builder.show();
	}

	class ViewHolder {
		private TextView Title;
		private TextView Contact;

	}
	
	public class OrderBLAdpater extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return dataList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final GeneralBLModel generalbltext = (GeneralBLModel) getItem(position);
			ViewHolder viewHolder = null;
			if (convertView != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.order_listview_detail, null);
				viewHolder.Title = (TextView) convertView.findViewById(R.id.OrderTitle);
				viewHolder.Contact = (TextView) convertView.findViewById(R.id.OrderContactName);
				ImageView MessageBox = (ImageView) convertView.findViewById(R.id.OrderMessage);
				MessageBox.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View v) {
						showPopup(generalbltext);
				    }
				});
				convertView.setTag(viewHolder);
			}

			viewHolder.Title.setText(generalbltext.getTitle());
			viewHolder.Contact.setText(generalbltext.getContact());
			return convertView;
		}
	}

}
