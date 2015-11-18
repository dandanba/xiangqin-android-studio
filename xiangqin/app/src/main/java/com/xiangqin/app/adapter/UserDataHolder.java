package com.xiangqin.app.adapter;

import com.xiangqin.app.model.User;

/**
 * Created by dandanba on 11/16/15.
 */
public class UserDataHolder extends BaseDataHolder {
    public UserDataHolder(int type) {
        super(type);
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}