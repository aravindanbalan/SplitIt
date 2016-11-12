package com.paypal.splittable.managers;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by arbalan on 3/28/16.
 */
public class SplitTableWebViewClient extends WebViewClient {
    private static final String LOG_TAG = SplitTableWebViewClient.class.getName();

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        Log.i(LOG_TAG, "********** onReceivedSslError : " + error.toString());

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Log.i(LOG_TAG, "********** onReceivedError : " + error.getDescription());
        Log.i(LOG_TAG, "********** onReceivedError : " + error.getErrorCode());
    }
}