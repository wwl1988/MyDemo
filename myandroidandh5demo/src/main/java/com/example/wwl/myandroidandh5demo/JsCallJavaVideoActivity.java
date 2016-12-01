package com.example.wwl.myandroidandh5demo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JsCallJavaVideoActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(new AndroidAndJSInterface(), "android");
        mWebView.loadUrl("file:///android_asset/RealNetJSCallJavaActivity.htm");
    }

    class AndroidAndJSInterface {
        @JavascriptInterface
        public void playVideo(int id, String videoUrl, String title){

            //调起系统所有播放器
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(videoUrl), "video/*");
            startActivity(intent);
        }

    }

}
