package com.xiangqin.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xiangqin.app.R;
import com.xiangqin.app.model.User;

import butterknife.Bind;

public class LogoActivity extends BaseActivity {

    @Bind(R.id.image)
    ImageView mImage;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    final User user = User.getCurrentUser(User.class);
                    if (user == null) {
                        startActivity(IntentGenerator.genSimpleActivityIntent(LogoActivity.this, SplashActivity.class));
                    } else {
                        startActivity(IntentGenerator.genSimpleActivityIntent(LogoActivity.this, MainActivity.class));
                    }
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
        mHandler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


}