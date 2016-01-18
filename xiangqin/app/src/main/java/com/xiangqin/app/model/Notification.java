package com.xiangqin.app.model;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Notification")
public class Notification extends AVObject {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Notification() {
    }

    public Notification(Parcel in) {
        super(in);
    }


    //此处为我们的默认实现，当然你也可以自行实现
    public String getUser() {
        return getString("user");
    }

    public void setUser(String user) {
        put("user", user);
    }

    public String getTargetUser() {
        return getString("targetUser");
    }

    public void setTargetUser(String targetUser) {
        put("targetUser", targetUser);
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }

    // 新加字段 from "6.0.4"
    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        put("userName", userName);
    }

    public String getTargetUserName() {
        return getString("targetUserName");
    }

    public void setTargetUserName(String targetUserName) {
        put("targetUserName", targetUserName);
    }

}
