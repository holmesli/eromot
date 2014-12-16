package com.app.tomore.beans;

import android.app.ActivityManager;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
 * ImageLoader包装�?在这里统�?��置异步记载策�?
 * 
 */
public class ImageLoaderFactory {
    private static ImageLoaderFactory instance;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    
    public synchronized static ImageLoaderFactory getInstance() {
        if (null == instance) {
            instance = new ImageLoaderFactory();
        }
        return instance;
    }

    private ImageLoaderFactory() {}

    /**
     * 返回全局唯一的已经初始化的ImageLoader实例
     * 
     * @return
     */
    public ImageLoader createImageLoader(Context ctx) {
    	if(mImageLoader == null) {
    		synchronized (this) {
    			initImageLoader(ctx);
			}
    	}
    	return mImageLoader;
    }

    private void initImageLoader(Context ctx) {
    	mImageLoader = ImageLoader.getInstance();
    	options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
        
    	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
        //.memoryCacheExtraOptions(480, 800)
        // .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75) //
        // Can slow ImageLoader, use it carefully (Better don't use it)
        // .threadPoolSize(1)
        .threadPriority(Thread.MIN_PRIORITY + 2)
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new UsingFreqLimitedMemoryCache(getMemCacheSizePercent(ctx,0.25F)))
        .discCache(new UnlimitedDiscCache(FileUtil.getCacheDir(ctx)))
        //.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
        //.imageDownloader(new DefaultImageDownloader(5 * 1000, 30 *1000)) // connectTimeout (5 s), readTimeout (30 s)
         .defaultDisplayImageOptions(options).enableLogging().build();
        mImageLoader.init(config);
   }
    
    
    public int getMemCacheSizePercent(Context context, float percent) {
        if (percent < 0.05f || percent > 0.8f) {
            throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
                    + "between 0.05 and 0.8 (inclusive)");
        }
        return Math.round(percent * getMemoryClass(context) * 1024 * 1024);
    }

    private int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
    }
}
