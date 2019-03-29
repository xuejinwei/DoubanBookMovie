package com.xuejinwei.doubanbookmovie.kotlin.widget;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
public class DialogUtil {

    public static void simpleMessage(Activity activity, String message, OnConfirm onConfirm) {
        simpleMessage(activity, message, onConfirm, null);
    }
    public static void simpleMessage(Activity activity, String message, OnConfirm onConfirm, OnCancel onCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setPositiveButton("确定", (dialog, which) -> {
            if (onConfirm != null) onConfirm.onConfirm();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            if (onCancel != null) onCancel.cancel();
        });
        builder.show();
    }

    /**
     * 确定回调接口
     */
    public interface OnConfirm {
        void onConfirm();
    }

    /**
     * 取消回调接口
     */
    public interface OnCancel {
        void cancel();
    }
}
