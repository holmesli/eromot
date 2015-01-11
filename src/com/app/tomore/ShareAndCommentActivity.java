package com.app.tomore;

import com.app.tomore.beans.ArticleModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShareAndCommentActivity extends Activity
{
	private TextView share;
	private TextView comment;
	private ArticleModel articleItem;
	private String articleId;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.share_comment_bt_layout);
		
		Intent intent=getIntent();
		articleId=intent.getStringExtra("articleid");
		
		share = (TextView)findViewById(R.id.sharebt);
		share.setOnClickListener(new buttonShare());
		
		comment = (TextView)findViewById(R.id.sharebt);
		comment.setOnClickListener(new buttonCommentList());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		finish();
		return true;
	}

	public void tip(View view)
	{
		Toast.makeText(this, "点击弹出框外部关闭窗口~", Toast.LENGTH_SHORT).show();
	}

	private class buttonShare implements OnClickListener  
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}  
//	    public void onClick(View v)  
//	    {  
//	        Intent intent=new Intent(ShareAndCommentActivity.this,MagActivity.class);   
//	        startActivity(intent);  
//	    }  
	}  
	
	private class buttonCommentList implements OnClickListener  
	{  
	    public void onClick(View v)  
	    {  
	        Intent intent=new Intent(ShareAndCommentActivity.this,MagCommentActivity.class);
	        intent.putExtra("articleid", articleItem.getArticleID());
	        startActivity(intent);  
	    }  
	}  
	
}

