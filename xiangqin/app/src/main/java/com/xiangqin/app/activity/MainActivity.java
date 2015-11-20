package com.xiangqin.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangqin.app.R;
import com.xiangqin.app.fragment.LoversFragment;
import com.xiangqin.app.fragment.MessageFragment;
import com.xiangqin.app.fragment.MyFragment;
import com.xiangqin.app.fragment.NearbyFragment;
import com.xiangqin.app.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.tabak.fragmentswitcher.FragmentStateArrayPagerAdapter;
import me.tabak.fragmentswitcher.FragmentSwitcher;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private final List<Fragment> mFragments = new ArrayList<Fragment>();
    private int mClickedTabId = R.id.tab_1;
    private FragmentStateArrayPagerAdapter mFragmentAdapter;
    // ------ bind ------
    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.titlebar_left_button)
    ImageView mLeftButton;

    @Bind(R.id.content_layout)
    FragmentSwitcher mFragmentSwitcher;
    @Bind(R.id.tab_1)
    TextView mTabButton1;
    @Bind(R.id.tab_2)
    TextView mTabButton2;
    @Bind(R.id.tab_3)
    TextView mTabButton3;
    @Bind(R.id.tab_4)
    TextView mTabButton4;
    @Bind(R.id.tab_5)
    TextView mTabButton5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    public void initData() {
        mFragments.add(LoversFragment.newInstance());
        mFragments.add(SearchFragment.newInstance());
        mFragments.add(MessageFragment.newInstance());
        mFragments.add(NearbyFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
    }

    public void initView() {
        mFragmentAdapter = new FragmentStateArrayPagerAdapter(getSupportFragmentManager());
        mFragmentSwitcher.setAdapter(mFragmentAdapter);
        mFragmentAdapter.addAll(mFragments);
        mTabButton1.setOnClickListener(this);
        mTabButton1.setTextColor(Color.parseColor("#F56034"));
        mTabButton1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_1_foucsed, 0, 0);
        mTabButton2.setOnClickListener(this);
        mTabButton3.setOnClickListener(this);
        mTabButton4.setOnClickListener(this);
        mTabButton5.setOnClickListener(this);
        mLeftButton.setVisibility(View.INVISIBLE);
        mCommonTitleText.setText("缘分");
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        TextView button;
        if (id != mClickedTabId) {
            button = (TextView) findViewById(mClickedTabId);
            button.setTextColor(Color.parseColor("#969696"));
            switch (mClickedTabId) {
                case R.id.tab_1:
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_1_default, 0, 0);
                    break;
                case R.id.tab_2:
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_2_default, 0, 0);
                    break;
                case R.id.tab_3:
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_3_default, 0, 0);
                    break;
                case R.id.tab_4:
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_4_default, 0, 0);
                    break;
                case R.id.tab_5:
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_5_default, 0, 0);
                    break;
                default:
                    break;
            }
            button = (TextView) v;
            button.setTextColor(Color.parseColor("#F56034"));
            mClickedTabId = id;
            switch (id) {
                case R.id.tab_1:
                    mCommonTitleText.setText("缘分");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_1_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(0);
                    break;
                case R.id.tab_2:
                    mCommonTitleText.setText("搜索");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_2_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(1);
                    break;
                case R.id.tab_3:
                    mCommonTitleText.setText("信箱");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_3_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(2);
                    break;
                case R.id.tab_4:
                    mCommonTitleText.setText("附近");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_4_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(3);
                    break;
                case R.id.tab_5:
                    mCommonTitleText.setText("我");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_5_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(4);
                    break;
                default:
                    break;
            }
        }
    }

}