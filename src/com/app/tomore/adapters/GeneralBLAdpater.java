package com.app.tomore.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tomore.R;
import com.app.tomore.beans.GeneralBLText;
import com.app.tomore.beans.GeneralBLViewCache;


public class GeneralBLAdpater extends ArrayAdapter<GeneralBLText> {

	public GeneralBLAdpater(Activity activity, List<GeneralBLText> generalBLText, ListView listview1) {
		// TODO Auto-generated constructor stub
        super(activity, 0, generalBLText); 
	}
	  
    public View getView(int position, View convertView, ViewGroup parent) {  
        Activity activity = (Activity) getContext();  

        View rowView = convertView;  
        GeneralBLViewCache GeneralBLCache;  
        if (rowView == null) {  
            LayoutInflater inflater = activity.getLayoutInflater();  
            rowView = inflater.inflate(R.layout.general_bl_text, null);  
            GeneralBLCache = new GeneralBLViewCache(rowView);  
            rowView.setTag(GeneralBLCache);  
        } else {  
        	GeneralBLCache = (GeneralBLViewCache) rowView.getTag();  
        }  
        GeneralBLText generalbltext = getItem(position);  
  
        // Set the text on the TextView  
        TextView TitleView = GeneralBLCache.getTextViewTitle();  
        TitleView.setText(generalbltext.getTitle()); 
        
        TextView ContactView = GeneralBLCache.getTextViewContact();  
        ContactView.setText(generalbltext.getContact()); 
        
        TextView LanguageView = GeneralBLCache.getTextViewLanguage();
        String language = "";
        if(generalbltext.getLanguage_C().equals("1"))
        {
        	language = activity.getString(R.string.cantonese) ;
        }
        
        if(generalbltext.getLanguage_E().equals("1")) 
        {
        	if(generalbltext.getLanguage_C().equals("1")){
        			language = language + "/" + activity.getString(R.string.english);;
        	}
        	else{
        		language = activity.getString(R.string.english);;
        	}
        }
       
        if(generalbltext.getLanguage_M().equals("1")) 
        {
        	if(generalbltext.getLanguage_C().equals("1") || generalbltext.getLanguage_E().equals("1"))
        	{
        		language = language + "/" + activity.getString(R.string.mandarin);
        	}
        	else{
        		language = activity.getString(R.string.mandarin);
        	}
        }
        LanguageView.setText(language); 
        return rowView;  
    }
}
