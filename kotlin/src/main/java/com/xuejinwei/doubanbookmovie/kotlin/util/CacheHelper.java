package com.xuejinwei.doubanbookmovie.kotlin.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xuejinwei.doubanbookmovie.kotlin.app.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xuejinwei on 16/4/30.
 * Email:xuejinwei@outlook.com
 */
public class CacheHelper {
    public static boolean isCachedBitmap(String name) {
        return new File(App.getApp().getCacheDir(), name).exists();
    }

    public static Bitmap readBitmap(String name) {
        return BitmapFactory.decodeFile(new File(App.getApp().getCacheDir(), name).getAbsolutePath());
    }

    public static void saveBitmap(Bitmap bitmap, String name) {
        FileOutputStream out = null;
        try {
            File file = new File(App.getApp().getCacheDir(), name);
            if (!file.getParentFile().exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
