package com.app.tomore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.app.tomore.net.UserCenterRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private DialogActivity dialog;
	
	EditText etLoginEmail;
	EditText etLoginPassword;
	
	String loginEmail;
	String loginPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etLoginEmail = (EditText) findViewById(R.id.etEmail);
		etLoginPassword = (EditText) findViewById(R.id.etPassword);
	}
	
	public void onLoginClick(View view){
		loginEmail = etLoginEmail.getText().toString();
		loginPassword = etLoginPassword.getText().toString();
		new Login(LoginActivity.this, 1).execute("");
	}
	
	public void onRegisterClick(View view){				
    	Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);   		
	}
	
	private class Login extends AsyncTask<String, String, String> {
		private int mType;

		private Login(Context context, int type) {
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
			UserCenterRequest request = new UserCenterRequest(
					LoginActivity.this);
			try {
				result = request.getLoginResponse(loginEmail, loginPassword);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
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
		    	Toast.makeText(getApplicationContext(), "请输入有效的邮箱和密码",
						Toast.LENGTH_SHORT).show();
			}else {
				Gson gson = new Gson();
				JsonElement jelement = new JsonParser().parse(result);
			    JsonObject  jobject = jelement.getAsJsonObject();
			    String loninResult = jobject.get("result").toString();
			    if(loninResult.equals("\"succ\"")){					
			    	Intent intent = new Intent(LoginActivity.this, MainDuoliaoActivity.class);
					startActivity(intent);   
			    }else {
			    	Toast.makeText(getApplicationContext(), "请输入有效的邮箱和密码",
							Toast.LENGTH_SHORT).show();
			    }
			}
		}
	}
	
}