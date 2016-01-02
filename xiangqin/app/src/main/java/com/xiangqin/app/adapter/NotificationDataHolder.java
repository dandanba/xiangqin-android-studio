package com.xiangqin.app.adapter;

import com.xiangqin.app.model.Notification;

/**
 * Created by dandanba on 11/16/15.
 */
public class NotificationDataHolder extends BaseDataHolder {
    public NotificationDataHolder(int type) {
        super(type);
    }

    private Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}