package com.xiangqin.app.adapter;

/**
 * Created by dandanba on 11/16/15.
 */
public class MyDataHolder extends BaseDataHolder {
    public MyDataHolder(int type) {
        super(type);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String title;
    private String text;

}