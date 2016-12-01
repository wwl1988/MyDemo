package com.example.wwl.myandroidandh5demo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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

    class AndroidAndJSInterface2 {
        /**
         * 被Js调用，用于加载数据
         */
        @JavascriptInterface
        public void showcontacts(){
            Log.d("WWL", "showcontacts是否被调用");
            String json = "[{\"name\":\"老汪\",\"phone\":\"18600868377\"}]";
//            mWebView.loadUrl("javascript:show('"+ json +"')");
//            String json2 = "[{\"name\":\"阿福\", \"phone\":\"18600012345\"}]";
            String json2 = "[{name:'阿福', phone:'18600012345'}]";
//            mWebView.loadUrl("javascript:show('" + json2 + "')");
            mWebView.loadUrl("javascript:showJson(" + "'" + json2 + "'" +")");
        }

        /**
         * 拨打电话
         * @param phone
         */
        @JavascriptInterface
        public void call(String phone){
            Toast.makeText(JsCallJavaCallPhoneActivity.this, "phone:"+phone, Toast.LENGTH_LONG).show();
            /*Intent intent = new Intent();
            //检查是否具备拨打电话的权限，如果没有，请求打开
            if(ActivityCompat.checkSelfPermission(JsCallJavaCallPhoneActivity.this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){
                intent.setAction(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 555);
            } else {
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }*/
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
