package com.xiangqin.app;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.WriteLog;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.leancloud.im.guide.MessageHandler;
import com.xiangqin.app.message.Messager;
import com.xiangqin.app.model.Notification;
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
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());

        Fresco.initialize(this);

        AVObject.registerSubclass(Notification.class);
        AVObject.registerSubclass(User.class);
        AVOSCloud.initialize(this, Constants.AVOS_APP_ID, Constants.AVOS_APP_KEY);

        // 启用崩溃错误统计
//        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);

        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

        sInstance = this;


    }

    public Messager getMessager() {
        return mMessager;
    }
}
