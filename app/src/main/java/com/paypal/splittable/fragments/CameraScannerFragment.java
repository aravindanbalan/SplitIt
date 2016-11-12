package com.paypal.splittable.fragments;

import com.paypal.splittable.R;
import com.paypal.splittable.activities.SmallCaptureActivity;
import com.paypal.splittable.managers.SplitTableWebViewClient;
import com.paypal.splittable.utils.ApplicationVersionUtil;
import com.paypal.splittable.utils.IConstants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by arbalan on 3/21/16.
 */
public class CameraScannerFragment extends Fragment {
    private static final String LOG_TAG = CameraScannerFragment.class.getName();
    private WebView mWebView;
    private Button mScan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_camera_scanner, container, false);
        mWebView = (WebView) top.findViewById(R.id.web);
        initWebView();
        mScan = (Button) top.findViewById(R.id.scan);
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanForCode();
            }
        });
        return top;
    }

    private void scanForCode() {
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(this);
        intentIntegrator.setCaptureActivity(SmallCaptureActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String scanData = (scanningResult != null) ? scanningResult.getContents() : "";

        if (scanData == null || scanData.isEmpty()) {
            Log.i(LOG_TAG, "Scan complete, no data");
        } else {
            Log.i(LOG_TAG, scanData);
            //Load paypal me url inside webview
            if (mWebView != null) {
                String url = IConstants.PAYPAL_ME_URL + scanData;
                Log.i(LOG_TAG, "*************** Final url to load : " + url);
                mWebView.loadUrl(url);
                mWebView.setVisibility(View.VISIBLE);
                if (mScan != null) {
                    mScan.setVisibility(View.GONE);
                }
            }
        }
    }

    //TODO implement on Hardware back press show camera fragment again and hide webview

    private void initWebView() {
        if (mWebView != null) {
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setUserAgentString(ApplicationVersionUtil.getUserAgent());
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            mWebView.setWebViewClient(new SplitTableWebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onConsoleMessage(ConsoleMessage message) {
                    Log.i(LOG_TAG, "onConsoleMessage: Line " + message.lineNumber() + " : " + message.message());
                    return true;
                }
                @Override
                public void onProgressChanged(WebView view, int progress) {
                }
            });
        }
    }
}