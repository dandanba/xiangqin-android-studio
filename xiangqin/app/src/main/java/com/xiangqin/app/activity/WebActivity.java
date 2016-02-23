package com.xiangqin.app.activity;

import android.os.Bundle;

import com.xiangqin.app.R;
import com.xiangqin.app.fragment.WebFragment;

public class WebActivity extends BaseActivity {
    private static final String TAG = WebActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        replace(R.id.content_frame, WebFragment.newInstance(getIntent().getStringExtra("url")));

    }


}