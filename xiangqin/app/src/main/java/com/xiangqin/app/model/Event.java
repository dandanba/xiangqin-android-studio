package com.xiangqin.app.model;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Event")
public class Event extends AVObject {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Event() {
    }

    public Event(Parcel in) {
        super(in);
    }

    public String getAreaTag() {
        return getString("areaTag");
    }

    public void setAreaTag(String areaTag) {
        put("areaTag", areaTag);
    }


    public String getImageUrl() {
        return getString("imageUrl");
    }

    public void setImageUrl(String imageUrl) {
        put("imageUrl", imageUrl);
    }

    public String getUrl() {
        return getString("url");
    }

    public void setUrl(String url) {
        put("url", url);
    }


}
