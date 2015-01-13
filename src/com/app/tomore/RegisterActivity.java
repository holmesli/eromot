package com.app.tomore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.app.tomore.net.UserCenterParse;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private DialogActivity dialog;
	
	EditText etRegisterEmail;
	EditText etRegisterUserName;
	EditText etRegisterPassword;
	EditText etRegisterConfirmPassword;
	Spinner spRegisterSchool;
	EditText etRegisterMajor;
	RadioGroup rgRegisterGenderGroup;
	RadioButton registerGenderSelect;
	CheckBox cbAgreement;
	
	String registerEmail;
	String registerUserName;
	String registerPassword;
	String registerConfirmPassword;
	String registerSchool;
	String registerMajor;
	String registerGender;
	Boolean registerAgreement;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		etRegisterEmail = (EditText) findViewById(R.id.etRegisterEmail);
		etRegisterUserName = (EditText) findViewById(R.id.etRegisterUserName);
		etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
		etRegisterConfirmPassword = (EditText) findViewById(R.id.etRegisterConfirmPassword);
		spRegisterSchool = (Spinner) findViewById(R.id.spRegisterSchool);
		etRegisterMajor = (EditText) findViewById(R.id.etRegisterMajor);
		cbAgreement = (CheckBox) findViewById(R.id.cbAgreement);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.schoolArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRegisterSchool.setAdapter(adapter);
		rgRegisterGenderGroup = (RadioGroup) findViewById(R.id.rgRegisterGenderGroup);
	}
	
	public void onBackClick(View view){
    	Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
		startActivity(intent);   		
	}
	/*
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.rbRegisterMale:
			if (checked)
				// Pirates are the best
				break;
		case R.id.rbRegisterFemale:
			if (checked)
				// Ninjas rule
				break;
		}
	}*/
	
	public void onCheckedClick(View view){
		registerAgreement = ((CheckBox) view).isChecked();
	}
	
	public void onRegisterClick(View view){
		registerEmail = etRegisterEmail.getText().toString();
		registerUserName = etRegisterUserName.getText().toString();
		registerPassword = etRegisterPassword.getText().toString();
		registerConfirmPassword = etRegisterConfirmPassword.getText().toString();
		registerSchool = spRegisterSchool.getSelectedItem().toString();
		registerMajor = etRegisterMajor.getText().toString();
		registerGenderSelect = (RadioButton) findViewById(rgRegisterGenderGroup
				.getCheckedRadioButtonId());
		registerGender = registerGenderSelect.getText().toString();
		if(registerAgreement.equals(true)){
			if(registerPassword.equals(registerConfirmPassword) && registerPassword.length() >= 3 && registerPassword.length() <= 20){
				new Register(RegisterActivity.this, 1).execute("");
			}else{
		    	Toast.makeText(getApplicationContext(), "请输入有效的密码",
						Toast.LENGTH_SHORT).show();				
			}
		}else{
	    	Toast.makeText(getApplicationContext(), "需要同意隐私协议和服务条款",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private class Register extends AsyncTask<String, String, String> {
		private int mType;

		private Register(Context context, int type) {
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
//			String email, String userName, String password, String school, String major, String gender
			String result = null;
			UserCenterRequest request = new UserCenterRequest(
					RegisterActivity.this);
			try {
				result = request.getLoginResponse(registerEmail, registerUserName, registerPassword, registerSchool, registerMajor, registerGender);
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
			if (result == null || result.equals("")) {
		    	Toast.makeText(getApplicationContext(), "ע注册失败",
						Toast.LENGTH_SHORT).show();
			}else {
				UserCenterParse ucParse = new UserCenterParse();
				String registerResult = ucParse.parseRegisterResponse(result);
			    if(registerResult.equals("\"succ\"")){	
			    	Intent intent = new Intent(RegisterActivity.this, MainDuoliaoActivity.class);
					startActivity(intent);   
			    }else if(registerResult.equals("\"2\"")){
			    	Toast.makeText(getApplicationContext(), "用户名已存在",
							Toast.LENGTH_SHORT).show();
			    }else if(registerResult.equals("\"3\"")){
			    	Toast.makeText(getApplicationContext(), "邮箱已存在",
							Toast.LENGTH_SHORT).show();
			    }else{
			    	Toast.makeText(getApplicationContext(), "注册失败",
							Toast.LENGTH_SHORT).show();			    	
			    }
			}
		}
	}
}
