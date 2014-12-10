package com.app.tomore;


import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.Application;
import android.content.Intent;
import android.app.TabActivity;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener {
	//private View magMain;  
	//private View bianliMain; 
	//private View memMain;
	private TabHost tabHost;
	private TextView main_tab_new_message;
	private RadioGroup radioderGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
//        main_tab_new_message.setVisibility(View.VISIBLE);
//        main_tab_new_message.setText("10");
        
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, MainDuoliaoActivity.class);
        spec=tabHost.newTabSpec("多聊").setIndicator("多聊").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,MainMagActivity.class);
        spec=tabHost.newTabSpec("杂志").setIndicator("杂志").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, MainBLActivity.class);
        spec=tabHost.newTabSpec("便利").setIndicator("便利").setContent(intent);
        tabHost.addTab(spec);
        
     
        intent=new Intent().setClass(this, MainMemActivity.class);
        spec=tabHost.newTabSpec("卡包").setIndicator("卡包").setContent(intent);
        tabHost.addTab(spec);
        //tabHost.setCurrentTab(0);
        radioderGroup = (RadioGroup) findViewById(R.id.main_tab_group);
		radioderGroup.setOnCheckedChangeListener(this);
		radioderGroup.check(R.id.main_duoliao);
	}
        
//        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
//        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_duoliao://Ìí¼Ó¿¼ÊÔ
					tabHost.setCurrentTabByTag("多聊");
					break;
				case R.id.main_mag://ÎÒµÄ¿¼ÊÔ
					tabHost.setCurrentTabByTag("杂志");
					break;
				case R.id.main_bianli://ÎÒµÄÍ¨Öª
					tabHost.setCurrentTabByTag("便利");
					break;
				case R.id.main_member://ÉèÖÃ
					tabHost.setCurrentTabByTag("卡包");
					break;
				default:
					//tabHost.setCurrentTabByTag("ÎÒµÄ¿¼ÊÔ");
					break;
				
			}
	
	}
	
	 //public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    	//	Bundle savedInstanceState) {
	    	// View view = inflater.inflate(R.layout.bar_bottom, container,
	 			//	false);
	    	 //magMain = view.findViewById(R.id.main_meg);
	    	// magMain.setOnClickListener(this);
	    	 //bianliMain = view.findViewById(R.id.main_bianli);
	    	 //bianliMain.setOnClickListener(this);
	    	 //memMain = view.findViewById(R.id.main_member);
	    	 //memMain.setOnClickListener(this);
	    	 
	 		//System.out.println();
	    	//return view;
	   // }
		//Button magMain = (Button) findViewById(R.id.main_meg);
		//Button bianliMain = (Button) findViewById(R.id.main_bianli);
		//Button memMain = (Button) findViewById(R.id.main_member);
		//magMain.setOnClickListener(new OnClickListener(){

//			@Override
//			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent();   
				//intent.setClass(MainActivity.this, MainMagActivity.class);
				//startActivity(intent);
				//setContentView(R.layout.main_mag_activity);
				//MainMagActivity newContent = null;
				//switch (v.getId()) {
				//case R.id.main_meg: //æ˜“ä¿¡çš„ç‚¹å‡»äº‹ä»¶
				//	newContent = new MainMagActivity();
				//	magMain.setSelected(true);
				//	bianliMain.setSelected(false);
				//	memMain.setSelected(false);
					
				//	break;
				//case R.id.main_bianli: //æœ‹å�‹åœˆçš„ç‚¹å‡»äº‹ä»¶
				//	newContent = new MainMagActivity();
				//	magMain.setSelected(false);
				//	bianliMain.setSelected(true);
				//	memMain.setSelected(false);
				//	break;
				//case R.id.main_member: //è®¾ç½®çš„ç‚¹å‡»äº‹ä»¶
				//	newContent = new MainMagActivity();
				//	magMain.setSelected(false);
				//	bianliMain.setSelected(false);
				//	memMain.setSelected(true);
				//    break;
				//default:
				//	break;
				}
				
				
			//}
			
			
		
		//bianliMain.setOnClickListener(new OnClickListener(){
			//@Override
			//public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent();   
				//intent.setClass(MainActivity.this, MainMagActivity.class);
				//startActivity(intent);
			//	setContentView(R.layout.main_bianli_activity);
		//	}
		//});
		//memMain.setOnClickListener(new OnClickListener(){
			//@Override
			//public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent();   
				//intent.setClass(MainActivity.this, MainMagActivity.class);
				//startActivity(intent);
			//	setContentView(R.layout.main_member_activity);
			//}//
		//});
		


	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
	//	return true;
	//}

	//@Override
	//public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	//	int id = item.getItemId();
	//	if (id == R.id.action_settings) {
	//		return true;
	//	}
	//	return super.onOptionsItemSelected(item);
	//}
//}



//package com.app.tomore;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.RadioGroup;
//import android.widget.TabHost;
//import android.widget.TextView;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.app.Application;
//import android.content.Intent;
//


//package com.app.tomore;
//
//
//import com.app.tomore.components.RMFragmentTabController;
//import com.app.tomore.components.RMFragmentTabController.RMFragmentTabControlable;
//import com.app.tomore.components.RMFragmentTabItem;
//import com.app.tomore.MainDuoliaoActivity;
//import com.app.tomore.MainMagActivity;
//import com.app.tomore.MainBLActivity;
//import com.app.tomore.MainMemActivity;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTabHost;
//import android.view.View;
//
//public class MainActivity extends FragmentActivity implements
//		RMFragmentTabControlable {
//
//	private FragmentTabHost mTabHost;
//	private RMFragmentTabController tabController;
//
//	private final static int TABCOUNT = 4;
//
//	private RMFragmentTabItem homeTab = null;
//
//	private RMFragmentTabItem messageTab = null;
//
//	private RMFragmentTabItem customersTab = null;
//
//	private RMFragmentTabItem moreTab = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		initUI();
//	}
//
//	protected void initUI() {
//		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//
//		tabController = new RMFragmentTabController(this);
//		tabController.setupTab();
//	}
//
//	private RMFragmentTabItem getHomeTab() {
//		if (homeTab == null) {
//			homeTab = new RMFragmentTabItem();
//			homeTab.setName("1");
//			homeTab.setFragment(MainDuoliaoActivity.class);
//			homeTab.setIconResourceId(R.drawable.bar_bottom_duoliao);
//		}
//		return homeTab;
//	}
//
//	private RMFragmentTabItem getMessageTab() {
//		if (messageTab == null) {
//			messageTab = new RMFragmentTabItem();
//			messageTab.setName("2");
//			messageTab.setFragment(MainMagActivity.class);
//			messageTab.setIconResourceId(R.drawable.bar_bottom_meg);
//		}
//		return messageTab;
//	}
//
//	private RMFragmentTabItem getCustomerTab() {
//		if (customersTab == null) {
//			customersTab = new RMFragmentTabItem();
//			customersTab.setName("3");
//			customersTab.setFragment(MainBLActivity.class);
//			customersTab.setIconResourceId(R.drawable.bar_bottom_bianli);
//		}
//		return customersTab;
//	}
//
//	private RMFragmentTabItem getMoreTab() {
//		if (moreTab == null) {
//			moreTab = new RMFragmentTabItem();
//			moreTab.setName("4");
//			moreTab.setFragment(MainMemActivity.class);
//			moreTab.setIconResourceId(R.drawable.bar_bottom_member);
//		}
//		return moreTab;
//	}
//
//	@Override
//	public FragmentActivity getActivity() {
//		return this;
//	}
//
//	@Override
//	public FragmentTabHost getTabHost() {
//		return mTabHost;
//	}
//
//	@Override
//	public int getTabCount() {
//		return TABCOUNT;
//	}
//
//	@Override
//	public RMFragmentTabItem getTabItem(int tabIndex) {
//		switch (tabIndex) {
//		case 0:
//			return getHomeTab();
//		case 1:
//			return getMessageTab();
//		case 2:
//			return getCustomerTab();
//		case 3:
//			return getMoreTab();
//		default:
//			break;
//		}
//		return null;
//	}
//
//	@Override
//	public View getTabView(int tabIndex) {
//		return null;
//	}
//
//}


//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.text.Html;
//import android.text.Html.ImageGetter;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//public class MainActivity extends Activity {
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //EditText et=(EditText)findViewById(R.id.EditText1);
        
//        ImageGetter imageGetter = new ImageGetter() {
//
//    		public Drawable getDrawable(String source) {
//    			int id = Integer.parseInt(source);
//    			Drawable d = getResources().getDrawable(id);
//    			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//    			return d;
//    		} 
    //	};
//    	et.append("hello");
//    	et.append(Html.fromHtml("<img src='"+ R.drawable.smile +"'/>", imageGetter, null));  
//    	et.append("fasdf");
//    	et.append(Html.fromHtml("<img src='"+ R.drawable.smile +"'/>", imageGetter, null));  
//    	Button b = (Button)findViewById(R.id.Button01);
//    	b.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				Intent i = new Intent();
//				i.setClass(Main.this, Main2.class);
//				startActivity(i);
			//}
		//});
    //}

	
//}
//public class MainActivity extends Activity implements OnClickListener {
//	//private View magMain;  
//	//private View bianliMain; 
//	//private View memMain;
//	//private TabHost tabHost;
//	//private TextView main_tab_new_message;
//
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//	}
//
//	//@Override
//	//protected void onCreate(Bundle savedInstanceState) {
//		//super.onCreate();
//		//setContentView(R.layout.activity_main);
//		
//		//main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
//        //main_tab_new_message.setVisibility(View.VISIBLE);
//        //main_tab_new_message.setText("10");
//        
//        //tabHost=this.getTabHost();
//       // TabHost.TabSpec spec;
//        //Intent intent;
//
//        //intent=new Intent().setClass(this, MainDuoliaoActivity.class);
//        //spec=tabHost.newTabSpec("多聊").setIndicator("多多聊").setContent(intent);
//        //tabHost.addTab(spec);
//        
//        //intent=new Intent().setClass(this,MainMagActivity.class);
//        //spec=tabHost.newTabSpec("杂志").setIndicator("杂杂志").setContent(intent);
//        //tabHost.addTab(spec);
//        
//        //intent=new Intent().setClass(this, MainBLActivity.class);
//       /// spec=tabHost.newTabSpec("便利").setIndicator("便便利").setContent(intent);
//        //tabHost.addTab(spec);
//        
//     
//       // intent=new Intent().setClass(this, MainMemActivity.class);
//       // spec=tabHost.newTabSpec("卡包").setIndicator("卡卡包").setContent(intent);
//       // tabHost.addTab(spec);
//       // tabHost.setCurrentTab(1);
//        
//        //RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
//        //radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//		//	@Override
//		//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//			//	switch (checkedId) {
//			//	case R.id.main_duoliao://Ìí¼Ó¿¼ÊÔ
//			//		tabHost.setCurrentTabByTag("多聊");
//			//		break;
//			//	case R.id.main_mag://ÎÒµÄ¿¼ÊÔ
//			//		tabHost.setCurrentTabByTag("杂志");
//			//		break;
//			//	case R.id.main_bianli://ÎÒµÄÍ¨Öª
//			//		tabHost.setCurrentTabByTag("便利");
//			//		break;
//			//	case R.id.main_member://ÉèÖÃ
//			//		tabHost.setCurrentTabByTag("卡包");
//			//		break;
//			//	default:
//					//tabHost.setCurrentTabByTag("ÎÒµÄ¿¼ÊÔ");
//			//		break;
//			//	}
//			//}
//		//});
//	//}
//	
//	 //public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	    	//	Bundle savedInstanceState) {
//	    	// View view = inflater.inflate(R.layout.bar_bottom, container,
//	 			//	false);
//	    	 //magMain = view.findViewById(R.id.main_meg);
//	    	// magMain.setOnClickListener(this);
//	    	 //bianliMain = view.findViewById(R.id.main_bianli);
//	    	 //bianliMain.setOnClickListener(this);
//	    	 //memMain = view.findViewById(R.id.main_member);
//	    	 //memMain.setOnClickListener(this);
//	    	 
//	 		//System.out.println();
//	    	//return view;
//	   // }
//		//Button magMain = (Button) findViewById(R.id.main_meg);
//		//Button bianliMain = (Button) findViewById(R.id.main_bianli);
//		//Button memMain = (Button) findViewById(R.id.main_member);
//		//magMain.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//Intent intent = new Intent();   
//				//intent.setClass(MainActivity.this, MainMagActivity.class);
//				//startActivity(intent);
//				//setContentView(R.layout.main_mag_activity);
//				//MainMagActivity newContent = null;
//				//switch (v.getId()) {
//				//case R.id.main_meg: //æ˜“ä¿¡çš„ç‚¹å‡»äº‹ä»¶
//				//	newContent = new MainMagActivity();
//				//	magMain.setSelected(true);
//				//	bianliMain.setSelected(false);
//				//	memMain.setSelected(false);
//					
//				//	break;
//				//case R.id.main_bianli: //æœ‹å�‹åœˆçš„ç‚¹å‡»äº‹ä»¶
//				//	newContent = new MainMagActivity();
//				//	magMain.setSelected(false);
//				//	bianliMain.setSelected(true);
//				//	memMain.setSelected(false);
//				//	break;
//				//case R.id.main_member: //è®¾ç½®çš„ç‚¹å‡»äº‹ä»¶
//				//	newContent = new MainMagActivity();
//				//	magMain.setSelected(false);
//				//	bianliMain.setSelected(false);
//				//	memMain.setSelected(true);
//				//    break;
//				//default:
//				//	break;
//				}
//				
//				
//			//}
//			
//			
//		
//		//bianliMain.setOnClickListener(new OnClickListener(){
//			//@Override
//			//public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//Intent intent = new Intent();   
//				//intent.setClass(MainActivity.this, MainMagActivity.class);
//				//startActivity(intent);
//			//	setContentView(R.layout.main_bianli_activity);
//		//	}
//		//});
//		//memMain.setOnClickListener(new OnClickListener(){
//			//@Override
//			//public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//Intent intent = new Intent();   
//				//intent.setClass(MainActivity.this, MainMagActivity.class);
//				//startActivity(intent);
//			//	setContentView(R.layout.main_member_activity);
//			//}//
//		//});
//		
//
//
//	//@Override
//	//public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//	//	getMenuInflater().inflate(R.menu.main, menu);
//	//	return true;
//	//}
//
//	//@Override
//	//public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//	//	int id = item.getItemId();
//	//	if (id == R.id.action_settings) {
//	//		return true;
//	//	}
//	//	return super.onOptionsItemSelected(item);
//	//}
//}
