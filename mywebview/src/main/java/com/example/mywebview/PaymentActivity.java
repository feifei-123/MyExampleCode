package com.example.mywebview;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class PaymentActivity extends Activity {

    private static final String TAG = PaymentActivity.class.getSimpleName();

    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Util.hookWebView();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initview();
    }
    public void initview(){
//        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });





        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(true);//支持放缩 - 但不显示放缩控件
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);//webview设置支持Javascript
//        settings.setSavePassword(false);//不保存密码
//        settings.setSaveFormData(true);
//        settings.setAllowFileAccess(true);
//        settings.setDomStorageEnabled(true);
//		settings.setAppCacheEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS 打开新窗口
//        settings.setDomStorageEnabled(true);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mWebView.setBackgroundColor(0);
        mWebView.setAlpha(1);
        String httpUrl = "https://account.sogou.com/connect/login?provider=weixin&client_id=10425&ru=https%3A%2F%2Fwan.sogou.com%2Foauth%2Frecieveru.do%3Fcb%3Dhttps%253A%252F%252Fwan.sogou.com%252F&third_appid=wx6634d697e8cc0a29&href=https%3A%2F%2Fwan01.sogoucdn.com%2Fcdn%2Fstatic%2Fcss%2Fspecial%2Fweixin-login%2Fnormal.css";
        httpUrl =  "https://account.sogou.com/connect/login?provider=weixin&client_id=10425&ru=https://www.sogou.com/&third_appid=wx6634d697e8cc0a29&href=https%3A%2F%2Fwan01.sogoucdn.com%2Fcdn%2Fstatic%2Fcss%2Fspecial%2Fweixin-login%2Fnormal.css";
        mWebView.loadUrl(httpUrl);
//        mWebView.loadUrl("http://iot.sogou.com/web/tr1_user_agreement.html");
        Log.d(TAG,"enter:");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                Uri url = request.getUrl();
                Log.d(TAG,"loadUrl1:"+url);
                //LogUtil.d(TAG,"shouldOverrideUrlLoading:"+request.getUrl()+"\n scheme:"+ url.getScheme()+",\nAuthority:"+url.getAuthority()+"\npath:"+url.getPath());

//                return super.shouldOverrideUrlLoading(view, request);
                mWebView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"loadUrl2:"+url);
                //LogUtil.d(TAG,"shouldOverrideUrlLoading:"+request.getUrl()+"\n scheme:"+ url.getScheme()+",\nAuthority:"+url.getAuthority()+"\npath:"+url.getPath());

//                return super.shouldOverrideUrlLoading(view, request);
                mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.setAlpha(1);

                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                Log.d(TAG, "Cookies = " + CookieStr);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d(TAG,"onReceivedError:"+request.getUrl()+",error:"+error);
//                view.loadUrl("file:///android_asset/agreement.html");
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return true;
            }

        });

    }

}
