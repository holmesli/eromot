package com.app.tomore.beans;

import com.app.tomore.R;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

	public class MagDetailViewCache {

	    private View baseView;
	    private TextView textView;
	    private ImageView imageView;
	    private WebView webview;

	    public MagDetailViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTextView() {
	        if (textView == null) {
	            textView = (TextView) baseView.findViewById(R.id.news_title_text);
	        }
	        return textView;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.news_image);
	        }
	        return imageView;
	    }
	    
	    public WebView getWebView(){
	    	if(webview == null){
	    		webview = (WebView) baseView.findViewById(R.id.news_content_text);
	    	}
	    	return webview;
	    }
	    

}

