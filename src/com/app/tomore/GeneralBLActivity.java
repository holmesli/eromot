package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.DialogActivity;
import com.app.tomore.R;
import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.net.YellowPageParse;
import com.app.tomore.net.YellowPageRequest;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.PullToRefreshBase;
import com.app.tomore.utils.PullToRefreshBase.OnLastItemVisibleListener;
import com.app.tomore.utils.PullToRefreshBase.OnRefreshListener;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.ToastUtils;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class GeneralBLActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<GeneralBLModel> dataList;
	int BLID;
	private PullToRefreshListView mListView;
	private Activity mContext;
	private TextView noneData;
	private View no_net_lay;
	GeneralBLAdpater newsListAdapter;
	private boolean onRefresh = false;
	private boolean headerRefresh = false; // false -> footer
	private int pageNumber;
	private int limit;
	private LayoutInflater inflater; 
	private View layout;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general_bl_layout);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		Intent i = getIntent();
		BLID = Integer.parseInt(i.getStringExtra("BLID"));
		name = i.getStringExtra("name");
		mContext = this;
		limit = 20;
		pageNumber = 1;
		new GetData(GeneralBLActivity.this, 1).execute("");
		mListView = (PullToRefreshListView) findViewById(R.id.list);
		mListView.setOnRefreshListener(onRefreshListener);
		mListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
		mListView.setOnItemClickListener(itemClickListener);
		noneData = (TextView) findViewById(R.id.noneData);
		no_net_lay = findViewById(R.id.no_net_lay);
		inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = findViewById(R.id.GeneralBLLayout);
		//LinearLayout whole_layout = (LinearLayout)findViewById(R.id.GeneralBLLayout);
		TextView header_Text = (TextView) layout.findViewById(R.id.btMeg);
		header_Text.setText(name);
		final Button btnBack = (Button) layout.findViewById(R.id.bar_title_bl_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}


	private void BindDataToListView() {
		if (onRefresh) {
			onRefresh = false;
		}
		if (newsListAdapter == null) {
			newsListAdapter = new GeneralBLAdpater();
			mListView.setAdapter(newsListAdapter);
		} else {
			newsListAdapter.notifyDataSetChanged();
		}
		if (dataList != null && dataList.size() > 0) {
			showDataUi();
		} else {
			showNoDataUi();
		}

	}

	private void Open_Activity(int position) {
		Intent intent;
		intent = new Intent(GeneralBLActivity.this,
				GeneralBLDetailActivity.class);
		intent.putExtra("BLdata", (Serializable)dataList.get(position));
		startActivityForResult(intent, 100);
	}

	void showDataUi() {
		mListView.setVisibility(View.VISIBLE);
		noneData.setVisibility(View.GONE);
		no_net_lay.setVisibility(View.GONE);
	}

	void showNoDataUi() {
		mListView.setVisibility(View.GONE);
		noneData.setVisibility(View.VISIBLE);
		no_net_lay.setVisibility(View.GONE);
	}

	protected void showNoNetUi() {
		no_net_lay.setVisibility(View.VISIBLE);
		noneData.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (!AppUtil.networkAvailable(mContext)) {
				String refresh = getString(R.string.refreshing);
				ToastUtils.showToast(mContext, refresh);
				return;
			}
			if (dataList == null) {
				return;
			}
			Object obj = (Object) dataList.get(position - 1);
			if (obj instanceof String) {
				return;
			}
			Open_Activity(position-1);
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mListView.onRefreshComplete();
		}
	};

	public OnRefreshListener<ListView> onRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if (AppUtil.networkAvailable(mContext)) {
				headerRefresh = true;
				onRefresh = true;
				pageNumber = 1;
				new GetData(GeneralBLActivity.this, 1).execute("");
			} else {
				String refresh = getString(R.string.refreshing);
				ToastUtils.showToast(mContext, refresh);
				mListView.onRefreshComplete();
			}
		}

	};

	private OnLastItemVisibleListener onLastItemVisibleListener = new OnLastItemVisibleListener() {
		@Override
		public void onLastItemVisible() {
			if (AppUtil.networkAvailable(mContext)) {
				headerRefresh = false;
				pageNumber ++;
				new GetData(GeneralBLActivity.this, 1).execute("");
			} else {
				String refresh = getString(R.string.refreshing);
				ToastUtils.showToast(mContext, refresh);
			}
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
			YellowPageRequest request = new YellowPageRequest(GeneralBLActivity.this); // BLRequest
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
			mListView.onRefreshComplete();
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				// show empty alert
			} else {
				
				if(dataList!=null && dataList.size()!=0)
				{
					if(headerRefresh)
						dataList = new ArrayList<GeneralBLModel>();
				}
				else
					dataList = new ArrayList<GeneralBLModel>();
				try {
					if(headerRefresh)
						dataList = new YellowPageParse().parseGeneralBLResponse(result);
					else
					{
						dataList.addAll(new YellowPageParse().parseGeneralBLResponse(result));
					}
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
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
	    private TextView Language;
	}
	
	
	public class GeneralBLAdpater extends BaseAdapter {

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
						R.layout.general_bl_text, null);
				viewHolder.Title = (TextView) convertView.findViewById(R.id.Title);
				viewHolder.Contact = (TextView) convertView.findViewById(R.id.Contact);
				viewHolder.Language = (TextView) convertView.findViewById(R.id.language);
				ImageView Messageicon = (ImageView) convertView.findViewById(R.id.OrderMessage);
				Messageicon.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View v) {
						showPopup(generalbltext);
				    }
				});
				convertView.setTag(viewHolder);
			}
			String Title = generalbltext.getTitle();
			if (Title.length() > 32){
				Title = Title.substring(0,14) + ".....";
			}

			viewHolder.Title.setText(Title);
			viewHolder.Contact.setText(generalbltext.getContact());
			String language = "";
			if (generalbltext.getLanguage_C().equals("1")) {
				language = mContext.getString(R.string.cantonese);
			}

			if (generalbltext.getLanguage_E().equals("1")) {
				if (generalbltext.getLanguage_C().equals("1")) {
					language = language + "/"
							+ mContext.getString(R.string.english);
					;
				} else {
					language = mContext.getString(R.string.english);
					;
				}
			}

			if (generalbltext.getLanguage_M().equals("1")) {
				if (generalbltext.getLanguage_C().equals("1")
						|| generalbltext.getLanguage_E().equals("1")) {
					language = language + "/"
							+ mContext.getString(R.string.mandarin);
				} else {
					language = mContext.getString(R.string.mandarin);
				}
			}
			viewHolder.Language.setText(language);
			return convertView;
		}
	}
}
