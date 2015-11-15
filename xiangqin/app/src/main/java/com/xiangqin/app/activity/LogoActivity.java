package com.xiangqin.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xiangqin.app.R;


import butterknife.Bind;
import butterknife.ButterKnife;

public class LogoActivity extends BaseActivity {

    @Bind(R.id.image)
    ImageView mImage;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    startActivity(IntentGenerator.genSimpleActivityIntent(LogoActivity.this, SplashActivity.class));
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        mHandler.sendEmptyMessageDelayed(1,3000);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}