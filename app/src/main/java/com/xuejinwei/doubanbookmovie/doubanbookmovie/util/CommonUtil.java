package com.xuejinwei.doubanbookmovie.doubanbookmovie.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.App;

import net.qiujuer.genius.blur.StackBlur;

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

    public static void blur(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) return;
        final String BLUR_CACHE = "blur_cache/blur";
        if (CacheHelper.isCachedBitmap(BLUR_CACHE + url.hashCode())) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(CacheHelper.readBitmap(BLUR_CACHE + url.hashCode()));
            return;
        }
        BitmapTypeRequest<String> request = Glide.with(imageView.getContext()).load(url).asBitmap();
        BitmapRequestBuilder<String, Bitmap> builder = request.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        builder.into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);
                    Bitmap blur = StackBlur.blur(scaledBitmap, 8, true);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(blur);
                    CacheHelper.saveBitmap(blur, BLUR_CACHE + url.hashCode());
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e("BlurCache",String.format("StackBlur.blur => %s", e.getMessage()));
                }
            }
        });
    }
}
