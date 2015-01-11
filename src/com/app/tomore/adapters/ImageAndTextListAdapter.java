package com.app.tomore.adapters;

import java.util.List;

import com.app.tomore.R;
import com.app.tomore.ViewCache;
import com.app.tomore.beans.ImageAndText;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.app.Activity;  
import android.graphics.Bitmap;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.GridView;  
import android.widget.ImageView;  
import android.widget.TextView;  

  
public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {  
  
        private GridView gridView;  
        public ImageAndTextListAdapter(Activity activity, List<ImageAndText> imageAndTexts, GridView gridView2) {  
            super(activity, 0, imageAndTexts);  
            this.gridView = gridView2;  
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));

        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            Activity activity = (Activity) getContext();  
  
            View rowView = convertView;  
            ViewCache viewCache;  
            if (rowView == null) {  
                LayoutInflater inflater = activity.getLayoutInflater();  
                rowView = inflater.inflate(R.layout.bianlidatalayout, null);  
                viewCache = new ViewCache(rowView);  
                rowView.setTag(viewCache);  
            } else {  
                viewCache = (ViewCache) rowView.getTag();  
            }  
            ImageAndText imageAndText = getItem(position);  
  
            // Load the image and set it on the ImageView  
            final String imageUrl = imageAndText.getImageUrl();  
            ImageView imageView = viewCache.getImageView();  
            imageView.setTag(imageUrl);  
           
            /*ImageLoader.getInstance().loadImage(imageUrl, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                	ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);  
                    if (imageViewByTag != null) {  
                        imageViewByTag.setImageBitmap(loadedImage); 
                    }  
                }
            }); */
            
            ImageLoader.getInstance().displayImage(imageUrl,
            		imageView);
            
            // Set the text on the TextView  
            TextView textView = viewCache.getTextView();  
            textView.setText(imageAndText.getText());  
            return rowView;  
        }  
  
}  

