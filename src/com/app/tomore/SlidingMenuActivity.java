//package com.app.tomore;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.app.tomore.R;
//import com.app.tomore.fragment.SlidingMenu;
//
//public class SlidingMenuActivity extends Activity implements OnClickListener{
//	SlidingMenu mSlidingMenu;
//
//	//private ListView list_view,list_rightView;
//	//private ListAdapter adapter,adapter1;;
//	//public String[] info={"兰花","菊花","百合花","玫瑰","蹴鞠","薰衣草","栀子花","黄玫瑰"};
//	///public String[] info1={"个人信息","相册设置","好友列表设置","社区设置","花蕾设置","退出"};
//	@Override
//	protected void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.activity_main);
//
//
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
//		mSlidingMenu.setAlignScreenWidth((dm.widthPixels / 5) * 2);
//		
//		View leftView=getLayoutInflater().inflate(R.layout.main_left_fragment, null);
//		//View rightView=getLayoutInflater().inflate(R.layout.right_menu, null);
//		View centerView=getLayoutInflater().inflate(R.layout.activity_main, null);
//		
//		//list_view=(ListView)centerView.findViewById(R.id.main_listview);
//		//ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
//		//for(String s:info)
//		//	adapter.add(s);
//		//list_view.setAdapter(adapter);
//		
//		//list_rightView=(ListView)rightView.findViewById(R.id.right_listview);
//		//ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,info1);
//		//list_rightView.setAdapter(adapter1);
//		
//		
//		mSlidingMenu.setLeftView(leftView);
//		//mSlidingMenu.setRightView(rightView);
//		mSlidingMenu.setCenterView(centerView);
//        
//		ImageButton showLeftMenu=(ImageButton)centerView.findViewById(R.id.ivTitleBtnLeft);
//		showLeftMenu.setOnClickListener(this);
//		ImageButton showRightMenu=(ImageButton)centerView.findViewById(R.id.ivTitleBtnRigh);
//		showRightMenu.setOnClickListener(this);
//		
//	
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.ivTitleBtnLeft:
//			mSlidingMenu.showLeftView();
//			break;
//        case R.id.ivTitleBtnRigh:
//        	mSlidingMenu.showRightView();
//			break;
//		default:
//			break;
//		}
//	}
//	
//}
