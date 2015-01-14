package com.app.tomore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.app.tomore.beans.CommonModel;
import com.app.tomore.net.ToMoreParse;
import com.app.tomore.net.YellowPageRequest;
import com.google.gson.JsonSyntaxException;

public class LeagueActivity extends Activity{

	private TextView submit;
	private EditText nameText;
	private EditText contactText;
	private EditText messageText;
	private String finalResult = null;
	private View layout;
	String result = null;
	private LayoutInflater inflater; 
	private DialogActivity dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		setContentView(R.layout.league_layout);
		inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		submit = (TextView)findViewById(R.id.submit_button); 
		nameText = (EditText)findViewById(R.id.nameText); 
		contactText = (EditText)findViewById(R.id.contactText); 
		messageText = (EditText)findViewById(R.id.messageText); 
		layout = findViewById(R.id.container);
		submit.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View viewIn) {
	    		new GetData(LeagueActivity.this, 1).execute("");
	        }
	    });
		final Button btnBack = (Button) layout.findViewById(R.id.bar_title_league_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
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
			YellowPageRequest request = new YellowPageRequest(LeagueActivity.this);
			try {
				Log.d("doInBackground", "start request");
				result = request.postFeedbackToAdmin(messageText.getText().toString(), nameText.getText().toString(), contactText.getText().toString(), contactText.getText().toString());
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
				CommonModel returnResult = new CommonModel();
				try {
					ToMoreParse getrequest= new ToMoreParse();
		    		 returnResult = getrequest.CommonPares(result);
		    		} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				finalResult = returnResult.getResult();
	    		if(finalResult.equals("succ")){
	    			final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.league_pop_window, null, false),400,170, true);
	    			pw.setFocusable(true);  
	    			//pw.setBackgroundDrawable(new BitmapDrawable());
	    			pw.setOutsideTouchable(true);
	    		     final Runnable r = new Runnable() {
	    		            @Override
	    		            public void run() {
	    		                if(pw.isShowing()) {
	    		                    pw.dismiss();
	    		                }
	    		            }
	    		        };
	    	        final Handler handler = new Handler();
	    	        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
	    	            @Override
	    	            public void onDismiss() {
	    	                handler.removeCallbacks(r);
	    	            }
	    	        });
	    			pw.showAtLocation(layout, Gravity.TOP, 0, 200);
	    	        handler.postDelayed(r, 2000);


			        
			        new Handler().postDelayed(new Runnable() {
			            public void run() {
				        	Intent Main_BL_intent;
				        	Main_BL_intent = new Intent(LeagueActivity.this,
				        			MainBLActivity.class);
					        startActivityForResult(Main_BL_intent, 100);;
							finish();
			            }
			        }, 2200);
			        
	    		}
			}
		}
	}
	
}
