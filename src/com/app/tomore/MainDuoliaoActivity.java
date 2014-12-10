
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




////package com.app.tomore;
////
////import com.app.tomore.components.RMFragmentPage;
////
////import android.app.Activity;
////import android.os.Bundle;
////import android.support.v4.app.Fragment;
////import android.support.v4.app.FragmentActivity;
////import android.view.LayoutInflater;
////import android.view.Menu;
////import android.view.MenuItem;
////import android.view.View;
////import android.view.ViewGroup;
////import android.view.View.OnClickListener;
////import android.view.WindowManager;
////import android.widget.Button;
////import android.app.Application;
////import android.content.Intent;
////
////public class MainDuoliaoActivity extends RMFragmentPage {
////	
////	@Override
////	public View onCreateView(LayoutInflater inflater, ViewGroup container,
////			Bundle savedInstanceState) {
////		return inflater.inflate(R.layout.main_duoliao_activity, null);   
////	}
////
////	@Override
////	public void fresh() {
////		// TODO Auto-generated method stub
////		
////	}
////	
////	
////}
//
//package com.app.tomore;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.app.tomore.R;
//import com.app.tomore.components.RMFragmentPage;
//import com.app.tomore.components.SlidingMenu;
//
//public class MainDuoliaoActivity extends RMFragmentPage implements OnClickListener{
//	SlidingMenu mSlidingMenu;
//
//	private ListView list_view,list_rightView;
//	private ListAdapter adapter,adapter1;;
//	public String[] info={"兰花","菊花","百合花","玫瑰","蹴鞠","薰衣草","栀子花","黄玫瑰"};
//	public String[] info1={"个人信息","相册设置","好友列表设置","社区设置","花蕾设置","退出"};
//	
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.main_duoliao_activity, null);   
//	}
//	
//
//	public void onCreate(Bundle arg0) {
//		android.app.Activity activity = new Activity();
//		super.onCreate(arg0);
//		activity.setContentView(R.layout.main);
//
//
//		DisplayMetrics dm = new DisplayMetrics();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		mSlidingMenu = (SlidingMenu) activity.findViewById(R.id.main_id);
//		mSlidingMenu.setAlignScreenWidth((dm.widthPixels / 5) * 2);
//		
//		View leftView=activity.getLayoutInflater().inflate(R.layout.main_left_fragment, null);
////		View rightView=getLayoutInflater().inflate(R.layout.right_menu, null);
//		View centerView=activity.getLayoutInflater().inflate(R.layout.main_duoliao_activity, null);
//		
////		list_view=(ListView)centerView.findViewById(R.id.main_listview);
//		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
//		for(String s:info)
//			adapter.add(s);
//		list_view.setAdapter(adapter);
//		
////		list_rightView=(ListView)rightView.findViewById(R.id.right_listview);
//		ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,info1);
//		list_rightView.setAdapter(adapter1);
//		
//		
//		mSlidingMenu.setLeftView(leftView);
////		mSlidingMenu.setRightView(rightView);
////		mSlidingMenu.setCenterView(centerView);
//        
//		ImageButton showLeftMenu=(ImageButton)centerView.findViewById(R.id.ivTitleBtnLeft);
//		showLeftMenu.setOnClickListener(this);
////		Button showRightMenu=(Button)centerView.findViewById(R.id.btn_Righ);
////		showRightMenu.setOnClickListener(this);
//		
//	
//	}
//
//
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.ivTitleBtnLeft:
//			mSlidingMenu.showLeftView();
//			break;
////        case R.id.btn_Righ:
////        	mSlidingMenu.showRightView();
////			break;
//		default:
//			break;
//		}
//	}
//
//
//	public void fresh() {
//		// TODO Auto-generated method stub
//		
//	}
//	
//}
//
