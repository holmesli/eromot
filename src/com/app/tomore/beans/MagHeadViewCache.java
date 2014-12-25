package com.app.tomore.beans;

import com.app.tomore.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

	public class MagHeadViewCache {

	    private View baseView;
	    private TextView textView;
	    private ImageView imageView;

	    public MagHeadViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTextView() {
	        if (textView == null) {
	            textView = (TextView) baseView.findViewById(R.id.headinfo);
	        }
	        return textView;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.headimg);
	        }
	        return imageView;
	    }

}

