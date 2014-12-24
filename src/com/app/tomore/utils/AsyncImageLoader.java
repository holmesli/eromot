package com.app.tomore.utils;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

     private HashMap<String, SoftReference<Drawable>> imageCache;
     public AsyncImageLoader() {
             imageCache = new HashMap<String, SoftReference<Drawable>>();
         }
      
     public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
             if (imageCache.containsKey(imageUrl)) {
                 SoftReference<Drawable> softReference = imageCache.get(imageUrl);
                 Drawable drawable = softReference.get();
                 if (drawable != null) {
                     return drawable;
                 }
             }
             final Handler handler = new Handler() {
                 public void handleMessage(Message message) {
                     imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
                 }
             };
             new Thread() {
                 @Override
                 public void run() {
                     Drawable drawable = loadImageFromUrl(imageUrl);
                     imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                     Message message = handler.obtainMessage(0, drawable);
                     handler.sendMessage(message);
                 }
             }.start();
             return null;
         }
       
    public static Drawable loadImageFromUrl(String url) {

    	    Options options=new Options();
    	    options.inSampleSize=2;
    	    //Bitmap bitmap=BitmapFactory.decodeFile(url, options);
    	    Bitmap bitmap = getBitmap(url);
    	    Drawable drawable=new BitmapDrawable(bitmap);
            return drawable;
        }
      
    public interface ImageCallback {
             public void imageLoaded(Drawable imageDrawable, String imageUrl);
             
         }
    //Get image from URL
    public static Bitmap getBitmap(String httpUrl)
    {
        Bitmap bmp = null;
 
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmp;
    }
}