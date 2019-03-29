package com.xuejinwei.doubanbookmovie.kotlin.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.xuejinwei.doubanbookmovie.kotlin.model.Me;
import com.xuejinwei.doubanbookmovie.kotlin.model.OAuthResult;

import java.util.Set;

/**
 * Created by xuejinwei on 16/4/7.
 * Email:xuejinwei@outlook.com
 */
public class Setting {
    public static final  String APIKEY       = "0b2933ef8c6011b5217b9bcea520351f";
    public static final  String SERCET       = "317feedcc3289fab";
    public static final  String REDIRECT_URL = "http://baidu.com";
    private static final String sName        = "settings";

    public enum Key {
        auth_success,//是否登录成功
        access_token,
        expires_in,//期限
        refresh_token,
        douban_user_id,
        douban_user_name,
        loc_name,//常居
        large_avatar,//大头像URL
        signature,//签名
        desc
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

    public static boolean getAuthState() {
        return Setting.getSetting(Key.auth_success, false);
    }

    /**
     * 登录成功时候写入sp操作
     *
     * @param oAuthResult 登录成功的返回实体
     */
    public static void Login(OAuthResult oAuthResult) {
        Setting.putSetting(Key.auth_success, true);
        Setting.putSetting(Setting.Key.access_token, "Bearer " + oAuthResult.access_token);
        Setting.putSetting(Setting.Key.expires_in, oAuthResult.expires_in);
        Setting.putSetting(Setting.Key.refresh_token, oAuthResult.refresh_token);
        Setting.putSetting(Setting.Key.douban_user_id, oAuthResult.douban_user_id);
        Setting.putSetting(Setting.Key.douban_user_name, oAuthResult.douban_user_name);
    }

    /**
     * 退出登录sp初始化
     */
    public static void Logout() {
        Setting.putSetting(Key.auth_success, false);
        Setting.putSetting(Setting.Key.access_token, "");
        Setting.putSetting(Setting.Key.expires_in, "");
        Setting.putSetting(Setting.Key.refresh_token, "");
        Setting.putSetting(Setting.Key.douban_user_id, "");
        Setting.putSetting(Setting.Key.douban_user_name, "");
    }

    /**
     * 写入用户信息到sp，居住的，大头像，签名，描述
     * 注：id和网名在{@link #Login}中已经写入
     */
    public static void putMeInformation(Me me) {
        Setting.putSetting(Key.loc_name, me.loc_name);
        Setting.putSetting(Key.large_avatar, me.large_avatar);
        Setting.putSetting(Key.signature, me.signature);
        Setting.putSetting(Key.desc, me.desc);

    }
}
