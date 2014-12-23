package com.app.tomore.beans;

import com.app.tomore.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

	public class MagViewCache {

	    private View baseView;
	    private TextView textView;
	    private ImageView imageView;

	    public MagViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTextView() {
	        if (textView == null) {
	            textView = (TextView) baseView.findViewById(R.id.info);
	        }
	        return textView;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.img);
	        }
	        return imageView;
	    }

}

