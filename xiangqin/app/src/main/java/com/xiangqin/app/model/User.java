package com.xiangqin.app.model;

import android.content.Context;
import android.text.TextUtils;

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
        int sex = getInt("sex");
        if (1 == sex) {
            return "男";
        } else if (sex == 2) {
            return "女";
        } else {
            return "未知";
        }
    }

    public int getSexInt() {
        return getInt("sex");
    }

    public void setSex(String sex) {
        if ("男".equals(sex)) {
            put("sex", 1);
        } else if ("女".equals(sex)) {
            put("sex", 2);
        } else {
            put("sex", 0);
        }
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

    public String getIcon() {
        final String icon = getString("icon");
        return TextUtils.isEmpty(icon) ? "http://sfault-avatar.b0.upaiyun.com/346/390/3463903554-1030000000193988_huge256" : icon;
    }

    public void setIcon(String icon) {
        put("icon", icon);
    }

}
