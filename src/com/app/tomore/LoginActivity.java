package com.app.tomore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.app.tomore.net.UserCenterParse;
import com.app.tomore.net.UserCenterRequest;
import com.app.tomore.beans.UserModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.tomore.utils.SpUtils;

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
				UserCenterParse ucParse = new UserCenterParse();
				UserModel usermodel = ucParse.parseLoginResponse(result);
			    if(usermodel!=null){
			    	SpUtils.saveUserId(LoginActivity.this, usermodel.getMemberID());
			    	finish();
			    }else {
			    	Toast.makeText(getApplicationContext(), "请输入有效的邮箱和密码",
							Toast.LENGTH_SHORT).show();
			    }
			}
		}
	}
	
}