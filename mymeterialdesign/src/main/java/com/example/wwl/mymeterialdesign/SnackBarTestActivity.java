package com.example.wwl.mymeterialdesign;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SnackBarTestActivity extends AppCompatActivity {

    private Button bt_snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar_test);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("SnackBarTest");
        bt_snackbar = (Button) findViewById(R.id.bt_snackbar);
        bt_snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar();
            }
        });
    }

    //测试
    private void showSnackbar() {
        Snackbar snackbar = SnackBarUtil.ShortSnackbar(bt_snackbar, "妹子删了你发出的消息", SnackBarUtil.Warning)
                .setActionTextColor(Color.WHITE)
                .setAction("再次发送", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackBarUtil.LongSnackbar(bt_snackbar, "妹子已将你拉黑", SnackBarUtil.Alert).show();
                    }
                });
        SnackBarUtil.SnackbarAddView(snackbar, R.layout.snackbar_addview, 0);
        SnackBarUtil.SnackbarAddView(snackbar, R.layout.snackbar_addview2, 2);
        snackbar.show();

    }

}
