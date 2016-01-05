package com.xiangqin.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogInCallback;
import com.xiangqin.app.R;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.account_edit)
    EditText mAccountEdit;
    @Bind(R.id.password_edit)
    EditText mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCommonTitleText.setText("登录");
    }

    @OnClick(R.id.titlebar_left_button)
    public void onLeftButtonClcik(View view) {
        onBackPressed();
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View view) {
        final String phoneNumber = mAccountEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();
        User.loginByMobilePhoneNumberInBackground(phoneNumber, password, new LogInCallback<User>() {
            @Override
            public void done(User user, AVException e) {
                if (e == null) {
                    EventBus.getDefault().post(new ActionEvent("account"));
                    //    User.putUser(LoginActivity.this, result);
                    startActivity(IntentGenerator.genSimpleActivityIntent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.showToast(LoginActivity.this, e.toString());
                }
            }
        }, User.class);
    }


}