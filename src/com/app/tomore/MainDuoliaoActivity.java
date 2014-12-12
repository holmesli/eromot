
package com.app.tomore;

import com.app.tomore.fragment.BackToMainActivity;
import com.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainDuoliaoActivity extends Activity implements OnClickListener{
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4,bt5,bt6,bt7;
	private Context context;
	private ImageButton menubtn;
	private SlidingMenu menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_duoliao_activity);
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
		bt1=(Button)view.findViewById(R.id.my_backtomain_bt);
		bt2=(Button)view.findViewById(R.id.my_tiezi_bt);
		bt3=(Button)view.findViewById(R.id.my_guanzhu_bt);
		bt4=(Button)view.findViewById(R.id.my_fensi_bt);
		bt4=(Button)view.findViewById(R.id.my_blacklist_bt);
		bt4=(Button)view.findViewById(R.id.my_aboutus_bt);
		bt4=(Button)view.findViewById(R.id.my_logout_bt);
		menubtn=(ImageButton)findViewById(R.id.ivTitleBtnLeft);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		menubtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		BackToMainActivity newContent = null;
		switch (v.getId()) {
		case R.id.my_backtomain_bt:
			newContent = new BackToMainActivity();
			//Toast.makeText(context, "点我1", 1).show();
			break;
		case R.id.my_tiezi_bt:
			Toast.makeText(context, "点我2", 1).show();
			break;
		case R.id.my_guanzhu_bt:
			Toast.makeText(context, "点我3", 1).show();
			break;
		case R.id.ivTitleBtnLeft:
			menu.toggle();
			break;
		case R.id.my_fensi_bt:
			Toast.makeText(context, "点我1", 1).show();
			break;
		case R.id.my_blacklist_bt:
			Toast.makeText(context, "点我1", 1).show();
			break;
		case R.id.my_aboutus_bt:
			Toast.makeText(context, "点我1", 1).show();
			break;
		case R.id.my_logout_bt:
			Toast.makeText(context, "点我1", 1).show();
			break;
		}

	}
}



