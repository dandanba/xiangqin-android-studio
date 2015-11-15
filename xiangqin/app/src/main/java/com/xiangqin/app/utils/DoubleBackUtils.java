package com.xiangqin.app.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;

public class DoubleBackUtils {
	private static Boolean mStartTimer = false;

	public static boolean checkDoubleClick(Activity activity) {
		if (!mStartTimer) {
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					mStartTimer = false;
				}
			}, 2000);
			mStartTimer = true;
		} else {
			mStartTimer = false;
		}
		return mStartTimer;
	}
}
