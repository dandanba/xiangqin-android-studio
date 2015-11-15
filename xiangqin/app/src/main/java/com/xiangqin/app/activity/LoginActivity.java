package com.xiangqin.app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.xiangqin.app.R;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... params) {
                try {
                    return User.loginByMobilePhoneNumber(params[0], params[1], User.class);
                } catch (AVException e) {
                    Log.e(TAG, "login", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(User result) {
                super.onPostExecute(result);
                if (result != null) {
                    EventBus.getDefault().post(new ActionEvent("account"));
                    startActivity(IntentGenerator.genSimpleActivityIntent(LoginActivity.this, MainActivity.class));
                    finish();
                    User.putUser(LoginActivity.this, result);
                }
            }
        }.execute(phoneNumber, password);
    }


}