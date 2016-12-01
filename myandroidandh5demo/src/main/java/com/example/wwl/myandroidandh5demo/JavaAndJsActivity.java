package com.example.wwl.myandroidandh5demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * java和js互调
 */
public class JavaAndJsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mETNumber;
    private EditText mPassWord;
    private Button mBtn;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js);

        mETNumber = (EditText) findViewById(R.id.et_number);
        mPassWord = (EditText) findViewById(R.id.et_password);
        mBtn = (Button) findViewById(R.id.btn_login);
        mBtn.setOnClickListener(this);

        initWebView();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){
            login();
        }
    }

    private void initWebView() {
        mWebView = new WebView(this);
        WebSettings mWebSettings = mWebView.getSettings();
        //支持javaScript脚本语言
        mWebSettings.setJavaScriptEnabled(true);
        //支持缩放按钮，前提是页面要支持才显示
        mWebSettings.setBuiltInZoomControls(true);
        //设置不跳转到默认浏览器中
        mWebView.setWebViewClient(new WebViewClient());
        //支持js调用java
        mWebView.addJavascriptInterface(new JavaAndJsInterface(), "Android");
        mWebView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

    }

    private void login() {
        String number = mETNumber.getText().toString().trim();
        String passWord = mPassWord.getText().toString().trim();
        if(TextUtils.isEmpty(number) || TextUtils.isEmpty(passWord)){
            Toast.makeText(this, "账号或者密码为空", Toast.LENGTH_SHORT).show();
        } else {
            login(number);
        }

    }

    /**
     * 实现java向js传数据   注意方法名一定对应
     * @param number
     */
    private void login(String number) {
        //两种写法都可以
//        mWebView.loadUrl("javascript:javaCallJs(" + "\"" + number + "\"" +")");
        mWebView.loadUrl("javascript:javaCallJs(" + "'" + number + "'" +")");
        setContentView(mWebView);
    }

    /**
     * js点击调用java方法
     *
     * onclick="window.Android.showToast()" 方法必须一致，否则不生效
     */
    class JavaAndJsInterface {

        @JavascriptInterface
        public void showToast(){
            Toast.makeText(JavaAndJsActivity.this, "我被js调用了", Toast.LENGTH_SHORT).show();
        }

    }


}
