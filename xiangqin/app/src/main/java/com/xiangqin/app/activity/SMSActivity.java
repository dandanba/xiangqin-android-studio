package com.xiangqin.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.xiangqin.app.Constants;
import com.xiangqin.app.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SMSActivity extends BaseActivity implements Handler.Callback {
    private Handler mHandler;
    private final EventHandler mEventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            final Message msg = mHandler.obtainMessage();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            mHandler.sendMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        mHandler = new Handler(this);
        SMSSDK.initSDK(this, Constants.SMS_APP_KEY, Constants.SMS_APP_SECRET);
        SMSSDK.registerEventHandler(mEventHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public void getVerificationCode(String phone) {
        SMSSDK.getVerificationCode("86", phone);
    }
}