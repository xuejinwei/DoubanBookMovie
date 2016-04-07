package com.xuejinwei.doubanbookmovie.doubanbookmovie.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by xuejinwei on 16/4/7.
 * Email:xuejinwei@outlook.com
 */
public class Setting {
    public static final String APIKEY       = "0b2933ef8c6011b5217b9bcea520351f";
    public static final String SERCET       = "317feedcc3289fab";
    public static final String REDIRECT_URL = "http://baidu.com";
    private static final String sName = "settings";

    public enum Key {
        access_token,
        expires_in,//期限
        refresh_token,
        douban_user_id,
        douban_user_name
    }

    public static void putSetting(Key key, boolean value) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(String.valueOf(key.ordinal()), value);
        editor.apply();
    }

    public static void putSetting(Key key, int value) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(String.valueOf(key.ordinal()), value);
        editor.apply();
    }

    public static void putSetting(Key key, long value) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(String.valueOf(key.ordinal()), value);
        editor.apply();
    }

    public static void putSetting(Key key, float value) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(String.valueOf(key.ordinal()), value);
        editor.apply();
    }

    public static void putSetting(Key key, String value) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(String.valueOf(key.ordinal()), value);
        editor.apply();
    }

    public static void putSetting(Key key, Set<String> value) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(String.valueOf(key.ordinal()), value);
        editor.apply();
    }

    public static boolean getSetting(Key key, boolean defaultValue) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        return preferences.getBoolean(String.valueOf(key.ordinal()), defaultValue);
    }

    public static int getSetting(Key key, int defaultValue) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        return preferences.getInt(String.valueOf(key.ordinal()), defaultValue);
    }

    public static long getSetting(Key key, long defaultValue) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        return preferences.getLong(String.valueOf(key.ordinal()), defaultValue);
    }

    public static float getSetting(Key key, float defaultValue) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        return preferences.getFloat(String.valueOf(key.ordinal()), defaultValue);
    }

    public static String getSetting(Key key, String defaultValue) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        return preferences.getString(String.valueOf(key.ordinal()), defaultValue);
    }

    public static Set<String> getSetting(Key key, Set<String> defaultValue) {
        SharedPreferences preferences = App.getApp().getSharedPreferences(sName, Context.MODE_PRIVATE);
        return preferences.getStringSet(String.valueOf(key.ordinal()), defaultValue);
    }
}
