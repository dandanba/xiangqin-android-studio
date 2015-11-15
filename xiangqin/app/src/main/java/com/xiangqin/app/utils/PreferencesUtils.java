package com.xiangqin.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
	public static String DEFAULT_NAME = "settings";

	// ----------------------------- simple -----------------------------
	public static void remove(Context context, String key) {
		remove(context, DEFAULT_NAME, key);
	}

	public static void clear(Context context) {
		clear(context, DEFAULT_NAME);
	}

	public static boolean putString(Context context, String key, String value) {
		return putString(context, DEFAULT_NAME, key, value);
	}

	public static boolean putInt(Context context, String key, int value) {
		return putInt(context, DEFAULT_NAME, key, value);
	}

	public static boolean putLong(Context context, String key, long value) {
		return putLong(context, DEFAULT_NAME, key, value);
	}

	public static boolean putFloat(Context context, String key, float value) {
		return putFloat(context, DEFAULT_NAME, key, value);
	}

	public static boolean putBoolean(Context context, String key, boolean value) {
		return putBoolean(context, DEFAULT_NAME, key, value);
	}

	public static String getString(Context context, String key) {
		return getString(context, key, "");
	}

	public static String getString(Context context, String key, String vlaue) {
		return getString(context, DEFAULT_NAME, key, vlaue);
	}

	public static int getInt(Context context, String key) {
		return getInt(context, key, -1);
	}

	public static int getInt(Context context, String key, int value) {
		return getInt(context, DEFAULT_NAME, key, value);
	}

	public static long getLong(Context context, String key) {
		return getLong(context, DEFAULT_NAME, key, -1);
	}

	public static float getFloat(Context context, String key) {
		return getFloat(context, DEFAULT_NAME, key, -1);
	}

	public static boolean getBoolean(Context context, String key) {
		return getBoolean(context, DEFAULT_NAME, key, false);
	}

	// ----------------------------- base -----------------------------
	public static void remove(Context context, String name, String key) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}

	public static void clear(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}

	public static boolean putString(Context context, String name, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	public static boolean putInt(Context context, String name, String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	public static boolean putLong(Context context, String name, String key, long value) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static boolean putFloat(Context context, String name, String key, float value) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	public static boolean putBoolean(Context context, String name, String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public static String getString(Context context, String name, String key, String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	public static int getInt(Context context, String name, String key, int defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static long getLong(Context context, String name, String key, long defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getLong(key, defaultValue);
	}

	public static float getFloat(Context context, String name, String key, float defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getFloat(key, defaultValue);
	}

	public static boolean getBoolean(Context context, String name, String key, boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}
}
