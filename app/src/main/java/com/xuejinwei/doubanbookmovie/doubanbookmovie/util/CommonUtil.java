package com.xuejinwei.doubanbookmovie.doubanbookmovie.util;

import android.widget.Toast;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.App;

/**
 * Created by xuejinwei on 16/3/26.
 * Email:xuejinwei@outlook.com
 */
public class CommonUtil {
    /**
     * TOAST Application
     */
    public static void toast(String message) {
        Toast.makeText(App.getApp(), message, Toast.LENGTH_SHORT).show();
    }
}
