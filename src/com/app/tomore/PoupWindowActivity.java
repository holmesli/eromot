//package com.app.tomore;
//
//import java.util.ArrayList;  
//import java.util.List;  
//  
//
//
//import android.app.Activity;  
//import android.content.Context;  
//import android.graphics.drawable.BitmapDrawable;  
//import android.os.Bundle;  
//import android.util.Log;  
//import android.view.LayoutInflater;  
//import android.view.Menu;
//import android.view.View;  
//import android.view.Window;  
//import android.view.WindowManager;  
//import android.widget.AdapterView;  
//import android.widget.AdapterView.OnItemClickListener;  
//import android.widget.BaseAdapter;
//import android.widget.ListView;  
//import android.widget.PopupWindow;  
//import android.widget.TextView;  
//import android.widget.Toast;  
//  
//public class PoupWindowActivity extends Activity {  
//  
//	private String[] allOptionsMenuTexts = {"删除","保存","帮助","添加","详细","发送","电话","照相"};  
//	   private int[] allOptionsMenuOrders = {5,2,6,1,4,3,7,8};  
//	   private int[] allOptionsMenuIds = {Menu.FIRST+1,Menu.FIRST+2,Menu.FIRST+3,Menu.FIRST+4,Menu.FIRST+5,Menu.FIRST+6,Menu.FIRST+7,Menu.FIRST+8};  
//	   private int[] allOptionsMenuIcons = {  
//	        android.R.drawable.ic_menu_delete,  
//	        android.R.drawable.ic_menu_edit,  
//	        android.R.drawable.ic_menu_help,  
//	        android.R.drawable.ic_menu_add,  
//	        android.R.drawable.ic_menu_info_details,  
//	        android.R.drawable.ic_menu_send,  
//	        android.R.drawable.ic_menu_call,  
//	        android.R.drawable.ic_menu_camera  
//	        };  
//	  
//	   @Override  
//	   public void onCreate(Bundle savedInstanceState) {  
//	       super.onCreate(savedInstanceState);  
//	       setContentView(R.layout.main_dialog);  
//	   }  
//	     
//	  
//	     
//	   public void clickButtonDialog8(View v){  
//	    showDialog8();  
//	   }  
//	     
//	  
//	public void showDialog8(){  
//	    final Context context = this;  
//	      
//	    //获取自定义布局  
//	    LayoutInflater layoutInflater = getLayoutInflater();  
//	    View menuView = layoutInflater.inflate(R.layout.menu_gridview, null);  
//	      
//	    //获取GridView组件并配置适配器  
//	    GridView gridView = (GridView)menuView.findViewById(R.id.gridview);  
//	    SimpleAdapter menuSimpleAdapter = createSimpleAdapter(allOptionsMenuTexts,allOptionsMenuIcons);  
//	    gridView.setAdapter(menuSimpleAdapter);  
//	    gridView.setOnItemClickListener(new OnItemClickListener(){  
//	        @Override  
//	        public void onItemClick(AdapterView<?> parent, View view,  
//	                int position, long id) {  
//	            Toast.makeText(context, "菜单["+allOptionsMenuTexts[position]+"]点击了.", Toast.LENGTH_SHORT).show();  
//	        }  
//	    });  
//	      
//	    //创建对话框并显示  
//	    new AlertDialog.Builder(context).setView(menuView).show();  
//	}  
//	  
//	public SimpleAdapter createSimpleAdapter(String[] menuNames,int[] menuImages){  
//	    List<Map<String,?>> data = new ArrayList<Map<String,?>>();  
//	    String[] fromsAdapter = {"item_text","item_image"};  
//	    int[] tosAdapter = {R.id.item_text,R.id.item_image};  
//	    for(int i=0;i<menuNames.length;i++){  
//	        Map<String,Object> map = new HashMap<String,Object>();  
//	        map.put(fromsAdapter[0], menuNames[i]);  
//	        map.put(fromsAdapter[1], menuImages[i]);  
//	        data.add(map);  
//	    }  
//	      
//	    SimpleAdapter SimpleAdapter = new SimpleAdapter(this, data, R.layout.menu_item, fromsAdapter, tosAdapter);  
//	    return SimpleAdapter;  
//	}  
//}