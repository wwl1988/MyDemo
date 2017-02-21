package com.example.wwl.myfragmenttest.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wwl.myfragmenttest.R;
import com.example.wwl.myfragmenttest.fragment.BaseFragment;
import com.example.wwl.myfragmenttest.listener.PermissionResultListener;

/**
 *
 * Created by wwl on 2017/1/9.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected BaseFragment currentFragment;

    private long lastBackTime = 0;

    private static final long TIME_INTERVAL = 2 * 1000;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //强制横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initIntentParam(getIntent());
    }

    /**
     * 初始化传入参数
     * @param intent
     */
    protected abstract void initIntentParam(Intent intent);

    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment){

        if(currentFragment == fragment)  return;

        if(!fragment.isAdded()){// 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.fl_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = (BaseFragment) fragment;

        setToolbarStyle();
    }

    protected void setToolbarStyle(){

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(isShouldHideInput(v, ev)){
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否隐藏软键盘  true 隐藏
     * 根据EditText所在的坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏键盘
     * @param v
     * @param ev
     * @return
     */
    protected boolean isShouldHideInput(View v, MotionEvent ev){
        if(v != null && v instanceof EditText){//判断是否是EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if(ev.getX() > left && ev.getX() < right &&
                    ev.getY() > top && ev.getY() < bottom){//点击的光标坐标是否在EditText范围内
                return  false;
            } else {
                return true;
            }
        }
        //如果不是EditText，则忽略，这个发生在视图刚绘制完，第一个焦点不在Edittext上
        return false;
    }

    /**
     * 多种隐藏键盘的方式的一种
     * @param token
     */
    protected void hideSoftInput(IBinder token){
        if(token != null){
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 双击返回才退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(this instanceof MainActivity){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                long currentBackTime = System.currentTimeMillis();
                if(currentBackTime - lastBackTime > TIME_INTERVAL){
                    Toast.makeText(this, getResources().getString(R.string.press_back_twice), Toast.LENGTH_SHORT).show();
                    lastBackTime = currentBackTime;
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private PermissionResultListener mListener;

    private int mRequestCode;

    /**
     * 其他Activity继承BaseActivity 调用该方法
     * @param des
     * @param permissions
     * @param requestCode
     * @param listener
     */
    protected void performRequestPermission(String des, String[] permissions, int requestCode, PermissionResultListener listener){

        if(permissions == null || permissions.length == 0)
            return;

        mRequestCode = requestCode;
        mListener = listener;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//SDK》23
            if(checkEachSelfPermission(permissions)){//先检查权限是否申请，只有有一个没申请，就进入
                requestEachPermission(des, permissions, requestCode);
            } else {//权限都已申请
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionDenied();
            }
        }


    }

    /**
     * 申请权限前先判断是否需要申明
     * @param des
     * @param permissions
     * @param requestCode
     */
    protected void requestEachPermission(String des, String[] permissions, int requestCode){
        if(ShouldShowRequestPermissionRationale(permissions)){//是否需要提示弹出提示申请某个权限的对话框
            showRationaleDialog(des, permissions, requestCode);//展示对话框
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);//不展示对话框，直接申请
        }

    }

    /**
     * 弹出声明的Dialog
     * @param des
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String des, final String[] permissions, final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.tips))
                .setMessage(des)
                .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();


    }

    /**
     * 再次申请权限时，是否需要声明
     * @param permissions
     * @return
     */
    protected boolean ShouldShowRequestPermissionRationale(String[] permissions){
        for (String permission : permissions) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                return  true;
            }
        }
        return false;
    }

    /**
     *  检查每个权限是否申请
     * @param permissions
     * @return
     */
    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == mRequestCode){
            if(checkEachPermissionGranted(grantResults)){
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            } else {//权限被拒绝
                if (mListener != null) {
                    mListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 检查回调结果
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionGranted(int[] grantResults) {
        for (int result : grantResults) {
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

}
