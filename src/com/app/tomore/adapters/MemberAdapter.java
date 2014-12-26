package com.app.tomore.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tomore.R;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.beans.MemberTextAndImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.graphics.Bitmap;

public class MemberAdapter extends ArrayAdapter<ImageAndTexts> {
	
	private ListView listview;  
    public MemberAdapter(Activity activity, List<ImageAndTexts> imageAndTexts, ListView listview1) {  
        super(activity, 0, imageAndTexts);  
        this.listview = listview1;  
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));
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
        final String imageUrl = imageAndTexts.getImageUrl();  
        ImageView imageView = memberTextAndImage.getImageView();  
        imageView.setTag(imageUrl);  
        ImageLoader.getInstance().loadImage(imageUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            	ImageView imageViewByTag = (ImageView) listview.findViewWithTag(imageUrl);  
                if (imageViewByTag != null) {  
                	imageViewByTag.setImageBitmap(loadedImage);
                } 
            }
        });
        
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
