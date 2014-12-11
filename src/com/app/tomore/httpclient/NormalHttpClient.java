package com.app.tomore.httpclient;

import java.net.HttpURLConnection;

import android.os.Build;

import com.app.tomore.httpcore.AbstractHttpClient;
import com.app.tomore.httpcore.HttpDelete;
import com.app.tomore.httpcore.HttpGet;
import com.app.tomore.httpcore.HttpHead;
import com.app.tomore.httpcore.HttpPost;
import com.app.tomore.httpcore.HttpPut;
import com.app.tomore.httpcore.HttpRequest;
import com.app.tomore.httpcore.HttpRequestException;
import com.app.tomore.httpcore.HttpResponse;
import com.app.tomore.httpcore.ParameterMap;
import com.app.tomore.httpcore.RequestHandler;

public class NormalHttpClient extends AbstractHttpClient {

	public NormalHttpClient(String baseUrl) {
		super(baseUrl);
	}
	
	

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
	public HttpResponse head(String path, ParameterMap params)
			throws HttpRequestException {
		HttpRequest req = new HttpHead(path, params);
		return tryMany(req);
	}

	@Override
	public HttpResponse get(String path, ParameterMap params)
			throws HttpRequestException {
		HttpRequest req = new HttpGet(path, params);
		return tryMany(req);
	}

	@Override
	public HttpResponse post(String path, ParameterMap params)
			throws HttpRequestException {
		HttpRequest req = new HttpPost(path, params);
		return tryMany(req);
	}

	@Override
	public HttpResponse post(String path, String contentType, byte[] data)
			throws HttpRequestException {
		HttpPost req = new HttpPost(path, null, contentType, data);
		return tryMany(req);
	}

	@Override
	public HttpResponse put(String path, String contentType, byte[] data)
			throws HttpRequestException {
		HttpRequest req = new HttpPut(path, null, contentType, data);
		return tryMany(req);
	}

	@Override
	public HttpResponse delete(String path, ParameterMap params)
			throws HttpRequestException {
		HttpDelete req = new HttpDelete(path, params);
		return tryMany(req);
	}

}
