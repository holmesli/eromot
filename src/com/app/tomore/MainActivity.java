package com.app.tomore;


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

import com.app.tomore.httpclient.*;
//import com.app.tomore.httpclient.android.*;
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
		
//http://54.213.167.5/APIV2/getArticleByArticleIssue.php?&articleIssue=0
		//BasicHttpClient aClient =new BasicHttpClient("http://54.213.167.5/APIV2/");
		//ParameterMap aMap = new ParameterMap().add("articleIssue", "0");
		//HttpResponse aRespone = aClient.get("getArticleByArticleIssue.php", aMap);
//	byte[] responsBogy = aRespone.getBody();
		//System.out.println(aRespone.getBodyAsString());
		
		
		AndroidHttpClient httpClient = new AndroidHttpClient("http://54.213.167.5/APIV2/");
        httpClient.setMaxRetries(5);
        ParameterMap params = httpClient.newParams()
                .add("articleIssue", "0");
             
        httpClient.post("getArticleByArticleIssue.php", params, new AsyncCallback() {
            
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
			@Override
			public void onComplete(HttpResponse httpResponse) {
				// TODO Auto-generated method stub
				System.out.println(httpResponse.getBodyAsString());
			}
        });
		
		
        
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
}