package com.example.wwl.myandroidandh5demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * java与js互调
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnJavaAndJs;
    private Button btnJsCallJava;
    private Button btnJsCallPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJavaAndJs = (Button) findViewById(R.id.btn_java_h5);
        btnJsCallJava = (Button) findViewById(R.id.btn_js_call_java);
        btnJsCallPhone = (Button) findViewById(R.id.btn_js_call_phone);

        btnJavaAndJs.setOnClickListener(this);
        btnJsCallJava.setOnClickListener(this);
        btnJsCallPhone.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_java_h5){
            startActivity(new Intent(this, JavaAndJsActivity.class));
        } else if(id == R.id.btn_js_call_java){
            startActivity(new Intent(this, JsCallJavaVideoActivity.class));
        } else if(id == R.id.btn_js_call_phone){
            startActivity(new Intent(this, JsCallJavaCallPhoneActivity.class));
        }

    }

}
