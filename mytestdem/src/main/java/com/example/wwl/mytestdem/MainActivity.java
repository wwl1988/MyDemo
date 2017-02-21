package com.example.wwl.mytestdem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wwl.mytestdem.retrofitTest.RetrofitTest1;
import com.example.wwl.mytestdem.retrofitTest.utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("WWL", "onCreate");
//        finish();
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniBT1();

            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }

        });

        tv1 = (TextView) findViewById(R.id.tv1);
//        String msgValue = "汪威林|28岁|男|本科|北京";
//        String msgValue = "|||||男||";
//        String msgValue = "男";
        String msgValue = "|||||||";
        if(msgValue.endsWith("|")){
            msgValue = String.format("%s ", msgValue);
        }
        String[] array = msgValue.split("\\|");
        tv1.append("@@"+array.length+"##\n");
        for (int j = 0; j < array.length; j++) {
            tv1.append(j+"@@"+array[j] + "$$\n");
        }

    }

    private void iniBT1() {
        startActivity(new Intent(this, RetrofitTest1.class));
    }

    int i = 0;

    private void init() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title")
                .setMessage("Dialog")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();*/

        ToastUtil.showTooast(this, "Toast测试第"+(i++) + "次");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("WWL", "onStart");
    }
}
