package com.xiangqin.app.activity;

import android.os.Bundle;

import com.xiangqin.app.R;
import com.xiangqin.app.fragment.UserFragment;

public class InfoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // 没有接收数据，数据由Fragment处理
        //final User user = (User) XQApplication.getInstance().getMessager().get("user");
        replace(R.id.content_frame, UserFragment.newInstance());
    }

}