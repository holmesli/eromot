package com.app.tomore.beans;

import java.util.List;

import com.app.tomore.AsyncImageLoader;
import com.app.tomore.AsyncImageLoader.ImageCallback;
import com.app.tomore.ImageAndText;
import com.app.tomore.R;
import com.app.tomore.ViewCache;

import android.app.Activity;  
import android.content.Context;
import android.graphics.drawable.Drawable;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.GridView;  
import android.widget.ImageView;  
import android.widget.ListView;
import android.widget.TextView;  
  
public class ArticleAdapter extends ArrayAdapter<ImageAndText> {  
  
      

		private ListView listview;  
        private AsyncImageLoader asyncImageLoader;  
        public ArticleAdapter(Activity activity, List<ImageAndText> imageAndTexts, ListView listview1) {  
            super(activity, 0, imageAndTexts);  
            this.listview = listview1;  
            asyncImageLoader = new AsyncImageLoader();  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            Activity activity = (Activity) getContext();  
  
            // Inflate the views from XML  
            View rowView = convertView;  
            MagViewCache magviewCache;  
            if (rowView == null) {  
                LayoutInflater inflater = activity.getLayoutInflater();  
                rowView = inflater.inflate(R.layout.mag_listview, null);  
                magviewCache = new MagViewCache(rowView);  
                rowView.setTag(magviewCache);  
            } else {  
            	magviewCache = (MagViewCache) rowView.getTag();  
            }  
            ImageAndText imageAndText = getItem(position);  
  
            // Load the image and set it on the ImageView  
            String imageUrl = imageAndText.getImageUrl();  
            ImageView imageView = magviewCache.getImageView();  
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
            TextView textView = magviewCache.getTextView();  
            textView.setText(imageAndText.getText());  
            return rowView;  
        }  
  
}  

