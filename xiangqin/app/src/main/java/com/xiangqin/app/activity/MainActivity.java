package com.xiangqin.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xiangqin.app.R;
import com.xiangqin.app.fragment.SearchFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replace(R.id.content_frame, SearchFragment.newInstance());

    }

}