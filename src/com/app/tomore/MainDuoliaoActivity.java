
package com.app.tomore;

import com.app.tomore.fragment.BackToMainActivity;
import com.app.tomore.httpclient.AndroidHttpClient;
import com.app.tomore.httpclient.AsyncCallback;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainDuoliaoActivity extends Activity implements OnClickListener{
	private TextView bt1;
	private TextView bt2;
	private TextView bt3;
	private TextView bt4,bt5,bt6,bt7;
	private Context context;
	private ImageButton menubtn;
	private SlidingMenu menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_duoliao_activity);
		
//		AndroidHttpClient httpClient = new AndroidHttpClient("http://54.213.167.5/APIV2/");
//        httpClient.setMaxRetries(5);
		
//        ParameterMap params = httpClient.newParams()
//                .add("articleIssue", "0");
//                //.add("email", "test@example.com")
//                //.add("action", "Log In");
//        httpClient.post("getArticleByArticleIssue.php", params, new AsyncCallback() {
//            public void onSuccess(HttpResponse httpResponse) {
//                System.out.println(httpResponse.getBodyAsString());
//                
//                
//            }
//            @Override
//            public void onError(Exception e) {
//                e.printStackTrace();
//            }
//			@Override
//			public void onComplete(HttpResponse httpResponse) {
//				// TODO Auto-generated method stub
//				
//			}
//        });
		
		
		
		context=this;
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View view=LayoutInflater.from(this).inflate(R.layout.main_left_fragment, null);
		menu.setMenu(view);
		bt1=(TextView)view.findViewById(R.id.my_backtomain_bt);
		bt2=(TextView)view.findViewById(R.id.my_tiezi_bt);
		bt3=(TextView)view.findViewById(R.id.my_guanzhu_bt);
		bt4=(TextView)view.findViewById(R.id.my_fensi_bt);
		bt4=(TextView)view.findViewById(R.id.my_blacklist_bt);
		bt4=(TextView)view.findViewById(R.id.my_aboutus_bt);
		bt4=(TextView)view.findViewById(R.id.my_logout_bt);
		menubtn=(ImageButton)findViewById(R.id.ivTitleBtnLeft);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		menubtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		BackToMainActivity newContent = null;
		int id = v.getId();
		if (id == R.id.my_backtomain_bt) {
			newContent = new BackToMainActivity();
		} else if (id == R.id.my_tiezi_bt) {
			Toast.makeText(context, "����2", 1).show();
		} else if (id == R.id.my_guanzhu_bt) {
			Toast.makeText(context, "����3", 1).show();
		} else if (id == R.id.ivTitleBtnLeft) {
			menu.toggle();
		} else if (id == R.id.my_fensi_bt) {
			Toast.makeText(context, "����1", 1).show();
		} else if (id == R.id.my_blacklist_bt) {
			Toast.makeText(context, "����1", 1).show();
		} else if (id == R.id.my_aboutus_bt) {
			Toast.makeText(context, "����1", 1).show();
		}

	}
	
	public void onLogoutClick (View view){		
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);   
	}
}



