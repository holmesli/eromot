package com.app.tomore.httpclient;

import java.net.HttpURLConnection;

import android.os.Build;

import com.app.tomore.httpclient.AbstractHttpClient;
import com.app.tomore.httpclient.HttpDelete;
import com.app.tomore.httpclient.HttpGet;
import com.app.tomore.httpclient.HttpHead;
import com.app.tomore.httpclient.HttpPost;
import com.app.tomore.httpclient.HttpPut;
import com.app.tomore.httpclient.HttpRequest;
import com.app.tomore.httpclient.HttpRequestException;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.app.tomore.httpclient.RequestHandler;

public class NormalHttpClient extends AbstractHttpClient {
//
//	public NormalHttpClient(String baseUrl) {
//		super(baseUrl);
//	}
//	
//	

	public NormalHttpClient(String baseUrl, RequestHandler requestHandler) {
		super(baseUrl, requestHandler);
	}



	static {
		disableConnectionReuseIfNecessary();
		// See http://code.google.com/p/basic-http-client/issues/detail?id=8
		if (Build.VERSION.SDK_INT > 8)
			ensureCookieManager();
	}
	
	 /**
     * Work around bug in {@link HttpURLConnection} on older versions of
     * Android.
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
    

	@Override
	public HttpResponse head(String path, ParameterMap params) {
		HttpRequest req = new HttpHead(path, params);
		return execute(req);
	}

	@Override
	public HttpResponse get(String path, ParameterMap params) {
		HttpRequest req = new HttpGet(path, params);
		return execute(req);
	}

	@Override
	public HttpResponse post(String path, ParameterMap params) {
		HttpRequest req = new HttpPost(path, params);
		return execute(req);
	}

	@Override
	public HttpResponse post(String path, String contentType, byte[] data) {
		HttpPost req = new HttpPost(path, null, contentType, data);
		return execute(req);
	}
	
	@Override
	public HttpResponse post(String path, ParameterMap params, String contentType, byte[] data) {
		HttpPost req = new HttpPost(path, params, contentType, data);
		return execute(req);
    }

	@Override
	public HttpResponse put(String path, String contentType, byte[] data) {
		HttpRequest req = new HttpPut(path, null, contentType, data);
		return execute(req);
	}

	@Override
	public HttpResponse delete(String path, ParameterMap params) {
		HttpDelete req = new HttpDelete(path, params);
		return execute(req);
	}

}
