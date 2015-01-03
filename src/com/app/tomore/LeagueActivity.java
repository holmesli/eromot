package com.app.tomore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.tomore.beans.CommonModel;
import com.app.tomore.net.ToMoreParse;
import com.app.tomore.net.YellowPageRequest;

public class LeagueActivity extends Activity{

	private TextView submit;
	private EditText nameText;
	private EditText contactText;
	private EditText messageText;
	private String finalResult = null;
	private View layout;
	String result = null;
	private LayoutInflater inflater; 
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
	        	YellowPageRequest request = new YellowPageRequest(LeagueActivity.this);
	    		try {
					result = request.postFeedbackToAdmin(messageText.getText().toString(), nameText.getText().toString(), contactText.getText().toString(), contactText.getText().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		ToMoreParse getrequest= new ToMoreParse();
	    		CommonModel returnResult = getrequest.CommonPares(result);
	    		finalResult = returnResult.getResult();
	    		if(finalResult.equals("succ")){
	    			PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.league_pop_window, null, false),100,100, true);
	    			pw.showAtLocation(layout, Gravity.TOP, 0, 0);
	    		}
	        }
	    });
	}

	
}
