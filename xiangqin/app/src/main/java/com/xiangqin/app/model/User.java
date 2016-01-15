package com.xiangqin.app.model;

import android.text.TextUtils;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.leancloud.im.guide.Constants;

@AVClassName("User")
public class User extends AVUser {
    public static final Creator CREATOR = AVObjectCreator.instance;
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
        return getString("icon");
    }

    public String getIconUrl() {
        final String icon = getString("icon");
        if (TextUtils.isEmpty(icon)) {
            if (1 == getInt("sex")) {
                return "http://ac-svu6vore.clouddn.com/ffe9afbf04793258.jpg";
            } else {
                return com.xiangqin.app.Constants.DEFAULT_USER_HEADER;
            }
        } else {
            return icon;
        }
    }

    public void setIcon(String icon) {
        put("icon", icon);
    }

    public double getLongitude() {
        return getDouble("longitude");
    }

    public void setLongitude(double longitude) {
        put("longitude", longitude);
    }

    public double getLatitude() {
        return getDouble("latitude");
    }

    public void setLatitude(double latitude) {
        put("latitude", latitude);
    }

    public void setInstallationId(String installationId) {
        put("installationId", installationId);
    }

    public String getInstallationId() {
        return getString("installationId");
    }

    public void sayHello(User sender) {
        AVPush push = new AVPush();
        push.setChannel("public");
        push.setMessage(sender.getNickname() + "向您打招呼了。");
        AVQuery<AVInstallation> query = AVInstallation.getQuery();
        query.whereEqualTo("installationId", getInstallationId());
        push.setQuery(query);
        push.setPushToAndroid(true);
        push.sendInBackground();
    }
}
