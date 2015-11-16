package com.xiangqin.app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;

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


        new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... params) {
                mUser.setMobilePhoneNumber(params[0]);
                mUser.setUsername(params[0]);
                mUser.setPassword(params[1]);
                try {
                    mUser.signUp();
                    return mUser;
                } catch (AVException e) {
                    Log.e(TAG, "signUp", e);
                }
                return null;
            }

            protected void onPostExecute(User result) {
                if (result != null) {
                    User.putUser(RegisterActivity.this, result);
                    EventBus.getDefault().post(new ActionEvent("account"));
                    startActivity(IntentGenerator.genSimpleActivityIntent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }

            ;
        }.execute(phoneNumber, password);

    }

}