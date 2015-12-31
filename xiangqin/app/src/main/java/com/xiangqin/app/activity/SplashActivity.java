package com.xiangqin.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.xiangqin.app.R;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.fragment.ImagePagerFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.buttons_layout)
    ViewGroup mButtonsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        final Fragment f = new ImagePagerFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, f, "imagepager");
        ft.commit();
    }

    @Override
    public void onEvent(Object event) {
        super.onEvent(event);
        if (event instanceof ActionEvent) {
            final ActionEvent action = (ActionEvent) event;
            if ("account".equals(action.mAction)) {
                finish();
            } else if ("image".equals(action.mAction)) {
                boolean show = (boolean) action.mData;
                mButtonsLayout.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View view) {
        startActivity(IntentGenerator.genSimpleActivityIntent(this, LoginActivity.class));
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClick(View view) {
        startActivity(IntentGenerator.genSimpleActivityIntent(this, UserEditActivity.class));
    }


}