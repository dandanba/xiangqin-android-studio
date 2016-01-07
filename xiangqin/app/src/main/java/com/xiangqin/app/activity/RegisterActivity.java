package com.xiangqin.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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


    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mCommonTitleText.setText("注册");
        mLoginButton.setText("注册");

        mUser = (User) XQApplication.getInstance().getMessager().get("user");
    }

    @OnClick(R.id.titlebar_left_button)
    public void onLeftButtonClcik(View view) {
        onBackPressed();
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View view) {
        final String phoneNumber = mAccountEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();


        if (isMobile(phoneNumber)) {
            if (isPassword(password)) {
                mUser.setMobilePhoneNumber(phoneNumber);
                mUser.setUsername(phoneNumber);
                mUser.setPassword(password);


                mUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            EventBus.getDefault().post(new ActionEvent("account"));
                            startActivity(IntentGenerator.genSimpleActivityIntent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            ToastUtils.showToast(RegisterActivity.this, e.toString());
                        }
                    }
                });
            } else {
                ToastUtils.showToast(RegisterActivity.this, "密码长度6位或6位以上");
            }
        } else {
            ToastUtils.showToast(RegisterActivity.this, "手机号码格式有误");
        }
    }

    private boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^1(3|5|7|8|4)\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private boolean isPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

}