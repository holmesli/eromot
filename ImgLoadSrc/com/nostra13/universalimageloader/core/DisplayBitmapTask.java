package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * Displays bitmap in {@link ImageView}. Must be called on UI thread.
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see ImageLoadingListener
 */
final class DisplayBitmapTask implements Runnable {

    private final Bitmap bitmap;
    private final ImageView imageView;
    private final ImageLoadingListener listener;

    public DisplayBitmapTask(Bitmap bitmap, ImageView imageView, ImageLoadingListener listener) {
        this.bitmap = bitmap;
        this.imageView = imageView;
        this.listener = listener;
    }

    @Override
    public void run() {
        imageView.setImageBitmap(bitmap);
        if (imageView.getTag() instanceof ImageTag) {
            ImageTag tag = (ImageTag) imageView.getTag();
            imageView.setTag(tag.originalObj);
            imageView.setScaleType(tag.scaleType);
        }
        imageView.setBackgroundResource(0);
        listener.onLoadingComplete();
    }
}
