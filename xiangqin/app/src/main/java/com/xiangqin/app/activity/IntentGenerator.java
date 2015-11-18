package com.xiangqin.app.activity;

import android.content.Context;
import android.content.Intent;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by dandanba on 11/13/15.
 */
public class IntentGenerator {

    public static Intent genSimpleActivityIntent(Context context, Class<?> clazz) {
        return new Intent(context, clazz);
    }

    public static Intent showSelector(Context mBaseActivity, int selectCount) {
        Intent intent = new Intent(mBaseActivity, MultiImageSelectorActivity.class);
        // whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, selectCount);
        // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        return intent;
    }
}
