package com.xiangqin.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.xiangqin.app.message.Messager;
import com.xiangqin.app.model.User;

public class XQApplication extends Application {
    private static XQApplication sInstance;

    public static XQApplication getInstance() {
        return sInstance;
    }

    private final Messager mMessager = new Messager();

    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(User.class);
        AVOSCloud.initialize(this, Constants.AVOS_APP_ID, Constants.AVOS_APP_KEY);
        sInstance = this;
    }

    public Messager getMessager() {
        return mMessager;
    }
}
