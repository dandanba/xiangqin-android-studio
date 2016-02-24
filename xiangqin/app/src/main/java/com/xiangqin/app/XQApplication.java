package com.xiangqin.app;

import android.app.Service;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.WriteLog;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.leancloud.im.guide.MessageHandler;
import com.liulishuo.filedownloader.FileDownloader;
import com.xiangqin.app.message.Messager;
import com.xiangqin.app.model.Config;
import com.xiangqin.app.model.Event;
import com.xiangqin.app.model.Notification;
import com.xiangqin.app.model.User;

import java.util.HashMap;

public class XQApplication extends MultiDexApplication {

    public final HashMap<String, String> mNumberName = new HashMap<>();

    public LocationService locationService;
    public Vibrator mVibrator;

    private static XQApplication sInstance;

    public static XQApplication getInstance() {
        return sInstance;
    }

    private final Messager mMessager = new Messager();

    public boolean isRlease;

    @Override
    public void onCreate() {
        MultiDex.install(getApplicationContext());
        super.onCreate();

        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());

        Fresco.initialize(this);

        AVObject.registerSubclass(Event.class);
        AVObject.registerSubclass(Config.class);
        AVObject.registerSubclass(Notification.class);
        AVObject.registerSubclass(User.class);
        AVOSCloud.initialize(this, Constants.AVOS_APP_ID, Constants.AVOS_APP_KEY);

        // 启用崩溃错误统计
//        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);

        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerDefaultMessageHandler(new MessageHandler(this));

        // Just cache Application's Context.
        FileDownloader.init(this);

        sInstance = this;


    }

    public Messager getMessager() {
        return mMessager;
    }
}
