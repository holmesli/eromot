package com.app.tomore.adapters;

import java.util.List;

import com.app.tomore.beans.ImageAndText;
import com.app.tomore.beans.MagViewCache;
import com.app.tomore.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;  
import android.graphics.Bitmap;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.ImageView;  
import android.widget.ListView;
import android.widget.TextView;  
  
public class ArtilceHeadAdapter extends ArrayAdapter<ImageAndText> {  
  
		private TextView textview;
		private ImageView imageview;
		private ListView listview;  
        public ArtilceHeadAdapter(Activity activity, List<ImageAndText> imageAndTexts, ListView listview1) {  
            super(activity, 0, imageAndTexts);  
            this.listview = listview1;  
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));

        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            Activity activity = (Activity) getContext();  
  
            View rowView = convertView;  
            MagViewCache magviewCache;  
            if (rowView == null) {  
                LayoutInflater inflater = activity.getLayoutInflater();  
                rowView = inflater.inflate(R.layout.mag_headview, null);  
                magviewCache = new MagViewCache(rowView);  
                rowView.setTag(magviewCache);  
            } else {  
            	magviewCache = (MagViewCache) rowView.getTag();  
            }  
            ImageAndText imageAndText = getItem(position);  
  
            // Load the image and set it on the ImageView  
           final String imageUrl = imageAndText.getImageUrl();  
            ImageView imageView = magviewCache.getImageView();  
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
            TextView textView = magviewCache.getTextView();  
            textView.setText(imageAndText.getText());  
            return rowView;  
        }  
  
}  

