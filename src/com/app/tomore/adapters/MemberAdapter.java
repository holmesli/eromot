package com.app.tomore.adapters;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.app.tomore.MainMemActivity;
import com.app.tomore.MemberDetailActivity;
import com.app.tomore.MyCameraActivity;
import com.app.tomore.R;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.beans.MagViewCache;
import com.app.tomore.beans.MemberTextAndImage;
import com.app.tomore.utils.AsyncImageLoader;
import com.app.tomore.utils.AsyncImageLoader.ImageCallback;

public class MemberAdapter extends ArrayAdapter<ImageAndTexts> {
	
	private ListView listview;  
    private AsyncImageLoader asyncImageLoader;  
    public MemberAdapter(Activity activity, List<ImageAndTexts> imageAndTexts, ListView listview1) {  
        super(activity, 0, imageAndTexts);  
        this.listview = listview1;  
        asyncImageLoader = new AsyncImageLoader();  
    }  

    public View getView(int position, View convertView, ViewGroup parent) {  
        Activity activity = (Activity) getContext();  

        View rowView = convertView;  
        MemberTextAndImage memberTextAndImage;  
        if (rowView == null) {  
            LayoutInflater inflater = activity.getLayoutInflater();  
            rowView = inflater.inflate(R.layout.member_listview_item, null);  
            memberTextAndImage = new MemberTextAndImage(rowView);  
            rowView.setTag(memberTextAndImage); 
        } else {  
        	memberTextAndImage = (MemberTextAndImage) rowView.getTag();  
        }  
        ImageAndTexts imageAndTexts = getItem(position);  

        // Load the image and set it on the ImageView  
        String imageUrl = imageAndTexts.getImageUrl();  
        ImageView imageView = memberTextAndImage.getImageView();  
        imageView.setTag(imageUrl);  
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {  
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {  
                ImageView imageViewByTag = (ImageView) listview.findViewWithTag(imageUrl);  
                if (imageViewByTag != null) {  
                    imageViewByTag.setImageDrawable(imageDrawable);  
                }  
            }  
        });  
        imageView.setImageDrawable(cachedImage);    
         
        // Set the text on the TextView  
        TextView textView1 = memberTextAndImage.getTitleView();  
        textView1.setText(imageAndTexts.getTitle());
        
        TextView textView2 = memberTextAndImage.getDesView();  
        textView2.setText(imageAndTexts.getDes());
        
        TextView textView3 = memberTextAndImage.getToMoreView();
        String cardType = imageAndTexts.getCardType();
        
        if(!cardType.equals("0"))
        {
	        textView3.setVisibility(View.INVISIBLE);
        }
        else if (cardType.equals("0"))
        {
	        textView3.setVisibility(View.VISIBLE);
        }
        
        return rowView;  
    }  

}
