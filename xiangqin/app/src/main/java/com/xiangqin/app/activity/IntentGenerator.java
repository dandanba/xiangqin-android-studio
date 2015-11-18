package com.xiangqin.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.smssdk.gui.layout.TitleLayout;

/**
 * Created by dandanba on 11/13/15.
 */
public class IntentGenerator {

    public static Intent genSimpleActivityIntent(Context context, Class<?> clazz) {
        return new Intent(context, clazz);
    }
}
