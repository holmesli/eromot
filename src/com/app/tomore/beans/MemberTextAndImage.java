package com.app.tomore.beans;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tomore.R;

public class MemberTextAndImage {
	
	

	    private View baseView;
	    private TextView textView1;
	    private TextView textView2;
	    private TextView textView3;
	    private ImageView imageView;

	    public MemberTextAndImage(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTitleView() {
	        if (textView1 == null) {
	        	textView1 = (TextView) baseView.findViewById(R.id.title);
	        }
	        return textView1;
	    }
	    
	    public TextView getDesView() {
	        if (textView2 == null) {
	        	textView2 = (TextView) baseView.findViewById(R.id.des);
	        }
	        return textView2;
	    }
	    
	    public TextView getToMoreView() {
	        if (textView3 == null) {
	        	textView3 = (TextView) baseView.findViewById(R.id.tomoreCard);
	        }
	        return textView3;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.img);
	        }
	        return imageView;
	    }

}
