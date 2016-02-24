package com.xiangqin.app.activity;

import android.os.Bundle;

import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.fragment.UserFragment;

public class InfoActivity extends BaseActivity {


    private static final String TAG = InfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // 没有接收数据，数据由Fragment处理
        //final User user = (User) XQApplication.getInstance().getMessager().get("user");
        replace(R.id.content_frame, UserFragment.newInstance());
        if (XQApplication.getInstance().isRlease) {
            setupBannerAd();
        }
    }


    /**
     * 设置广告条广告
     */
    private void setupBannerAd() {

    }
}