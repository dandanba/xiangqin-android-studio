package com.xiangqin.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiangqin.app.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.OnClick;

public class TermsActivity extends BaseActivity {
    @Bind(R.id.terms)
    TextView terms;

    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.titlebar_right_button)
    ViewGroup mRightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        mCommonTitleText.setText("服务条款");
        mRightButton.setVisibility(View.INVISIBLE);

        readFromRaw();
    }


    @OnClick(R.id.titlebar_left_button)
    public void onLeftButtonClick(View view) {
        onBackPressed();
    }

    private void readFromRaw() {
        try {
            InputStream is = getResources().openRawResource(R.raw.protocol);
            String text = readTextFromSDcard(is);
            terms.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}