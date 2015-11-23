package com.xiangqin.app;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.WriteLog;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.xiangqin.app.message.Messager;
import com.xiangqin.app.model.User;

public class XQApplication extends Application {

    public LocationService locationService;
    public Vibrator mVibrator;

    private static XQApplication sInstance;

    public static XQApplication getInstance() {
        return sInstance;
    }

    private final Messager mMessager = new Messager();

    @Override
    public void onCreate() {
        super.onCreate();

        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());

        Fresco.initialize(this);
        AVObject.registerSubclass(User.class);
        AVOSCloud.initialize(this, Constants.AVOS_APP_ID, Constants.AVOS_APP_KEY);
        sInstance = this;
    }

    public Messager getMessager() {
        return mMessager;
    }
}
