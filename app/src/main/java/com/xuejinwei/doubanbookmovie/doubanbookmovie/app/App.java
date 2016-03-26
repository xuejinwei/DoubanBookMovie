package com.xuejinwei.doubanbookmovie.doubanbookmovie.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * 自己的application
 * Created by xuejinwei on 16/3/12.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        Logger.init("Api_json");
    }
    /**
     * 获得 Application 实例
     */
    public static App getApp() {
        if (instance == null) {
            return new App();
        }
        return instance;
    }
}
