package com.xiangqin.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.location.service.LocationService;
import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.fragment.LoversFragment;
import com.xiangqin.app.fragment.MessageFragment;
import com.xiangqin.app.fragment.MyFragment;
import com.xiangqin.app.fragment.NearbyFragment;
import com.xiangqin.app.fragment.SearchFragment;
import com.xiangqin.app.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.tabak.fragmentswitcher.FragmentStateArrayPagerAdapter;
import me.tabak.fragmentswitcher.FragmentSwitcher;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "XQ";
    private LocationService mLocationService;
    // ------ bind ------
    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.titlebar_left_button)
    ImageView mLeftButton;
    @Bind(R.id.titlebar_right_button)
    ViewGroup mRightButton;
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

    private final List<Fragment> mFragments = new ArrayList<Fragment>();
    private int mClickedTabId = R.id.tab_1;
    private FragmentStateArrayPagerAdapter mFragmentAdapter;
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                User user = User.getCurrentUser(User.class);

                user.setLongitude(location.getLongitude());
                user.setLatitude(location.getLatitude());
                user.setArea(location.getCity());
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            mLocationService.unregisterListener(mListener); //注销掉监听
                            mLocationService.stop(); //停止定位服务
                        }
                    }
                });
                logLocation(location);
            }
        }


        private void logLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            /**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             */
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\nCountryCode : ");
            sb.append(location.getCountryCode());
            sb.append("\nCountry : ");
            sb.append(location.getCountry());
            sb.append("\ncitycode : ");
            sb.append(location.getCityCode());
            sb.append("\ncity : ");
            sb.append(location.getCity());
            sb.append("\nDistrict : ");
            sb.append(location.getDistrict());
            sb.append("\nStreet : ");
            sb.append(location.getStreet());
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\nDescribe: ");
            sb.append(location.getLocationDescribe());
            sb.append("\nDirection(not all devices have value): ");
            sb.append(location.getDirection());
            sb.append("\nPoi: ");
            if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                for (int i = 0; i < location.getPoiList().size(); i++) {
                    Poi poi = (Poi) location.getPoiList().get(i);
                    sb.append(poi.getName() + ";");
                }
            }
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：km/h
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            Log.i(TAG, sb.toString());
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPush();
        initData();
        initView();
    }

    @Override
    public void onEvent(Object obj) {
        if (obj instanceof ActionEvent) {
            final ActionEvent actionEvent = (ActionEvent) obj;
            if ("logout".equals(actionEvent.mAction)) {
                finish();
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
    }

    private void processExtraData() {
        // TODO
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        mLocationService.unregisterListener(mListener); //注销掉监听
        mLocationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        mLocationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            mLocationService.setLocationOption(mLocationService.getOption());
        }
        mLocationService.start();// 定位SDK
    }


    @OnClick(R.id.titlebar_right_button)
    public void onRightButtonClick(View view) {
        startActivity(IntentGenerator.genSimpleActivityIntent(this, SettingsActivity.class));
    }


    private void initPush() {
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, MainActivity.class);
        // 订阅频道，当该频道消息到来的时候，打开对应的 Activity
        PushService.subscribe(this, "public", MainActivity.class);
        PushService.subscribe(this, "private", MainActivity.class);
        PushService.subscribe(this, "protected", MainActivity.class);
        // 保存 installation 到服务器
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    final String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    final User user = User.getCurrentUser(User.class);
                    user.setInstallationId(installationId);
                    user.saveInBackground();
                }
            }
        });
    }

    public void initData() {
        // -----------location config ------------
        mLocationService = ((XQApplication) getApplication()).locationService;

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
        mRightButton.setVisibility(View.INVISIBLE);
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
                    mRightButton.setVisibility(View.INVISIBLE);
                    mCommonTitleText.setText("缘分");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_1_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(0);
                    break;
                case R.id.tab_2:
                    mRightButton.setVisibility(View.INVISIBLE);
                    mCommonTitleText.setText("搜索");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_2_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(1);
                    break;
                case R.id.tab_3:
                    mRightButton.setVisibility(View.INVISIBLE);
                    mCommonTitleText.setText("信箱");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_3_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(2);
                    break;
                case R.id.tab_4:
                    mRightButton.setVisibility(View.INVISIBLE);
                    mCommonTitleText.setText("附近");
                    button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_icon_4_foucsed, 0, 0);
                    mFragmentSwitcher.setCurrentItem(3);
                    break;
                case R.id.tab_5:
                    mRightButton.setVisibility(View.VISIBLE);
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