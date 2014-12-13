package com.app.tomore.httpclient;

import android.os.AsyncTask;

import com.app.tomore.httpclient.AsyncCallback;
import com.app.tomore.httpclient.AsyncHttpClient;
import com.app.tomore.httpclient.AsyncRequestExecutor;
import com.app.tomore.httpclient.AsyncRequestExecutorFactory;

/**
 * Android-specific factory produces an {@link AsyncTask} that can 
 * execute an HTTP request. 
 * 
 * @author David M. Chandler
 */
public class AsyncTaskFactory implements AsyncRequestExecutorFactory {

    /* (non-Javadoc)
     * @see com.turbomanage.httpclient.AsyncRequestExecutorFactory#getAsyncRequestExecutor(com.turbomanage.httpclient.AsyncHttpClient, com.turbomanage.httpclient.AsyncCallback)
     */
    @Override
    public AsyncRequestExecutor getAsyncRequestExecutor(AsyncHttpClient client,
            AsyncCallback callback) {
        return new DoHttpRequestTask(client, callback);
    }

}
