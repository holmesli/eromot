package com.app.tomore.utils;

import org.json.JSONObject;

public class PushModel {

	String appId;
	String deviceId;
	String channelApp;
	String notifyToken;
	String ts;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getChannelApp() {
		return channelApp;
	}

	public void setChannelApp(String channelApp) {
		this.channelApp = channelApp;
	}

	public String getNotifyToken() {
		return notifyToken;
	}

	public void setNotifyToken(String notifyToken) {
		this.notifyToken = notifyToken;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "PushModel [appId=" + appId + ", deviceId=" + deviceId
				+ ", channelApp=" + channelApp + ", notifyToken=" + notifyToken
				+ ", ts=" + ts + "]";
	}

	public PushModel(JSONObject json) {
		appId = json.optString("app_id");
		deviceId = json.optString("device_id");
		channelApp = json.optString("channel_app");
		notifyToken = json.optString("notify_token");
		ts = json.optString("ts");
	}

	public PushModel() {}
	
}
