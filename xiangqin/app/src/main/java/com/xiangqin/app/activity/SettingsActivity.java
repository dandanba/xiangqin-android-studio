package com.xiangqin.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiangqin.app.R;
import com.xiangqin.app.fragment.SettingsFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {
    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.titlebar_right_button)
    ViewGroup mRightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mCommonTitleText.setText("设置");
        mRightButton.setVisibility(View.INVISIBLE);

        replace(R.id.content_frame, SettingsFragment.newInstance());
    }

    @OnClick(R.id.titlebar_left_button)
    public void onLeftButtonClick(View view) {
        onBackPressed();
    }
}