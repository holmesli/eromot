package com.app.tomore.beans;

import com.app.tomore.R;

import android.view.View;
import android.widget.TextView;

public class GeneralBLViewCache {

    private View baseView;
    private TextView Title;
    private TextView Contact;
    private TextView Language;
	public GeneralBLViewCache(View baseView) {
		// TODO Auto-generated constructor stub
		this.baseView = baseView;
	}
    public TextView getTextViewTitle() {
        if (Title == null) {
        	Title = (TextView) baseView.findViewById(R.id.Title);
        }
        return Title;
    }
    
    public TextView getTextViewContact() {
        if (Contact == null) {
        	Contact = (TextView) baseView.findViewById(R.id.Contact);
        }
        return Contact;
    }
    
    public TextView getTextViewLanguage() {
        if (Language == null) {
        	Language = (TextView) baseView.findViewById(R.id.language);
        }
        return Language;
    }

}
