package com.app.tomore.beans;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.Build.VERSION;
import android.text.TextUtils;

public class FileUtil {
	private static File root = Environment.getExternalStorageDirectory();
	private static final String FOLDER_NAME = "SoHuSport";

	public static File getPrivateFileDir() {
		return new File(root, FOLDER_NAME);
	}

	/**
	 * 获得缓存路径，如果当前的SD卡不可用的情况下，使用系统cacheDir
	 */
	public static File getCacheDir(Context ctx) {
				
		String cachePath = "";
		if(SystemUtils.isSdCardAvailable() || !SystemUtils.isExternalStorageRemovable()) {
			cachePath = getExternalCacheDir(ctx).getPath();
		}else {
			cachePath = ctx.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator);
	}

    @TargetApi(8)
	public static File getExternalCacheDir(Context context) {
    	
    		if (SystemUtils.hasFroyo()) {
    			if(VERSION.SDK_INT == 8){
    				return context.getExternalCacheDir();
    			}
    		}
    		
    		final String cacheDir = "/Android/data/" + context.getPackageName()+ "/cache/";
    		String path = Environment.getExternalStorageDirectory().getPath();
    		if(TextUtils.isEmpty(path)){
    			path = "mnt/sdcard/";
    		}
    		File file = new File(path+ cacheDir);
    		if(!file.exists()){
    			file.mkdirs();
    		}
    		return file;
		
	}
    
    @TargetApi(8)
	public static File getExternalVoiceCacheDir(Context context) {
    	
    		if (SystemUtils.hasFroyo()) {
    			if(VERSION.SDK_INT == 8){
    				return context.getExternalCacheDir();
    			}
    		}
    		
    		final String cacheDir = "/Android/data/" + context.getPackageName()+ "/Voice/";
    		String path = Environment.getExternalStorageDirectory().getPath();
    		if(TextUtils.isEmpty(path)){
    			path = "mnt/sdcard/";
    		}
    		File file = new File(path+ cacheDir);
    		if(!file.exists()){
    			file.mkdirs();
    		}
    		return file;
		
	}
}
    
