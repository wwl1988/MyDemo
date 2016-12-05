package com.example.wwl.myandroidandh5demo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class  JsCallJavaCallPhoneActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_call_phone);
        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(new AndroidAndJSInterface2(), "Android");
        mWebView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");

    }

    private Handler mHandler = new Handler();

    class AndroidAndJSInterface2 {
        /**
         * 被Js调用，用于加载数据
         */
        @JavascriptInterface
        public void showcontacts(){
            //必须handler，否则无法正常调用mWebView.loadUrl。
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String json = "[{\"name\":\"老汪\",\"phone\":\"18600868377\"}]";
                    mWebView.loadUrl("javascript:showJson(" + "'" + json + "'" +")");
                }
            });

        }

        /**
         * 拨打电话
         * @param phone
         */
        @JavascriptInterface
        public void call(String phone){
            Toast.makeText(JsCallJavaCallPhoneActivity.this, "phone:"+phone, Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            //检查是否具备拨打电话的权限，如果没有，请求打开
            Log.d("WWL", "权限判断1："+ ActivityCompat.checkSelfPermission(JsCallJavaCallPhoneActivity.this, Manifest.permission.CALL_PHONE));
            Log.d("WWL", "权限判断2："+ PackageManager.PERMISSION_GRANTED);
            if(ActivityCompat.checkSelfPermission(JsCallJavaCallPhoneActivity.this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){
                Log.d("WWL", "权限被禁止了。。。");
                intent.setAction(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 555);
            } else {
                Log.d("WWL", "权限被允许了。。。可以打电话了");
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("WWL", "onActivityResult 进来了");
        if(requestCode == 555){
            if(Settings.System.canWrite(this)){
                Log.d("WWL", "onActivityResult 555");
            }
        }
    }
}
