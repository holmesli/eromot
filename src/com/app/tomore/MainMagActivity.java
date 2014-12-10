package com.app.tomore;

import android.app.Activity;
import android.os.Bundle;

public class MainMagActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_mag_activity);
	}
}


//package com.app.tomore;
//
//import com.app.tomore.components.RMFragmentPage;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.app.Application;
//import android.content.Intent;
//
//public class MainMagActivity extends RMFragmentPage {
//	
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.main_mag_activity, null);   
//	}
//
//	@Override
//	public void fresh() {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	
//}
