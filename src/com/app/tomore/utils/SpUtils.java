package com.app.tomore.utils;

import com.app.tomore.utils.PushModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SpUtils {

	private static final String USERINFO = "userinfo";
	private static final String USERID = "userid";
	private static final String UID = "uid";
	private static final String ACCESS_TOKEN = "access_token";
	
	//push
	private static final String PUSH = "push";
	private static final String PUSH_appId = "appId";
	private static final String PUSH_deviceId = "deviceId";
	private static final String PUSH_channelApp = "channelApp";
	private static final String PUSH_notifyToken = "notifyToken";
	private static final String PUSH_ts = "ts";
	
	private static final String PUSH_SETTING = "push_setting";
	private static final String PUSH_SETTING_compiti_notifi = "push_setting_compiti_notifi";
	private static final String PUSH_SETTING_news_notifi = "push_settingnews_notifi";
	
	
	public static void saveCompiti_notifi (Activity activity,boolean isOpen){
		SharedPreferences sp = activity.getSharedPreferences(PUSH_SETTING, Activity.MODE_PRIVATE);
		sp.edit().putBoolean(PUSH_SETTING_compiti_notifi, isOpen).commit();
	}
	
	public static boolean getCompiti_notifi (Context activity){
		SharedPreferences sp = activity.getSharedPreferences(PUSH_SETTING, Activity.MODE_PRIVATE);
		return sp.getBoolean(PUSH_SETTING_compiti_notifi, true);
	}
	public static boolean getNews_notifi  (Context activity){
		SharedPreferences sp = activity.getSharedPreferences(PUSH_SETTING, Activity.MODE_PRIVATE);
		return sp.getBoolean(PUSH_SETTING_news_notifi, true);
	}
	
	public static void saveNews_notifi (Activity activity,boolean isOpen){
		SharedPreferences sp = activity.getSharedPreferences(PUSH_SETTING, Activity.MODE_PRIVATE);
		sp.edit().putBoolean(PUSH_SETTING_news_notifi, isOpen).commit();
	}
	
	/**
	 * 保存用户信息
	 * @param activity
	 * @param userid
	 */
	public static void saveUserId(Activity activity,String userid){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		sp.edit().putString(USERID, userid).commit();
	}
	
	public static void savePushModel(Activity activity,PushModel pushModel){
		SharedPreferences sp = activity.getSharedPreferences(PUSH, Activity.MODE_PRIVATE);
		sp.edit().putString(PUSH_appId, pushModel.getAppId())
		.putString(PUSH_channelApp, pushModel.getChannelApp())
		.putString(PUSH_deviceId, pushModel.getDeviceId())
		.putString(PUSH_notifyToken, pushModel.getNotifyToken())
		.putString(PUSH_ts, pushModel.getTs()).commit();
	}
	
	public static PushModel getPushModel(Context activity){
		SharedPreferences sp = activity.getSharedPreferences(PUSH, Activity.MODE_PRIVATE);
		String appId = sp.getString(PUSH_appId, null);
		if(TextUtils.isEmpty(appId)){
			return null;
		}else{
			PushModel pushModel = new PushModel();
			pushModel.setAppId(appId);
			pushModel.setChannelApp(sp.getString(PUSH_channelApp, null));
			pushModel.setDeviceId(sp.getString(PUSH_deviceId, null));
			pushModel.setNotifyToken(sp.getString(PUSH_notifyToken, null));
			pushModel.setTs(sp.getString(PUSH_ts, null));
			
			return pushModel;
		}
		
	}
	
	public static void saveStartPicName(Activity activity,String fileName){
		SharedPreferences sp = activity.getSharedPreferences("start_pic", Activity.MODE_PRIVATE);
		sp.edit().putString("name", fileName).commit();
	}
	
	public static String getStartPicName(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences("start_pic", Activity.MODE_PRIVATE);
		return sp.getString("name", null);
	}
	
	/**
	 * 保存用户信息
	 * @param activity
	 * @param userid
	 */
	public static void saveUid(Activity activity,String uid){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		sp.edit().putString(UID, uid).commit();
	}
	
	/**
	 * 保存用户信息
	 * @param activity
	 * @param userid
	 */
	public static void saveToken(Activity activity,String token){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		sp.edit().putString(ACCESS_TOKEN, token).commit();
	}
	
	/**
	 * 获取用户信息
	 * @param activity
	 * @return
	 */
	public static String getUserId(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		return sp.getString(USERID, null);
	}
	
	/**
	 * 获取用户信息
	 * @param activity
	 * @return
	 */
	public static String getUid(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		return sp.getString(UID, null);
	}
	
	/**
	 * 获取用户信息
	 * @param activity
	 * @return
	 */
	public static String getToken(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		return sp.getString(ACCESS_TOKEN, null);
	}
	
	/**
	 * 判断是否已经登录
	 * @param activity
	 * @return true:已登录，false：未登录
	 */
	public static boolean isLogin(Activity activity){
		String uid = getUserId(activity);
		if(TextUtils.isEmpty(uid)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 清除用户信息
	 * @param activity
	 */
	public static void clearUserInfo(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences(USERINFO, Activity.MODE_PRIVATE);
		sp.edit().clear().commit();
	}
	
	
	
}
