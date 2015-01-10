package com.app.tomore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tomore.beans.CommonModel;
import com.app.tomore.net.MagRequest;
import com.app.tomore.net.ToMoreParse;
import com.google.gson.JsonSyntaxException;

public class PostCommentActivity extends Activity{

	private Button submit;
	private EditText content;
	private String finalResult = null;
	private View layout;
	String result = null;
	private LayoutInflater inflater; 
	private DialogActivity dialog;
	private String memberId;
	private String articleId;
	private String page = "1";
	private String limit = "10";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		setContentView(R.layout.mag_comment_submit);
		//inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		submit = (Button)findViewById(R.id.bar_title_bt_postcomment); 
		content = (EditText)findViewById(R.id.commentContent); 
		layout = findViewById(R.id.commentPostLayout);
		Intent intent=getIntent();
		articleId=intent.getStringExtra("articleCommentId");
		memberId = intent.getStringExtra("memberid");
		RelativeLayout rl = (RelativeLayout) getWindow().getDecorView()
				.findViewById(R.id.bar_title_commentpost);
		final Button btnBack = (Button) rl
				.findViewById(R.id.bar_title_bt_comment);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View viewIn) {
	    		new GetData(PostCommentActivity.this, 1).execute("");
	    		Intent i = new Intent();
	    		i .setClass(PostCommentActivity.this, MagCommentActivity.class);
	    		i.putExtra("articleid", articleId);
				startActivity(i);
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
			MagRequest request = new MagRequest(PostCommentActivity.this);
			try {
				Log.d("doInBackground", "start request");
				result = request.PostCommentByMemberId(articleId, memberId, content.getText().toString());
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
	    			Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
	    			
	    			
	    			

	    		}
	    		else
	    		{
	    			Toast.makeText(getApplicationContext(), "请重新发送", Toast.LENGTH_SHORT).show();
	    		}
			}
		}
	}
	
}
