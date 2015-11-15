package com.xiangqin.app.model;

import android.content.Context;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVUser;
import com.xiangqin.app.utils.PreferencesUtils;

@AVClassName("User")
public class User extends AVUser {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public static User getUser(Context context) {
        final String obj = PreferencesUtils.getString(context, "user");
        try {
            return (User) AVUser.parseAVObject(obj);
        } catch (Exception e) {
        }
        return null;
    }

    public static void putUser(Context context, User user) {
        PreferencesUtils.putString(context, "user", user.toString());
    }

    // {"性别:sex", "生日:birthday", "身高:height", "学历:education", "婚姻状况:state"}
    //{"工作地区:area", "月收入:earning", "昵称:nickname"}

    public String getSex() {
        return getString("sex");
    }

    public void setSex(String sex) {
        put("sex", sex);
    }

    public String getBirthday() {
        return getString("birthday");
    }

    public void setBirthday(String birthday) {
        put("birthday", birthday);
    }

    public String getHeight() {
        return getString("height");
    }

    public void setHeight(String height) {
        put("height", height);
    }

    public String getEducation() {
        return getString("education");
    }

    public void setEducation(String education) {
        put("education", education);
    }

    public String getState() {
        return getString("state");
    }

    public void setState(String state) {
        put("state", state);
    }

    public String getArea() {
        return getString("area");
    }

    public void setArea(String area) {
        put("area", area);
    }

    public String getEarning() {
        return getString("earning");
    }

    public void setEarning(String earning) {
        put("earning", earning);
    }

    public String getNickname() {

        return getString("nickname");
    }

    public void setNickname(String nickname) {
        put("nickname", nickname);
    }
}