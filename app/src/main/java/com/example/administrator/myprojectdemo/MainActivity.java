package com.example.administrator.myprojectdemo;

import android.content.Intent;
import android.view.KeyEvent;

import com.example.administrator.myprojectdemo.base.BaseActivity;
import com.example.administrator.myprojectdemo.utils.NetHelperUtils;

public class MainActivity extends BaseActivity {

    private long mExitTime = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        if (network() == -1) {
            //当前界面所有控件设置成不可见  只有没网路时显示的空间为可见
            showToast(this, "当前没有网络");
        }

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void requestData() {

    }

    public int network() {
        int type = NetHelperUtils.getConnectedType(this);
        return type;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast(this, "再按一次退出应用");
                mExitTime = System.currentTimeMillis();
            } else {
                //销毁所有的Activity
                Intent intent = new Intent();
                //广播
                intent.setAction("com.andly.bro");
                sendBroadcast(intent);

            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
