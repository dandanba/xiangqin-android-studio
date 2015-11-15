package com.xiangqin.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.account_edit)
    EditText mAccountEdit;
    @Bind(R.id.password_edit)
    EditText mPasswordEdit;
    @Bind(R.id.login_button)
    Button mLoginButton;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mCommonTitleText.setText("注册");
        mLoginButton.setText("注册");

        user = (User) XQApplication.getInstance().getMessager().get("user");
    }

    @OnClick(R.id.titlebar_left_button)
    public void onLeftButtonClcik(View view) {
        onBackPressed();
    }

    @OnClick(R.id.terms_textview)
    public void onTermsButtonClcik(View view) {
    }

    @OnClick(R.id.privacy_policy_textview)
    public void onPrivacyButtonClcik(View view) {

    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View view) {
        final String phoneNumber = mAccountEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();

    }

}