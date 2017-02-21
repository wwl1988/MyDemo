package com.example.wwl.myfragmenttest.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.wwl.myfragmenttest.R;
import com.example.wwl.myfragmenttest.fragment.BaseDialogFragment;
import com.example.wwl.myfragmenttest.fragment.CommonDialog;
import com.example.wwl.myfragmenttest.listener.PermissionResultListener;
import com.example.wwl.myfragmenttest.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = "WWL";
    private static final int PER_REQUEST_CODE = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;

    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Override
    protected void initIntentParam(Intent intent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();

    }

    private void initToolbar() {

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_setting:
                ToastUtil.showShortToast("Settings");
                return true;
            case R.id.action_permissions:
                String[] permissionArray = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
                performRequestPermission(getResources().getString(R.string.permission_desc), permissionArray,
                        PER_REQUEST_CODE, new PermissionResultListener() {
                            @Override
                            public void onPermissionGranted() {
                                ToastUtil.showShortToast("已申请权限");
                            }

                            @Override
                            public void onPermissionDenied() {
                                ToastUtil.showShortToast("拒绝权限");
                            }
                        });

                return true;
            case R.id.action_dialog:
                CommonDialog commonDialog = CommonDialog.newInstance("提示", "这是一个Message~", "确认", "取消",
                        new BaseDialogFragment.onDialogInteraction() {

                    @Override
                    public void onConfirm(DialogInterface dialog, int which) {
                        ToastUtil.showShortToast("onConfirm");
                    }

                    @Override
                    public void onCancel(DialogInterface dialog, int which) {
                        ToastUtil.showShortToast("onCancel");
                    }

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showShortToast("onClick");
                    }
                });
                commonDialog.setCancelable(false);//钻研源码了解   设置后点击Dialog外部Dialog不会消失，点击返回也无效
                commonDialog.show(getSupportFragmentManager(), TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
