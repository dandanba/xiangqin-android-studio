package com.xiangqin.app.model;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Config")
public class Config extends AVObject {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Config() {
    }

    public Config(Parcel in) {
        super(in);
    }

    public boolean isRelease() {
        return getBoolean("release");
    }

    public void setRelease(boolean release) {
        put("release", release);
    }

}
