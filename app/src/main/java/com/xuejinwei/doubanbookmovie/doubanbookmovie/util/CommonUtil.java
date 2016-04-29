package com.xuejinwei.doubanbookmovie.doubanbookmovie.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @SuppressWarnings("unused") public static void showKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }
}
