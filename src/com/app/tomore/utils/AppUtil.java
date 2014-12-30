package com.app.tomore.utils;

import java.text.DecimalFormat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AppUtil {

	
	public static int getPixels(Activity activity,int dipValue) {
		Resources r = activity.getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, r.getDisplayMetrics());
		return px;
	}
	
	public static int getScreenWidth(Activity context){
		return context.getWindowManager().getDefaultDisplay().getWidth();
	}
	
	public static String getTrueNum(String num){
		if(num == null || "".equals(num) || "null".equals(num)){
			return "0";
		}
		double numInt = Integer.valueOf(num);
		if(numInt<10000){
			return num+"";
		}
		DecimalFormat   df=new  DecimalFormat("#.#");
		return df.format(numInt/10000)+"��";
	}
	
	public static boolean networkAvailable(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		if(info != null) {
			return (info != null && info.isConnected());
		}else {
			return false;
		}
	}
//	
//	public static Animation startAnim(Context context,View view) {
//		Animation anim = AnimationUtils.loadAnimation(context, R.anim.scalebig);
//		view.startAnimation(anim);
//		return anim;
//	}

	/**
	 * 获取设备Id
	 * @param activity
	 * @return
	 */
	public static String getDeviceId(Activity activity) {
		TelephonyManager tm = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		return deviceId;
	}

}
