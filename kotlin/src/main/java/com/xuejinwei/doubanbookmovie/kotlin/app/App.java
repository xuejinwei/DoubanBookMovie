package com.xuejinwei.doubanbookmovie.kotlin.app;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.kotlin.model.MovieCollections;

/**
 * 自己的application
 * Created by xuejinwei on 16/3/12.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        Fresco.initialize(this);
        Logger.init("Api_json");

        // 初始化参数依次为 this, AppId, AppKey
        AVObject.registerSubclass(MovieCollections.class);
        AVOSCloud.initialize(this,"45zfVSyyntekSLHioGDBccz5-gzGzoHsz","OcBW6svtdveOO2Lr142iVixB");
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
